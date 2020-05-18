package com.example.FunAlgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class Level3Page extends Level1Page {
    final private int[] targetArea = { 600 , 371 };
    final private int[] nonForbiddenAreaX = { 0 ,0 , 200 , 400 , 600};
    final private int[] nonForbiddenAreaY = { 551 , 371 , 371 , 371 , 371 };
    TextView nu;
    private TextView movements;
    private Spinner spinnerForward;
    private Spinner spinnerLeft;
    private Spinner spinnerRight;
    private Spinner spinnerNectar;
    private Integer[] times = {1, 2, 3};
    private ArrayAdapter<Integer> timesAdapter;
    private ArrayList<String> list;
    private ImageView bee;
    private ImageView flower;
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
    private Drawable volumeoff;
    private Drawable volumeon;
    private Drawable flower0;
    private float x;
    private float y;
    private int count = 0;
    private int starsCount;
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
    private SharedPreferences sp;
    private SharedPreferences.Editor et;
    // sharedPreferences for transport data to AchievementsPage
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int background;
    private ConstraintLayout level3Page;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_page);
        level3Page = findViewById(R.id.level3_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        level3Page.setBackgroundResource(background);
        //starting activity
        Intent i = getIntent();
        movementsCount = 0;
        //Views
        reset = findViewById(R.id.reset);
        apply = findViewById(R.id.apply);
        bee = findViewById(R.id.bee);
        flower = findViewById(R.id.flower);
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
        nu = findViewById(R.id.textView);

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
        spinnerNectar = findViewById(R.id.spinnerNectar);

        list = new ArrayList<String>();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);

        flower0ID = R.drawable.flower0;
        flower0 = AppCompatDrawableManager.get().getDrawable(this, flower0ID);

        beeX = bee.getTranslationX();
        beeY = bee.getTranslationY();

        isGameOver = false;
        //SharedPreferences to save Level
        sp = getSharedPreferences("isFinishedBooleans", MODE_PRIVATE);
        et = sp.edit();
        isFinished(Level3Page.this, "3", 5, 6);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level3Page.this, LevelPage.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level3Page.this, SettingsPage.class);
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
                Toast toast = Toast.makeText(getApplicationContext(), "Bee needs to reach to nectar. Help it with your algorithm!", Toast.LENGTH_LONG);
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
               // MoveLoop(list, bee, 200, 180, flower, null, flower0, null, 600, 0, 371, 0);

                //apply.setEnabled(false);
                apply.setEnabled(false);
                ApplyMove applyMove = new ApplyMove(bee,list,200,180,targetArea,nonForbiddenAreaX,nonForbiddenAreaY,flower,null,flower0,null,600,0,371,0,nu,null,null,0,0, movementsCount);
                Thread t1 = new Thread(applyMove);
                t1.start();


                /*if (nu.getText().toString() == "0") {
                    System.out.println("true");
                    isGameOver = true;

                }*/

                /*if (((bee.getX() == 0) && (bee.getY() == 371)) || ((bee.getX() == 200) && (bee.getY() == 371)) || ((bee.getX() == 400) && (bee.getY() == 371)) || ((bee.getX() == 600) && (bee.getY() == 371))) {
                } else {
                    TryAgain();
                }


                if (isGameOver == true) {
                    et.putBoolean("finished3", isGameOver);
                    finishedScreen(Level3Page.this, movementsCount,5,6);
                    sharedPreferences = getSharedPreferences("starsData", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    starsCount = sharedPreferences.getInt("starsCount", 1);
                    editor.putInt("starsCountLevel3", starsCount);
                    editor.commit();

                }*/

            }
        });

        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = goForwardButton(timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerForward);
            }
        });

        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = turnLeftButton(timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerLeft);
            }
        });

        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = turnRightButton(timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerRight);
            }
        });

        getNectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = getNectarButton(timesNectar, layout1, layout2, list, movementsCount, movements, spinnerNectar,"NECTAR");
            }
        });

    }
    public void TryAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Level3Page.this);
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
        SharedPreferences sharedPref = Level3Page.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CODEMESSAGE", codeMessage);
        editor.commit();
    }

    public void setCodeMessage() {
        SharedPreferences sharedPref = Level3Page.this.getPreferences(Context.MODE_PRIVATE);
        code += sharedPref.getString("CODEMESSAGE", "") + "\n";
    }
}
