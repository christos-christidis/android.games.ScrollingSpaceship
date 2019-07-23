package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

// The use of specs that we feed to a factory is the cleanest way to create different game objects.
// The GameObjectFactory will create a new GameObject based on the spec
class GameObjectSpec {

    enum Component {
        StdGraphics,
        BackgroundGraphics,
        LaserUpdate,
        AlienChaserUpdate,
        AlienDiverUpdate,
        AlienPatrollerUpdate,
        BackgroundUpdate,
        PlayerUpdate,
        LaserSpawn,
        AlienHorizontalSpawn,
        AlienVerticalSpawn,
        BackgroundSpawn,
        PlayerSpawn,
        PlayerInput,
    }

    // the tag is only solely for collision detection. See detectCollisions().
    private final String mTag;
    private final int mResourceId;

    // These fields make the object's speed and size relative to the screenWidth.
    private final float mSpeedDivisor;  // speed will be screenWidth / mSpeedDivisor (in pixels/sec)
    private final PointF mSizeScale;    // width will be screenWidth / mSizeScale.x (in pixels)

    private final Component[] mComponents;

    GameObjectSpec(String tag, int resourceId, float speedDivisor, PointF sizeScale, Component[] components) {
        mTag = tag;
        mResourceId = resourceId;
        mSpeedDivisor = speedDivisor;
        mSizeScale = sizeScale;
        mComponents = components;
    }

    String getTag() {
        return mTag;
    }

    int getResourceId() {
        return mResourceId;
    }

    float getSpeedDivisor() {
        return mSpeedDivisor;
    }

    PointF getSizeScale() {
        return mSizeScale;
    }

    Component[] getComponents() {
        return mComponents;
    }
}
