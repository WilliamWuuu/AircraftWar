package edu.hitsz.application.Score;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 16:32
 */
public class ScoreManager implements ScoreInterface {

    private static final ScoreManager instance = new ScoreManager();

    public static ScoreManager getInstance() {
        return instance;
    }

    // private static final String FILE_PATH = "score.txt";

    public static String getFilePath(String Mode) {
        return "score_" +  Mode + ".txt";
    }

    public List<ScoreRecord> getScoreRecords(String Mode) {
        List<ScoreRecord> scoreRecords = new ArrayList<>();

        // read out the score files "score_{mode}.txt"
        if (!Files.exists(Paths.get(getFilePath(Mode)))) {
            return scoreRecords;
        }

        // load them as ScoreRecord
        try (BufferedReader br = new BufferedReader(new FileReader(getFilePath(Mode)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) continue;
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                LocalDateTime time = LocalDateTime.parse(parts[2]);
                scoreRecords.add(new ScoreRecord(name, score, time));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scoreRecords;
    }

    public void addScoreRecord(ScoreRecord scoreRecord, String mode) {
        // write the record into "score.txt"
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFilePath(mode), true))) {
            // 写入格式: name,score,时间戳
            String line = String.format("%s,%d,%s",
                    scoreRecord.name,
                    scoreRecord.score,
                    scoreRecord.time.toString());
            bw.write(line);
            bw.newLine(); // 换行
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteScoreRecord(ScoreRecord scoreRecord, String mode) {
        List<ScoreRecord> scoreRecords = getScoreRecords(mode);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFilePath(mode), false))) {
            // 写入格式: name,score,时间戳
            for (ScoreRecord scoreRecord1 : scoreRecords) {
                if (scoreRecord1.score == scoreRecord.score)
                    continue;
                String line = String.format("%s,%d,%s",
                        scoreRecord1.name,
                        scoreRecord1.score,
                        scoreRecord1.time.toString());
                bw.write(line);
                bw.newLine(); // 换行
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
