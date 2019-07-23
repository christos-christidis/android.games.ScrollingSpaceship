package com.gamecodeschool.scrollingspaceship;

class BackgroundUpdateComponent extends UpdateComponent {

    BackgroundUpdateComponent(GameObjectData data) {
        super(data);
    }

    @Override
    public boolean update(long fps, GameObjectData playerData) {
        GameObjectData backgroundData = getGameObjectData();

        float speed = backgroundData.getSpeed();
        int currentXClip = backgroundData.getXClip();

        if (playerData.facingRight()) {
            currentXClip -= speed / fps;
        } else {
            currentXClip += speed / fps;
        }

        float backgroundWidth = backgroundData.getObjectWidth();

        if (currentXClip >= backgroundWidth) {
            backgroundData.setXClip(0);
            backgroundData.flipReversedBackgroundFirst();
        } else if (currentXClip <= 0) {
            backgroundData.setXClip((int) backgroundWidth);
            backgroundData.flipReversedBackgroundFirst();
        } else {
            backgroundData.setXClip(currentXClip);
        }

        return true;
    }
}
