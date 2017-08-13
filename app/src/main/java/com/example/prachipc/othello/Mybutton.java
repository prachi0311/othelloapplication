package com.example.prachipc.othello;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PRACHI PC on 1/31/2017.
 */

public class Mybutton extends ImageView {
    private int player;
    int row;
    int col;
    public Mybutton(Context context) {
        super(context);
    }
    public int getstatus(){
        return player;
    }
    public void setstatus(int player){
        this.player=player;
    }
}
