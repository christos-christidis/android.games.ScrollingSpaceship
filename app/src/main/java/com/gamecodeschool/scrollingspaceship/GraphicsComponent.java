package com.gamecodeschool.scrollingspaceship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

// We changed this interface to an abstract class in order to replace the initialize() method w a
// constructor. That way we avoid duplication. Previously, both StdGraphics and BackgroundGraphics
// implemented their own init() which was exactly the same.
abstract class GraphicsComponent {

    private final GameObjectData mData;

    private final Bitmap mBitmap;
    private final Bitmap mBitmapReversed;

    GraphicsComponent(GameObjectData data, int resourceId, Context context) {
        mData = data;

        // create & resize, then create reverse bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        mBitmap = Bitmap.createScaledBitmap(bitmap, (int) data.getObjectWidth(),
                (int) data.getObjectHeight(), false);

        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        mBitmapReversed = Bitmap.createBitmap(mBitmap, 0, 0,
                mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
    }

    abstract void draw(Canvas canvas, Paint paint);

    Bitmap getBitmap() {
        return mBitmap;
    }

    Bitmap getBitmapReversed() {
        return mBitmapReversed;
    }

    GameObjectData getGameObjectData() {
        return mData;
    }
}
