package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

import java.util.Random;

// TODO: I don't know wtf is going on here. Despite my other TODO's the alien behaves correctly in-game
class AlienChaserUpdateComponent extends UpdateComponent {

    private final Random mRandom = new Random();

    // this class can tell the GameEngine to spawn a laser through this interface
    private final AlienLaserSpawner mALS;

    AlienChaserUpdateComponent(GameObjectData objectData, AlienLaserSpawner als) {
        super(objectData);
        mALS = als;
    }

    @Override
    boolean update(long fps, GameObjectData playerData) {
        GameObjectData alienData = getGameObjectData();

        PointF location = alienData.getLocation();
        float speed = alienData.getSpeed();

        PointF playerLocation = playerData.getLocation();

        final float CHASING_DISTANCE = GameEngine.screenWidth * 0.33f;
        final float FIRING_DISTANCE = GameEngine.screenWidth * 0.66f;

        // if distance becomes bigger than chasingDistance, turn towards player
        if (Math.abs(location.x - playerLocation.x) > CHASING_DISTANCE) {
            horizontalTurnTowardsPlayer(location, playerLocation);
        }

        if (Math.abs(location.x - playerLocation.x) < FIRING_DISTANCE) {
            // if above/below player > 20 pixels, head down/up until within 20 pixels
            final int VERTICAL_RANGE = 20;
            if (location.y - playerLocation.y > VERTICAL_RANGE) {
                alienData.headUp();
            } else if (location.y - playerLocation.y < -VERTICAL_RANGE) {
                alienData.headDown();
            }

            // TODO: he says compensate for movement relative to player. Otherwise alien will disappear
            // miles off the screen. WHAT?
            final float SLOWDOWN_RELATIVE_TO_PLAYER = 1.8f;
            if (playerData.facingRight()) {
                location.x -= SLOWDOWN_RELATIVE_TO_PLAYER * speed / fps;
            } else {
                location.x += SLOWDOWN_RELATIVE_TO_PLAYER * speed / fps;
            }
        } else {
            // if not within FIRING_DISTANCE, stop vertical speed, otherwise alien will go off screen.
            alienData.stopVertical();
        }

        // Moving vertically is slower than horizontal. Change this to make game harder.
        final float VERTICAL_SLOWDOWN = 0.3f;
        if (alienData.headingDown()) {
            location.y += VERTICAL_SLOWDOWN * speed / fps;
        } else {
            location.y -= VERTICAL_SLOWDOWN * speed / fps;
        }

        // TODO: I'm 99% sure this should not be here (maybe in the else branch of FIRING_DISTANCE?)
        // otherwise it may ADD this displacement to the update of x above, no?
        if (alienData.headingLeft()) {
            location.x -= speed / fps;
        } else {
            location.x += speed / fps;
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

    private void horizontalTurnTowardsPlayer(PointF location, PointF playerLocation) {
        GameObjectData alienData = getGameObjectData();

        if (location.x < playerLocation.x) {
            alienData.headRight();
        } else if (location.x > playerLocation.x) {
            alienData.headLeft();
        }
    }
}
