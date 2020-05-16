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

public class Level6Page extends Level1Page {

    private TextView movements;
    private Spinner spinnerForward;
    private Spinner spinnerLeft;
    private Spinner spinnerRight;
    private Spinner spinnerNectar;
    private Integer[] times = {1,2,3};
    private ArrayAdapter<Integer> timesAdapter;
    private ArrayList<String> list;
    private static ImageView bee;
    private ImageView flower2;
    private ImageView flower3;
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
    private int starsCount;
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

    public static ImageView getBee() {
        return bee;
    }

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
        spinnerNectar = findViewById(R.id.spinnerNectar);

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
                MoveLoop(list, bee, 160, 146, flower2, flower3, flower0, flower00, 312, 152, 146, 292);
                for (int i = 0; i < list.size(); i++) {

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
                    finishedScreen(Level6Page.this, movementsCount,12,14);
                    SharedPreferences sharedPreferences = getSharedPreferences("starsData", MODE_PRIVATE);
                    starsCount = sharedPreferences.getInt("starsCount", 1);
                    et.putInt("starsCountLevel6", starsCount);
                    et.commit();
                }
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
                if (movementsCount >= 9){
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
                if (movementsCount < 9){
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

    @Override
    public void SaveData(String codeMessage) {
        SharedPreferences sharedPref = Level6Page.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CODEMESSAGE", codeMessage);
        editor.commit();
    }

    @Override
    public void setCodeMessage() {
        SharedPreferences sharedPref = Level6Page.this.getPreferences(Context.MODE_PRIVATE);
        code += sharedPref.getString("CODEMESSAGE", "") + "\n";
    }
}
