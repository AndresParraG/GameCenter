package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BoxDisplay extends AppCompatActivity {

    BoxGame game;
    GridLayout grid;
    TextView textID;
    TextView score;
    Button startButton;
    Button undoButton;

    private static final String TAG = "BoxDisplay";
    private static final String BOX_BOARD = "index";

    private boolean winShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_display);

        if (savedInstanceState != null) {
            game = (BoxGame) savedInstanceState.getSerializable(BOX_BOARD);
        } else {
            game = new BoxGame();
        }
        grid = findViewById(R.id.grid);
        score = findViewById(R.id.score);
        startButton = findViewById(R.id.button);
        undoButton = findViewById(R.id.undoBox);
        updateGrid(game.getMatriz());

        //reset
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game = new BoxGame();
                updateGrid(game.getMatriz());
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.undo();
                updateGrid(game.getMatriz());
            }
        });

        //controlador de gestos
        grid.setOnTouchListener(new OnSwipeTouchListener(BoxDisplay.this) {
            public void onSwipeTop() {
                if (!game.isLose()) {
                    game.swipeUp();
                    updateGrid(game.getMatriz());

                }
                if (game.isLose()){
                    lose();
                }
                if (game.isWin()) {
                    win();
                }
            }

            public void onSwipeRight() {
                if (!game.isLose()) {
                    game.swipeRight();
                    updateGrid(game.getMatriz());

                }
                if (game.isLose()){
                    lose();
                }
                if (game.isWin()) {
                    win();
                }
            }

            public void onSwipeLeft() {
                if (!game.isLose()) {
                    game.swipeLeft();
                    updateGrid(game.getMatriz());

                }
                if (game.isLose()){
                    lose();
                }
                if (game.isWin()) {
                    win();
                }
            }

            public void onSwipeBottom() {
                if (!game.isLose()) {
                    game.swipeDown();
                    updateGrid(game.getMatriz());

                }
                if (game.isLose()){
                    lose();
                }
                if (game.isWin()) {
                    win();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putSerializable(BOX_BOARD, game);
    }

    public TextView getTextViewID(int i, int j) {
        String viewID = "view" + i + j;
        int resID = getResources().getIdentifier(viewID, "id", getPackageName());
        return findViewById(resID);
    }

    public void updateGrid(int[][] m) {
        System.out.println("Grid updated");
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                textID = getTextViewID(i, j);
                if (m[i][j] != 0) {
                    textID.setText(Integer.toString(m[i][j]));
                } else {
                    textID.setText(getString(R.string.null_value));
                }
            }
        }
        score.setText(Integer.toString(game.getScore()));
    }

    public void lose() {
        Toast.makeText(this, "LOSE", Toast.LENGTH_SHORT).show();
    }

    public void win() {
        if (!winShow) {
            Toast.makeText(this, "WIN", Toast.LENGTH_SHORT).show();
            winShow = true;
        }
    }

}