package com.gamecodeschool.scrollingspaceship;

import android.graphics.PointF;

class AlienDiverSpec extends GameObjectSpec {

    private static final Component[] components = new Component[]{
            Component.StdGraphics,
            Component.AlienDiverUpdate,
            Component.AlienVerticalSpawn,
    };

    AlienDiverSpec() {
        super("Alien", R.drawable.tie_bomber,
                4, new PointF(15, 15), components);
    }
}
