package com.example.firstpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    String userName;
    Drawable avatar;
    ImageView avatarPg;
    int avatarId;
    TextView tv;
    Button b;
    Button play;
    Button settingsButton;
    int background;
    int avatarID;

    ConstraintLayout homePageLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent i = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        homePageLayout = findViewById(R.id.home_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        homePageLayout.setBackgroundResource(background);

        //set nick name
        userName = sharedPreferences.getString("nickname", "User");
        tv = findViewById(R.id.userName);
        tv.setText(userName);
        b = findViewById(R.id.return_button_homePage);
        play = findViewById(R.id.play2);
        settingsButton = findViewById(R.id.settings_button_homePage);

        //get avatar from sharedPref
        avatarID = sharedPreferences.getInt("avatar", 0);
        avatarPg = findViewById(R.id.avatarH);
        avatar = AppCompatDrawableManager.get().getDrawable(HomePage.this, avatarID);
        avatarPg.setBackground(avatar);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this,MainActivity.class);
                startActivity(i);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(HomePage.this,PlayPage.class);
                startActivity(i);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, SettingsPage.class);
                startActivity(i);
            }
        });
    }
}
