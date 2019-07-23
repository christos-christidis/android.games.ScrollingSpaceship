package com.gamecodeschool.scrollingspaceship;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Level {

    private static final int[] imageResIds = {
            R.drawable.tatooine, R.drawable.hoth, R.drawable.alderaan, R.drawable.coruscant,
            R.drawable.coruscant2, R.drawable.dagobah
    };

    static final int BACKGROUND_INDEX = 0;
    static final int PLAYER_INDEX = 1;
    static final int FIRST_PLAYER_LASER = 2;
    static final int LAST_PLAYER_LASER = 4;
    static final int FIRST_ALIEN_LASER = 5;
    static final int LAST_ALIEN_LASER = 9;
    static final int FIRST_ALIEN = 10;
    private static final int SECOND_ALIEN = 11;
    private static final int THIRD_ALIEN = 12;
    // I can add more aliens here...
    static final int LAST_ALIEN = 12;

    static int nextPlayerLaser;
    static int nextAlienLaser;

    private final List<GameObject> objects;

    Level(Context context, GameEngine gameEngine) {
        objects = new ArrayList<>();

        GameObjectFactory factory = new GameObjectFactory(context, gameEngine);
        buildInitialGameObjects(factory);
    }

    private void buildInitialGameObjects(GameObjectFactory factory) {
        Random random = new Random();
        int imageResId = imageResIds[random.nextInt(imageResIds.length)];

        objects.clear();
        objects.add(BACKGROUND_INDEX, factory.create(new BackgroundSpec(imageResId)));
        objects.add(PLAYER_INDEX, factory.create(new PlayerSpec()));

        // create player lasers
        for (int i = FIRST_PLAYER_LASER; i <= LAST_PLAYER_LASER; i++) {
            objects.add(i, factory.create(new PlayerLaserSpec()));
        }
        nextPlayerLaser = FIRST_PLAYER_LASER;

        // create some alien lasers
        for (int i = FIRST_ALIEN_LASER; i <= LAST_ALIEN_LASER; i++) {
            objects.add(i, factory.create(new AlienLaserSpec()));
        }
        nextAlienLaser = FIRST_ALIEN_LASER;

        // create some aliens
        objects.add(FIRST_ALIEN, factory.create(new AlienChaserSpec()));
        objects.add(SECOND_ALIEN, factory.create(new AlienPatrollerSpec()));
        objects.add(THIRD_ALIEN, factory.create(new AlienDiverSpec()));
    }

    List<GameObject> getGameObjects() {
        return objects;
    }
}
