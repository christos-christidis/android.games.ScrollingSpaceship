package com.gamecodeschool.scrollingspaceship;

import java.util.Random;

class AlienVerticalSpawnComponent extends SpawnComponent {

    private final Random mRandom = new Random();

    AlienVerticalSpawnComponent(GameObjectData data) {
        super(data);
    }

    @Override
    void spawn(GameObjectData playerData) {
        GameObjectData alienData = getGameObjectData();

        float shipHeight = alienData.getObjectHeight();

        float xPosition = mRandom.nextInt(GameEngine.screenWidth);
        float spawnHeight = -shipHeight - mRandom.nextInt(300);

        alienData.setLocation(xPosition, spawnHeight);
        alienData.headDown();
    }
}
