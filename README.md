<img src="https://skillicons.dev/icons?i=java,swing" />

# AircraftWar
🎮 A demo of a plane shooting game based on Java Swing.

🎓 The lab project of "[COMP3059] Introduction to Object-oriented Software Construction" at HITSZ. 

👏 Original skeleton by [dustphantom](https://github.com/duskphantom).

## Highlights
- Three difficulties: Easy, Normal, Hard
  - Easy: relaxed pace, no bosses
  - Normal: gradual difficulty ramp, bosses spawn as score increases
  - Hard: faster ramp, earlier bosses whose HP scales up
- Enemy variety: Mob, Elite, Elite Plus, and Boss
- Power‑ups (Supplies): Heal, Bomb, Bullet (Three‑way for 10s), Bullet Plus (Ring for 10s)
- Background music and SFX with a music toggle in the main menu
- Local leaderboard per difficulty with delete support (stored in `score_<mode>.txt`)
- Simple, responsive mouse controls


## Run the Game
You can run the project in IntelliJ IDEA (recommended) or from the command line. The project ships with its only external dependency as a local JAR.

### Option A: Run in IntelliJ IDEA
1. Open the project folder `AircraftWar-base` in IDEA
2. Ensure the library `lib/commons-lang3-3.8.1.jar` is on the classpath (IDEA usually picks it up from the `.iml`)
3. Run the main class: `edu.hitsz.application.Main`
4. In the menu, pick a difficulty and optionally enable music

### Option B: Run from Terminal
Run these commands from the project root so images/audio load via the expected relative paths:

```shell
# Compile classes into ./out
mkdir -p out
javac -cp "lib/commons-lang3-3.8.1.jar:src" -d out $(find src -name "*.java")

# Launch the game
java -cp "out:lib/commons-lang3-3.8.1.jar" edu.hitsz.application.Main
```

If you see image/audio not found errors, make sure your working directory is the project root (where `src/` resides) before running `java`.


## Project Structure
```
├── src/
│   ├── edu/
│   │   └── hitsz/
│   │       ├── application/
│   │       │   ├── Main.java
│   │       │   ├── Menu.java
│   │       │   ├── Game.java
│   │       │   ├── EasyGame.java
│   │       │   ├── NormalGame.java
│   │       │   ├── HardGame.java
│   │       │   ├── Score/
│   │       │   │   ├── ScoreInterface.java (interface)
│   │       │   │   ├── ScoreManager.java
│   │       │   │   └── ScoreRecord.java
│   │       │   ├── ImageManager.java
│   │       │   ├── MusicManager.java
│   │       │   └── MusicThread.java
│   │       ├── aircraft/
│   │       │   ├── HeroAircraft.java
│   │       │   ├── BaseEnemy.java
│   │       │   ├── MobEnemy.java
│   │       │   ├── EliteEnemy.java
│   │       │   ├── ElitePlusEnemy.java
│   │       │   ├── Boss.java
│   │       │   └── [Other Enemy and Factory]
│   │       ├── shootmode/
│   │       │   ├── ShootMode.java (interface)
│   │       │   ├── ShootModeEnum.java (enum)
│   │       │   ├── Straight.java
│   │       │   ├── Threeway.java
│   │       │   └── Ring.java
│   │       └── supply/
│   │           ├── Supply.java (interface)
│   │           ├── SupplyFactory.java (interface)
│   │           ├── BaseSupply.java
│   │           ├── BombSupply.java
│   │           ├── BombSupplyFactory.java
│   │           ├── BombObserver.java (interface)
│   │           └── [Other Supply and Factory: Blood, Bullet (Threeway), BulletPlus (Ring)]
│   ├── images/
│   ├── videos/
└── lib/
    └── commons-lang3-3.8.1.jar
```

## Design Notes
- Patterns used
  - Singleton: `HeroAircraft`
  - Factory: enemy and supply creation (`*Factory` classes)
  - Strategy: interchangeable shooting modes in `shootmode`
  - Observer: `BombSupply` notifies registered `BombObserver`s (e.g., `MobEnemy`)
- Game loop
  - Fixed‑delay scheduled task (default tick 40ms; new‑cycle logic ~600ms) to spawn enemies, shoot, move, collide, and repaint
- Difficulty
  - On each cycle tick reaching a new “round” (about every 600ms), a counter increases; roughly every 10 rounds (~6s) difficulty ramps (Normal/Hard override). Hard increases HP/speed faster and raises boss HP each time a boss spawns. Normal increases HP/speed modestly; boss HP stays constant
  - Easy does not spawn bosses
- Assets are loaded via relative file paths under `src/images` and `src/videos`, so the working directory matters


## Scores and Leaderboard
- Scores persist to text files next to the project root: `score_easy.txt`, `score_normal.txt`, `score_hard.txt`
- On game over, enter your name to save a record (name, score, timestamp)
- Leaderboard view sorts by score (descending) and supports deleting a selected record


## Troubleshooting
- Images or audio fail to load
  - Ensure you run from the project root so relative paths like `src/images/...` and `src/videos/...` resolve
- No sound
  - Check the music checkbox in the menu, and confirm your system can play WAV files
- Class not found for `org.apache.commons.lang3.concurrent.BasicThreadFactory`
  - Make sure `lib/commons-lang3-3.8.1.jar` is on your runtime classpath

