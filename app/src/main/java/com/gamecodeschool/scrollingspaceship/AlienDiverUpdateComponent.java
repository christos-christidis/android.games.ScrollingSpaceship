package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

import java.util.Random;

class AlienDiverUpdateComponent extends UpdateComponent {

    private final Random mRandom = new Random();

    AlienDiverUpdateComponent(GameObjectData data) {
        super(data);
    }

    @Override
    boolean update(long fps, GameObjectData playerData) {
        GameObjectData alienData = getGameObjectData();
        PointF location = alienData.getLocation();

        float verticalSpeed = alienData.getSpeed();
        float horizontalSpeed = verticalSpeed * 1.8f; // arrived at by trial & error

        if (playerData.facingRight()) {
            location.x -= horizontalSpeed / fps;
        } else {
            location.x += horizontalSpeed / fps;
        }

        // Fall down, then respawn somewhere above
        location.y += verticalSpeed / fps;
        if (location.y > GameEngine.screenHeight) {
            location.x = mRandom.nextInt(GameEngine.screenWidth);
            location.y = mRandom.nextInt(300) - alienData.getObjectHeight();
        }

        alienData.updateCollider();

        return true;
    }
}
