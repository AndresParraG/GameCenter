package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button logInButton;
    Button signUpButton;
    EditText userEdit;
    EditText passEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DataBase db = new DataBase(this);

        logInButton = (Button) findViewById(R.id.logInButton);
        signUpButton = (Button) findViewById(R.id.signUp);
        userEdit = (EditText) findViewById(R.id.logInUser);
        passEdit = (EditText) findViewById(R.id.logInPass);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.checkUserPass(userEdit.getText().toString(), passEdit.getText().toString())) {
                    Intent intent = new Intent(Login.this, MenuActivity.class);
                    intent.putExtra("user", userEdit.getText().toString());
                    startActivity(intent);
                    Login.this.finish();
                } else {
                    Toast.makeText(Login.this, "INVALID USER OR PASSWORD", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });
    }
}