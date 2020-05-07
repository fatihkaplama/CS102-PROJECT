package com.example.firstpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelPage extends AppCompatActivity {
    boolean isLevel1Finished;
    boolean isVolumeOn;
    Drawable volumeoff;
    Drawable volumeon;
    int volumeoffID;
    int volumeonID;
    Button volumeB;
    Button button13;
    Button button14;
    Button button15;
    Button button17;
    Button button18;
    Button button19;
    Button button20;
    Button button21;
    Button button22;

    // get settings return and volume
    Button settingsB;
    Button returnB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_page);
        //get buttons
         button13 = (Button) findViewById(R.id.button13);
         button14 = (Button) findViewById(R.id.button14);
         button15 = (Button) findViewById(R.id.button15);
         button17 = (Button) findViewById(R.id.button17);
         button18 = (Button) findViewById(R.id.button18);
         button19 = (Button) findViewById(R.id.button19);
         button20 = (Button) findViewById(R.id.button20);
         button21 = (Button) findViewById(R.id.button21);
         button22 = (Button) findViewById(R.id.button22);

        // get settings return and volume
        settingsB = (Button) findViewById(R.id.settings_button_LevelPage);
        returnB = (Button) findViewById(R.id.return_button_LevelPage);
        volumeB = (Button) findViewById(R.id.volume_button_LevelPage);

        //getting information from Level 1 Page
        Intent i = getIntent();
        isLevel1Finished = i.getBooleanExtra("finished", false);

        //volume
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);
        isVolumeOn = true;
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.daybreaker);

        // set buttons disabled
        button13.setEnabled(true);
        button14.setEnabled(false);
        button15.setEnabled(false);
        button17.setEnabled(false);
        button18.setEnabled(false);
        button19.setEnabled(false);
        button20.setEnabled(false);
        button21.setEnabled(false);
        button22.setEnabled(false);

        if (isLevel1Finished){
            button14.setEnabled(true);
        }
        //add clicklistener to buttons
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LevelPage.this, Level1Page.class);
                startActivity(i);
                //button14.setEnabled(true);

            }
        });
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(levelPage.this, map2.class);
                // startActivity(i);
            }
        });
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(levelPage.this, map3.class);
                // startActivity(i);
            }
        });
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(levelPage.this, map4.class);
                //startActivity(i);
            }
        });
        button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(levelPage.this, map5.class);
                // startActivity(i);
            }
        });
        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(levelPage.this, map6.class);
                // startActivity(i);

            }
        });
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(levelPage.this, map7.class);
                //  startActivity(i);
            }
        });
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(levelPage.this, map8.class);
                //startActivity(i);
            }
        });
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent i = new Intent(levelPage.this, map9.class);
                // startActivity(i);
            }
        });

        //return settings and volume buttons
        settingsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelPage.this, SettingsPage.class);
//                intent.putExtra("toSettingsPage", "MainActivity");
                startActivity(intent);
            }
        });
        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( LevelPage.this, PlayPage.class);
                startActivity(i);
            }
        });
        volumeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( isVolumeOn) {
                    volumeB.setBackground(volumeoff);
                    isVolumeOn = false;
                    mediaPlayer.pause();
                } else {
                    volumeB.setBackground(volumeon);
                    isVolumeOn = true;
                    mediaPlayer.start();
                }
            }
        });
    }
}
