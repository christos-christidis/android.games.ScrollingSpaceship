package com.gamecodeschool.scrollingspaceship;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;

class SoundEngine {

    private final SoundPool mSoundPool;

    private int mShoot_ID = -1;
    private int mAlien_Explode_ID = -1;
    private int mPlayer_Explode_ID = -1;

    SoundEngine(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(5).setAudioAttributes(audioAttributes).build();
        } else {
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("shoot.ogg");
            mShoot_ID = mSoundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("alien_explosion.ogg");
            mAlien_Explode_ID = mSoundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("player_explosion.ogg");
            mPlayer_Explode_ID = mSoundPool.load(descriptor, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void playShoot() {
        mSoundPool.play(mShoot_ID, 1, 1, 0, 0, 1);
    }

    void playPlayerExplode() {
        mSoundPool.play(mPlayer_Explode_ID, 1, 1, 0, 0, 1);
    }

    void playAlienExplode() {
        mSoundPool.play(mAlien_Explode_ID, 1, 1, 0, 0, 1);
    }
}
