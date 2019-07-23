package com.gamecodeschool.scrollingspaceship;

// we turned this interface into a class because ALL update components have to store their object's
// basic data (we do that in the constructor)
abstract class UpdateComponent {

    private final GameObjectData mData;

    UpdateComponent(GameObjectData data) {
        mData = data;
    }

    // We also need playerData, eg an alien moves according to where the player is
    abstract boolean update(long fps, GameObjectData playerData);

    GameObjectData getGameObjectData() {
        return mData;
    }
}
