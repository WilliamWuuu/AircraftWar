package edu.hitsz.application.Score;

import java.time.LocalDateTime;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 16:32
 */
public class ScoreRecord {
    public final String name;
    public final int score;
    public final LocalDateTime time;

    public ScoreRecord(String name, int score, LocalDateTime time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
