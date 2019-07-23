package com.gamecodeschool.scrollingspaceship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

class StdGraphicsComponent extends GraphicsComponent {

    StdGraphicsComponent(GameObjectData objectData, int resourceId, Context context) {
        super(objectData, resourceId, context);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        GameObjectData objectData = getGameObjectData();

        PointF location = objectData.getLocation();
        Bitmap bitmap = objectData.facingRight() ? getBitmap() : getBitmapReversed();
        canvas.drawBitmap(bitmap, location.x, location.y, paint);
    }
}
