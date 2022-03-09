package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ScoreScreen extends AppCompatActivity {

    private ArrayList<Puntuacion> lista;
    private RecyclerView recyclerView;

    private DataBase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ToggleButton show2048 = (ToggleButton) findViewById(R.id.show2048);
        ToggleButton showPeg = (ToggleButton) findViewById(R.id.showPeg);

        conn = new DataBase(this);
        lista = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        returnAll2048();

        CompoundButton.OnCheckedChangeListener changeCheck;

        changeCheck = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (compoundButton == show2048) {
                        show2048.setChecked(true);
                        showPeg.setChecked(false);
                        show2048.setBackgroundResource(R.color.purple_700);
                        show2048.setTextColor(getResources().getColor(R.color.white));
                        showPeg.setBackgroundResource(R.color._0);
                        showPeg.setTextColor(getResources().getColor(R.color.black));
                        returnAll2048();
                    }
                    if(compoundButton == showPeg) {
                        show2048.setChecked(false);
                        showPeg.setChecked(true);
                        show2048.setBackgroundResource(R.color._0);
                        show2048.setTextColor(getResources().getColor(R.color.black));
                        showPeg.setBackgroundResource(R.color.purple_700);
                        showPeg.setTextColor(getResources().getColor(R.color.white));
                        returnAllPeg();
                    }
                }
            }
        };

        show2048.setOnCheckedChangeListener(changeCheck);
        showPeg.setOnCheckedChangeListener(changeCheck);
    }

    private void returnAll2048() {
        lista.clear();
        String query = "SELECT * FROM " + DataBase.SCORE_LIST_TABLE + " WHERE " +
                DataBase.SCORE_2048 + " != "+ 0 + " ORDER BY " + DataBase.SCORE_2048 + " DESC";
        SQLiteDatabase db = conn.getReadableDatabase();
        Puntuacion puntuacion = null;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            puntuacion = new Puntuacion();
            puntuacion.setUserName(cursor.getString(1));
            puntuacion.setScore(String.valueOf(cursor.getInt(3)));

            lista.add(puntuacion);
        }

        ScoreAdapter adapter = new ScoreAdapter(lista);
        recyclerView.setAdapter(adapter);
    }

    private void returnAllPeg() {
        lista.clear();
        String query = "SELECT * FROM " + DataBase.SCORE_LIST_TABLE + " WHERE " +
                DataBase.SCORE_PEG + " != " + 0 + " ORDER BY " + DataBase.SCORE_PEG + " ASC";
        SQLiteDatabase db = conn.getReadableDatabase();
        Puntuacion puntuacion = null;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            puntuacion = new Puntuacion();
            puntuacion.setUserName(cursor.getString(1));
            puntuacion.setScore(getTimerText(cursor.getInt(4)));

            lista.add(puntuacion);
        }

        ScoreAdapter adapter = new ScoreAdapter(lista);
        recyclerView.setAdapter(adapter);
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