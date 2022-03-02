package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    EditText user;
    EditText pass;
    EditText rePass;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        DataBase db = new DataBase(this);

        user = (EditText) findViewById(R.id.signUpUser);
        pass = (EditText) findViewById(R.id.signUpPass);
        rePass = (EditText) findViewById(R.id.signUpPassRep);
        signUp = (Button) findViewById(R.id.signUpButton);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.checkUser(user.getText().toString())) {
                    Toast.makeText(Signup.this, "USER ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                } else if(pass.getText().toString().equals(rePass.getText().toString())) {
                    db.insert(user.getText().toString(), pass.getText().toString());
                    startActivity(new Intent(Signup.this, MenuActivity.class));
                    finish();
                } else {
                    Toast.makeText(Signup.this, "INVALID PASSWORD", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}