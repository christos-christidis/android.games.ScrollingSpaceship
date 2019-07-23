package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

class AlienPatrollerSpec extends GameObjectSpec {

    private static final Component[] components = new Component[]{
            Component.StdGraphics,
            Component.AlienPatrollerUpdate,
            Component.AlienHorizontalSpawn,
    };

    AlienPatrollerSpec() {
        super("Alien", R.drawable.tie_interceptor,
                5, new PointF(15, 15), components);
    }
}
