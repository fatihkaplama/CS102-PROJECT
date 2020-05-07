package com.example.firstpage;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.media.MediaPlayer;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PlayPage extends AppCompatActivity {

    boolean isVolumeon;
    String userName;
    Drawable avatar;
    ImageView user;
    int avatarId;
    TextView tv;
    Button b;
    Button educationalMode;
    Button creativeMode;
    Button quizMode;
    Button settingsButton;
    Button volume;
    Drawable volumeon;
    Drawable volumeoff;
    int background;
    int volumeOffID;
    int volumeOnID;
    ConstraintLayout playPageLayout;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);
        Intent i =getIntent();
        playPageLayout =findViewById(R.id.play_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        playPageLayout.setBackgroundResource(background);
        userName = i.getStringExtra("nickname");
        tv = findViewById(R.id.userName);
        tv.setText(userName);
        isVolumeon = true;
        volume = findViewById(R.id.volume_button_playPage);
        volumeOnID = R.drawable.volumeon;
        volumeOffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeOnID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this,volumeOffID);

        b = findViewById(R.id.return_button_playPage);
        educationalMode = findViewById(R.id.educationalMode);
        settingsButton = findViewById(R.id.settings_button_playPage);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayPage.this,HomePage.class);
                startActivity(i);
            }
        });

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVolumeon) {
                    volume.setBackground(volumeoff);
                    isVolumeon = false;
                }
                else {
                    volume.setBackground(volumeon);
                    isVolumeon = true;
                }
            }
        });
        educationalMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayPage.this, LevelPage.class);
                startActivity(i);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayPage.this, SettingsPage.class);
                startActivity(i);
            }
        });
    }

}
