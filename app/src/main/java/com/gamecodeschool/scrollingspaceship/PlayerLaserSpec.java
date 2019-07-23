package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

class PlayerLaserSpec extends GameObjectSpec {

    private static final Component[] components = new Component[]{
            Component.StdGraphics,
            Component.LaserUpdate,
            Component.LaserSpawn,
    };

    PlayerLaserSpec() {
        super("Player Laser", R.drawable.player_laser,
                0.75f, new PointF(8, 100), components);
    }
}
