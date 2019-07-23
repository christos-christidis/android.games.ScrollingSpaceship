package com.gamecodeschool.scrollingspaceship;

import android.graphics.RectF;

import java.util.List;

import static java.lang.Thread.sleep;

class PhysicsEngine {

    boolean update(long fps, List<GameObject> objects, ParticleSystem particleSystem,
                   SoundEngine soundEngine, GameState gameState) {

        GameObject player = objects.get(Level.PLAYER_INDEX);

        for (GameObject object : objects) {
            if (object.isSpawned()) {
                object.update(fps, player.getData());
            }
        }

        if (particleSystem.isRunning()) {
            particleSystem.update(fps);
        }

        return detectCollisions(objects, gameState, soundEngine, particleSystem);
    }

    private boolean detectCollisions(List<GameObject> objects, GameState gameState,
                                     SoundEngine soundEngine, ParticleSystem particleSystem) {
        boolean playerHit = false;

        for (GameObject object1 : objects) {
            if (object1.isSpawned()) {
                for (GameObject object2 : objects) {
                    if (object2.isSpawned()) {
                        RectF objectCollider1 = object1.getData().getCollider();
                        RectF objectCollider2 = object2.getData().getCollider();

                        if (RectF.intersects(objectCollider1, objectCollider2)) {
                            switch (object1.getTag() + " with " + object2.getTag()) {
                                case "Player with Alien Laser":
                                case "Player with Alien":
                                    playerHit = true;
                                    gameState.loseLife(soundEngine);
                                    break;
                                case "Player Laser with Alien":
                                    gameState.increaseScore();
                                    particleSystem.emitParticles(object2.getData().getLocation());
                                    object2.unspawn();

                                    GameObject player = objects.get(Level.PLAYER_INDEX);
                                    object2.spawn(player.getData());

                                    object1.unspawn(); // unspawn player laser
                                    soundEngine.playAlienExplode();
                                    break;
                                default:
                                    break;
                            }

                        }
                    }
                }
            }
        }

        return playerHit;
    }
}

