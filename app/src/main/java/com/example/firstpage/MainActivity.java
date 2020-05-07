package com.example.firstpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean isVolumeOn;
    ImageView user;
    Button avatar1;
    Button avatar2;
    Button avatar3;
    Button avatar4;
    Button avatar5;
    Button avatar6;
    Button avatar7;
    Button avatar8;
    Button avatar9;
    Button volume;
    Button settings;
    Button apply;
    Button start;
    int avatarID;
    int volumeoffID;
    int volumeonID;
    int background;
    ConstraintLayout mainPageLayout;
    Drawable volumeoff;
    Drawable volumeon;
    Drawable avatar;
    SharedPreferences sharedPreferencesNickname;
    SharedPreferences sharedPreferencesAvatar;
    SharedPreferences.Editor editor;
    EditText nickname;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nickname = findViewById(R.id.nickname);
        mainPageLayout = findViewById(R.id.main_layout);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.daybreaker);
        mediaPlayer.start();
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        mainPageLayout.setBackgroundResource(background);
        isVolumeOn = true;
        user = findViewById(R.id.user);
        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);
        avatar5 = findViewById(R.id.avatar5);
        avatar6 = findViewById(R.id.avatar6);
        avatar7 = findViewById(R.id.avatar7);
        avatar8 = findViewById(R.id.avatar8);
        avatar9 = findViewById(R.id.avatar9);
        volume = findViewById(R.id.volume_button_main);
        settings = findViewById(R.id.settings_button_main);
        apply = findViewById(R.id.apply);
        start = findViewById(R.id.start);
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);

        sharedPreferencesNickname = getSharedPreferences("maindata",MODE_PRIVATE);
        editor = sharedPreferencesNickname.edit();

        avatar1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.batman;
                avatar = AppCompatDrawableManager.get().getDrawable(MainActivity.this, avatarID);
                user.setBackground(avatar);
            }
        });


        avatar2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.captainamerica;
                avatar = AppCompatDrawableManager.get().getDrawable(MainActivity.this, avatarID);
                user.setBackground(avatar);
            }
        });

        avatar3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.joker;
                avatar = AppCompatDrawableManager.get().getDrawable(MainActivity.this, avatarID);
                user.setBackground(avatar);
            }
        });

        avatar4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.herofemale;
                avatar = AppCompatDrawableManager.get().getDrawable(MainActivity.this, avatarID);
                user.setBackground(avatar);
            }
        });

        avatar5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.ironman;
                avatar = AppCompatDrawableManager.get().getDrawable(MainActivity.this, avatarID);
                user.setBackground(avatar);
            }
        });

        avatar6.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.heromale;
                avatar = AppCompatDrawableManager.get().getDrawable(MainActivity.this, avatarID);
                user.setBackground(avatar);
            }
        });

        avatar7.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.thor;
                avatar = AppCompatDrawableManager.get().getDrawable(MainActivity.this, avatarID);
                user.setBackground(avatar);
            }
        });

        avatar8.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.spider;
                avatar = AppCompatDrawableManager.get().getDrawable(MainActivity.this, avatarID);
                user.setBackground(avatar);
            }
        });

        avatar9.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.thanos;
                avatar = AppCompatDrawableManager.get().getDrawable(MainActivity.this, avatarID);
                user.setBackground(avatar);
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("nickname", nickname.getText().toString());
                editor.putInt("avatar", avatarID );
                editor.commit();
                //apply.setEnabled(false);
                Toast.makeText(MainActivity.this, "User was created", Toast.LENGTH_SHORT).show();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HomePage.class);
                i.putExtra("nickname", nickname.getText().toString());
                i.putExtra("avatar", avatarID);
                startActivity(i);
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

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsPage.class);
//                intent.putExtra("toSettingsPage", "MainActivity");
                startActivity(intent);
            }
        });
    }
}