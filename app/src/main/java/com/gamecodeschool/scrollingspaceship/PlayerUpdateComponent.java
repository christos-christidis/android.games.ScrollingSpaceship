package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

class PlayerUpdateComponent extends UpdateComponent {

    PlayerUpdateComponent(GameObjectData objectData) {
        super(objectData);
    }

    // This component can get the playerData w getData(). But we choose to use the parameter
    // instead so that we don't get a warning
    @Override
    public boolean update(long fps, GameObjectData playerData) {

        PointF location = playerData.getLocation();
        float speed = playerData.getSpeed();
        float playerHeight = playerData.getObjectHeight();

        final float VERTICAL_SLOWDOWN = 0.5f; // otherwise it moves up/down too fast
        if (playerData.headingDown()) {
            location.y += VERTICAL_SLOWDOWN * speed / fps;
        } else if (playerData.headingUp()) {
            location.y -= VERTICAL_SLOWDOWN * speed / fps;
        }

        // Limit ship between screen top and bottom
        int screenHeight = GameEngine.screenHeight;
        if (location.y > screenHeight - playerHeight) {
            location.y = screenHeight - playerHeight;
        } else if (location.y < 0) {
            location.y = 0;
        }

        playerData.updateCollider();

        return true;
    }
}
