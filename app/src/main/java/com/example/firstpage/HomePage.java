package com.example.firstpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent i = getIntent();
        userName = i.getStringExtra("nickname");
        tv = findViewById(R.id.userName);
        tv.setText(userName);
        b = findViewById(R.id.backB);
        play = findViewById(R.id.play2);
        // avatarPg = findViewById(R.id.avatarH);
        //avatar = AppCompatDrawableManager.get().getDrawable(HomePage.this, avatarId);
        // avatarPg.setBackground(avatar);
        //user.setBackground(avatar);

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
                Intent i = new Intent(HomePage.this, Map1Page.class);
                startActivity(i);
            }
        });
    }
}
