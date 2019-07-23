package com.gamecodeschool.scrollingspaceship;

import android.view.MotionEvent;

interface InputObserver {
    void handleInput(MotionEvent event, GameState gameState, HUD hud);
}
