package com.example.prachipc.othello;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.prachipc.othello.R.drawable.black;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Mybutton buttons[][];
    LinearLayout rows[];
    int n = 8;
    int white = 1;
    int black = 2;
    int green = 0;
    int wc=0;
    int bc=0;
    ArrayList<Integer> dir = new ArrayList<>();
    LinearLayout mainLayout;
    TextView blackScore;
    TextView whiteScore;
    boolean player1;
    boolean player2;
    int x[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    int y[] = {-1, 0, 1, 1, 1, 0, -1, -1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.boardlayout);
        blackScore = (TextView) findViewById(R.id.textView3);
        whiteScore = (TextView) findViewById(R.id.textView4);
        setupboard();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.newGame)
            resetBoard();

        return true;
    }

    private void resetBoard() {
        setupboard();
    }

    private void setupboard() {
        buttons = new Mybutton[n][n];
        rows = new LinearLayout[n];
        blackScore.setBackgroundColor(Color.parseColor("#228B22"));
        blackScore.setTextColor(Color.BLACK);
        whiteScore.setBackgroundColor(Color.parseColor("#006400"));
        whiteScore.setTextColor(Color.BLACK);
        whiteScore.setText("white : " );
        blackScore.setText("black : " );
        wc=0;
        bc=0;
        player1 = true;

        // gameover = false;
        mainLayout.removeAllViews();
        //score.setText("score : 0");
        Toast.makeText(this,"White's Turn",Toast.LENGTH_SHORT).show();

        for (int i = 0; i < n; i++) {
            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            params.setMargins(0, 0, 0, 0);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rows[i]);
        }
        //  rows[0].addView(score);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buttons[i][j] = new Mybutton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(0, 0, 0, 0);
                buttons[i][j].row = i;
                buttons[i][j].col = j;
                buttons[i][j].setLayoutParams(params);
                //  buttons[i][j].setText("");
                if ((i + j) % 2 == 0) {
                    buttons[i][j].setBackgroundColor(Color.parseColor("#228B22"));
                } else
                    buttons[i][j].setBackgroundColor(Color.parseColor("#006400"));
                buttons[i][j].setPadding(6, 6, 6, 6);
                buttons[i][j].setstatus(green);
                buttons[i][j].setOnClickListener(this);
              //   buttons[i][j].setOnLongClickListener(this);
                //buttons[i][j].longclick=false;
                rows[i].addView(buttons[i][j]);
            }
        }

        buttons[3][3].setImageResource(R.drawable.black);
        buttons[3][3].setstatus(black);
        buttons[3][4].setImageResource(R.drawable.white);
        buttons[3][4].setstatus(white);
        buttons[4][3].setImageResource(R.drawable.white);
        buttons[4][3].setstatus(white);
        buttons[4][4].setImageResource(R.drawable.black);
        buttons[4][4].setstatus(black);
    }

    /**
     * Created by PRACHI PC on 1/31/2017.
     */


    @Override
    public void onClick(View v) {

        Mybutton b = (Mybutton) v;
        if(player2 && allowedBlack()==false){
            player2=false;
            player1=true;
            Toast.makeText(this,"White's Turn",Toast.LENGTH_SHORT).show();
        }
        else if(player1 && allowedWhite()==false){
          player1=true;
            player2=false;
            Toast.makeText(this,"Black's Turn",Toast.LENGTH_SHORT).show();
        }
        if(gameover() || (!allowedBlack() && !allowedWhite())){
            if(wc>bc){
                Toast.makeText(this,"white player won",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"black player won",Toast.LENGTH_SHORT).show();

            return;
        }
        if (validMove(b) && b.getstatus()==green) {
            if (player1){
             //   Toast.makeText(this,"WHITE'S TURN",Toast.LENGTH_SHORT).show();
                b.setImageResource(R.drawable.white);
                b.setstatus(white);
                for(int i=0;i<dir.size();i++){
                   int a=b.row+x[dir.get(i)];
                    int c=b.col+y[dir.get(i)];
                    while(buttons[a][c].getstatus()!=white ){
                        buttons[a][c].setImageResource(R.drawable.white);
                        buttons[a][c].setstatus(white);
                        a=a+x[dir.get(i)];
                        c=c+y[dir.get(i)];
                    }
                }
                score();
                dir.clear();
                player1=false;
                player2=true;
                Toast.makeText(this,"Black's Turn",Toast.LENGTH_SHORT).show();
            }
            else if (player2){
              //  Toast.makeText(this,"BLACK'S TURN",Toast.LENGTH_SHORT).show();
                b.setImageResource(R.drawable.black);
                b.setstatus(black);
                for(int i=0;i<dir.size();i++){
                    int a=b.row+x[dir.get(i)];
                    int c=b.col+y[dir.get(i)];
                    while(buttons[a][c].getstatus()!=black){
                        buttons[a][c].setImageResource(R.drawable.black);
                        buttons[a][c].setstatus(black);
                        a=a+x[dir.get(i)];
                        c=c+y[dir.get(i)];
                    }
                }
                score();
                dir.clear();
                player2=false;
                player1=true;
                Toast.makeText(this,"White's Turn",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this,"INVALID MOVE",Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private boolean allowedWhite() {
        int a, c, blackcount = 0,loop=0;
      //  int ans = 0;
        for(int p=0;p<n;p++) {
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < 8; i++) {
                    a = buttons[p][j].row + (x[i]);
                    c = buttons[p][j].col + (y[i]);
                    blackcount = 0;
                    loop = 0;
                    if (a >= 0 && a < n && c >= 0 && c < n && buttons[p][j].getstatus()==white) {
                        while (a >= 0 && a < n && c >= 0 && c < n) {
                            loop++;
//                    if(buttons[a][c].getstatus()==green)
//                        break;
                            if (buttons[a][c].getstatus() == black)
                                blackcount++;

                            if (buttons[a][c].getstatus() == green && blackcount == loop - 1 && blackcount != 0) {
                                return true;
//                                ans++;
//                                dir.add(i);
//                                break;
                            }
                            a = a + x[i];
                            c = c + y[i];

                        }

                    }
                }
            }
        }

        return false;
    }

    private boolean allowedBlack() {
        int a, c, whitecount = 0,loop=0;
        //  int ans = 0;
        for(int p=0;p<n;p++) {
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < 8; i++) {
                    a = buttons[p][j].row + (x[i]);
                    c = buttons[p][j].col + (y[i]);
                    whitecount = 0;
                    loop = 0;
                    if (a >= 0 && a < n && c >= 0 && c < n && buttons[p][j].getstatus()==black) {
                        while (a >= 0 && a < n && c >= 0 && c < n) {
                            loop++;
//                    if(buttons[a][c].getstatus()==green)
//                        break;
                            if (buttons[a][c].getstatus() == white)
                                whitecount++;

                            if (buttons[a][c].getstatus() == green && whitecount == loop - 1 && whitecount != 0) {
                                return true;
//                                ans++;
//                                dir.add(i);
//                                break;
                            }
                            a = a + x[i];
                            c = c + y[i];

                        }

                    }
                }
            }
        }

        return false;
    }

    private boolean gameover() {
      //  boolean ans = true;
        if(wc+bc==(n*n)){
            return true;
        }
        else return false;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                if (buttons[i][j].getstatus() == green)
//                    return false;
//            }
//
//        }
//        return ans;

    }

    private void score() {

           int wc=0,bc=0;
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(buttons[i][j].getstatus()==white)
                         wc++;
                    else if(buttons[i][j].getstatus()==black)
                        bc++;
                }
            }
            whiteScore.setText("white : " + wc);

            blackScore.setText("black : " + bc);

        }






    private int containswhite(Mybutton b) {
        int a, c, blackcount = 0,loop=0;
        int ans = 0;
        for (int i=0 ; i<8 ; i++){
            a = b.row + (x[i]);
            c = b.col + (y[i]);
            blackcount=0;
            loop=0;
            if (a >= 0 && a < n && c >= 0 && c < n) {
                while (a >= 0 && a < n && c >= 0 && c < n) {
                    loop++;
                    if(buttons[a][c].getstatus()==green)
                        break;
                    if (buttons[a][c].getstatus() == black)
                        blackcount++;

                    if (buttons[a][c].getstatus() == white && blackcount==loop-1 && blackcount!=0 ) {
                        ans++;
                        dir.add(i);
                        break;
                    }
                    a=a+x[i];
                    c=c+y[i];

                }

            }
        }
        return ans;
    }

    private int containsblack(Mybutton b) {
        int a, c, whitecount = 0,loop=0;
        int ans = 0;

        for (int i = 0; i < 8; i++) {
            a = b.row + (x[i]);
            c = b.col + (y[i]);
            whitecount=0;
            loop=0;
            if (a >= 0 && a < n && c >= 0 && c < n) {

                while (a >= 0 && a < n && c >= 0 && c < n) {
                    loop++;
                    if(buttons[a][c].getstatus()==green)
                        break;
                    if (buttons[a][c].getstatus() == white) {
                        whitecount++;
                    }
                    if (buttons[a][c].getstatus() == black && whitecount == loop-1 && whitecount!=0) {
                        ans++;
                        dir.add(i);
                        break;
                    }
                    a=a+x[i];
                    c=c+y[i];

                }

            }
        }
        return ans;
    }


    private boolean validMove(Mybutton b) {
       // int a, c;
        boolean ans = false;


                if (player1) {
                    if (containswhite(b) != 0)
                        ans=true;
                } else if (player2 ) {
                    if (containsblack(b) != 0)
                        ans= true;

                }
        return ans;
            }

        }


