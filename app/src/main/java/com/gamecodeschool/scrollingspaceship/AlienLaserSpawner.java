package com.gamecodeschool.scrollingspaceship;

// this allows an alien to communicate w the GameEngine in order to spawn a laser
interface AlienLaserSpawner {
    void spawnAlienLaser(GameObjectData alienData);
}
