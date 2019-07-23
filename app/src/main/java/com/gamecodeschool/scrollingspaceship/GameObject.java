package com.gamecodeschool.scrollingspaceship;

import android.graphics.Canvas;
import android.graphics.Paint;

class GameObject {

    private final String mTag;
    private final GameObjectData mData;

    // all objects are created once and can be spawned and unspawned many times.
    private boolean isSpawned = false;

    private GraphicsComponent mGraphicsComponent;
    private UpdateComponent mUpdateComponent;
    private SpawnComponent mSpawnComponent;

    GameObject(String tag, GameObjectData data) {
        mTag = tag;
        mData = data;
    }

    void setGraphicsComponent(GraphicsComponent graphicsComponent) {
        mGraphicsComponent = graphicsComponent;
    }

    void setUpdateComponent(UpdateComponent updateComponent) {
        mUpdateComponent = updateComponent;
    }

    void setSpawnComponent(SpawnComponent spawnComponent) {
        mSpawnComponent = spawnComponent;
    }

    void draw(Canvas canvas, Paint paint) {
        mGraphicsComponent.draw(canvas, paint);
    }

    void update(long fps, GameObjectData playerData) {
        // returns false if the object must be unspawned. This happens to lasers when they move off-
        // screen after they're updated.
        if (!mUpdateComponent.update(fps, playerData)) {
            isSpawned = false;
        }
    }

    boolean spawn(GameObjectData playerData) {
        if (!isSpawned) {
            mSpawnComponent.spawn(playerData);
            isSpawned = true;
            return true;
        }
        return false;
    }

    boolean isSpawned() {
        return isSpawned;
    }

    void unspawn() {
        isSpawned = false;
    }

    String getTag() {
        return mTag;
    }

    GameObjectData getData() {
        return mData;
    }
}
