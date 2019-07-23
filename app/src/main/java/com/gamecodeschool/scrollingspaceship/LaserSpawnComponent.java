package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

// NOTE: this component is used by the player AND the aliens
class LaserSpawnComponent extends SpawnComponent {

    LaserSpawnComponent(GameObjectData objectData) {
        super(objectData);
    }

    // NOTE: shooterData is either the player or the alien that fired this laser
    @Override
    public void spawn(GameObjectData shooterData) {
        GameObjectData laserData = getGameObjectData();
        float laserWidth = laserData.getObjectWidth();

        PointF startPosition = shooterData.getFiringLocation(laserWidth);

        // NOTE: I think he casts to draw on exact pixel, not in-between (aliasing issues...)
        laserData.setLocation((int) startPosition.x, (int) startPosition.y);

        if (shooterData.facingRight()) {
            laserData.headRight();
        } else {
            laserData.headLeft();
        }
    }
}
