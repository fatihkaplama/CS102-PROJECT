package com.example.firstpage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class Level5Page extends AppCompatActivity implements  ShowCodeI {


    private TextView movements;
    private Spinner spinnerForward;
    private Spinner spinnerLeft;
    private Spinner spinnerRight;
    private Spinner spinnerNectar;
    private Integer[] times = {1,2,3};
    private ArrayAdapter<Integer> timesAdapter;
    private ArrayList<String> list;
    private ImageView bee;
    private ImageView flower;
    private ImageView flower2;
    private Button goForward;
    private Button turnRight;
    private Button turnLeft;
    private Button getNectar;
    private Button settings;
    private Button volume;
    private Button back;
    private Button info;
    private Button apply;
    private Button reset;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout.LayoutParams params;
    private int volumeoffID;
    private int volumeonID;
    private int flower0ID;
    private int flower00ID;
    private Drawable volumeoff;
    private Drawable volumeon;
    private Drawable flower0;
    private Drawable flower00;
    private float x;
    private float y;
    private int count = 0;
    private int timesForward;
    private int timesLeft;
    private int timesRight;
    private int timesNectar;
    private boolean isGameOver;
    private float beeX;
    private float beeY;
    private boolean isVolumeOn;
    private int movementsCount;
    private Button show;
    private String code;

    //sharedPreferences to update and save levels
    SharedPreferences sp;
    SharedPreferences.Editor et;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level5_page);
        //starting activity
        Intent i = getIntent();
        movementsCount = 0;
        //Views
        reset = findViewById(R.id.reset);
        apply = findViewById(R.id.apply);
        bee = findViewById(R.id.bee);
        flower = findViewById(R.id.flower);
        flower2 = findViewById(R.id.flower2);
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
        show = findViewById(R.id.showCode_button);
        code = "";

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
        flower00 = AppCompatDrawableManager.get().getDrawable(this, flower00ID);

        beeX = bee.getTranslationX();
        beeY = bee.getTranslationY();

        isGameOver = false;
        //SharedPreferences to save Level
        sp = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE);
        et = sp.edit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level5Page.this,LevelPage.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level5Page.this, SettingsPage.class);
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

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                if (code == "") {
                    toast = Toast.makeText(getApplicationContext(), "No code here yet.", Toast.LENGTH_LONG);
                } else {
                    toast = Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG);
                }
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset();
                recreate();
                code = "";
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
                if (flower.getBackground() == flower0 && flower2.getBackground() == flower0){
                    System.out.println("true");
                    isGameOver = true;

                }
                if (((bee.getX() == 394) && (bee.getY() == 180)) || ((bee.getX() == 394) && (bee.getY() == 360)) || ((bee.getX() == 594) && (bee.getY() == 360)) || ((bee.getX() == 194) && (bee.getY() == 360))|| ((bee.getX() == 194) && (bee.getY() == 540))) {
                } else {
                    TryAgain();
                }

                if (isGameOver == true){
                    et.putBoolean("finished5", isGameOver);
                    et.apply();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Level5Page.this);
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
                            Intent i = new Intent(Level5Page.this, HomePage.class);
                            startActivity(i);
                        }
                    });

                    continuebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Level5Page.this, LevelPage.class);
                            startActivity(i);
                        }
                    });
                }
            }
        });

        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeMessage;
                timesForward = (Integer) spinnerForward.getSelectedItem();
                if (timesForward == 1) {
                    codeMessage = "goForward();";
                } else {
                    codeMessage = "for(int i = 0 ; i < " + timesForward + " ; i++){\n" +
                            "goForward()\n}";
                }
                SaveData(codeMessage);
                setCodeMessage();
                if (count >= 9){
                    list.add("forward" + timesForward);
                    Button forward = new Button(Level5Page.this);
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
                    Button forward = new Button(Level5Page.this);
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
                String codeMessage;
                timesLeft = (Integer) spinnerLeft.getSelectedItem();
                if (timesLeft == 1) {
                    codeMessage = "turnLeft();";
                } else {
                    codeMessage = "for(int i = 0 ; i < " + timesLeft + " ; i++){\n" +
                            "turnLeft()\n}";
                }
                SaveData(codeMessage);
                setCodeMessage();
                if (count >= 9){
                    list.add("left" + timesLeft);
                    Button left = new Button(Level5Page.this);
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
                    Button left = new Button(Level5Page.this);
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
                String codeMessage;
                timesRight = (Integer) spinnerRight.getSelectedItem();
                if (timesRight == 1) {
                    codeMessage = "turnRight();";
                } else {
                    codeMessage = "for(int i = 0 ; i < " + timesRight + " ; i++){\n" +
                            "turnRight()\n}";
                }
                SaveData(codeMessage);
                setCodeMessage();
                if (count >= 9){
                    list.add("right" + timesRight);
                    Button right = new Button(Level5Page.this);
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
                    Button right = new Button(Level5Page.this);
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
                String codeMessage;
                timesNectar = (Integer) spinnerNectar.getSelectedItem();
                if (timesNectar == 1) {
                    codeMessage = "getNectar();";
                } else {
                    codeMessage = "for(int i = 0 ; i < " + timesNectar + " ; i++){\n" +
                            "getNectar()\n}";
                }
                SaveData(codeMessage);
                setCodeMessage();
                if (count >= 9){
                    list.add("nectar" + timesNectar);
                    Button nectar = new Button(Level5Page.this);
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
                    Button nectar = new Button(Level5Page.this);
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
            y -= (180);
            bee.setTranslationY(y);

            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 90){
            x += (200);
            bee.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 270){
            x -= (200);
            bee.setTranslationX(x);
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

    public void GetNectar(){
        if (bee.getX() == 594 && bee.getY() == 360) {
            flower.setBackground(flower0);

        }
        if (bee.getX() == 194 && bee.getY() == 540){
            flower2.setBackground(flower00);

        }
    }
    public void TryAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Level5Page.this);
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

    public void SaveData(String codeMessage) {
        SharedPreferences sharedPref = Level5Page.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CODEMESSAGE", codeMessage);
        editor.commit();
    }

    public void setCodeMessage() {
        SharedPreferences sharedPref = Level5Page.this.getPreferences(Context.MODE_PRIVATE);
        code += sharedPref.getString("CODEMESSAGE", "") + "\n";
    }
}
