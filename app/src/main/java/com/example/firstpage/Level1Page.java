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

public class Level1Page extends DefaultLevelPage {
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
    private Button show;
    private String code;
    final static private int changeX = 200;
    final static private int changeY = 180;
    //sharedPreferences to update and save levels
    SharedPreferences sp;
    SharedPreferences.Editor et;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_page);
        //starting activity
        Intent i = getIntent();
        movementsCount = 0;
        //Views
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
        sp = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE);
        et = sp.edit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level1Page.this, LevelPage.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level1Page.this, SettingsPage.class);
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
                System.out.println(bee.getX());
                System.out.println(bee.getY());
                MoveLoop(list, bee, changeX, changeY);


                apply.setEnabled(false);
                if ((bee.getX() == 600) && (bee.getY() == 184)) {
                    System.out.println("true");
                    isGameOver = true;
                }
                if(!((bee.getY() == 184)&&((bee.getX() == 0)||(bee.getX() == 200)||(bee.getX() == 400)||(bee.getX() == 600))))  {
                    TryAgain(Level1Page.this);
                }

                if (isGameOver == true) {
                    et.putBoolean("finished1", true);
                    et.apply();
                    finishedScreen(Level1Page.this,movementsCount);
                }
            }
        });

        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = goForwardButton(timesForward, layout1, layout2, list, count, movementsCount, movements);
            }
        });

        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = turnLeftButton(timesForward, layout1, layout2, list, count, movementsCount, movements);
            }
        });

        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = turnRightButton(timesForward, layout1, layout2, list, count, movementsCount, movements);
            }
        });
    }

    public int goForwardButton (int timesForward, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int count, int movementsCount, TextView movements){
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
            Button forward = new Button(Level1Page.this);
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
            Button forward = new Button(Level1Page.this);
            forward.setTextSize(10);
            forward.setText(timesForward + " " + "GO FORWARD");
            forward.setBackgroundColor(Color.CYAN);
            layout1.addView(forward, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        return movementsCount;
    }

    public int turnLeftButton(int timesForward, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int count, int movementsCount, TextView movements){
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
            Button left = new Button(Level1Page.this);
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
            Button left = new Button(Level1Page.this);
            left.setTextSize(10);
            left.setText(timesLeft + " " + "TURN LEFT");
            left.setBackgroundColor(Color.CYAN);
            layout1.addView(left, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        return movementsCount;
    }

    public int turnRightButton(int timesForward, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int count, int movementsCount, TextView movements){
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
            Button right = new Button(Level1Page.this);
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
            Button right = new Button(Level1Page.this);
            right.setTextSize(10);
            right.setText(timesRight + " " + "TURN RIGHT");
            right.setBackgroundColor(Color.CYAN);
            layout1.addView(right, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        return movementsCount;
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


    public void GoForward(ImageView bee, int changeX, int changeY) {

        if (bee.getRotation() == 0) {
            y -= (changeY);
            bee.setTranslationY(y);
            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 90) {
            x += (changeX);
            bee.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 180) {
            y += (changeY);
            bee.setTranslationY(y);
            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 270) {
            x -= (changeX);
            bee.setTranslationX(x);
        }

        if (bee.getRotation() == -90) {
            x -= (changeX);
            bee.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }
        System.out.println( bee.getX());
        System.out.println( bee.getY());
    }

    public void TurnRight(ImageView bee) {

        bee.setRotation(bee.getRotation() + (90));
    }

    public void TurnLeft(ImageView bee) {

        bee.setRotation(bee.getRotation() - (90));
    }


    public void TryAgain(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        SharedPreferences sharedPref = Level1Page.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CODEMESSAGE", codeMessage);
        editor.commit();
    }

    public void setCodeMessage() {
        SharedPreferences sharedPref = Level1Page.this.getPreferences(Context.MODE_PRIVATE);
        code += sharedPref.getString("CODEMESSAGE", "") + "\n";
    }
    public void MoveLoop(ArrayList<String> list, ImageView bee, int changeX, int changeY){
        System.out.println("deneme");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("forward1")) {
                GoForward(bee,changeX,changeY);
            } else if (list.get(i).equals("forward2")) {
                for (int k = 0; k < 2; k++) {
                    GoForward(bee,changeX,changeY);
                }
            } else if (list.get(i).equals("forward3")) {
                for (int k = 0; k < 3; k++) {
                    GoForward(bee,changeX,changeY);
                }
            }
            if (list.get(i).equals("left1")) {
                TurnLeft(bee);
            }
            if (list.get(i).equals("left2")) {
                for (int k = 0; k < 2; k++) {
                    TurnLeft(bee);
                }
            }
            if (list.get(i).equals("left3")) {
                for (int k = 0; k < 3; k++) {
                    TurnLeft(bee);
                }
            }
            if (list.get(i).equals("right1")) {
                TurnRight(bee);
            }
            if (list.get(i).equals("right2")) {
                for (int k = 0; k < 2; k++) {
                    TurnRight(bee);
                }
            }
            if (list.get(i).equals("right3")) {
                for (int k = 0; k < 3; k++) {
                    TurnRight(bee);
                }
            }
        }
    }
}
