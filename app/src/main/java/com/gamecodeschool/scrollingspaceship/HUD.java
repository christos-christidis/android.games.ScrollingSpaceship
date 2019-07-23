package com.gamecodeschool.scrollingspaceship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.HashMap;
import java.util.Map;

class HUD {

    private final Map<Button.Id, Button> mButtons;

    HUD(Context context) {
        int screenWidth = GameEngine.screenWidth;
        int screenHeight = GameEngine.screenHeight;

        int buttonSize = screenWidth / 15;
        int buttonPadding = screenWidth / 90;
        final int FUZZ_FACTOR = buttonSize;   // so that the touchRect is a bit bigger than the bitmap.

        Point bitmapPosition = new Point();

        // Note that we draw the whole dpad at the position of upButton, we won't draw anything for
        // downButton. The only thing we're interested in is the downButton's touchRect which will
        // cover the bottom half of the dpad!
        bitmapPosition.x = buttonPadding;
        bitmapPosition.y = screenHeight - buttonPadding - buttonSize * 3;
        Rect touchRect = new Rect(0, bitmapPosition.y - FUZZ_FACTOR,
                bitmapPosition.x + buttonSize * 3 + FUZZ_FACTOR, bitmapPosition.y + buttonSize * 3 / 2);

        Button upButton = new Button(R.drawable.dpad, bitmapPosition, buttonSize * 3, touchRect, context);

        bitmapPosition.x = buttonPadding;
        bitmapPosition.y = screenHeight - buttonPadding - buttonSize * 3 / 2;
        touchRect = new Rect(0, bitmapPosition.y,
                buttonPadding + buttonSize * 3 + FUZZ_FACTOR, screenHeight);

        Button downButton = new Button(R.drawable.dpad, bitmapPosition, buttonSize * 3, touchRect, context);

        bitmapPosition.x = screenWidth - buttonPadding - buttonSize * 2;
        bitmapPosition.y = screenHeight - buttonPadding - buttonSize * 3;
        touchRect = new Rect(bitmapPosition.x, bitmapPosition.y,
                bitmapPosition.x + buttonSize, bitmapPosition.y + buttonSize);

        Button pauseButton = new Button(R.drawable.ps4_triangle, bitmapPosition, buttonSize, touchRect, context);

        bitmapPosition.x = screenWidth - buttonPadding - buttonSize;
        bitmapPosition.y = screenHeight - buttonPadding - buttonSize * 2;
        touchRect = new Rect(bitmapPosition.x, bitmapPosition.y - buttonSize,
                screenWidth, bitmapPosition.y + buttonSize);

        Button flipButton = new Button(R.drawable.ps4_circle, bitmapPosition, buttonSize, touchRect, context);

        bitmapPosition.x = screenWidth - buttonPadding - buttonSize * 2;
        bitmapPosition.y = screenHeight - buttonPadding - buttonSize;
        touchRect = new Rect(bitmapPosition.x - buttonSize, bitmapPosition.y, screenWidth, screenHeight);

        Button shootButton = new Button(R.drawable.ps4_cross, bitmapPosition, buttonSize, touchRect, context);

        bitmapPosition.x = screenWidth - buttonPadding - buttonSize * 3;
        bitmapPosition.y = screenHeight - buttonPadding - buttonSize * 2;
        touchRect = new Rect(0, 0, 0, 0);    // won't be used anyway.

        Button unusedButton = new Button(R.drawable.ps4_square, bitmapPosition, buttonSize, touchRect, context);

        mButtons = new HashMap<>();
        mButtons.put(Button.Id.UP, upButton);
        mButtons.put(Button.Id.DOWN, downButton);
        mButtons.put(Button.Id.PAUSE, pauseButton);
        mButtons.put(Button.Id.FLIP, flipButton);
        mButtons.put(Button.Id.SHOOT, shootButton);
        mButtons.put(Button.Id.UNUSED, unusedButton);
    }

    void draw(Canvas canvas, Paint paint, GameState gameState) {
        int screenWidth = GameEngine.screenWidth;
        int screenHeight = GameEngine.screenHeight;
        final int textSize = screenWidth / 50;

        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);

        canvas.drawText("Hi: " + gameState.getHighScore(), textSize, textSize, paint);
        canvas.drawText("Score: " + gameState.getScore(), textSize, textSize * 2, paint);
        canvas.drawText("Lives: " + gameState.getNumShips(), textSize, textSize * 3, paint);

        if (gameState.gameOver()) {
            paint.setTextSize(textSize * 5);
            String gameOverString = "PRESS TRIANGLE";
            float textWidth = paint.measureText(gameOverString);
            canvas.drawText(gameOverString, (float) screenWidth / 2 - textWidth / 2, (float) screenHeight / 2, paint);
        }

        if (gameState.paused() && !gameState.gameOver()) {
            paint.setTextSize(textSize * 5);
            String pauseString = "PAUSED";
            float textWidth = paint.measureText(pauseString);
            canvas.drawText(pauseString, (float) screenWidth / 2 - textWidth / 2, (float) screenHeight / 2, paint);
        }

        drawButtons(canvas, paint);
    }

    private void drawButtons(Canvas canvas, Paint paint) {
        paint.setColor(Color.WHITE);

        // draw everything except DOWN. The drawing of the dpad for UP covers DOWN as well...
        // Btw this is the fastest way to iterate a map in general (per benchmarks)
        for (Button.Id key : mButtons.keySet()) {
            if (key == Button.Id.DOWN) {
                continue;
            }
            Button button = mButtons.get(key);
            if (button != null) {
                button.draw(canvas, paint);
            }
        }
    }

    Button getButton(Button.Id buttonId) {
        return mButtons.get(buttonId);
    }
}
