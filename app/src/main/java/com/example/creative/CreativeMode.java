package com.example.creative;
/*Creative Mode Main Class
@version 13.05.2020
@author Tepe_Remzi & Ozal_Deniz
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.FunAlgo.R;
import com.example.menu.SettingsPage;


public class CreativeMode extends AppCompatActivity {

    // properties
    private Button clockwise;
    private Button counterClockwise;
    private Button applyCreative;
    private Button resetCreative;
    private Button returnButton;
    private Button volumeButton;
    private Button settingsButton;
    private LinearLayout linearLayout, linearLayout2;
    private String[] degrees = {"0", "30", "45","60", "90", "120", "180"};
    private String[] distance = {"10", "30", "60", "100", "200"};
    private int distanceGo;
    private int degreesTurn;
    private int buttonLimit;
    private int countOfPieces;
    private int background;
    private android.widget.Spinner spinnerDegrees;
    private android.widget.Spinner spinnerDistance;
    private ArrayAdapter<String> dataAdapterForDistance;
    private ArrayAdapter<String> dataAdapterForDegrees;
    private LineView lineView;
    private ConstraintLayout creativeModeLayout;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // sets layout creative mode xml
        setContentView(R.layout.activity_creative_mode);

        // sets orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // find all properties below in xml codes
        creativeModeLayout = findViewById(R.id.creativeMode_layout);
        creativeModeLayout.setBackgroundResource(background);
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
        spinnerDegrees = findViewById(R.id.degreeSpinner);
        spinnerDistance = findViewById(R.id.distanceSpinner);
        resetCreative.setEnabled(false);

        // takes background image from shared preferences method
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        creativeModeLayout.setBackgroundResource(background);


        //sets count of pieces to 0
        countOfPieces = 0;

        // sets button limit to 0
        buttonLimit = 0;

        // adds data adapter for bot distance and degree options in order to show them as spinner
        dataAdapterForDegrees = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, degrees);
        dataAdapterForDistance = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, distance);

        dataAdapterForDegrees.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForDistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDegrees.setAdapter(dataAdapterForDegrees);
        spinnerDistance.setAdapter(dataAdapterForDistance);

        // sets touch listener for both clockwise and counter clockwise buttons
        clockwise.setOnTouchListener(new MyTouchListener());
        counterClockwise.setOnTouchListener( new MyTouchListener());

        // sets drag listener to apply drag and drop action on those layouts
        linearLayout.setOnDragListener(new MyDragListener());
        linearLayout2.setOnDragListener(new MyDragListener());

        // if it is pressed, the game resets itself
        resetCreative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // resets the whole layout
                recreate();
            }
        });

        // when all buttons added, the user pressed apply button and it draws the shapes asked user
        applyCreative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applyCreative.setEnabled(false);
                applyCreative.setBackgroundColor( getResources().getColor(R.color.holo_blue_dark));
                resetCreative.setEnabled(true);


                // handler object to obtain timer when drawing shapes
                final Handler handler= new Handler();

                // it starts count list starter which is a method from line view class
                lineView.countListStarter();

                // it operates actions with specified time
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // it starts to count methods
                        lineView.countOfMethod();

                        // it starts to draw the shapes from nothing with delaying
                        lineView.draw();

                        // it controls the delay time
                        handler.postDelayed( this, 100);
                    }
                },100);
            }
        });

        // when it is pressed, it returns previous page
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // when it is pressed, it goes to setting page
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( CreativeMode.this, SettingsPage.class);
                startActivity( intent);
            }
        });
    }

    /*Method to get Linear Layout
    @return linearLayout
     */
    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    /*
    set Linear Layout asked
    @param LinearLayout property
    @return linear layout
     */
    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    /*
    it operates the action of dragging
     */
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

    /*
    it operates the drag and drop event
     */
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
                        if( buttonLimit == 8) // if limit exceed 8 it goes to second layout

                            // sets linear layout
                            setLinearLayout(linearLayout2);

                        // convert spinner distance and degrees  to integer
                        distanceGo = Integer.parseInt((String) spinnerDistance.getSelectedItem());
                        degreesTurn = Integer.parseInt((String) spinnerDegrees.getSelectedItem());

                        // degrees and distances are added to their list in lineview class
                        lineView.addListDegree( degreesTurn);
                        lineView.addListDistanceArray( distanceGo);
                        lineView.addListDistance( distanceGo);

                        // then new clockwise button is created and text is set
                        clockwise = new Button(CreativeMode.this);
                        clockwise.setText( "↻" + distanceGo + "u " + degreesTurn + "°");
                        clockwise.setTextColor(Color.BLACK);
                        clockwise.setTextSize(10f);
                        clockwise.setBackgroundColor( getResources().getColor(R.color.holo_blue_bright));
                        clockwise.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,95));
                        container.addView( clockwise);
                        clockwise.setVisibility(View.VISIBLE);
                    }

                    // it is same with the above but it is the situation if counter clockwise is dragged
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
                        counterClockwise.setTextColor(Color.BLACK);
                        counterClockwise.setTextSize(10f);
                        counterClockwise.setBackgroundColor( getResources().getColor(R.color.holo_blue_bright));
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
