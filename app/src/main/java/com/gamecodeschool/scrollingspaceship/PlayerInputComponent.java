package com.gamecodeschool.scrollingspaceship;

import android.view.MotionEvent;

class PlayerInputComponent implements InputObserver {

    private final GameObjectData mPlayerData;
    private final PlayerLaserSpawner mPLS;

    PlayerInputComponent(GameObjectData playerData, GameEngine gameEngine) {
        mPlayerData = playerData;
        mPLS = gameEngine;
        gameEngine.addInputObserver(this);
    }

    // SOS: read answer at https://stackoverflow.com/a/4269592 carefully,
    // everything it says is 100% correct. Then try to understand the example at
    // https://android-developers.googleblog.com/2010/06/making-sense-of-multitouch.html
    // (what he does is ignore 2nd or 3rd finger going down or moving, but when/if
    // the 1st (active) finger is lifted, he makes one of the other fingers active.
    // Finally, see https://stackoverflow.com/a/12559204 to understand how Android
    // batches movement for efficiency reasons
    @Override
    public void handleInput(MotionEvent event, GameState gameState, HUD hud) {
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);

        Button upButton = hud.getButton(Button.Id.UP);
        Button downButton = hud.getButton(Button.Id.DOWN);
        Button flipButton = hud.getButton(Button.Id.FLIP);
        Button shootButton = hud.getButton(Button.Id.SHOOT);

        // TODO: In Platformer he also checks if game is paused...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                // player has released either Up or Down button
                if (upButton.contains(x, y) || downButton.contains(x, y)) {
                    mPlayerData.stopVertical();
                    upButton.setBitmap(R.drawable.dpad);
                }
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (upButton.contains(x, y)) {
                    mPlayerData.headUp();
                    upButton.setBitmap(R.drawable.dpad_up);
                } else if (downButton.contains(x, y)) {
                    mPlayerData.headDown();
                    upButton.setBitmap(R.drawable.dpad_down);
                } else if (flipButton.contains(x, y)) {
                    if (!gameState.gameOver()) {
                        mPlayerData.flip();
                    }
                } else if (shootButton.contains(x, y)) {
                    mPLS.spawnPlayerLaser(mPlayerData);
                }
                break;
        }
    }
}
