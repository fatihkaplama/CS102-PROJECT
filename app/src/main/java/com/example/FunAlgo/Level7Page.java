package com.example.FunAlgo;

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
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.Timer;

public class Level7Page extends Level1Page {
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
    private Drawable volumeoff;
    private Drawable volumeon;
    private Drawable princess;
    private float x;
    private float y;
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
    private int avatarID;
    private Drawable avatar;
    private Button show;
    private String code;
    //sharedPreferences to update and save levels
    private SharedPreferences sp;
    private SharedPreferences.Editor et;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level7_page);
        //starting activity
        Intent i = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        //setting the avatar
        avatarID = sharedPreferences.getInt("avatar", 0);
        avatar = AppCompatDrawableManager.get().getDrawable(Level7Page.this, avatarID);
        hero = findViewById(R.id.hero);
        hero.setBackground(avatar);

        movementsCount = 0;
        //Views
        reset = findViewById(R.id.reset);
        apply = findViewById(R.id.apply);
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level7Page.this,LevelPage.class);
                startActivity(i);
            }
        });

        //when the user click the SETTINGS button
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level7Page.this, SettingsPage.class);
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
                    else if (list.get(i).equals("left2")) {
                        for (int k = 0; k < 2; k++) {
                            TurnLeft();
                        }
                    }
                    else if (list.get(i).equals("left3")) {
                        for (int k = 0; k < 3; k++) {
                            TurnLeft();
                        }
                    }
                    else if (list.get(i).equals("right1")) {
                        TurnRight();
                    }
                    else if (list.get(i).equals("right2")) {
                        for (int k = 0; k < 2; k++) {
                            TurnRight();
                        }
                    }
                    else if (list.get(i).equals("right3")) {
                        for (int k = 0; k < 3; k++) {
                            TurnRight();
                        }
                    }
                    else if (list.get(i).equals("key1")){
                        GetKey();
                    }
                    else if (list.get(i).equals("key2")) {
                        for (int k = 0; k < 2; k++) {
                            GetKey();
                        }
                    }
                    else if (list.get(i).equals("key3")) {
                        for (int k = 0; k < 3; k++) {
                            GetKey();
                        }
                    }
                }
                apply.setEnabled(false);
                //finish the game if the necessary conditions are met
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

                //show the finish screen if the game is over
                if (isGameOver == true){
                    et.putBoolean("finished7", isGameOver);
                    et.apply();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Level7Page.this);
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
                    builder.setView(myView);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    //when the user click the RETRY button in finish screen
                    retry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recreate();
                        }
                    });
                    //when the user click the MENU button in finish screen
                    menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Level7Page.this, HomePage.class);
                            startActivity(i);
                        }
                    });

                    //when the user click the CONTINUE button in finish screen
                    continuebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Level7Page.this, LevelPage.class);
                            startActivity(i);
                        }
                    });
                }
            }
        });
        //when the user click the GO FORWARD button
        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating the parameter for showButton
                String codeMessage;
                //to find out how many times the user will move forward
                timesForward = (Integer) spinnerForward.getSelectedItem();
                if (timesForward == 1) {
                    codeMessage = "goForward();";
                } else {
                    codeMessage = "for(int i = 0 ; i < " + timesForward + " ; i++){\n" +
                            "goForward()\n}";
                }
                SaveData(codeMessage);
                setCodeMessage();
                //if the number of buttons added is more than 9, add this button to the second layout
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
                    System.out.println(movementsCount);
                }
                //if the number of buttons added is less than 9, add this button to the first layout
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
                    System.out.println(movementsCount);
                }
            }
        });
        //when the user click the TURN LEFT button
        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating the parameter for showButton
                String codeMessage;
                //to find out how many times the user will turn left
                timesLeft = (Integer) spinnerLeft.getSelectedItem();
                if (timesLeft == 1) {
                    codeMessage = "turnLeft();";
                } else {
                    codeMessage = "for(int i = 0 ; i < " + timesLeft + " ; i++){\n" +
                            "turnLeft()\n}";
                }
                SaveData(codeMessage);
                setCodeMessage();
                //if the number of buttons added is more than 9, add this button to the second layout
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
                    System.out.println(movementsCount);
                }
                //if the number of buttons added is less than 9, add this button to the first layout
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
                    System.out.println(movementsCount);
                }
            }
        });
        //when the user click the TURN RIGHT button
        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating the parameter for showButton
                String codeMessage;
                //to find out how many times the user will turn right
                timesRight = (Integer) spinnerRight.getSelectedItem();
                if (timesRight == 1) {
                    codeMessage = "turnRight();";
                } else {
                    codeMessage = "for(int i = 0 ; i < " + timesRight + " ; i++){\n" +
                            "turnRight()\n}";
                }
                SaveData(codeMessage);
                setCodeMessage();
                //if the number of buttons added is more than 9, add this button to the second layout
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
                    System.out.println(movementsCount);
                }
                //if the number of buttons added is less than 9, add this button to the first layout
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
                    System.out.println(movementsCount);
                }
            }
        });
        //when the user click the GET KEY button
        getKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating the parameter for showButton
                String codeMessage;
                //to find out how many times the key will be taken
                timesKey = (Integer) spinnerKey.getSelectedItem();
                if (timesKey == 1) {
                    codeMessage = "turnRight();";
                } else {
                    codeMessage = "for(int i = 0 ; i < " + timesKey + " ; i++){\n" +
                            "turnRight()\n}";
                }
                SaveData(codeMessage);
                setCodeMessage();
                //if the number of buttons added is more than 9, add this button to the second layout
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
                    System.out.println(movementsCount);
                }
                //if the number of buttons added is less than 9, add this button to the first layout
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
                    System.out.println(movementsCount);
                }
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
        if (hero.getX() == 266 && hero.getY() == 130) {
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

    @Override
    public void SaveData(String codeMessage) {
        SharedPreferences sharedPref = Level7Page.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CODEMESSAGE", codeMessage);
        editor.commit();
    }

    @Override
    public void setCodeMessage() {
        SharedPreferences sharedPref = Level7Page.this.getPreferences(Context.MODE_PRIVATE);
        code += sharedPref.getString("CODEMESSAGE", "") + "\n";
    }
}
