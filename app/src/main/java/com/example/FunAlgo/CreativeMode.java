package com.example.FunAlgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.creative.LineView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CreativeMode extends AppCompatActivity {
    private Button clockwise, counterClockwise, applyCreative, resetCreative, returnButton, volumeButton, settingsButton;
    private PointF pointA, pointB;
    private int starterX, starterY, pencilX, pencilY, startPointX;
    private double getDistance, getDegree;
    private ArrayList<Integer> listDegree;
    private ArrayList<Integer> listDistance;
    private LinearLayout linearLayout, linearLayout2;
    private LinearLayout linearView;
    private String[] degrees = {"0", "30", "45","60", "90", "120", "180"};
    private String[] distance = {"10", "30", "60", "100", "200"};
    private int distanceGo, degreesTurn;
    private android.widget.Spinner spinnerDegrees;
    private android.widget.Spinner spinnerDistance;
    private ArrayAdapter<String> dataAdapterForDistance;
    private ArrayAdapter<String> dataAdapterForDegrees;
    private Paint paint;
    private Canvas canvas;
    private Bitmap bitmap;
    private ImageView pencil;
    private TranslateAnimation translateAnimation;
    private int buttonLimit;
    private LineView lineView;
    private int countOfPieces;

    public int getButtonLimit() {
        return buttonLimit;
    }

    public void setButtonLimit(int buttonLimit) {
        this.buttonLimit = buttonLimit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creative_mode);
//        pencil = findViewById(R.id.pencil_creative);
        returnButton = findViewById(R.id.returnButton_creative);
        volumeButton = findViewById(R.id.volumeButton_creative);
        settingsButton = findViewById(R.id.settingButton_creative);
        clockwise = findViewById(R.id.clockwise_creative);
        counterClockwise = findViewById( R.id.counterClockwise_creative);
        applyCreative = findViewById(R.id.applyCreative);
        resetCreative = findViewById(R.id.resetCreative);
        linearLayout = findViewById(R.id.applyLayout);
        linearLayout2 = findViewById(R.id.applyLayout2);
        lineView = findViewById(R.id.lineView);
        buttonLimit = 0;
//        linearView = findViewById(R.id.linearView);
        spinnerDegrees = findViewById(R.id.degreeSpinner);
        spinnerDistance = findViewById(R.id.distanceSpinner);
        countOfPieces = 0;

        dataAdapterForDegrees = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, degrees);
        dataAdapterForDistance = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, distance);

        dataAdapterForDegrees.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForDistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDegrees.setAdapter(dataAdapterForDegrees);
        spinnerDistance.setAdapter(dataAdapterForDistance);

        listDegree = new ArrayList<Integer>();
        listDistance = new ArrayList<Integer>();

        clockwise.setOnTouchListener(new MyTouchListener());
        counterClockwise.setOnTouchListener( new MyTouchListener());

        linearLayout.setOnDragListener(new MyDragListener());
        linearLayout2.setOnDragListener(new MyDragListener());


        resetCreative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        applyCreative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler= new Handler();
                lineView.countListStarter();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Süre sonunda burada yer alan kodlar çalışır.
                        lineView.countOfMethod();
                        lineView.draw();
                        handler.postDelayed( this, 100);
                    }
                    // Kodların ne kadar süre sonra çalışacağını belirttik. Burada 1000 değeri ms (milisaniye)
                },100);
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( CreativeMode.this, SettingsPage.class);
                startActivity( intent);
            }
        });
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }


    private final class MyTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if ( event.getAction() == MotionEvent.ACTION_DOWN){
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data,shadowBuilder,v,0);
                v.setVisibility(View.VISIBLE);
                return true;
            }
            else {
                return false;
            }
        }
    }
    class MyDragListener implements View.OnDragListener{

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action){
                case DragEvent.ACTION_DRAG_STARTED:
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    LinearLayout container = (LinearLayout)v;
                    if( view.getId() == R.id.clockwise_creative && v == getLinearLayout()){
                        buttonLimit++;
                        if( buttonLimit == 8)
                            setLinearLayout(linearLayout2);
                        distanceGo = Integer.parseInt((String) spinnerDistance.getSelectedItem());
                        degreesTurn = Integer.parseInt((String) spinnerDegrees.getSelectedItem());
                        lineView.addListDegree( degreesTurn);
                        lineView.addListDistanceArray( distanceGo);
                        lineView.addListDistance( distanceGo);
                        clockwise = new Button(CreativeMode.this);
                        clockwise.setText( "↻" + distanceGo + "u " + degreesTurn + "°");
                        clockwise.setTextSize(10f);
                        clockwise.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,95));
                        container.addView( clockwise);
                        clockwise.setVisibility(View.VISIBLE);
                    }
                    else if( view.getId() == R.id.counterClockwise_creative && v == getLinearLayout()){
                        buttonLimit++;
                        if( buttonLimit == 8)
                            setLinearLayout(linearLayout2);
                        distanceGo = Integer.parseInt((String) spinnerDistance.getSelectedItem());
                        degreesTurn = 360 - Integer.parseInt((String) spinnerDegrees.getSelectedItem());
                        lineView.addListDegree( degreesTurn);
                        lineView.addListDistanceArray( distanceGo);
                        lineView.addListDistance( distanceGo);
                        counterClockwise = new Button(CreativeMode.this);
                        counterClockwise.setText( "↺" + distanceGo + "u " + ( 360 - degreesTurn)+ "°");
                        counterClockwise.setTextSize(10f);
                        counterClockwise.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,95));
                        container.addView( counterClockwise);
                        counterClockwise.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            return true;
        }
    }
}
