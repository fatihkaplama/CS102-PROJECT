package com.example.firstpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstpage.ThemePage;

public class SettingsPage extends ThemePage implements View.OnClickListener{
    Button returnButton;
    Button voiceButton;
    Button themesButton;
    Button musicsButton;
    boolean isPressed;
    AudioManager audioManager;
    ConstraintLayout settingsPage;
    int background;
    int volumeOn, volumeOff;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        settingsPage = findViewById(R.id.settings_page_layout);
        volumeOn = R.drawable.volumeon;
        volumeOff = R.drawable.volumeoff;
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        settingsPage.setBackgroundResource(background);
        isPressed = false;
        returnButton = findViewById(R.id.return_button_settingsPage);
        voiceButton = findViewById(R.id.voice_button_settingsPage);
        themesButton = findViewById(R.id.themes_button_settingsPage);
        musicsButton = findViewById(R.id.musics_button_SettingPage);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        returnButton.setOnClickListener(this);
        themesButton.setOnClickListener(this);
        musicsButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if ( v.getId() == themesButton.getId()) {
            Intent intent = new Intent(SettingsPage.this, ThemePage.class);
            startActivity(intent);
        }
        else if ( v.getId() == musicsButton.getId()){
            Intent intent = new Intent(SettingsPage.this, MusicsPage.class);
            startActivity(intent);
        }
        else if ( v.getId() == returnButton.getId()){
//            Intent intent;
//            intent = getIntent();
//            String str;
//            str = intent.getStringExtra("toSettingsPage");
//            if ( str == "MainActivity"){
                Intent intent = new Intent(SettingsPage.this, HomePage.class);
                startActivity(intent);
        }
    }
}