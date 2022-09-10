package com.example.musicideation;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;


public class SoundService extends Service {

    // declaring object of MediaPlayer
    private MediaPlayer notePlayer;

    @Override
    // execution of service will start on calling this method
    public int onStartCommand(Intent intent, int flags, int startId) {

        playSound();


        // returns program status
        return START_STICKY;
    }

    @Override
    // destroy service
    public void onDestroy() {
        super.onDestroy();

        // stop
        notePlayer.stop();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void playSound(){

        MediaPlayer notePlayer = MediaPlayer.create(SoundService.this, R.raw.note);
        notePlayer.start();


    }

}
