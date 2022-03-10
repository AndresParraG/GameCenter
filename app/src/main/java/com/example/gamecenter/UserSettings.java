package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserSettings extends AppCompatActivity {

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DataBase db = new DataBase(this);
        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");

        EditText oldPassword = (EditText) findViewById(R.id.oldPass);
        EditText newPassword = (EditText) findViewById(R.id.newPass);
        EditText newPassRep = (EditText) findViewById(R.id.newPassRep);
        Button button1 = (Button) findViewById(R.id.changePassButton);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = oldPassword.getText().toString();
                String newPass = newPassword.getText().toString();
                String newPassR = newPassRep.getText().toString();
                if (newPass.equals("")) {
                    Toast.makeText(UserSettings.this, "INVALID INPUT", Toast.LENGTH_SHORT).show();
                } else if (newPass.equals(newPassR)) {
                    if (db.checkUserPass(user, oldPass)) {
                        db.updatePassword(user, newPass);
                        Toast.makeText(UserSettings.this, "PASSWORD CHANGED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        UserSettings.this.finish();
                    } else {
                        Toast.makeText(UserSettings.this, "INCORRECT PASSWORD", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserSettings.this, "NEW PASSWORDS DON'T MATCH", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}