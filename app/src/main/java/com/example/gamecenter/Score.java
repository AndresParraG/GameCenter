package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Score extends AppCompatActivity {

    private DataBase db;
    private String user;
    private TextView userS;
    private TextView score2048;
    private TextView scorePeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        db = new DataBase(this);
        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");

        userS = (TextView) findViewById(R.id.userVariable);
        score2048 = (TextView) findViewById(R.id.boxVariable);
        scorePeg = (TextView) findViewById(R.id.pegVariable);

        String[] data = db.returnData(user);

        userS.setText(data[0]);
        score2048.setText(data[1]);
        if (Integer.parseInt(data[2]) != -10) {
            scorePeg.setText(getTimerText(Integer.parseInt(data[2])));
        } else {
            scorePeg.setText(getTimerText(0));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private String getTimerText(int time) {
        int seconds = ((time % 86400) % 3600) % 60;
        int minutes = ((time % 86400) % 3600) / 60;

        return formatTime(seconds, minutes);
    }

    private String formatTime(int seconds, int minutes) {
        return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }
}