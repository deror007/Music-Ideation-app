package com.example.musicideation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

public class UserGuide extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/UserGuide.html");

        notifyNiceMessage();
    }

    //send nice message to user.
    public void notifyNiceMessage(){

        //Create channel for certain Android OS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notifyChannel = new NotificationChannel("Notify", "popUp", NotificationManager.IMPORTANCE_HIGH);
            notifyChannel.setDescription("Message");

            NotificationManager notifyManager = getSystemService(NotificationManager.class);
            notifyManager.createNotificationChannel(notifyChannel);
        }

        //Create Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"Notify")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("From Russell de Roeper")
                .setContentText("Hope you have a great day❗ \uD83D\uDE04")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notifyMangComp = NotificationManagerCompat.from(this);
        notifyMangComp.notify(111, builder.build());
    }
}