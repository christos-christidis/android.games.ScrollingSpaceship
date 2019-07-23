package com.gamecodeschool.scrollingspaceship;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ViewConstructor")
class GameEngine extends SurfaceView implements Runnable, InputBroadcaster,
        PlayerLaserSpawner, AlienLaserSpawner, ObjectRespawner {

    private Thread mThread = null;
    private long mFPS;

    private final GameState mGameState;
    private final SoundEngine mSoundEngine;
    private final HUD mHUD;
    private final Renderer mRenderer;
    private final ParticleSystem mParticleSystem;
    private final PhysicsEngine mPhysicsEngine;
    private final Level mLevel;

    // These are needed pretty much everywhere so I made them static
    static int screenWidth;
    static int screenHeight;

    private final List<InputObserver> mInputObservers = new ArrayList<>();

    GameEngine(Context context, Point screenSize) {
        super(context);

        GameEngine.screenWidth = screenSize.x;
        GameEngine.screenHeight = screenSize.y;

        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);
        mHUD = new HUD(context);
        mRenderer = new Renderer(this);
        mParticleSystem = new ParticleSystem(500);
        mPhysicsEngine = new PhysicsEngine();

        // this also creates the initial game objects
        mLevel = new Level(context, this);

        // NOTE: this isn't gc'd because see GameInputObserver's constructor.
        new GameInputObserver(this);
    }

    // This is the game loop running in a separate thread. mGameState is checked to see if game loop
    // should be running or not
    @Override
    public void run() {
        while (mGameState.gameLoopShouldRun()) {
            long frameStartTime = System.currentTimeMillis();

            List<GameObject> objects = mLevel.getGameObjects();

            if (!mGameState.paused()) {
                boolean playerHit = mPhysicsEngine.update(mFPS, objects, mParticleSystem, mSoundEngine, mGameState);
                if (playerHit) {
                    respawnAllObjects();
                }
            }

            mRenderer.draw(objects, mParticleSystem, mHUD, mGameState);

            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame > 0) {
                final int MILLIS_IN_SECOND = 1000;
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }

    void startGameLoop() {
        mGameState.setGameLoopShouldRun();
        mThread = new Thread(this);
        mThread.start();
    }

    void stopGameLoop() {
        mGameState.stopEverything();
        try {
            mThread.join();
        } catch (InterruptedException e) {
            Log.e("Exception", "stopGameLoop() " + e.getMessage());
        }
    }

    // We delegate the handling of touches to the inputObservers.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (InputObserver inputObserver : mInputObservers) {
            inputObserver.handleInput(event, mGameState, mHUD);
        }
        return true;
    }

    // Called every time we start a new game. It unspawns & then respawns everything.
    @Override
    public void respawnAllObjects() {
        List<GameObject> objects = mLevel.getGameObjects();

        for (GameObject object : objects) {
            object.unspawn();
        }

        // NOTE: In both cases, the data we pass to spawn is not actually used
        GameObject player = objects.get(Level.PLAYER_INDEX);
        player.spawn(player.getData());

        GameObject background = objects.get(Level.BACKGROUND_INDEX);
        background.spawn(background.getData());

        for (int i = Level.FIRST_ALIEN; i <= Level.LAST_ALIEN; i++) {
            GameObject alien = objects.get(i);
            alien.spawn(player.getData());
        }
    }

    @Override
    public void addInputObserver(InputObserver observer) {
        mInputObservers.add(observer);
    }

    // We have to spawn lasers through the GameEngine, because it alone has access to all the objects,
    // the sound engine etc. NOTE the fact that we always pass the shooter in spawn (which is player
    // for the 1st method and the alien for the 2nd)
    @Override
    public void spawnPlayerLaser(GameObjectData playerData) {
        List<GameObject> objects = mLevel.getGameObjects();
        GameObject nextPlayerLaser = objects.get(Level.nextPlayerLaser);

        // returns false if there's no available (ie unspawned) laser to spawn...
        if (nextPlayerLaser.spawn(playerData)) {
            Level.nextPlayerLaser++;
            mSoundEngine.playShoot();
            if (Level.nextPlayerLaser == Level.LAST_PLAYER_LASER + 1) {
                Level.nextPlayerLaser = Level.FIRST_PLAYER_LASER;
            }
        }
    }

    @Override
    public void spawnAlienLaser(GameObjectData alienData) {
        List<GameObject> objects = mLevel.getGameObjects();
        GameObject nextAlienLaser = objects.get(Level.nextAlienLaser);

        if (nextAlienLaser.spawn(alienData)) {
            Level.nextAlienLaser++;
            mSoundEngine.playShoot();
            if (Level.nextAlienLaser == Level.LAST_ALIEN_LASER + 1) {
                Level.nextAlienLaser = Level.FIRST_ALIEN_LASER;
            }
        }
    }
}
