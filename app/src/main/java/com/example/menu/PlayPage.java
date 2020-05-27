package com.example.menu;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.FunAlgo.R;
import com.example.creative.CreativeMode;
import com.example.educational.LevelPage;

public class PlayPage extends AppCompatActivity {

    private boolean isVolumeon;
    private String userName;
    private Drawable avatar;
    private ImageView user;
    private int avatarId;
    private TextView tv;
    private Button b;
    private Button educationalMode;
    private Button creativeMode;
    private Button settingsButton;
    private Button volume;
    private Drawable volumeon;
    private Drawable volumeoff;
    private int background;
    private int volumeOffID;
    private int volumeOnID;
    private ConstraintLayout playPageLayout;
    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent i =getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        playPageLayout =findViewById(R.id.play_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        playPageLayout.setBackgroundResource(background);

        //setting nickname
        userName = sharedPreferences.getString("nickname", "user");
        tv = findViewById(R.id.userName);
        tv.setText(userName);

        //setting avatar
        avatarId = sharedPreferences.getInt("avatar",0);
        avatar = AppCompatDrawableManager.get().getDrawable(PlayPage.this, avatarId);
        user = findViewById(R.id.user);
        user.setBackground(avatar);

        isVolumeon = true;
        volume = findViewById(R.id.volume_button_playPage);
        volumeOnID = R.drawable.volumeon;
        volumeOffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeOnID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this,volumeOffID);

        b = findViewById(R.id.return_button_playPage);
        educationalMode = findViewById(R.id.educationalMode);
        settingsButton = findViewById(R.id.settings_button_playPage);
        creativeMode = findViewById(R.id.creativeMode);

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
        creativeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayPage.this, CreativeMode.class);
                startActivity(i);
            }
        });
    }

}
