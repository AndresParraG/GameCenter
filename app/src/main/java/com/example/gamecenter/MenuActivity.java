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

        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");

        Button button1 = (Button) findViewById(R.id.startGameSelector);
        Button button2 = (Button) findViewById(R.id.startScore);
        Button button3 = (Button) findViewById(R.id.userSettings);
        Button button4 = (Button) findViewById(R.id.logOut);
        TextView userName = (TextView) findViewById(R.id.userName);
        userName.setText(user);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selector = new Intent(MenuActivity.this, GameSelector.class);
                selector.putExtra("user", user);
                startActivity(selector);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent score = new Intent(MenuActivity.this, ScoreScreen.class);
                startActivity(score);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings = new Intent(MenuActivity.this, UserSettings.class);
                settings.putExtra("user", user);
                startActivity(settings);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOut = new Intent(MenuActivity.this, Login.class);
                startActivity(logOut);
                MenuActivity.this.finish();
            }
        });
    }
}