package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BoxDisplay extends AppCompatActivity {

    BoxGame game;
    GridLayout grid;
    TextView textID;
    Button startButton;
    Button undoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_display);

        grid = findViewById(R.id.grid);
        startButton = findViewById(R.id.button);
        undoButton = findViewById(R.id.undoBox);
        game = new BoxGame();
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

                } else {
                    lose();
                }
            }
            public void onSwipeRight() {
                if (!game.isLose()) {
                    game.swipeRight();
                    updateGrid(game.getMatriz());

                } else {
                    lose();
                }
            }
            public void onSwipeLeft() {
                if (!game.isLose()) {
                    game.swipeLeft();
                    updateGrid(game.getMatriz());

                } else {
                    lose();
                }
            }
            public void onSwipeBottom() {
                if (!game.isLose()) {
                    game.swipeDown();
                    updateGrid(game.getMatriz());

                } else {
                    lose();
                }
            }
        });
    }

    public TextView getTextViewID(int i, int j) {
        String viewID = "view"+i+j;
        int resID = getResources().getIdentifier(viewID, "id", getPackageName());
        return findViewById(resID);
    }

    public void updateGrid(int[][] m) {
        System.out.println("Grid updated");
        for (int i=0; i<m.length; i++) {
            for (int j=0; j<m[0].length; j++) {
                textID = getTextViewID(i, j);
                if (m[i][j] != 0) {
                    textID.setText(Integer.toString(m[i][j]));
                } else {
                    textID.setText(getString(R.string.null_value));
                }
            }
        }
    }

    public void lose() {
        Toast.makeText(this, "LOSE", Toast.LENGTH_SHORT).show();
    }

}