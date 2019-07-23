package com.gamecodeschool.scrollingspaceship;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ParticleSystem {

    private float mDuration;

    private final List<Particle> mParticles = new ArrayList<>();
    private boolean mIsRunning = false;

    ParticleSystem(int numParticles) {
        Random random = new Random();

        // we create all particles w random velocities. Every new emission will use the same particles
        // w the same speeds over and over again
        for (int i = 0; i < numParticles; i++) {
            float angle = random.nextInt(360);
            angle *= 3.14f / 180.f;

            float speed = random.nextInt(10) + 1;

            PointF velocity = new PointF((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
            mParticles.add(new Particle(velocity));
        }
    }

    void update(long fps) {
        for (Particle particle : mParticles) {
            particle.update();
        }

        mDuration -= 1f / fps;
        if (mDuration < 0) {
            mIsRunning = false;
        }
    }

    void emitParticles(PointF startPosition) {
        mIsRunning = true;
        mDuration = 0.75f;

        for (Particle particle : mParticles) {
            particle.setPosition(startPosition);
        }
    }

    void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.WHITE);
        for (Particle particle : mParticles) {
            particle.draw(canvas, paint);
        }
    }

    boolean isRunning() {
        return mIsRunning;
    }
}
