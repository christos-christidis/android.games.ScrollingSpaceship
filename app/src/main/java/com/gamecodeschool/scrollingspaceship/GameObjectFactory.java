package com.gamecodeschool.scrollingspaceship;

import android.content.Context;
import android.graphics.PointF;

class GameObjectFactory {

    private final Context mContext;
    private final GameEngine mGameEngine;

    GameObjectFactory(Context context, GameEngine gameEngine) {
        mContext = context;
        mGameEngine = gameEngine;
    }

    GameObject create(GameObjectSpec spec) {
        GameObject object = createObjectWithBasicData(spec);
        addComponentsToObject(object, spec);
        return object;
    }

    private GameObject createObjectWithBasicData(GameObjectSpec spec) {
        PointF location = new PointF(-2000f, -2000f);   // hidden

        int screenWidth = GameEngine.screenWidth;
        int screenHeight = GameEngine.screenHeight;

        float speed = screenWidth / spec.getSpeedDivisor();

        // TODO: this might distort the aspect ratio of the bitmap. Maybe I can fix that...
        float objectWidth = screenWidth / spec.getSizeScale().x;
        float objectHeight = screenHeight / spec.getSizeScale().y;
        PointF objectSize = new PointF(objectWidth, objectHeight);

        // NOTE: all the stuff needed for drawing/colliding is gathered in GameObjectData
        GameObjectData data = new GameObjectData(location, speed, objectSize);

        return new GameObject(spec.getTag(), data);
    }

    private void addComponentsToObject(GameObject object, GameObjectSpec spec) {
        // NOTE: each component will need a ref to its object's basic data to perform its basic functions,
        // which is why I pass the objectData in its constructor.
        GameObjectData objectData = object.getData();

        for (int i = 0; i < spec.getComponents().length; i++) {
            switch (spec.getComponents()[i]) {
                case PlayerInput:
                    // NOTE: There's no need to connect this component to its object (the player object)
                    // with setInputComponent() because, unlike the other components, I don't need to
                    // call it from inside the object. This component implements InputObserver and is
                    // called by the GameEngine which will hold a ref to it. See its constructor.
                    new PlayerInputComponent(objectData, mGameEngine);
                    break;
                case StdGraphics:
                    object.setGraphicsComponent(new StdGraphicsComponent(objectData, spec.getResourceId(), mContext));
                    break;
                case PlayerUpdate:
                    object.setUpdateComponent(new PlayerUpdateComponent(objectData));
                    break;
                case LaserUpdate:
                    object.setUpdateComponent(new LaserUpdateComponent(objectData));
                    break;
                case PlayerSpawn:
                    object.setSpawnComponent(new PlayerSpawnComponent(objectData));
                    break;
                case LaserSpawn:
                    object.setSpawnComponent(new LaserSpawnComponent(objectData));
                    break;
                case BackgroundGraphics:
                    object.setGraphicsComponent(new BackgroundGraphicsComponent(objectData, spec.getResourceId(), mContext));
                    break;
                case BackgroundUpdate:
                    object.setUpdateComponent(new BackgroundUpdateComponent(objectData));
                    break;
                case BackgroundSpawn:
                    object.setSpawnComponent(new BackgroundSpawnComponent(objectData));
                    break;
                case AlienChaserUpdate:
                    object.setUpdateComponent(new AlienChaserUpdateComponent(objectData, mGameEngine));
                    break;
                case AlienDiverUpdate:
                    object.setUpdateComponent(new AlienDiverUpdateComponent(objectData));
                    break;
                case AlienPatrollerUpdate:
                    object.setUpdateComponent(new AlienPatrollerUpdateComponent(objectData, mGameEngine));
                    break;
                case AlienHorizontalSpawn:
                    object.setSpawnComponent(new AlienHorizontalSpawnComponent(objectData));
                    break;
                case AlienVerticalSpawn:
                    object.setSpawnComponent(new AlienVerticalSpawnComponent(objectData));
                    break;
                default:
                    throw new RuntimeException("Some component is not covered by the switch!");
            }

        }
    }
}
