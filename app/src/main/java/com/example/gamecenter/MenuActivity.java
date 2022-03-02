package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        /*
        if (savedInstanceState != null) {
            user = savedInstanceState.getString();
        }
        */

        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");

        Button button1 = (Button) findViewById(R.id.start2048);
        Button button2 = (Button) findViewById(R.id.startPeg);
        Button button3 = (Button) findViewById(R.id.startScore);
        TextView userName = (TextView) findViewById(R.id.userName);
        userName.setText(user);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, BoxDisplay.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, PegDisplay.class));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, Score.class));
            }
        });
    }
}