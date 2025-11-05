package edu.hitsz.application.Score;

import java.util.List;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 16:30
 */
public interface ScoreInterface {
    List<ScoreRecord> getScoreRecords(String mode);
    void addScoreRecord(ScoreRecord scoreRecord, String mode);
    void deleteScoreRecord(ScoreRecord scoreRecord, String mode);
}
