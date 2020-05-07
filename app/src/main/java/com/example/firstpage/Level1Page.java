package com.example.firstpage;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Level1Page extends AppCompatActivity {
    Spinner spinnerForward;
    Spinner spinnerLeft;
    Spinner spinnerRight;
    Integer[] times = {1,2,3};
    ArrayAdapter<Integer> timesAdapter;
    ArrayList<String> list;
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
    Button apply;
    Button reset;
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout.LayoutParams params;
    float x;
    float y;
    int count = 0;
    int timesForward;
    int timesLeft;
    int timesRight;
    boolean isGameOver;
    float beeX;
    float beeY;
    float honeyX;
    float honeyY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_page);
        reset = findViewById(R.id.reset);
        apply = findViewById(R.id.apply);
        bee = findViewById(R.id.bee);
        honey = findViewById(R.id.honey);
        goForward = findViewById(R.id.goForward);
        turnLeft = findViewById(R.id.turnLeft);
        turnRight = findViewById(R.id.turnRight);
        settings = findViewById(R.id.settings);
        volume = findViewById(R.id.volume);
        back = findViewById(R.id.back);
        info = findViewById(R.id.info);
        layout1 = findViewById(R.id.leftLayout1);
        layout2 = findViewById(R.id.leftLayout2);
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);
        timesAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, times);
        timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForward.setAdapter(timesAdapter);
        spinnerRight.setAdapter(timesAdapter);
        spinnerLeft.setAdapter(timesAdapter);
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);
        up = new Button(this);
        left = new Button(this);
        right = new Button(this);
        list = new ArrayList<String>();
        up.setText("GO FORWARD");
        up.setBackgroundColor(Color.CYAN);
        left.setText("TURN LEFT");
        left.setBackgroundColor(Color.CYAN);
        right.setText("TURN RIGHT");
        right.setBackgroundColor(Color.CYAN);
        beeX = bee.getTranslationX();
        beeY = bee.getTranslationY();
        honeyX = honey.getTranslationX();
        honeyY = honey.getTranslationY();

        isGameOver = false;

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset();
                recreate();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals("forward1")) {
                        GoForward();
                    } else if (list.get(i).equals("forward2")) {
                        for (int k = 0; k < 2; k++) {
                            GoForward();
                        }
                    } else if (list.get(i).equals("forward3")) {
                        for (int k = 0; k < 3; k++) {
                            GoForward();

                        }
                    }
                    if (list.get(i).equals("left1")) {
                        TurnLeft();
                    }
                    if (list.get(i).equals("left2")) {
                        for (int k = 0; k < 2; k++) {
                            TurnLeft();
                        }
                    }
                    if (list.get(i).equals("left3")) {
                        for (int k = 0; k < 3; k++) {
                            TurnLeft();
                        }
                    }
                    if (list.get(i).equals("right1")) {
                        TurnRight();
                    }
                    if (list.get(i).equals("right2")) {
                        for (int k = 0; k < 2; k++) {
                            TurnRight();
                        }
                    }
                    if (list.get(i).equals("right3")) {
                        for (int k = 0; k < 3; k++) {
                            TurnRight();
                        }
                    }
                }
                apply.setEnabled(false);
                if ((bee.getX() == 600) && (bee.getY() == 11)){
                    System.out.println("true");
                    isGameOver = true;

                }


                if (isGameOver == true){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Level1Page.this);
                    View myView = getLayoutInflater().inflate(R.layout.finishscreen, null);
                    TextView message = myView.findViewById(R.id.message);
                    ImageView star1 = myView.findViewById(R.id.star1);
                    ImageView star2 = myView.findViewById(R.id.star2);
                    ImageView star3 = myView.findViewById(R.id.star3);
                    Button menu = (Button) myView.findViewById(R.id.menubtn);
                    Button retry = (Button) myView.findViewById(R.id.retrybtn);
                    Button continuebtn = (Button) myView.findViewById(R.id.continuebtn);
                    retry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recreate();
                        }
                    });
                    builder.setView(myView);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    continuebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }
        });

        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timesForward = (Integer)spinnerForward.getSelectedItem();
                if (count >= 9){
                    list.add("forward" + timesForward);
                    Button forward = new Button(Level1Page.this);
                    forward.setTextSize(10);
                    forward.setText(timesForward + " " + "GO FORWARD");
                    forward.setBackgroundColor(Color.CYAN);
                    layout2.addView(forward, params);
                    count++;
                }

                if (count < 9) {
                    list.add("forward" + timesForward);
                    Button forward = new Button(Level1Page.this);
                    forward.setTextSize(10);
                    forward.setText(timesForward + " " + "GO FORWARD");
                    forward.setBackgroundColor(Color.CYAN);
                    layout1.addView(forward, params);
                    count++;
                }
            }
        });

        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timesLeft = (Integer)spinnerLeft.getSelectedItem();
                if (count >= 9){
                    list.add("left" + timesLeft);
                    Button left = new Button(Level1Page.this);
                    left.setTextSize(10);
                    left.setText(timesLeft + " " + "TURN LEFT");
                    left.setBackgroundColor(Color.CYAN);
                    layout2.addView(left, params);
                    count++;
                }
                if (count < 9) {
                    list.add("left" + timesLeft);
                    Button left = new Button(Level1Page.this);
                    left.setTextSize(10);
                    left.setText(timesLeft + " " + "TURN LEFT");
                    left.setBackgroundColor(Color.CYAN);
                    layout1.addView(left, params);
                    count++;
                }
            }
        });

        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timesRight = (Integer) spinnerRight.getSelectedItem();
                if (count >= 9){
                    list.add("right" + timesRight);
                    Button right = new Button(Level1Page.this);
                    right.setTextSize(10);
                    right.setText(timesRight + " " + "TURN RIGHT");
                    right.setBackgroundColor(Color.CYAN);
                    layout2.addView(right, params);
                    count++;
                }
                if (count < 9) {
                    list.add("right" + timesRight);
                    Button right = new Button(Level1Page.this);
                    right.setTextSize(10);
                    right.setText(timesRight + " " + "TURN RIGHT");
                    right.setBackgroundColor(Color.CYAN);
                    layout1.addView(right, params);
                    count++;
                }
            }
        });
    }

    public void reset(){
        count = 0;
        layout1.removeAllViewsInLayout();
        int size = list.size();
        for (int i = 0; i < size; i++){
            list.remove(0);
        }
        if (!list.isEmpty()){
            System.out.println(list.get(0));
        }
        x = 0;
        y = 0;
        timesForward = 0;
        timesRight = 0;
        timesLeft = 0;
        bee.setTranslationX(beeX);
        bee.setTranslationY(beeY);
        bee.setRotation(90);
        apply.setEnabled(true);
    }
    public void GoForward(){
        if (bee.getRotation() == 0){
            y -= (180);
            bee.setTranslationY(y);

            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 90){
            x += (200);
            bee.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 180){
            y += (180);
            bee.setTranslationY(y);
            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);
        }

        if (bee.getRotation() == -90){
            x -= (200);
            bee.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);
        }
        System.out.println(bee.getX());
        System.out.println(bee.getY());
    }

    public void TurnRight(){

        bee.setRotation(bee.getRotation() + (90));
    }
    public void TurnLeft(){

        bee.setRotation(bee.getRotation() - (90));
    }
}
