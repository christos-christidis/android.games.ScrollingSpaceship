package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

class BackgroundSpec extends GameObjectSpec {

    private static final Component[] components = new Component[]{
            Component.BackgroundGraphics,
            Component.BackgroundUpdate,
            Component.BackgroundSpawn,
    };

    BackgroundSpec(int resourceId) {
        super("Background", resourceId,
                2, new PointF(1, 1), components);
    }
}
