package com.gamecodeschool.scrollingspaceship;

import android.view.MotionEvent;

// this listens to the same events that PlayerInputComponent does. It takes care only of the game
// flow (pause/resume/new game), whereas PlayerInputComponent takes care of the ship response to input.
class GameInputObserver implements InputObserver {

    GameInputObserver(InputBroadcaster broadcaster) {
        broadcaster.addInputObserver(this);
    }

    // Read up on events etc on the link provided in PlayerInputComponent
    @Override
    public void handleInput(MotionEvent event, GameState gameState, HUD hud) {
        int index = event.getActionIndex();
        int x = (int) event.getX(index);
        int y = (int) event.getY(index);

        Button pauseButton = hud.getButton(Button.Id.PAUSE);

        int eventType = event.getAction() & MotionEvent.ACTION_MASK;
        if (eventType == MotionEvent.ACTION_UP || eventType == MotionEvent.ACTION_POINTER_UP) {
            // if player pressed PAUSE button
            if (pauseButton.contains(x, y)) {
                if (!gameState.paused()) {
                    gameState.pauseGame();
                } else if (gameState.gameOver()) {
                    gameState.startNewGame();
                } else if (gameState.paused()) {
                    gameState.resumeGame();
                }
            }
        }
    }
}
