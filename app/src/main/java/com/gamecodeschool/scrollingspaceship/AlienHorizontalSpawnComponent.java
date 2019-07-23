package com.gamecodeschool.scrollingspaceship;

import java.util.Random;

class AlienHorizontalSpawnComponent extends SpawnComponent {

    private final Random mRandom = new Random();

    AlienHorizontalSpawnComponent(GameObjectData data) {
        super(data);
    }

    @Override
    void spawn(GameObjectData playerData) {
        GameObjectData alienData = getGameObjectData();

        float shipHeight = alienData.getObjectHeight();

        float spawnHeight = GameEngine.screenHeight * mRandom.nextFloat() - shipHeight;

        // Come from either left or right off-the-screen
        int randomDistance = mRandom.nextInt(GameEngine.screenWidth);
        if (mRandom.nextBoolean()) {
            alienData.setLocation(-randomDistance, spawnHeight);
            alienData.headRight();
        } else {
            alienData.setLocation(GameEngine.screenWidth + randomDistance, spawnHeight);
            alienData.headLeft();
        }
    }
}
