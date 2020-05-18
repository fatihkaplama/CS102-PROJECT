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
import android.media.Image;
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

import org.w3c.dom.Text;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

public class Level1Page extends DefaultLevelPage implements ShowCodeI {
    final private int[] targetArea = {600, 184};
    final private int[] nonForbiddenAreaX = {200, 400, 600};
    final private int[] nonForbiddenAreaY = {184, 184, 184};
    private TextView nu;
    private boolean isSelected;
    private boolean isSelected2;
    private boolean heroHasKey;
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
    private int starsCount;
    private boolean isGameOver;
    private float beeX;
    private float beeY;
    private float honeyX;
    private float honeyY;
    private boolean isVolumeOn;
    private int movementsCount;
    private Button show;
    private String code;
    private boolean isTryAgain;
    final static private int changeX = 200;
    final static private int changeY = 180;
    //sharedPreferences to update and save levels
    private SharedPreferences sp;
    private SharedPreferences.Editor et;
    // sharedPreferences for transport data to AchievementsPage
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    //sharedPreferences to move accordingly
    SharedPreferences sharedP;
    SharedPreferences.Editor etS;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_page);
        //starting activity
        Intent i = getIntent();

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
        //flower

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

        isGameOver = false;
        isTryAgain = false;
        isSelected = false;
        movementsCount = 0;

        //SharedPreferences to save Level
        sp = getSharedPreferences("isFinishedBooleans", MODE_PRIVATE);
        et = sp.edit();

        sharedP = getSharedPreferences("isThis", MODE_PRIVATE);
        etS = sharedP.edit();
        etS.clear();
        isTryAgain = getSharedPreferences("isThis", MODE_PRIVATE).getBoolean("isTry", false);
        isGameOver = getSharedPreferences("isThis", MODE_PRIVATE).getBoolean("isOver", false);
        movementsCount = getSharedPreferences("isThis", MODE_PRIVATE).getInt("movements", 0);
        isFinished(Level1Page.this, "1", 20 , 30);
        if (isTryAgain) {
            TryAgain(Level1Page.this);
            etS.putBoolean("isTry", false);
            etS.commit();
        }


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
                apply.setEnabled(false);
                ApplyMove applyMove = new ApplyMove(bee, list, changeX, changeY, targetArea, nonForbiddenAreaX, nonForbiddenAreaY, null, null, null, null, 0, 0, 0, 0, null, null,null,0,0, movementsCount);
                Thread t1 = new Thread(applyMove);
                t1.start();
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
    }

    public int goForwardButton(int timesForward, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int count, int movementsCount, TextView movements, Spinner spinnerForward) {
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
        if (movementsCount >= 9) {
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

        if (movementsCount < 9) {
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

    public int turnLeftButton(int timesForward, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int count, int movementsCount, TextView movements, Spinner spinnerLeft) {
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
        if (movementsCount >= 9) {
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
        if (movementsCount < 9) {
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

    @SuppressLint("SetTextI18n")
    public int turnRightButton(int timesForward, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int count, int movementsCount, TextView movements, Spinner spinnerRight) {
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
        if (movementsCount >= 9) {
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
        if (movementsCount < 9) {
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

    public int getNectarButton(int timesNectar, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int movementsCount, TextView movements, Spinner spinnerNectar, String object) {
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
        if (movementsCount >= 9) {
            list.add("nectar" + timesNectar);
            Button nectar = new Button(Level1Page.this);
            nectar.setTextSize(10);
            nectar.setText(timesNectar + " " + "GET " + object);
            nectar.setBackgroundColor(Color.CYAN);
            layout2.addView(nectar, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        if (movementsCount < 9) {
            list.add("nectar" + timesNectar);
            Button nectar = new Button(Level1Page.this);
            nectar.setTextSize(10);
            nectar.setText(timesNectar + " " + "GET "+ object);
            nectar.setBackgroundColor(Color.CYAN);
            layout1.addView(nectar, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        return movementsCount;
    }

    public void GetNectar(ImageView bee, ImageView flower, ImageView flower2, Drawable flower0, Drawable flower00, int valueX1, int valueX2, int valueY1, int valueY2, final TextView nu, final TextView nu2,final ImageView nu3, int valueX3, int valueY3) {
        if (bee.getX() == valueX1 && bee.getY() == valueY1) {
            isSelected = true;
            System.out.println("çalıştı1");
            // flower.setBackground(flower0);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nu.setText("0");

                }
            });
        }
        if (bee.getX() == valueX2 && bee.getY() == valueY2) {
            isSelected2 = true;
            System.out.println("çalıştı2");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nu2.setText("0");
                }
            });
            // flower2.setBackground(flower00);
        }
        if (bee.getX() == valueX3 && bee.getY() == valueY3) {
            System.out.println("çalıştı1");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nu3.setVisibility(View.INVISIBLE);
                    heroHasKey = true;
                }
            });
        }        System.out.print("almadı");
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

    public void isFinished(Context context, String level, int lower, int upper){
        if (isGameOver) {
            // for arman achievements
            System.out.println("Movements : " + movementsCount);
            et.putBoolean("finished" + level, true);
            finishedScreen(context, movementsCount, lower, upper);
            sharedPreferences = getSharedPreferences("starsData", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            starsCount = sharedPreferences.getInt("starsCount", 3);
            editor.putInt("starsCountLevel" + level, starsCount);
            editor.commit();
            System.out.println("level" + level + " stars " + starsCount);
            et.commit();
            //for finish screen
            etS.putBoolean("isOver", false);
            etS.commit();
        }
    }

    public class ApplyMove implements Runnable {
        ArrayList<String> list;
        ImageView bee;
        int movements;
        int changeX;
        int changeY;
        int[] target;
        int[] nonForbiddenAreaX;
        int[] nonForbiddenAreaY;
        boolean isForbidden;
        ImageView flower;
        ImageView flower2;
        Drawable flower0;
        Drawable flower00;
        int valueX1;
        int valueX2;
        int valueY1;
        int valueY2;
        TextView nu;
        TextView nu2;
        ImageView nu3;
        int valueX3;
        int valueY3;
        public ApplyMove(ImageView bee, ArrayList<String> list, int changeX, int changeY, int[] target, int[] nonForbiddenAreaX, int[] nonForbiddenAreaY,
                         ImageView flower, ImageView flower2, Drawable flower0, Drawable flower00, int valueX1, int valueX2, int valueY1, int valueY2, TextView nu, TextView nu2, ImageView nu3, int valueX3,int valueY3, int movements) {
            this.bee = bee;
            this.list = list;
            this.changeX = changeX;
            this.changeY = changeY;
            this.target = target;
            this.nonForbiddenAreaX = nonForbiddenAreaX;
            this.nonForbiddenAreaY = nonForbiddenAreaY;
            isForbidden = true;
            this.flower = flower;
            this.flower2 = flower2;
            this.flower0 = flower0;
            this.flower00 = flower00;
            this.valueX1 = valueX1;
            this.valueX2 = valueX2;
            this.valueY1 = valueY1;
            this.valueY2 = valueY2;
            this.nu = nu;
            this.nu2 = nu2;
            this.nu3 = nu3;
            this.valueX3 = valueX3;
            this.valueY3 = valueY3;
            this.movements = movements;
        }

        public void run() {
            try {
                for (int i = 0; i < list.size(); i++) {
                    isForbidden = true;
                    Thread.sleep(1000);
                    if (list.get(i).equals("forward1")) {
                        GoForward(bee, changeX, changeY);
                    } else if (list.get(i).equals("forward2")) {
                        for (int k = 0; k < 2; k++) {
                            GoForward(bee, changeX, changeY);
                        }
                    } else if (list.get(i).equals("forward3")) {
                        for (int k = 0; k < 3; k++) {
                            GoForward(bee, changeX, changeY);
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
                    } else if (list.get(i).equals("nectar1")) {
                        GetNectar(bee, flower, flower2, flower0, flower00, valueX1, valueX2, valueY1, valueY2, nu, nu2,nu3,valueX3, valueY3);
                    } else if (list.get(i).equals("nectar2")) {
                        for (int k = 0; k < 2; k++) {
                            GetNectar(bee, flower, flower2, flower0, flower00, valueX1, valueX2, valueY1, valueY2, nu, nu2,nu3,valueX3, valueY3);
                        }
                    } else if (list.get(i).equals("nectar3")) {
                        for (int k = 0; k < 3; k++) {
                            GetNectar(bee, flower, flower2, flower0, flower00, valueX1, valueX2, valueY1, valueY2, nu, nu2,nu3,valueX3, valueY3);
                        }
                    }if(nu3 != null) {
                        if (heroHasKey && (bee.getX() == target[0]) && (bee.getY() == target[1])) {
                            etS.putBoolean("isOver", true);
                            etS.commit();
                            Intent j = getIntent();
                            finish();
                            startActivity(j);
                        }
                    }
                    else if (nu2 == null && nu == null) {
                        if ((bee.getX() == target[0]) && (bee.getY() == target[1])) {
                            etS.putBoolean("isOver", true);
                            etS.commit();
                            Intent j = getIntent();
                            finish();
                            startActivity(j);
                        }
                    }
                    else if (nu2 == null && nu != null) {
                        if (isSelected && (bee.getX() == target[0]) && (bee.getY() == target[1])) {
                            etS.putBoolean("isOver", true);
                            etS.commit();
                            Intent j = getIntent();
                            finish();
                            startActivity(j);
                        }
                    }
                    else if (nu2 != null && nu != null) {
                        if (isSelected2 && isSelected&& (bee.getX() == target[0]) && (bee.getY() == target[1])) {
                            etS.putBoolean("isOver", true);
                            etS.commit();
                            Intent j = getIntent();
                            finish();
                            startActivity(j);
                        }
                    }
                    for (int t = 0; t < nonForbiddenAreaY.length; t++) {
                        if ((bee.getX() == nonForbiddenAreaX[t] && bee.getY() == nonForbiddenAreaY[t]))
                            isForbidden = false;
                    }
                    if (isForbidden) {
                        etS.putBoolean("isTry", true);
                        etS.commit();
                        Intent j = getIntent();
                        finish();
                        startActivity(j);
                        break;
                    }
                }
            } catch (InterruptedException e) {
            }
            System.out.println("mov : " + movements);
            etS.putInt("movements" , movements);
            etS.commit();
        }
    }

/**
 public void MoveLoop(ArrayList<String> list, ImageView bee, int changeX,
 int changeY, ImageView flower, ImageView flower2, Drawable flower0, Drawable
 flower00,
 int valueX1, int valueX2, int valueY1, int valueY2) {
 System.out.println("deneme");
 for (int i = 0; i < list.size(); i++) {
 if (list.get(i).equals("forward1")) {
 GoForward(bee, changeX, changeY);
 } else if (list.get(i).equals("forward2")) {
 for (int k = 0; k < 2; k++) {
 GoForward(bee, changeX, changeY);
 }
 } else if (list.get(i).equals("forward3")) {
 for (int k = 0; k < 3; k++) {
 GoForward(bee, changeX, changeY);
 }
 } else if (list.get(i).equals("left1")) {
 TurnLeft(bee);
 } else if (list.get(i).equals("left2")) {
 for (int k = 0; k < 2; k++) {
 TurnLeft(bee);
 }
 } else if (list.get(i).equals("left3")) {
 for (int k = 0; k < 3; k++) {
 TurnLeft(bee);
 }
 } else if (list.get(i).equals("right1")) {
 TurnRight(bee);
 } else if (list.get(i).equals("right2")) {
 for (int k = 0; k < 2; k++) {
 TurnRight(bee);
 }
 } else if (list.get(i).equals("right3")) {
 for (int k = 0; k < 3; k++) {
 TurnRight(bee);
 }
 } else if (list.get(i).equals("nectar1")) {
 GetNectar(bee, flower, flower2, flower0, flower00, valueX1, valueX2, valueY1, valueY2);
 } else if (list.get(i).equals("nectar2")) {
 for (int k = 0; k < 2; k++) {
 GetNectar(bee, flower, flower2, flower0, flower00, valueX1, valueX2, valueY1, valueY2);
 }
 } else if (list.get(i).equals("nectar3")) {
 for (int k = 0; k < 3; k++) {
 GetNectar(bee, flower, flower2, flower0, flower00, valueX1, valueX2, valueY1, valueY2);
 }
 }
 }
 }
 */
}
