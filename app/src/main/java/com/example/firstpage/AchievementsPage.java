package com.example.firstpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AchievementsPage extends AppCompatActivity {
    private Button volume;
    private boolean isVolumeOn;
    private Drawable volumeoff;
    private Drawable volumeon;
    private int volumeoffID;
    private int volumeonID;
    private Button back;
    private String userName;
    private int avatarId;
    private TextView tv;
    private int background;
    private ConstraintLayout AchievementsPageLayout;




    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_page);
        Intent i = getIntent();
        AchievementsPageLayout = findViewById(R.id.achievements_page_layout);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.start();
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        AchievementsPageLayout.setBackgroundResource(background);
        userName = i.getStringExtra("nickname");
        tv = findViewById(R.id.userName);
        tv.setText(userName);
        volume = findViewById(R.id.achievementsPage_voice_button);
        back = findViewById(R.id.achievementsPage_back_button);
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(AchievementsPage.this , PlayPage.class);
            startActivity(intent);
            }
        });


        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVolumeOn) {
                    volume.setBackground(volumeoff);
                    isVolumeOn = false;
                    mediaPlayer.pause();
                } else {
                    volume.setBackground(volumeon);
                    isVolumeOn = true;
                    mediaPlayer.start();
                }
            }
        });
    }
}
