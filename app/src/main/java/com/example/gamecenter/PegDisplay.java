package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PegDisplay extends AppCompatActivity {

    private PegGame peg;
    private TextView pegsCounter;
    private Button undoButton;
    private DataBase db;
    private String user;

    private static final String TAG = "PegDisplay";
    private static final String BOARD = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peg_display);

        db = new DataBase(this);
        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");

        if (savedInstanceState != null) {
            peg = (PegGame) savedInstanceState.getSerializable(BOARD);
        } else {
            peg = new PegGame();
        }
        pegsCounter = (TextView) findViewById(R.id.pegsCounter);
        undoButton = findViewById(R.id.undoPeg);
        update();

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peg.undo();
                update();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putSerializable(BOARD, peg);
    }

    public void onClick(View view) {
        if (!peg.isWin() && !peg.isLose()) {
            peg.clickPeg(view);
            update();
        }
        if(peg.isWin()){
            Toast.makeText(this, "WIN", Toast.LENGTH_SHORT).show();
        }
        if(peg.isLose()) {
            Toast.makeText(this, "LOSE", Toast.LENGTH_SHORT).show();
        }
    }

    public void reset(View view) {
        peg = new PegGame();
        update();
    }

    /*
    public void saveScore() {
        int hs = db.returnHS(user, true);
        if (hs == -1) {
            System.out.println("ERROR ON DATABASE HIGHSCORE RETRIEVAL");
        } else if (hs < peg.getScore()) {
            //db.updatePeg(, game.getScore()); return user string instead of id
        }
    }
     */


    public void update() {
        for (int i=0; i<peg.getBoard().length; i++) {
            for (int j=0; j<peg.getBoard()[0].length; j++) {
                ImageView imageView = getImageViewID(i, j);
                if (peg.getBoard()[i][j] == 0) {
                    imageView.setImageResource(R.drawable.empty);
                } else if (peg.getBoard()[i][j] == 1) {
                    imageView.setImageResource(R.drawable.blue_peg);
                } else if (peg.getBoard()[i][j] == 2){
                    imageView.setImageResource(R.drawable.selected_peg);
                }
            }
        }
        pegsCounter.setText(Integer.toString(peg.getPegsLeft()));
    }

    public ImageView getImageViewID(int i, int j) {
        String viewID = "view"+i+j;
        int resID = getResources().getIdentifier(viewID, "id", getPackageName());
        return findViewById(resID);
    }
}