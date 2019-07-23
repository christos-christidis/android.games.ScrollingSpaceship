package com.gamecodeschool.scrollingspaceship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

class Button {

    enum Id {
        UP, DOWN, PAUSE, FLIP, SHOOT, UNUSED,
    }

    private final Point mPosition;
    private final int mButtonSize;
    private final Rect mTouchRect;
    private final Context mContext;
    private Bitmap mBitmap;

    Button(int resourceId, Point position, int buttonSize, Rect touchRect, Context context) {
        mPosition = new Point(position);
        mButtonSize = buttonSize;
        mTouchRect = touchRect;
        mContext = context;

        setBitmap(resourceId);
    }

    void draw(Canvas canvas, Paint paint) {
        paint.setAlpha(128);
        canvas.drawBitmap(mBitmap, mPosition.x, mPosition.y, paint);
        paint.setAlpha(255);
    }

    boolean contains(int x, int y) {
        return mTouchRect.contains(x, y);
    }

    // NOTE: must be able to change the button's bitmap (to a highlighted version) when pressed.
    void setBitmap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resourceId);
        mBitmap = Bitmap.createScaledBitmap(bitmap, mButtonSize, mButtonSize, false);
    }
}
