package com.gamecodeschool.scrollingspaceship;

// SOS: this class confused the hell out of me initially! So, the data we pass in the constructor is
// the laser object's data, ok? The data we pass into spawn() can be many things: a) the playerData
// in the case of the player, b) the backgroundData in the case of the background, c) the shooterData
// (whether alien or player) when we talk about the laser and finally d) the playerData when we're
// talking about aliens (they need it because their spawn depends on the player location/direction etc)
abstract class SpawnComponent {

    private final GameObjectData mData;

    SpawnComponent(GameObjectData data) {
        mData = data;
    }

    // See NOTE above!
    abstract void spawn(GameObjectData data);

    GameObjectData getGameObjectData() {
        return mData;
    }
}
