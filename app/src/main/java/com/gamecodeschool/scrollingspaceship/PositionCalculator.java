package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

// This class's purpose is just to avoid code duplication! 2 alien classes use the same function
class PositionCalculator {
    static boolean inPositionForShot(GameObjectData shooterData, PointF targetLocation) {
        PointF location = shooterData.getLocation();
        boolean facingRight = shooterData.facingRight();

        boolean facingTarget = facingRight && targetLocation.x > location.x ||
                !facingRight && targetLocation.x < location.x;
        boolean withinOneShipHeight = Math.abs(location.y - targetLocation.y) < shooterData.getObjectHeight();
        boolean shooterOnScreen = location.x >= 0 && location.x <= GameEngine.screenWidth - shooterData.getObjectWidth();

        return facingTarget && withinOneShipHeight && shooterOnScreen;
    }
}
