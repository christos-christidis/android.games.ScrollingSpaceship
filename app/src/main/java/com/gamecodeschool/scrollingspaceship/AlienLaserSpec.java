package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

class AlienLaserSpec extends GameObjectSpec {

    private static final Component[] components = new Component[]{
            Component.StdGraphics,
            Component.LaserUpdate,
            Component.LaserSpawn,
    };

    AlienLaserSpec() {
        super("Alien Laser", R.drawable.alien_laser,
                0.75f, new PointF(14, 100), components);
    }
}
