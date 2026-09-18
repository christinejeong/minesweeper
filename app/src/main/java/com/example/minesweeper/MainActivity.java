package com.example.minesweeper;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // The board is 10 rows by 10 columns, with 6 mines.
    private static final int SIZE = 10;
    private static final int MINE_COUNT = 6;

    // One value per square.
    private final boolean[][] mines = new boolean[SIZE][SIZE];
    private final boolean[][] revealed = new boolean[SIZE][SIZE];
    private final boolean[][] flagged = new boolean[SIZE][SIZE];
    private final int[][] adjacent = new int[SIZE][SIZE];
    private final TextView[][] cellViews = new TextView[SIZE][SIZE];

    private GridLayout boardGrid;
    private Button modeButton;

    private int flagsPlaced = 0;
    private int safeCellsRevealed = 0;
    private boolean digMode = true;
    private boolean gameOver = false;

    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Keep the screen below the phone's status bar and navigation bar.
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

        // IDs come from activity_main.xml.
        boardGrid = findViewById(R.id.boardGrid);
        modeButton = findViewById(R.id.modeButton);

        placeMines();
        calculateAdjacentCounts();

        // Wait until Android measures the grid's width before sizing squares.
        boardGrid.post(this::createCellViews);

        modeButton.setOnClickListener(view -> {
            digMode = !digMode;

            if (digMode) {
                modeButton.setText("⛏ Dig — tap to switch");
            } else {
                modeButton.setText("🚩 Flag — tap to switch");
            }
        });
    }

    private void placeMines() {
        int placed = 0;

        while (placed < MINE_COUNT) {
            int row = random.nextInt(SIZE);
            int column = random.nextInt(SIZE);

            // A repeated random location does not count as another mine.
            if (!mines[row][column]) {
                mines[row][column] = true;
                placed++;
            }
        }
    }

    private void calculateAdjacentCounts() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {

                if (mines[row][column]) {
                    continue;
                }

                int count = 0;

                // Check the eight surrounding positions.
                for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
                    for (int columnOffset = -1;
                         columnOffset <= 1;
                         columnOffset++) {

                        if (rowOffset == 0 && columnOffset == 0) {
                            continue;
                        }

                        int neighborRow = row + rowOffset;
                        int neighborColumn = column + columnOffset;

                        if (isInsideBoard(neighborRow, neighborColumn)
                                && mines[neighborRow][neighborColumn]) {
                            count++;
                        }
                    }
                }

                adjacent[row][column] = count;
            }
        }
    }

    private boolean isInsideBoard(int row, int column) {
        return row >= 0 && row < SIZE
                && column >= 0 && column < SIZE;
    }

    private void createCellViews() {
        boardGrid.removeAllViews();

        // boardGrid fills the available width. Divide it into 10 squares.
        int squareSize = boardGrid.getWidth() / SIZE;
        int margin = dp(1);

        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                final int tappedRow = row;
                final int tappedColumn = column;

                TextView cell = new TextView(this);
                cell.setGravity(Gravity.CENTER);
                cell.setTextSize(16);
                cell.setTextColor(Color.BLACK);
                cell.setBackgroundColor(Color.rgb(78, 181, 103));
                cell.setContentDescription(
                        "Row " + (row + 1)
                                + ", column " + (column + 1)
                                + ", hidden"
                );

                GridLayout.LayoutParams parameters =
                        new GridLayout.LayoutParams(
                                GridLayout.spec(row),
                                GridLayout.spec(column)
                        );

                parameters.width = squareSize - 2 * margin;
                parameters.height = squareSize - 2 * margin;
                parameters.setMargins(margin, margin, margin, margin);

                boardGrid.addView(cell, parameters);
                cellViews[row][column] = cell;

                // Temporary check
                cell.setOnClickListener(view ->
                        Toast.makeText(
                                this,
                                "Row " + (tappedRow + 1)
                                        + ", column " + (tappedColumn + 1),
                                Toast.LENGTH_SHORT
                        ).show()
                );
            }
        }
    }

    private int dp(int value) {
        return Math.round(
                value * getResources().getDisplayMetrics().density
        );
    }
}