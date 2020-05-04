package com.example.firstpage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Map1Page extends AppCompatActivity {
    ImageView bee;
    ImageView honey;
    Button goForward;
    Button turnRight;
    Button turnLeft;
    Button settings;
    Button volume;
    Button back;
    Button info;
    Button up;
    LinearLayout layout;
    LinearLayout.LayoutParams params;
    Drawable upLogo;
    float x;
    float y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1_page);
        bee = findViewById(R.id.bee);
        honey = findViewById(R.id.honey);
        goForward = findViewById(R.id.goForward);
        turnRight = findViewById(R.id.turnRight);
        turnLeft = findViewById(R.id.turnLeft);
        settings = findViewById(R.id.settings);
        volume = findViewById(R.id.volume);
        back = findViewById(R.id.back);
        info = findViewById(R.id.info);
        layout = findViewById(R.id.linearLayout3);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        up = new Button(this);
        up.setText("GO FORWARD");
        up.setBackgroundColor(Color.CYAN);

        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.addView(up,params);
                //bee.setTranslationX(100);

            }
        });
    }
    public void GoForward(){
        if (bee.getRotation() == 0){
            y -= 200;
            bee.setTranslationY(y);
        }

        if (bee.getRotation()==90){
            x += 200;
            bee.setTranslationX(x);
        }

        if (bee.getRotation() == 180){
            y += 200;
            bee.setTranslationY(y);
        }

        if (bee.getRotation() == 270){
            x -= 200;
            bee.setTranslationX(x);
        }
    }
}
