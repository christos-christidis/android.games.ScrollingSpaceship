package com.gamecodeschool.scrollingspaceship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

class Particle {

    private final PointF mVelocity;
    private final PointF mPosition;

    Particle(PointF velocity) {
        mVelocity = velocity;
        mPosition = new PointF();
    }

    void setPosition(PointF newPosition) {
        mPosition.set(newPosition);
    }

    void update() {
        mPosition.offset(mVelocity.x, mVelocity.y);
    }

    void draw(Canvas canvas, Paint paint) {
        int particleSize = 10;
        canvas.drawRect(mPosition.x, mPosition.y, mPosition.x + particleSize,
                mPosition.y + particleSize, paint);
    }
}
