# Minesweeper

A native Android take on the classic Minesweeper game, built with Java and XML layouts. Clear a 10 × 10 board without hitting any of its six hidden mines, mark suspected mines with flags, and track how long it takes to finish each game.

## Features

- **Randomized boards:** Six mines are placed at distinct random positions at the start of every game.
- **Dig and flag modes:** Tap to reveal a cell, or switch modes to place and remove flags on hidden cells. Flagged cells are protected from digging.
- **Adjacent-mine clues:** Numbered cells show how many of their eight surrounding cells contain mines. Zero-mine cells appear blank.
- **Automatic area reveal:** A queue-based flood fill expands through connected blank cells and reveals their bordering numbered cells, while preserving flags.
- **Win/loss detection:** Reveal all 94 safe cells to win. Uncovering a mine ends the game in a loss.
- **Timed results:** A live timer starts when the game screen opens and stops when the game ends. A results screen shows the outcome and elapsed seconds.
- **Play again:** Start a fresh randomized board from the results screen.
- **Accessible cell descriptions:** Cells describe their position and current state for accessibility services.

## How to play

1. Start in **Dig** mode and tap a hidden cell to reveal it.
2. Use the numbers to infer where nearby mines might be.
3. Tap the mode button to switch to **Flag** mode, then tap a hidden cell to toggle its flag.
4. Switch back to **Dig** mode to continue clearing the board.
5. After a win or loss, the full board is revealed. Tap any cell to open the results screen.
6. Select **Play Again** to start a new game.

Flags are optional markers: winning requires revealing all safe cells. The flag counter displays `6 − flags placed`; flags are not capped at six, so the counter can become negative. Mines are placed before the first move, so the first dig is not guaranteed to be safe.

## Built with

- Java for game logic and Android activities
- XML layouts and Android `GridLayout` for the interface
- AndroidX AppCompat and Material Components
- Gradle with Kotlin DSL build configuration

## Getting started

### Requirements

Use Android Studio with support for the versions declared in this project:

| Setting | Project configuration |
| --- | --- |
| Minimum Android version | Android 6.0 / API 23 |
| Compile / target SDK | API 37 |
| Android Gradle Plugin | 9.3.3 |
| Gradle wrapper | 9.5.0 |
| Gradle daemon JVM | Java 25 |
| Java source / target compatibility | Java 11 |

These values reflect the supplied project configuration. The Gradle runtime JVM and Java source compatibility level are separate settings.

### Run in Android Studio

1. Clone this repository:
   ```bash
   git clone https://github.com/christinejeong/Minesweeper.git
   ```
2. Open the `Minesweeper` directory in Android Studio.
3. Install the required SDK, configure the Gradle JVM, and let Gradle sync finish.
4. Select an emulator or connected Android device running API 23 or higher.
5. Run the `app` configuration.

Android Studio creates your local SDK configuration in `local.properties`; this machine-specific file is excluded from version control.

### Build from the command line

With the Android SDK and required JVM configured:

```bash
./gradlew assembleDebug
```

On Windows, use `gradlew.bat assembleDebug`. The debug APK is generated in `app/build/outputs/apk/debug/`.

## How it works

`MainActivity` stores mines, revealed cells, flags, and neighboring-mine counts in two-dimensional arrays. It places six unique mines, calculates clues by checking up to eight neighbors per cell, and handles input and board rendering.

Empty-area expansion uses a queue-based flood fill with `ArrayDeque`. Each safe cell is revealed once; only cells with zero adjacent mines add their neighbors to the queue. Bounds checks and checks for revealed, flagged, or mined cells prevent invalid reveals.

The timer uses Android’s monotonic `SystemClock.elapsedRealtime()`. When the game ends, the outcome and final duration are passed to `ResultActivity` through an `Intent`.

## Project structure

```text
app/src/main/
├── java/com/example/minesweeper/
│   ├── MainActivity.java       # Board, game rules, input, and timer
│   └── ResultActivity.java     # Outcome, elapsed time, and replay
├── res/layout/
│   ├── activity_main.xml       # Game screen
│   └── activity_result.xml     # Results screen
└── AndroidManifest.xml        # App and activity declarations
gradle/                        # Version catalog and Gradle wrapper
```

The repository includes the original Android template test files. Comprehensive automated gameplay tests are not yet included.
