package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

import java.util.Random;

// TODO: must check the update function here. Not sure alien behaves as I want
class AlienPatrollerUpdateComponent extends UpdateComponent {

    private final AlienLaserSpawner mALS;
    private final Random mRandom = new Random();

    AlienPatrollerUpdateComponent(GameObjectData data, AlienLaserSpawner als) {
        super(data);
        mALS = als;
    }

    private class Range {
        final float minimum;
        final float maximum;

        Range(float minimum, float maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }
    }

    @Override
    boolean update(long fps, GameObjectData playerData) {
        GameObjectData alienData = getGameObjectData();

        PointF playerLocation = playerData.getLocation();

        int screenWidth = GameEngine.screenWidth;
        int screenHeight = GameEngine.screenHeight;

        PointF location = alienData.getLocation();
        float speed = alienData.getSpeed();
        float alienHeight = alienData.getObjectHeight();

        Range horizontalRange = new Range(-screenWidth, screenWidth * 2);
        Range verticalRange = new Range(0, screenHeight - alienHeight);

        // NOTE: this horizontal adjustment is either added to the speed (when alien and player face
        // each other or subtracted in the opposite case!
        float horizontalSpeedAdjustment = 0;

        // Essentially this reduces
        float seeingDistance = screenWidth / 2f;
        if (Math.abs(location.x - playerLocation.x) < seeingDistance) {
            if (playerData.facingRight() != alienData.facingRight()) {
                horizontalSpeedAdjustment = speed * 0.8f;
            } else {
                horizontalSpeedAdjustment = -speed * 0.8f;
            }
        }

        if (alienData.headingLeft()) {
            location.x -= (speed + horizontalSpeedAdjustment) / fps;

            if (location.x < horizontalRange.minimum) {
                location.x = horizontalRange.minimum;
                alienData.headRight();
            }
        } else {
            location.x += (speed + horizontalSpeedAdjustment) / fps;

            if (location.x > horizontalRange.maximum) {
                location.x = horizontalRange.maximum;
                alienData.headLeft();
            }
        }

        if (alienData.headingDown()) {
            location.y += speed / fps;
            if (location.y > verticalRange.maximum) {
                alienData.headUp();
            }
        } else {
            location.y -= speed / fps;
            if (location.y < verticalRange.minimum) {
                alienData.headDown();
            }
        }

        alienData.updateCollider();

        // 1 in 100 chance. It might seem low, but it's not, since we calculate this for EVERY frame!
        // The main restriction to rate is availability of lasers.
        if (PositionCalculator.inPositionForShot(alienData, playerLocation)) {
            final int SHOT_CHANCE = 100;
            if (mRandom.nextInt(SHOT_CHANCE) == 0) {
                mALS.spawnAlienLaser(alienData);
            }
        }

        return true;
    }
}
