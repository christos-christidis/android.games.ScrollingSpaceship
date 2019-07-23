package com.gamecodeschool.scrollingspaceship;

// this allows the player to communicate w the GameEngine in order to spawn a laser
interface PlayerLaserSpawner {
    void spawnPlayerLaser(GameObjectData playerData);
}
