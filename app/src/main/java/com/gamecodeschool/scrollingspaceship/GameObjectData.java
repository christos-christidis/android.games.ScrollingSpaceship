package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;
import android.graphics.RectF;

// Almost all components need access to just a subset of the object's data. That subset is included in
// GameObjectData. Note that some components also use the player's GameObjectData, besides their own,
// eg an alien's move() depends on where the player is.
class GameObjectData {

    private final RectF mCollider;
    private final PointF mLocation;
    private final float mSpeed;
    private final PointF mObjectSize;

    private boolean mFacingRight = true;
    private boolean mHeadingUp = false;
    private boolean mHeadingDown = false;
    private boolean mHeadingLeft = false;
    private boolean mHeadingRight = false;

    // these 2 are used only by the background
    private int mXClip = 0;
    private boolean mReversedBackgroundFirst = false;

    GameObjectData(PointF startingLocation, float speed, PointF objectSize) {
        mCollider = new RectF();
        mLocation = new PointF(startingLocation.x, startingLocation.y);
        mSpeed = speed;
        mObjectSize = objectSize;
    }

    void updateCollider() {
        // Pull the borders in a bit (10%). Don't know why he does this.
        mCollider.left = mLocation.x + getObjectWidth() / 10;
        mCollider.top = mLocation.y + getObjectHeight() / 10;
        mCollider.right = mLocation.x + getObjectWidth();
        mCollider.bottom = mLocation.y + getObjectHeight();
    }

    RectF getCollider() {
        return mCollider;
    }

    PointF getLocation() {
        return mLocation;
    }

    float getSpeed() {
        return mSpeed;
    }

    float getObjectWidth() {
        return mObjectSize.x;
    }

    float getObjectHeight() {
        return mObjectSize.y;
    }

    void headUp() {
        mHeadingUp = true;
        mHeadingDown = false;
    }

    void headDown() {
        mHeadingDown = true;
        mHeadingUp = false;
    }

    void headLeft() {
        mHeadingLeft = true;
        mHeadingRight = false;
        mFacingRight = false;
    }

    void headRight() {
        mHeadingRight = true;
        mHeadingLeft = false;
        mFacingRight = true;
    }

    boolean headingUp() {
        return mHeadingUp;
    }

    boolean headingDown() {
        return mHeadingDown;
    }

    boolean headingLeft() {
        return mHeadingLeft;
    }

    boolean headingRight() {
        return mHeadingRight;
    }

    boolean facingRight() {
        return mFacingRight;
    }

    void stopVertical() {
        mHeadingDown = false;
        mHeadingUp = false;
    }

    void flip() {
        mFacingRight = !mFacingRight;
    }

    void setLocation(float x, float y) {
        mLocation.set(x, y);
        updateCollider();
    }

    void setXClip(int newXClip) {
        mXClip = newXClip;
    }

    int getXClip() {
        return mXClip;
    }

    boolean reversedBackgroundFirst() {
        return mReversedBackgroundFirst;
    }

    void flipReversedBackgroundFirst() {
        mReversedBackgroundFirst = !mReversedBackgroundFirst;
    }

    // Obviously this is only used by player and alien objects
    PointF getFiringLocation(float laserLength) {
        PointF firingLocation = new PointF();

        firingLocation.x = mLocation.x + getObjectWidth() / 8;

        // hm, he wants to hide entire laser under shooter!
        if (!mFacingRight) {
            firingLocation.x -= laserLength;
        }

        firingLocation.y = mLocation.y + getObjectHeight() * 0.8f;

        return firingLocation;
    }
}
