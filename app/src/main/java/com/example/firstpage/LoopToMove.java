package com.example.firstpage;

import android.widget.ImageView;

import com.example.firstpage.Level1Page;

import java.util.ArrayList;

public class LoopToMove extends Level1Page implements Runnable {

    private ArrayList<String> list;
    ImageView bee;
    float x;
    float y;

    public LoopToMove(ArrayList<String> list, ImageView bee, Float x , Float y){
        this.list = list;
        this.bee = bee;
        this.x = x;
        this.y = y;
    }
    @Override
    public void run() {
        try {
            for (int i = 0; i < list.size(); i++) {
                Thread.sleep(1000);
                System.out.println("a");
                if (list.get(i).equals("forward1")) {
                    GoForward(bee);
                } else if (list.get(i).equals("forward2")) {
                    for (int k = 0; k < 2; k++) {
                        GoForward(bee);
                    }
                } else if (list.get(i).equals("forward3")) {
                    for (int k = 0; k < 3; k++) {
                        GoForward(bee);
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
        } catch (InterruptedException exception) {
        }
    }
}
