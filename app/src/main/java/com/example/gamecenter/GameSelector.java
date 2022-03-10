package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameSelector extends AppCompatActivity {

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selector);

        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");

        Button button1 = (Button) findViewById(R.id.play2048);
        Button button2 = (Button) findViewById(R.id.playPeg);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent boxGame = new Intent(GameSelector.this, BoxDisplay.class);
                boxGame.putExtra("user", user);
                startActivity(boxGame);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pegGame = new Intent(GameSelector.this, PegDisplay.class);
                pegGame.putExtra("user", user);
                startActivity(pegGame);
            }
        });
    }
}