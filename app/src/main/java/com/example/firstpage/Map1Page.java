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
    Button left;
    Button right;
    LinearLayout layout;
    LinearLayout.LayoutParams params;
    Drawable upLogo;
    float x;
    float y;
    int forwardCount = 1;
    int leftCount = 1;
    int rightCount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1_page);
        bee = findViewById(R.id.bee);
        honey = findViewById(R.id.);
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
        left = new Button(this);
        right = new Button(this);
        up.setText("GO FORWARD");
        up.setBackgroundColor(Color.CYAN);
        left.setText("TURN LEFT");
        left.setBackgroundColor(Color.CYAN);
        right.setText("TURN RIGHT");
        right.setBackgroundColor(Color.CYAN);

        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (forwardCount == 1){
                    layout.addView(up,params);
                    GoForward();
                    forwardCount++;
                }
                else {
                    GoForward();
                }

            }
        });

        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftCount == 1){
                    layout.addView(left,params);
                    TurnLeft();
                    leftCount++;
                }
                else {
                    TurnLeft();
                }
            }
        });

        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightCount == 1){
                    layout.addView(right,params);
                    TurnRight();
                    rightCount++;
                }
                else {
                    TurnRight();
                }
            }
        });
    }
    public void GoForward(){
        if (bee.getRotation() == 0){
            y -= 180;
            bee.setTranslationY(y);
        }

        if (bee.getRotation()==90){
            x += 200;
            bee.setTranslationX(x);
        }

        if (bee.getRotation() == 180){
            y += 180;
            bee.setTranslationY(y);
        }

        if (bee.getRotation() == -90){
            x -= 200;
            bee.setTranslationX(x);
        }
    }

    public void TurnLeft(){

        bee.setRotation(bee.getRotation() - 90);
    }

    public void TurnRight(){

        bee.setRotation((bee.getRotation() + 90));
    }
}
