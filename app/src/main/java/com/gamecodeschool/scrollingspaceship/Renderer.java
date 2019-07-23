package com.gamecodeschool.scrollingspaceship;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

class Renderer {

    private final Paint mPaint = new Paint();
    private final SurfaceHolder mSurfaceHolder;

    Renderer(SurfaceView surfaceView) {
        mSurfaceHolder = surfaceView.getHolder();
    }

    void draw(List<GameObject> objects, ParticleSystem particleSystem, HUD hud, GameState gameState) {
        if (mSurfaceHolder.getSurface().isValid()) {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            if (gameState.drawingObjectsIsAllowed()) {
                for (GameObject object : objects) {
                    if (object.isSpawned()) {
                        object.draw(canvas, mPaint);
                    }
                }
            }

            if (gameState.gameOver()) {
                objects.get(Level.BACKGROUND_INDEX).draw(canvas, mPaint);
            }

            if (particleSystem.isRunning()) {
                particleSystem.draw(canvas, mPaint);
            }

            hud.draw(canvas, mPaint, gameState);

            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
