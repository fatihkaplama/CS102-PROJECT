package com.example.firstpage;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class DragAndDrop extends AppCompatActivity {
    Button b1, b2, b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);;
        b1 = findViewById(R.id.button1);
        findViewById(R.id.button1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.button2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.button3).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.view1).setOnDragListener(new MyDragListener());
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
                    if(view.getId() == R.id.button1){
                        b1 = new Button(DragAndDrop.this);
                        b1.setText("Button 1");
                        b1.setBackgroundColor(Color.YELLOW);
                        container.addView(b1);
                        b1.setVisibility(View.VISIBLE);
                    }
                    if (view.getId() == R.id.button2){
                        b2 = new Button(DragAndDrop.this);
                        b2.setText("Button 2");
                        b2.setBackgroundColor(Color.BLUE);
                        container.addView(b2);
                        b2.setVisibility(View.VISIBLE);
                    }
                     if (view.getId() == R.id.button3){
                        b3 = new Button(DragAndDrop.this);
                        b3.setText("Button 3");
                        b3.setBackgroundColor(Color.RED);
                        container.addView(b3);
                        b3.setVisibility(View.VISIBLE);
                    }
//                    ViewGroup owner = (ViewGroup) view.getParent();
//                    owner.removeView(view);
                    break;
            }
            return true;
        }
    }

}
