package com.gamecodeschool.scrollingspaceship;

class BackgroundSpawnComponent extends SpawnComponent {

    BackgroundSpawnComponent(GameObjectData data) {
        super(data);
    }

    // We pass backgroundData here because we must pass something... (read SpawnComponent)
    // We could do without it since we have access to the backgroundData w getGameObjectData()
    @Override
    public void spawn(GameObjectData backgroundData) {
        backgroundData.setLocation(0, 0);
    }
}
