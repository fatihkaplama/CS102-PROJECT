package com.example.firstpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    ImageView imageView;
    Drawable avatar;
    Drawable deneme;
    int denemeID;
    int avatarID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        Intent i =getIntent();
        String nickname = i.getStringExtra("nickname");
        int avatarID = i.getIntExtra("avatar", 1);
        avatar = AppCompatDrawableManager.get().getDrawable(this, avatarID);
        textView.setText(nickname);
        imageView.setBackground(avatar);
    }
}
