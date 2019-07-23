package com.gamecodeschool.scrollingspaceship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

class BackgroundGraphicsComponent extends GraphicsComponent {

    BackgroundGraphicsComponent(GameObjectData data, int resourceId, Context context) {
        super(data, resourceId, context);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        GameObjectData backgroundData = getGameObjectData();

        // note that background's bitmap width/height are set equal in BackgroundSpec
        int bitmapWidth = getBitmap().getWidth();
        int bitmapHeight = getBitmap().getHeight();
        int xClip = backgroundData.getXClip();

        // The two srcRects split the bitmap in half. Then we paste each half to the corresponding
        // part of the screen. TBH, this requires a lot of drawing to understand, which I'll do later...
        // Also note that for some reason he stretches the bitmaps a bit vertically (with endY)
        int endY = GameEngine.screenHeight + 20;

        // for the regular bitmap
        Rect srcRect1 = new Rect(0, 0, bitmapWidth - xClip, bitmapHeight);
        Rect destRect1 = new Rect(xClip, 0, bitmapWidth, endY);

        // for the reversed bitmap
        Rect srcRect2 = new Rect(bitmapWidth - xClip, 0, bitmapWidth, bitmapHeight);
        Rect destRect2 = new Rect(0, 0, xClip, endY);

        // draw the two background bitmaps
        if (!backgroundData.reversedBackgroundFirst()) {
            canvas.drawBitmap(getBitmap(), srcRect1, destRect1, paint);
            canvas.drawBitmap(getBitmapReversed(), srcRect2, destRect2, paint);
        } else {
            canvas.drawBitmap(getBitmapReversed(), srcRect1, destRect1, paint);
            canvas.drawBitmap(getBitmap(), srcRect2, destRect2, paint);
        }
    }
}
