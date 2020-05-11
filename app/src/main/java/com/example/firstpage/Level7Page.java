package com.example.firstpage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Level7Page extends AppCompatActivity {
    TextView movements;
    Spinner spinnerForward;
    Spinner spinnerLeft;
    Spinner spinnerRight;
    Spinner spinnerKey;
    Integer[] times = {1,2,3};
    ArrayAdapter<Integer> timesAdapter;
    ArrayList<String> list;
    ImageView hero;
    ImageView key;
    ImageView prisoner;
    Button goForward;
    Button turnRight;
    Button turnLeft;
    Button getKey;
    Button settings;
    Button volume;
    Button back;
    Button info;
    Button apply;
    Button reset;
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout.LayoutParams params;
    int volumeoffID;
    int volumeonID;
    int princessID;
    Drawable volumeoff;
    Drawable volumeon;
    Drawable princess;
    float x;
    float y;
    int count = 0;
    int timesForward;
    int timesLeft;
    int timesRight;
    int timesKey;
    boolean isGameOver;
    float heroX;
    float heroY;
    boolean isVolumeOn;
    boolean heroHasKey;
    int movementsCount;
    //sharedPreferences to update and save levels
    SharedPreferences sp;
    SharedPreferences.Editor et;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level7_page);
        //starting activity
        Intent i = getIntent();
        movementsCount = 0;
        //Views
        reset = findViewById(R.id.reset);
        apply = findViewById(R.id.apply);
        hero = findViewById(R.id.hero);
        key = findViewById(R.id.key);
        prisoner = findViewById(R.id.prisoner);
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
        spinnerKey = findViewById(R.id.spinnerKey);
        movements = findViewById(R.id.movements);
        getKey = findViewById(R.id.getKey);

        //volume
        isVolumeOn = true;
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.daybreaker);

        //spinner
        timesAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, times);
        timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForward.setAdapter(timesAdapter);
        spinnerRight.setAdapter(timesAdapter);
        spinnerLeft.setAdapter(timesAdapter);
        spinnerKey.setAdapter(timesAdapter);
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);
        spinnerKey = findViewById(R.id.spinnerKey);

        list = new ArrayList<String>();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);

        princessID = R.drawable.princess;
        princess = AppCompatDrawableManager.get().getDrawable(this, princessID);
        heroHasKey = false;

        heroX = hero.getTranslationX();
        heroX = hero.getTranslationY();

        isGameOver = false;
        //SharedPreferences to save Level
        sp = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE);
        et = sp.edit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level7Page.this,LevelPage.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level7Page.this, SettingsPage.class);
                startActivity(i);
            }
        });

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVolumeOn){
                    volume.setBackground(volumeoff);
                    isVolumeOn = false;
                    mediaPlayer.pause();
                }
                else {
                    volume.setBackground(volumeon);
                    isVolumeOn = true;
                    mediaPlayer.start();;
                }
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Bee needs to reach to nectar. Help it with your algorithm!", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

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

                    if (list.get(i).equals("key1")){
                        GetKey();
                    }
                    if (list.get(i).equals("key2")) {
                        for (int k = 0; k < 2; k++) {
                            GetKey();
                        }
                    }
                    if (list.get(i).equals("key3")) {
                        for (int k = 0; k < 3; k++) {
                            GetKey();
                        }
                    }
                }
                apply.setEnabled(false);
                if (hero.getX() == 532 && hero.getY() == 9 && heroHasKey){
                    System.out.println("true");
                    isGameOver = true;

                }
                /*if (((hero.getX() == 0) && (hero.getY() == 393)) || ((hero.getX() == 133) && (hero.getY() == 393)) || ((hero.getX() == 266) && (hero.getY() == 393)))  {
                } else if (((hero.getX() == 399) && (hero.getY() == 372)) || ((hero.getX() == 532) && (hero.getY() == 372)) || ((hero.getX() == 399) && (hero.getY() == 251))) {
                } else if (((hero.getX() == 133) && (hero.getY() == 130)) || ((hero.getX() == 266) && (hero.getY() == 130)) || (hero.getX() == 399) && (hero.getY() == 130)) {
                } else if ((hero.getX() == 532) && (hero.getY() == 130) || (hero.getX() == 665) && (hero.getY() == 130) || hero.getX() == 133 && hero.getY() == 9){
                } else if ((hero.getX() == 532 && hero.getY() == 9) || (hero.getX() == 399) && (hero.getY() == 393)){
                }
                else{
                    TryAgain();
                }*/


                if (isGameOver == true){

                    AlertDialog.Builder builder = new AlertDialog.Builder(Level7Page.this);
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
                            Intent i = new Intent(Level7Page.this, LevelPage.class);
                            et.putBoolean("finished7", isGameOver);
                            et.apply();
                            startActivity(i);
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
                    Button forward = new Button(Level7Page.this);
                    forward.setTextSize(10);
                    forward.setText(timesForward + " " + "GO FORWARD");
                    forward.setBackgroundColor(Color.CYAN);
                    layout2.addView(forward, params);
                    count++;
                    movementsCount++;
                    movements.setText("Movements : " + movementsCount);
                }

                if (count < 9) {
                    list.add("forward" + timesForward);
                    Button forward = new Button(Level7Page.this);
                    forward.setTextSize(10);
                    forward.setText(timesForward + " " + "GO FORWARD");
                    forward.setBackgroundColor(Color.CYAN);
                    layout1.addView(forward, params);
                    count++;
                    movementsCount++;
                    movements.setText("Movements : " + movementsCount);
                }
            }
        });

        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timesLeft = (Integer)spinnerLeft.getSelectedItem();
                if (count >= 9){
                    list.add("left" + timesLeft);
                    Button left = new Button(Level7Page.this);
                    left.setTextSize(10);
                    left.setText(timesLeft + " " + "TURN LEFT");
                    left.setBackgroundColor(Color.CYAN);
                    layout2.addView(left, params);
                    count++;
                    movementsCount++;
                    movements.setText("Movements : " + movementsCount);
                }
                if (count < 9) {
                    list.add("left" + timesLeft);
                    Button left = new Button(Level7Page.this);
                    left.setTextSize(10);
                    left.setText(timesLeft + " " + "TURN LEFT");
                    left.setBackgroundColor(Color.CYAN);
                    layout1.addView(left, params);
                    count++;
                    movementsCount++;
                    movements.setText("Movements : " + movementsCount);
                }
            }
        });

        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timesRight = (Integer) spinnerRight.getSelectedItem();
                if (count >= 9){
                    list.add("right" + timesRight);
                    Button right = new Button(Level7Page.this);
                    right.setTextSize(10);
                    right.setText(timesRight + " " + "TURN RIGHT");
                    right.setBackgroundColor(Color.CYAN);
                    layout2.addView(right, params);
                    count++;
                    movementsCount++;
                    movements.setText("Movements : " + movementsCount);
                }
                if (count < 9) {
                    list.add("right" + timesRight);
                    Button right = new Button(Level7Page.this);
                    right.setTextSize(10);
                    right.setText(timesRight + " " + "TURN RIGHT");
                    right.setBackgroundColor(Color.CYAN);
                    layout1.addView(right, params);
                    count++;
                    movementsCount++;
                    movements.setText("Movements : " + movementsCount);
                }
            }
        });

        getKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timesKey = (Integer) spinnerKey.getSelectedItem();
                if (count >= 9){
                    list.add("key" + timesKey);
                    Button nectar = new Button(Level7Page.this);
                    nectar.setTextSize(10);
                    nectar.setText(timesKey + " " + "GET KEY");
                    nectar.setBackgroundColor(Color.CYAN);
                    layout2.addView(nectar, params);
                    count++;
                    movementsCount++;
                    movements.setText("Movements : " + movementsCount);
                }
                if (count < 9){
                    list.add("key" + timesKey);
                    Button nectar = new Button(Level7Page.this);
                    nectar.setTextSize(10);
                    nectar.setText(timesKey + " " + "GET KEY");
                    nectar.setBackgroundColor(Color.CYAN);
                    layout1.addView(nectar, params);
                    count++;
                    movementsCount++;
                    movements.setText("Movements : " + movementsCount);
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
        hero.setTranslationX(heroX);
        hero.setTranslationY(heroY);
        hero.setRotation(90);
        apply.setEnabled(true);
    }
    public void GoForward(){
        if (hero.getRotation() == 0){
            y -= (121);
            hero.setTranslationY(y);

            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (hero.getRotation() == 90){
            x += (133);
            hero.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }
        if (hero.getRotation() == 360){
            y -= (121);
            hero.setTranslationY(y);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }

        if (hero.getRotation() == 270){
            x -= (133);
            hero.setTranslationX(x);
        }

        if (hero.getRotation() == 180){
            y += (121);
            hero.setTranslationY(y);
            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (hero.getRotation() == -270){
            x += (133);
            hero.setTranslationX(x);
        }

        if (hero.getRotation() == -90){
            x -= (133);
            hero.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }
        System.out.println(hero.getX());
        System.out.println(hero.getY());
    }

    public void TurnRight(){

        hero.setRotation(hero.getRotation() + (90));
        movementsCount++;
    }
    public void TurnLeft(){

        hero.setRotation(hero.getRotation() - (90));
        movementsCount++;
    }

    public void GetKey(){
        if (hero.getX() == 266 && hero.getY() == 130) {
            key.setVisibility(View.INVISIBLE);
            heroHasKey = true;
        }
    }
    public void TryAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Level7Page.this);
        View myView = getLayoutInflater().inflate(R.layout.tryagain, null);
        Button menu = (Button) myView.findViewById(R.id.menubtn);
        Button retry = (Button) myView.findViewById(R.id.retrybtn);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        builder.setView(myView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
