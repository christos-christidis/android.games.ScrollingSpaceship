package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

class LaserUpdateComponent extends UpdateComponent {

    LaserUpdateComponent(GameObjectData objectData) {
        super(objectData);
    }

    @Override
    public boolean update(long fps, GameObjectData playerData) {
        GameObjectData laserData = getGameObjectData();

        PointF location = laserData.getLocation();
        float speed = laserData.getSpeed();

        if (laserData.headingRight()) {
            location.x += speed / fps;
        } else if (laserData.headingLeft()) {
            location.x -= speed / fps;
        }

        if (location.x < -GameEngine.screenWidth || location.x > GameEngine.screenWidth * 2) {
            // If laser goes off-screen a lot, we unspawn it.
            return false;
        }

        laserData.updateCollider();

        return true;
    }
}
