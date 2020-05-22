package com.example.educational;

import androidx.appcompat.app.AlertDialog;
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

import com.example.FunAlgo.R;
import com.example.menu.SettingsPage;

import java.util.ArrayList;

public class Level8Page extends Level1Page  {
    final private int[] targetArea = { 532 , 249 };
    final private int[] nonForbiddenAreaX = { 0 , 133 , 266 , 399 , 399 , 399, 399 , 266 , 133 , 133 , 0 ,532};
    final private int[] nonForbiddenAreaY = { 491 ,491 , 491 , 491 , 370 , 249, 128 , 128, 128 , 249 , 249, 249};
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
    //sharedPreferences to update and save levels
    private SharedPreferences sp;
    private SharedPreferences.Editor et;
    // sharedPreferences for transport data to AchievementsPage
    private SharedPreferences sharedPreferencesA;
    private SharedPreferences.Editor editor;
    private int background;
    private ConstraintLayout level8Page;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level8_page);
        level8Page = findViewById(R.id.level8_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        level8Page.setBackgroundResource(background);
        //starting activity
        Intent i = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        //setting the avatar
        avatarID = sharedPreferences.getInt("avatar", 0);
        avatar = AppCompatDrawableManager.get().getDrawable(Level8Page.this, avatarID);
        hero = findViewById(R.id.hero);
        hero.setBackground(avatar);

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
        spinnerKey.setAdapter(timesAdapter);
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);
        spinnerKey = findViewById(R.id.spinnerKey);

        //to add the buttons to the linear layout
        list = new ArrayList<String>();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);

        princessID = R.drawable.princess;
        princess = AppCompatDrawableManager.get().getDrawable(this, princessID);
        heroHasKey = false;

        //to get the first location of the hero
        heroX = hero.getTranslationX();
        heroY = hero.getTranslationY();

        isGameOver = false;

        //SharedPreferences to save Level
        sp = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE);
        et = sp.edit();

        //when the user click the BACK button
        isFinished(Level8Page.this, "8", 14, 18);
         back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent i = new Intent(Level8Page.this,LevelPage.class);
        startActivity(i);
        }
        });

         //when the user click the SETTINGS button
         settings.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent i = new Intent(Level8Page.this, SettingsPage.class);
        startActivity(i);
        }
        });

         //when the user click the VOLUME button
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
        //when the user click the INFO button
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

        //when the user click the RESET button
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset();
                recreate();
                code = "";
            }
        });

        //when the user click the APPLY button
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply.setEnabled(false);
                ApplyMove applyMove = new ApplyMove(hero,list,133,121,targetArea,nonForbiddenAreaX,nonForbiddenAreaY,0,0,0,0,null,null,key,266,128, movementsCount);
                Thread t1 = new Thread(applyMove);
                t1.start();

                //finish the game if the necessary conditions are met
                if (hero.getX() == 266 && hero.getY() == 128 && heroHasKey){
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

                //show the finish screen if the game is over

                 if (isGameOver == true){
                     et.putBoolean("finished8", isGameOver);
                     finishedScreen(Level8Page.this, movementsCount,23,27);
                     sharedPreferencesA = getSharedPreferences("starsData", MODE_PRIVATE);
                     editor = sharedPreferencesA.edit();
                     starsCount = sharedPreferencesA.getInt("starsCount", 1);
                     editor.putInt("starsCountLevel8", starsCount);
                     editor.commit();
                 }
            }
        });
        //when the user click the GO FORWARD button
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

        getKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = getNectarButton(timesKey, layout1, layout2, list, movementsCount, movements, spinnerKey,"NECTAR");
            }
        });
    }


    /** This method is used to reset the game.
     * @param
     * @return
     **/
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

    /** This method is used to move the character forward according to its rotation.
     * @param
     * @return
     **/
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

    /** This method is used to turn the character right.
     * @param
     * @return
     **/
    public void TurnRight(){
        hero.setRotation(hero.getRotation() + (90));

    }

    /** This method is used to turn the character left.
     * @param
     * @return
     **/
    public void TurnLeft(){
        hero.setRotation(hero.getRotation() - (90));

    }

    /** This method is used to get the key.
     * @param
     * @return
     **/
    public void GetKey(){
        if (hero.getX() == 0 && hero.getY() == 249) {
            key.setVisibility(View.INVISIBLE);
            heroHasKey = true;
        }
    }

    /** This method is used to give an error message
     * when the user goes the wrong place.
     * @param
     * @return
     **/
     public void TryAgain() {

     AlertDialog.Builder builder = new AlertDialog.Builder(Level8Page.this);
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
        SharedPreferences sharedPref = Level8Page.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CODEMESSAGE", codeMessage);
        editor.commit();
    }

    @Override
    public void setCodeMessage() {
        SharedPreferences sharedPref = Level8Page.this.getPreferences(Context.MODE_PRIVATE);
        code += sharedPref.getString("CODEMESSAGE", "") + "\n";
    }
}
