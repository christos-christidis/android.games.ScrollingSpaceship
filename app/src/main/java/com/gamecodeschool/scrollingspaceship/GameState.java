package com.gamecodeschool.scrollingspaceship;

import android.content.Context;
import android.content.SharedPreferences;

// This class gathers all the game state that is needed all over the place!
final class GameState {

    // These are volatile because they're changed by the inputObservers which run on the main thread,
    // and they're read by the other thread (the game loop). TODO: read Java Concurrency book
    private static volatile boolean mGameLoopShouldRun = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawingObjectsIsAllowed = false;

    private final ObjectRespawner mObjectRespawner;

    private int mScore;
    private int mHighScore;
    private int mNumShips;

    private final SharedPreferences mPrefs;

    GameState(ObjectRespawner objectRespawner, Context context) {
        mObjectRespawner = objectRespawner;

        mPrefs = context.getSharedPreferences("HiScore", Context.MODE_PRIVATE);
        mHighScore = mPrefs.getInt("hi_score", 0);
    }

    void startNewGame() {
        mScore = 0;
        mNumShips = 3;

        // We don't want the game-loop drawing & updating objects that are being unspawned!
        mDrawingObjectsIsAllowed = false;
        mObjectRespawner.respawnAllObjects();
        resumeGame();
        mDrawingObjectsIsAllowed = true;
    }

    private void endGame() {
        mPaused = true;
        mGameOver = true;
        if (mScore > mHighScore) {
            mHighScore = mScore;

            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putInt("hi_score", mHighScore);
            editor.apply();
        }
    }

    void loseLife(SoundEngine soundEngine) {
        mNumShips--;
        soundEngine.playPlayerExplode();
        if (mNumShips == 0) {
            endGame();
        }
    }

    void resumeGame() {
        mPaused = false;
        mGameOver = false;
    }

    void pauseGame() {
        mPaused = true;
    }

    void stopEverything() {
        mPaused = true;
        mGameOver = true;
        mGameLoopShouldRun = false;
    }

    boolean paused() {
        return mPaused;
    }

    boolean gameOver() {
        return mGameOver;
    }

    boolean gameLoopShouldRun() {
        return mGameLoopShouldRun;
    }

    void setGameLoopShouldRun() {
        mGameLoopShouldRun = true;
    }

    boolean drawingObjectsIsAllowed() {
        return mDrawingObjectsIsAllowed;
    }

    void increaseScore() {
        mScore++;
    }

    int getScore() {
        return mScore;
    }

    int getNumShips() {
        return mNumShips;
    }

    int getHighScore() {
        return mHighScore;
    }
}
