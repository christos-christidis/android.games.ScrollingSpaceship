package com.gamecodeschool.scrollingspaceship;

class PlayerSpawnComponent extends SpawnComponent {

    PlayerSpawnComponent(GameObjectData objectData) {
        super(objectData);
    }

    // We pass playerData here because we must pass something... (read SpawnComponent)
    // We could do without it since we have access to the playerData w getGameObjectData()
    @Override
    public void spawn(GameObjectData playerData) {
        int screenWidth = GameEngine.screenWidth;
        int screenHeight = GameEngine.screenHeight;
        playerData.setLocation((float) screenWidth / 2, (float) screenHeight / 2);
    }
}
