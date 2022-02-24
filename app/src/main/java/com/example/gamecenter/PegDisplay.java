package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PegDisplay extends AppCompatActivity {

    private PegGame peg;
    private TextView pegsCounter;
    private Button undoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peg_display);

        peg = new PegGame();
        pegsCounter = (TextView) findViewById(R.id.pegsCounter);
        undoButton = findViewById(R.id.undoPeg);

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peg.undo();
                update();
            }
        });
    }

    public void onClick(View view) {
        if (!peg.isWin()) {
            peg.clickPeg(view);
            update();
        } else {
            Toast.makeText(this, "WIN", Toast.LENGTH_SHORT).show();
        }
    }

    public void reset(View view) {
        peg = new PegGame();
        update();
    }

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