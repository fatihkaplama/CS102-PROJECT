package com.example.FunAlgo;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;

import java.util.ArrayList;

public class Level4Page extends Level1Page  {
    final private int[] targetArea = { 600 , 11 };
    final private int[] nonForbiddenAreaX = { 0, 200 , 200 , 200 , 400 , 400 , 600 };
    final private int[] nonForbiddenAreaY = { 551,551 , 371 , 191 , 191 , 11 , 11 };
    private TextView movements;
    private Spinner spinnerForward;
    private Spinner spinnerLeft;
    private Spinner spinnerRight;
    private Integer[] times = {1, 2, 3};
    private ArrayAdapter<Integer> timesAdapter;
    private ArrayList<String> list;
    private ImageView bee;
    private ImageView honey;
    private Button goForward;
    private Button turnRight;
    private Button turnLeft;
    private Button settings;
    private Button volume;
    private Button back;
    private Button info;
    private Button show;
    private String code;
    private Button apply;
    private Button reset;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout.LayoutParams params;
    private int volumeoffID;
    private int volumeonID;
    private Drawable volumeoff;
    private Drawable volumeon;
    private float x;
    private float y;
    private int starsCount;
    private int count = 0;
    private int timesForward;
    private int timesLeft;
    private int timesRight;
    private boolean isGameOver;
    private float beeX;
    private float beeY;
    private float honeyX;
    private float honeyY;
    private boolean isVolumeOn;
    private int movementsCount;
    //sharedPreferences to update and save levels
    private SharedPreferences sp;
    private SharedPreferences.Editor et;
    // sharedPreferences for transport data to AchievementsPage
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level4_page);
        //starting activity
        Intent i = getIntent();
        movementsCount = 0;
        //Views
        reset = findViewById(R.id.reset);
        apply = findViewById(R.id.apply);
        bee = findViewById(R.id.bee);
        honey = findViewById(R.id.flower);
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
        movements = findViewById(R.id.movements);
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
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);

        list = new ArrayList<String>();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);
        /*up = new Button(this);
        left = new Button(this);
        right = new Button(this);

        up.setText("GO FORWARD");
        up.setBackgroundColor(Color.CYAN);
        left.setText("TURN LEFT");
        left.setBackgroundColor(Color.CYAN);
        right.setText("TURN RIGHT");
        right.setBackgroundColor(Color.CYAN);*/


        beeX = bee.getTranslationX();
        beeY = bee.getTranslationY();
        honeyX = honey.getTranslationX();
        honeyY = honey.getTranslationY();

        isGameOver = false;
        //SharedPreferences to save Level
        sp = getSharedPreferences("isFinishedBooleans", MODE_PRIVATE);
        et = sp.edit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level4Page.this, LevelPage.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level4Page.this, SettingsPage.class);
                startActivity(i);
            }
        });

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVolumeOn) {
                    volume.setBackground(volumeoff);
                    isVolumeOn = false;
                    mediaPlayer.pause();
                } else {
                    volume.setBackground(volumeon);
                    isVolumeOn = true;
                    mediaPlayer.start();
                    ;
                }
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Bee needs to reach to hive. Help it with your algorithm!", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
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
                apply.setEnabled(false);
                ApplyMove applyMove = new ApplyMove(bee,list,200,180,targetArea,nonForbiddenAreaX,nonForbiddenAreaY,null,null,null,null,600,0,371,0,null,null);
                Thread t1 = new Thread(applyMove);
                t1.start();
/**
                if ((bee.getX() == 600) && (bee.getY() == 11)) {
                    System.out.println("true");
                    isGameOver = true;

                }
                if (((bee.getX() == 200) && (bee.getY() == 551)) || ((bee.getX() == 200) && (bee.getY() == 371)) || ((bee.getX() == 200) && (bee.getY() == 191)) || ((bee.getX() == 400) && (bee.getY() == 191))|| ((bee.getX() == 400) && (bee.getY() == 11))|| ((bee.getX() == 600) && (bee.getY() == 11))) {
                } else {
                    TryAgain();
                }
*/
/**
                if (isGameOver == true) {
                    et.putBoolean("finished4", isGameOver);
                    finishedScreen(Level4Page.this, movementsCount,9,10);
                    sharedPreferences = getSharedPreferences("starsData", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    starsCount = sharedPreferences.getInt("starsCount", 1);
                    editor.putInt("starsCountLevel4", starsCount);
                    editor.commit();
                }
 */
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

                if (count >= 9) {
                    list.add("forward" + timesForward);
                    Button forward = new Button(Level4Page.this);
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
                    Button forward = new Button(Level4Page.this);
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
                if (count >= 9) {
                    list.add("left" + timesLeft);
                    Button left = new Button(Level4Page.this);
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
                    Button left = new Button(Level4Page.this);
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
                if (count >= 9) {
                    list.add("right" + timesRight);
                    Button right = new Button(Level4Page.this);
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
                    Button right = new Button(Level4Page.this);
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
    }

    public void reset() {
        count = 0;
        layout1.removeAllViewsInLayout();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            list.remove(0);
        }
        if (!list.isEmpty()) {
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

    public void GoForward() {
        if (bee.getRotation() == 0) {
            y -= (180);
            bee.setTranslationY(y);

            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 90) {
            x += (200);
            bee.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 180) {
            y += (180);
            bee.setTranslationY(y);
            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 270) {
            x -= (200);
            bee.setTranslationX(x);
        }

        if (bee.getRotation() == -90) {
            x -= (200);
            bee.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }
        System.out.println( bee.getX());
        System.out.println( bee.getY());


    }

    public void TurnRight() {

        bee.setRotation(bee.getRotation() + (90));
    }

    public void TurnLeft() {

        bee.setRotation(bee.getRotation() - (90));
    }
    public void TryAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Level4Page.this);
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
        SharedPreferences sharedPref = Level4Page.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CODEMESSAGE", codeMessage);
        editor.commit();
    }

    public void setCodeMessage() {
        SharedPreferences sharedPref = Level4Page.this.getPreferences(Context.MODE_PRIVATE);
        code += sharedPref.getString("CODEMESSAGE", "") + "\n";
    }
}
