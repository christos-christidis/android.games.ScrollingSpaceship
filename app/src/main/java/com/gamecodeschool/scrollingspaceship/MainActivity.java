package com.gamecodeschool.scrollingspaceship;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

// TODO: check if laser-shooting spot is ok
// TODO: make controls semi-transparent
public class MainActivity extends Activity {

    private GameEngine mGameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VisibilityManager.hideSystemUI(this);

        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getRealSize(screenSize);

        mGameEngine = new GameEngine(this, screenSize);
        setContentView(mGameEngine);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            VisibilityManager.hideSystemUI(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGameEngine.startGameLoop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameEngine.stopGameLoop();
    }
}
