package com.example.creative;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class LineView extends View {
    private Paint paint = new Paint();
    private int[][] distanceArray = new int[ 30][ 40];
    private ArrayList<Integer> distance = new ArrayList<Integer>();
    private ArrayList<Integer> degrees = new ArrayList<Integer>();
    private ArrayList<Integer> countList = new ArrayList<Integer>();
    private ArrayList<Integer> booleanCheck = new ArrayList<Integer>();
    private int starterX;
    private int starterY;
    private int count = 0;
    private int i;
    private int numberOfLines = 0;
    private int countOfPieces = 0;
    private int countOfDistance = 0;
    private int countOfParts;
    private int numberOfMethod =  0;
    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onDraw(final Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        paint.setStrokeWidth( 5);
        countOfPieces = 0;
        countOfParts = 0;
        starterX = 150;
        starterY = 100;
        i = 0;
        while( i <= numberOfLines && numberOfLines < distance.size() && countOfPieces < 1) {
            int j = 0;
            while(  j <= countList.get( i) && countList.get( i) < distance.get( i) / 5) {

                canvas.drawLine(starterX, starterY, starterX + (int)
                                (distanceArray[i][j]
                                        * Math.cos(Math.toRadians(Double.valueOf(degrees.get(i))))),
                        starterY + (int) (distanceArray[i][j]
                                * Math.sin(Math.toRadians(Double.valueOf(degrees.get(i))))), paint);
                countOfParts++;
                if (j == countList.get(i) && countList.get(i) == distance.get(i) / 5 - 1) {
                    if( isNew( i)) {
                        booleanCheck.set( i, 1);
                        numberOfLines++;
                    }
                    starterX = starterX + (int)
                            (distance.get(i)
                                    * Math.cos(Math.toRadians(Double.valueOf(degrees.get(i)))));
                    starterY = starterY + (int) (distance.get(i)
                            * Math.sin(Math.toRadians(Double.valueOf(degrees.get(i)))));
                }
                j++;
            }
            if (j != countList.get(i) && countList.get(i) != distance.get(i) / 5 - 1 && isNew( i) ) {
                countList.set(i, countList.get(i) + 1);
            }
            i++;
            if( countOfParts == countOfMethod() * ( countOfMethod() + 1) / 2)
                countOfPieces++;
        }
        if( numberOfLines == distance.size() ) {
            starterX = 150;
            starterY = 100;
            for (int k = 0; k < distance.size(); k++) {
                canvas.drawLine(starterX, starterY, starterX + (int)
                                (distance.get(k)
                                        * Math.cos(Math.toRadians(Double.valueOf(degrees.get(k))))),
                        starterY + (int) (distance.get(k)
                                * Math.sin(Math.toRadians(Double.valueOf(degrees.get(k))))), paint);
                starterX = starterX + (int) (distance.get(k)
                        * Math.cos(Math.toRadians(Double.valueOf(degrees.get(k)))));
                starterY = starterY + (int) (distance.get(k)
                        * Math.sin(Math.toRadians(Double.valueOf(degrees.get(k)))));
            }
        }
    }
    public void draw(){
        invalidate();
        requestLayout();
    }


    public int addListDistanceArray( int distances){
        for( int i = 0; i < distances / 5; i++) {
            if( i > 0) {
                distanceArray[countOfDistance][i] = 5 + distanceArray[countOfDistance][i - 1];
            }
            else
                distanceArray[countOfDistance][i] = 5;
        }
        countOfDistance++;
        return distances;
    }

    public int addListDistance( int distances){
        distance.add( distances);
        return distances;
    }
    public int addListDegree( int degree){
        degrees.add( degree);
        return degree;
    }
    public int countOfMethod(){

        numberOfMethod++;
        return numberOfMethod;
    }

    public void countListStarter(){

        for( int index = 0; index < distance.size(); index++) {
            countList.add(count);
            booleanCheck.add(0);
        }
    }
    public boolean isNew( int order){
        if( countList.get( order) != 0 && booleanCheck.get( order) > 0){
            return false;
        }
        else {
            return true;
        }
    }
}
