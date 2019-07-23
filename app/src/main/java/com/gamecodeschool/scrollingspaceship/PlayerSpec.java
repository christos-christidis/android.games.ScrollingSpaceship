package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

class PlayerSpec extends GameObjectSpec {

    private static final Component[] components = new Component[]{
            Component.PlayerInput,
            Component.StdGraphics,
            Component.PlayerUpdate,
            Component.PlayerSpawn,
    };

    PlayerSpec() {
        super("Player", R.drawable.xwing,
                1, new PointF(15, 15), components);
    }
}
