package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

class Triangle {

    // Points start from right angle and go clockwise
    private final PointF mPoint1;
    private final PointF mPoint2;
    private final PointF mPoint3;

    Triangle() {
        mPoint1 = new PointF();
        mPoint2 = new PointF();
        mPoint3 = new PointF();
    }

    Triangle(PointF point1, PointF point2, PointF point3) {
        mPoint1 = point1;
        mPoint2 = point2;
        mPoint3 = point3;
    }
}
