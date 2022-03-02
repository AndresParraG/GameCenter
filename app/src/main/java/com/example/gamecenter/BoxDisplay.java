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
                    Toast.makeText(BoxDisplay.this, "LOSE", Toast.LENGTH_SHORT).show();
                }
                if (game.isWin()) {
                    Toast.makeText(BoxDisplay.this, "WIN", Toast.LENGTH_SHORT).show();
                }
            }

            public void onSwipeRight() {
                if (!game.isLose()) {
                    game.swipeRight();
                    updateGrid(game.getMatriz());

                }
                if (game.isLose()){
                    Toast.makeText(BoxDisplay.this, "LOSE", Toast.LENGTH_SHORT).show();
                }
                if (game.isWin()) {
                    Toast.makeText(BoxDisplay.this, "WIN", Toast.LENGTH_SHORT).show();
                }
            }

            public void onSwipeLeft() {
                if (!game.isLose()) {
                    game.swipeLeft();
                    updateGrid(game.getMatriz());

                }
                if (game.isLose()){
                    Toast.makeText(BoxDisplay.this, "LOSE", Toast.LENGTH_SHORT).show();
                }
                if (game.isWin()) {
                    Toast.makeText(BoxDisplay.this, "WIN", Toast.LENGTH_SHORT).show();
                }
            }

            public void onSwipeBottom() {
                if (!game.isLose()) {
                    game.swipeDown();
                    updateGrid(game.getMatriz());

                }
                if (game.isLose()){
                    Toast.makeText(BoxDisplay.this, "LOSE", Toast.LENGTH_SHORT).show();
                }
                if (game.isWin()) {
                    Toast.makeText(BoxDisplay.this, "WIN", Toast.LENGTH_SHORT).show();
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
                updateColor(textID, i, j);
            }
        }
        score.setText(Integer.toString(game.getScore()));
    }

    public void updateColor(TextView tile, int i, int j) {
        switch (game.getMatriz()[i][j]) {
            case 0:
                tile.setBackgroundResource(R.color._0);
                break;
            case 2:
                tile.setBackgroundResource(R.color._2);
                break;
            case 4:
                tile.setBackgroundResource(R.color._4);
                break;
            case 8:
                tile.setBackgroundResource(R.color._8);
                break;
            case 16:
                tile.setBackgroundResource(R.color._16);
                break;
            case 32:
                tile.setBackgroundResource(R.color._32);
                break;
            case 64:
                tile.setBackgroundResource(R.color._64);
                break;
            case 128:
                tile.setBackgroundResource(R.color._128);
                break;
            case 252:
                tile.setBackgroundResource(R.color._252);
                break;
            case 512:
                tile.setBackgroundResource(R.color._512);
                break;
            case 1048:
                tile.setBackgroundResource(R.color._1024);
                break;
            case 2048:
                tile.setBackgroundResource(R.color._2048);
                break;
            default:
                tile.setBackgroundResource(R.color._else);
                break;
        }
    }

}