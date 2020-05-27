package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatDrawableManager;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import com.example.FunAlgo.R;

public class InstructionsPage extends AppCompatActivity {

    private boolean isVolumeon;
    private String userName;
    private Drawable avatar;
    private ImageView avatarPg;
    private int avatarId;
    private TextView tv;
    private Button b;
    private Button settingsButton;
    private int background;
    private int avatarID;
    private Drawable volumeon;
    private Drawable volumeoff;
    private int volumeOffID;
    private int volumeOnID;
    private Button volume;
    private ConstraintLayout instructionsPageLayout;
    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_instructions_page);
        instructionsPageLayout = findViewById(R.id.instructions_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        instructionsPageLayout.setBackgroundResource(background);

        Intent i = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        userName = sharedPreferences.getString("nickname", "User");
        tv = findViewById(R.id.userName);
        tv.setText(userName);
        b= findViewById(R.id.return_button_instructionsPage);
        settingsButton = findViewById(R.id.settings_button_instructionsPage);

        avatarID = sharedPreferences.getInt("avatar", 0);
        avatarPg = findViewById(R.id.avatarH);
        avatar = AppCompatDrawableManager.get().getDrawable(InstructionsPage.this,avatarID);
        avatarPg.setBackground(avatar);

        volume = findViewById(R.id.volume_button_instructionsPage);
        volumeOnID = R.drawable.volumeon;
        volumeOffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeOnID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeOffID);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InstructionsPage.this, HomePage.class);
                startActivity(i);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InstructionsPage.this, SettingsPage.class);
                startActivity(i);
            }
        });
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVolumeon) {
                    volume.setBackground(volumeoff);
                    isVolumeon = false;
                }
                else {
                    volume.setBackground(volumeon);
                isVolumeon = true;
                }
            }
        });
    }
}