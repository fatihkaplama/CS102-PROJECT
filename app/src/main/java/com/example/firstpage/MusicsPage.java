package com.example.firstpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MusicsPage extends AppCompatActivity  {
    private MediaPlayer music1;
    private MediaPlayer music2;
    private MediaPlayer music3;
    private MediaPlayer music4;
    ConstraintLayout musicsPageLayout;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musics_page);
        Button b1 = findViewById(R.id.musıc_buton_musıc_page1);
        Button b2 = findViewById(R.id.button2);
        Button b3 = findViewById(R.id.musıc_buton_musıc_page3);
        Button b4 = findViewById(R.id.musıc_buton_musıc_page4);
        Button b5 = findViewById(R.id.return_button_musicsPage);
        Button ibVolume = findViewById(R.id.voice_button_musicsPage);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        //final ImageButton ibMute = findViewById(R.id.imageView3);
        //ibMute.setVisibility(View.INVISIBLE);
        musicsPageLayout = findViewById(R.id.musics_page_layout);
        int myInt = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        musicsPageLayout.setBackgroundResource(myInt);

        music1 = mediaPlayer.create(this, R.raw.glorious);
        music1.setLooping(true);

        music2 = mediaPlayer.create(this, R.raw.daybreaker);
        music2.setLooping(true);

        music3 = mediaPlayer.create(this, R.raw.wingless);
        music3.setLooping(true);

        music4 = mediaPlayer.create(this, R.raw.jumper);
        music4.setLooping(true);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music1.start();
                if(((MediaPlayer) music2).isPlaying() ) {
                    ((MediaPlayer) music2).pause();
                }
                if(music3.isPlaying()){
                    music3.pause();
                }
                if(music4.isPlaying()){
                    music4.pause();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MediaPlayer) music2).start();
                if(music1.isPlaying() ) {
                    music1.pause();
                }
                if(music3.isPlaying()){
                    music3.pause();
                }
                if(music4.isPlaying()){
                    music4.pause();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music3.start();
                if(music1.isPlaying() ) {
                    music1.pause();
                }
                if(((MediaPlayer) music2).isPlaying()){
                    ((MediaPlayer) music2).pause();
                }
                if(music4.isPlaying()){
                    music4.pause();
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music4.start();
                if(music1.isPlaying() ) {
                    music1.pause();
                }
                if(((MediaPlayer) music2).isPlaying()){
                    ((MediaPlayer) music2).pause();
                }
                if(music3.isPlaying()){
                    music3.pause();
                }
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
