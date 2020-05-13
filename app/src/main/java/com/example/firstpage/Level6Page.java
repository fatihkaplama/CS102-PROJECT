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

public class Level6Page extends AppCompatActivity {

    TextView movements;
    Spinner spinnerForward;
    Spinner spinnerLeft;
    Spinner spinnerRight;
    Spinner spinnerNectar;
    Integer[] times = {1,2,3};
    ArrayAdapter<Integer> timesAdapter;
    ArrayList<String> list;
    ImageView bee;
    ImageView flower2;
    ImageView flower3;
    Button goForward;
    Button turnRight;
    Button turnLeft;
    Button getNectar;
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
    int flower0ID;
    int flower00ID;
    Drawable volumeoff;
    Drawable volumeon;
    Drawable flower0;
    Drawable flower00;
    float x;
    float y;
    int count = 0;
    int timesForward;
    int timesLeft;
    int timesRight;
    int timesNectar;
    boolean isGameOver;
    float beeX;
    float beeY;
    boolean isVolumeOn;
    int movementsCount;
    //sharedPreferences to update and save levels
    SharedPreferences sp;
    SharedPreferences.Editor et;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level6_page);
        //starting activity
        Intent i = getIntent();
        movementsCount = 0;
        //Views
        reset = findViewById(R.id.reset);
        apply = findViewById(R.id.apply);
        bee = findViewById(R.id.bee);
        flower2 = findViewById(R.id.flower2);
        flower3 = findViewById(R.id.flower3);
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
        spinnerNectar = findViewById(R.id.spinnerNectar);
        movements = findViewById(R.id.movements);
        getNectar = findViewById(R.id.getNectar);

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
        spinnerNectar.setAdapter(timesAdapter);
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);
        spinnerNectar = findViewById(R.id.spinnerKey);

        list = new ArrayList<String>();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);

        flower0ID = R.drawable.flower0;
        flower00ID = R.drawable.flower00;
        flower0 = AppCompatDrawableManager.get().getDrawable(this, flower0ID);
        flower00 = AppCompatDrawableManager.get().getDrawable(this,flower00ID);

        beeX = bee.getTranslationX();
        beeY = bee.getTranslationY();

        isGameOver = false;
        //SharedPreferences to save Level
        sp = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE);
        et = sp.edit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level6Page.this,LevelPage.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level6Page.this, SettingsPage.class);
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

                    if (list.get(i).equals("nectar1")){
                        GetNectar();
                    }
                    if (list.get(i).equals("nectar2")) {
                        for (int k = 0; k < 2; k++) {
                            GetNectar();
                        }
                    }
                    if (list.get(i).equals("nectar3")) {
                        for (int k = 0; k < 3; k++) {
                            GetNectar();
                        }
                    }
                }
                apply.setEnabled(false);
                if (flower2.getBackground() == flower0 && flower3.getBackground() == flower00 && bee.getX() == 632 && bee.getY() == 438){
                    System.out.println("true");
                    isGameOver = true;

                }
                if (((bee.getX() == 472) && (bee.getY() == 146)) || ((bee.getX() == 312) && (bee.getY() == 146)) || ((bee.getX() == 152) && (bee.getY() == 146))) {
                } else if (((bee.getX() == 152) && (bee.getY() == 292)) || ((bee.getX() == 152) && (bee.getY() == 438)) || ((bee.getX() == 312) && (bee.getY() == 438))) {
                } else if (((bee.getX() == 472) && (bee.getY() == 438)) || ((bee.getX() == 632) && (bee.getY() == 438))) {
                } else{
                    TryAgain();
                }


                if (isGameOver == true){
                    et.putBoolean("finished6", isGameOver);
                    et.apply();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Level6Page.this);
                    View myView = getLayoutInflater().inflate(R.layout.finishscreen, null);
                    TextView message = myView.findViewById(R.id.message);
                    ImageView star1 = myView.findViewById(R.id.star1);
                    ImageView star2 = myView.findViewById(R.id.star2);
                    ImageView star3 = myView.findViewById(R.id.star3);
                    if (movementsCount > 12){
                        star2.setVisibility(View.INVISIBLE);
                    }
                    if (movementsCount > 15){
                        star1.setVisibility(View.INVISIBLE);
                        star2.setVisibility(View.VISIBLE);
                        star3.setVisibility(View.INVISIBLE);
                    }
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
                            Intent i = new Intent(Level6Page.this, HomePage.class);
                            startActivity(i);
                        }
                    });

                    continuebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Level6Page.this, LevelPage.class);
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
                    Button forward = new Button(Level6Page.this);
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
                    Button forward = new Button(Level6Page.this);
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
                    Button left = new Button(Level6Page.this);
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
                    Button left = new Button(Level6Page.this);
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
                    Button right = new Button(Level6Page.this);
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
                    Button right = new Button(Level6Page.this);
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

        getNectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timesNectar = (Integer) spinnerNectar.getSelectedItem();
                if (count >= 9){
                    list.add("nectar" + timesNectar);
                    Button nectar = new Button(Level6Page.this);
                    nectar.setTextSize(10);
                    nectar.setText(timesNectar + " " + "GET NECTAR");
                    nectar.setBackgroundColor(Color.CYAN);
                    layout2.addView(nectar, params);
                    count++;
                    movementsCount++;
                    movements.setText("Movements : " + movementsCount);
                }
                if (count < 9){
                    list.add("nectar" + timesNectar);
                    Button nectar = new Button(Level6Page.this);
                    nectar.setTextSize(10);
                    nectar.setText(timesNectar + " " + "GET NECTAR");
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
        bee.setTranslationX(beeX);
        bee.setTranslationY(beeY);
        bee.setRotation(90);
        apply.setEnabled(true);
    }
    public void GoForward(){
        if (bee.getRotation() == 0){
            y -= (146);
            bee.setTranslationY(y);

            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 90){
            x += (160);
            bee.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }
        if (bee.getRotation() == 360){
            y -= (146);
            bee.setTranslationY(y);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 270){
            x -= (160);
            bee.setTranslationX(x);
        }

        if (bee.getRotation() == 180){
            y += (146);
            bee.setTranslationY(y);
            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == -90){
            x -= (160);
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

    public void GetNectar(){
        if (bee.getX() == 312 && bee.getY() == 146) {
            //flower2.setBackground(flower0);
            flower2.setBackgroundResource(flower0ID);

        }
        if (bee.getX() == 152 && bee.getY() == 292){
            flower3.setBackground(flower00);

        }
    }
    public void TryAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Level6Page.this);
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
