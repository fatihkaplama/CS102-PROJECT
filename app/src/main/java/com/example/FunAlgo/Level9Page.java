package com.example.FunAlgo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Timer;

public class Level9Page extends Level1Page {
    final private int[] targetArea = {665 , 128};
    final private int[] nonForbiddenAreaX = {0 , 133 , 266 , 266 , 266 , 133 , 133 , 133 , 266 , 399 , 532 , 532 , 532 , 532 , 665};
    final private int[] nonForbiddenAreaY = {491 , 491 , 491 , 370 , 249 , 249 , 128 , 7 , 7 , 7 , 7 , 128 , 249 , 370 , 128};
    //variables
    private TextView movements;
    private Spinner spinnerForward;
    private Spinner spinnerLeft;
    private Spinner spinnerRight;
    private Spinner spinnerKey;
    private Integer[] times = {1,2,3};
    private ArrayAdapter<Integer> timesAdapter;
    private ArrayList<String> list;
    private ImageView hero;
    private ImageView key;
    private ImageView prisoner;
    private Button goForward;
    private Button turnRight;
    private Button turnLeft;
    private Button getKey;
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
    private int princessID;
    private int starsCount;
    private Drawable volumeoff;
    private Drawable volumeon;
    private Drawable princess;
    private Drawable avatar;
    private float x;
    private float y;
    private int avatarID;
    private int count = 0;
    private int timesForward;
    private int timesLeft;
    private int timesRight;
    private int timesKey;
    private boolean isGameOver;
    private float heroX;
    private float heroY;
    private boolean isVolumeOn;
    private boolean heroHasKey;
    private int movementsCount;
    private Button show;
    private String code;

    private SharedPreferences sp;
    private SharedPreferences.Editor et;

    private SharedPreferences sharedPreferencesA;
    private SharedPreferences.Editor editor;
    private int background;
    private ConstraintLayout level9Page;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level9_page);

        Intent i =getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        avatarID =sharedPreferences.getInt("avatar", 0);
        avatar = AppCompatDrawableManager.get().getDrawable(Level9Page.this, avatarID);
        hero = findViewById(R.id.hero);
        hero.setBackground(avatar);

        movementsCount = 0;

        reset =findViewById(R.id.reset);
        apply = findViewById(R.id.apply);
        hero = findViewById(R.id.hero);
        key= findViewById(R.id.key);
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
        show = findViewById(R.id.showCode_button);
        code ="";

        isVolumeOn = true;
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.daybreaker);

        timesAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, times);
        timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForward.setAdapter(timesAdapter);
        spinnerRight.setAdapter(timesAdapter);
        spinnerLeft.setAdapter(timesAdapter);
        spinnerKey.setAdapter(timesAdapter);
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerKey);
        spinnerKey = findViewById(R.id.spinnerKey);

        list = new ArrayList<String>();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);

        princessID = R.drawable.princess;
        princess = AppCompatDrawableManager.get().getDrawable(this,princessID);
        heroHasKey = false;

        heroX = hero.getTranslationX();
        heroY = hero.getTranslationY();

        isGameOver = false;

        sp= getSharedPreferences("isFinishedBooleans", MODE_PRIVATE);
        et = sp.edit();

        isFinished(Level9Page.this, "9" , 17, 18);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level9Page.this, LevelPage.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level9Page.this, SettingsPage.class);
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
                    mediaPlayer.start();
                }
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),"user needs to get the keys to reach.", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                toast.show();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                if (code =="") {
                    toast = Toast.makeText(getApplicationContext(), "No code here yet.", Toast.LENGTH_LONG);
                }
                else {
                    toast = Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG);
                }
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL,0,0);
                toast.show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
                code ="";

            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply.setEnabled(false);
                ApplyMove applyMove = new ApplyMove(hero,list,133,121,targetArea,nonForbiddenAreaX,nonForbiddenAreaY,null,null,null,null,0,0,0,0,null,null,key,532,370, movementsCount);
                Thread t1 = new Thread(applyMove);
                t1.start();

                if(hero.getX() == 665 && hero.getY() == 128 && heroHasKey){
                    System.out.println("true");
                    isGameOver = true;
                }

                if(isGameOver == true){
                    et.putBoolean("finished9", isGameOver);
                    finishedScreen(Level9Page.this, movementsCount,23,27);
                    sharedPreferencesA = getSharedPreferences("starsData",MODE_PRIVATE);
                    editor = sharedPreferencesA.edit();
                    starsCount = sharedPreferencesA.getInt("starsCount", 1);
                    editor.putInt("starsCountLevel9", starsCount);
                    editor.commit();
                }
            }
        });
        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = goForwardButton(timesForward, layout1 , layout2 , list , count , movementsCount, movements , spinnerForward);
            }
        });
        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = turnLeftButton(timesForward, layout1 , layout2 , list ,count ,movementsCount, movements, spinnerLeft);
            }
        });
        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = turnRightButton(timesForward, layout1 , layout2 , list , count , movementsCount , movements , spinnerRight);
            }
        });
        getKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = getNectarButton(timesKey, layout1 , layout2 , list , movementsCount , movements , spinnerKey , "NECTAR");
            }
        });
    }
    public void reset(){
        count =0;
        layout1.removeAllViewsInLayout();
        int size = list.size();
        for(int i =0; i< size; i++){
            list.remove(0);
        }
        if(!list.isEmpty()){
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
        if(hero.getRotation() == 0){
            y -=(121);
            hero.setTranslationY(y);
        }
        if(hero.getRotation() == 90){
            x += (133);
            hero.setTranslationX(x);
        }
        if(hero.getRotation() == 360){
            y -= (121);
            hero.setTranslationY(y);
        }
        if(hero.getRotation() == 270){
            x -= (133);
            hero.setTranslationY(x);
        }
        if(hero.getRotation() == 180){
            y += (121);
            hero.setTranslationY(y);
        }
        if(hero.getRotation() == -270){
            x += (133);
            hero.setTranslationX(x);
        }
        if(hero.getRotation() == -90){
            x -= (133);
            hero.setTranslationX(x);
        }
        System.out.println(hero.getX());
        System.out.println(hero.getY());
    }
    public void TurnRight () {
        hero.setRotation(hero.getRotation() + (90));
    }

    public void TurnLeft () {
        hero.setRotation(hero.getRotation() - (90));
    }

    public void GetKey() {
        if (hero.getX() == 532 && hero.getY() == 370) {
            key.setVisibility(View.INVISIBLE);
            heroHasKey = true;
        }
    }
    public void TryAgain() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Level9Page.this);
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

    @Override
    public void SaveData(String codeMessage) {
        SharedPreferences sharedPref = Level9Page.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CODEMESSAGE", codeMessage);
        editor.commit();
    }

    @Override
    public void setCodeMessage () {
        SharedPreferences sharedPref = Level9Page.this.getPreferences(Context.MODE_PRIVATE);
        code += sharedPref.getString("CODE MESSAGE" , "") + "\n";
    }
}