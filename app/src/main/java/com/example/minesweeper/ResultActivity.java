package com.example.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (view, insets) -> {
                    Insets bars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );
                    view.setPadding(
                            bars.left, bars.top, bars.right, bars.bottom
                    );
                    return insets;
                }
        );

        TextView resultText = findViewById(R.id.resultText);
        TextView resultTimeText = findViewById(R.id.resultTimeText);
        Button playAgainButton = findViewById(R.id.playAgainButton);

        boolean won = getIntent().getBooleanExtra("won", false);
        int seconds = getIntent().getIntExtra("seconds", 0);

        resultText.setText(won ? "You won!" : "You lost.");
        resultTimeText.setText(
                "Elapsed time: " + seconds + " seconds"
        );

        playAgainButton.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}