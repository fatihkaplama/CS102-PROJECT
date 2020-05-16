package com.example.FunAlgo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;
import com.softmoore.android.graphlib.Label;
import com.softmoore.android.graphlib.Point;


public class AchievementsPage extends AppCompatActivity {
    private Button volume;
    private boolean isVolumeOn;
    private Drawable volumeoff;
    private Drawable volumeon;
    private int volumeoffID;
    private int volumeonID;
    private Button back;
    private String userName;
    private int avatarId;
    private TextView tv;
    private int background;
    private ConstraintLayout AchievementsPageLayout;
    private Graph graph;
    //For every level creating starsCountLevel
    private int starsCountLevel1;
    private int starsCountLevel2;
    private int starsCountLevel3;
    private int starsCountLevel4;
    private int starsCountLevel5;
    private int starsCountLevel6;
    private int starsCountLevel7;
    private int starsCountLevel8;
    private int starsCountLevel9;
    private SharedPreferences sharedPreferences;
    private Button wipeData;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_page);
        Intent i = getIntent();
        AchievementsPageLayout = findViewById(R.id.achievements_page_layout);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.start();
        background = getSharedPreferences("ShareTheme", MODE_PRIVATE).getInt("theme", 0);
        AchievementsPageLayout.setBackgroundResource(background);
        userName = i.getStringExtra("nickname");
        tv = findViewById(R.id.userName);
        tv.setText(userName);
        volume = findViewById(R.id.achievementsPage_voice_button);
        back = findViewById(R.id.achievementsPage_back_button);
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);
        wipeData = findViewById(R.id.button_wipeData);

        //initialising the starsCountLevels
        sharedPreferences = getSharedPreferences("starsData", MODE_PRIVATE);
        starsCountLevel1 = sharedPreferences.getInt("starsCount1", 0);
        starsCountLevel2 = sharedPreferences.getInt("starsCount2", 0);
        starsCountLevel3 = sharedPreferences.getInt("starsCount3", 0);
        starsCountLevel4 = sharedPreferences.getInt("starsCount4", 0);
        starsCountLevel5 = sharedPreferences.getInt("starsCount5", 0);
        starsCountLevel6 = sharedPreferences.getInt("starsCount6", 0);
        starsCountLevel7 = sharedPreferences.getInt("starsCount7", 0);
        starsCountLevel8 = sharedPreferences.getInt("starsCount8", 0);
        starsCountLevel9 = sharedPreferences.getInt("starsCount9", 0);

        // creating the graph with graphlib library
        Point[] points =
                {
                        new Point(1, starsCountLevel1), new Point(2, starsCountLevel2), new Point(3, starsCountLevel3),
                        new Point(4, starsCountLevel4), new Point(5, starsCountLevel5), new Point(6, starsCountLevel6),
                        new Point(7, starsCountLevel7), new Point(8, starsCountLevel8), new Point(9, starsCountLevel9)

                };
        
        Label[] xLabels =
                {
                        new Label(1, "level1"), new Label(2, "level2"), new Label(3, "level3"),
                        new Label(4, "level4"), new Label(5, "level5"), new Label(6, "level6"),
                        new Label(7, "level7"), new Label(8, "level8"), new Label(9, "level9")
                };

        graph = new Graph.Builder()
                .setWorldCoordinates(-1, 11, -1, 4)
                .setXLabels(xLabels)
                .setYTicks(new double[]{1, 2, 3,})
                .addLineGraph(points, Color.RED)
                .build();
        GraphView graphView = findViewById(R.id.graph_view);
        graphView.setGraph(graph);
        setTitle("Achievements");
        TextView textView = findViewById(R.id.graphAchievements_label);
        textView.setText("Your Achievements");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AchievementsPage.this, PlayPage.class);
                startActivity(intent);
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
                }
            }
        });
        wipeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starsCountLevel1 = 0;
                starsCountLevel2 = 0;
                starsCountLevel3 = 0;
                starsCountLevel4 = 0;
                starsCountLevel5 = 0;
                starsCountLevel6 = 0;
                starsCountLevel7 = 0;
                starsCountLevel8 = 0;
                starsCountLevel9 = 0;
            }
        });
    }
}