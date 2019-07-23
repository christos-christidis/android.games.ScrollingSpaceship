package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

class AlienChaserSpec extends GameObjectSpec {

    private static final Component[] components = new Component[]{
            Component.StdGraphics,
            Component.AlienChaserUpdate,
            Component.AlienHorizontalSpawn,
    };

    AlienChaserSpec() {
        super("Alien", R.drawable.tie_fighter,
                4, new PointF(15, 15), components);
    }
}
