package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class PegDisplay extends AppCompatActivity {

    private PegGame peg;
    private TextView pegsCounter;
    private TextView timerText;
    private Button undoButton;
    private Button resetButton;
    private DataBase db;
    private String user;

    private Timer timer;
    private TimerTask timerTask;
    private Double time;
    private boolean timerStoped = false;

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
            time = savedInstanceState.getDouble("time");
        } else {
            peg = new PegGame();
            time = 0.0;
        }
        pegsCounter = (TextView) findViewById(R.id.pegsCounter);
        timerText = (TextView) findViewById(R.id.timer);
        undoButton = findViewById(R.id.undoPeg);
        resetButton = findViewById(R.id.resetPeg);
        timer = new Timer();
        startTimer();
        update();

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerStoped) {
                    startTimer();
                }
                peg.undo();
                update();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peg = new PegGame();
                resetTimer();
                startTimer();
                update();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putSerializable(BOARD, peg);
        savedInstanceState.putDouble("time", time);
    }

    public void onClick(View view) {
        if (!peg.isWin() && !peg.isLose()) {
            peg.clickPeg(view);
            update();
        }
        if (peg.isWin()) {
            Toast.makeText(this, "WIN", Toast.LENGTH_SHORT).show();
            stopTimer();
            saveScore();
        } else if (peg.isLose()) {
            Toast.makeText(this, "LOSE", Toast.LENGTH_SHORT).show();
            stopTimer();
        }
    }


    public void saveScore() {
        int hs = db.returnHSPeg(user);
        int round = (int) Math.round(time);
        if (hs == -1) {
            System.out.println("ERROR ON DATABASE HIGHSCORE RETRIEVAL");
        } else if (hs > round) {
            db.updatePeg(user, round);
        } else if (hs == 0) {
            db.updatePeg(user, round);
        }
    }

    public void update() {
        for (int i = 0; i < peg.getBoard().length; i++) {
            for (int j = 0; j < peg.getBoard()[0].length; j++) {
                ImageView imageView = getImageViewID(i, j);
                if (peg.getBoard()[i][j] == 0) {
                    imageView.setImageResource(R.drawable.empty);
                } else if (peg.getBoard()[i][j] == 1) {
                    imageView.setImageResource(R.drawable.blue_peg);
                } else if (peg.getBoard()[i][j] == 2) {
                    imageView.setImageResource(R.drawable.selected_peg);
                }
            }
        }
        pegsCounter.setText(Integer.toString(peg.getPegsLeft()));
    }

    public ImageView getImageViewID(int i, int j) {
        String viewID = "view" + i + j;
        int resID = getResources().getIdentifier(viewID, "id", getPackageName());
        return findViewById(resID);
    }

    public void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText() {
        int round = (int) Math.round(time);

        int seconds = ((round % 86400) % 3600) % 60;
        int minutes = ((round % 86400) % 3600) / 60;

        return formatTime(seconds, minutes);
    }

    private String formatTime(int seconds, int minutes) {
        return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    public void resetTimer() {
        timerTask.cancel();
        time = 0.0;
        timerText.setText(formatTime(0, 0));
    }

    public void stopTimer() {
        timerStoped = true;
        timerTask.cancel();
    }

}