package edu.hitsz.application;

import edu.hitsz.aircraft.Boss;
import edu.hitsz.aircraft.BossFactory;
import edu.hitsz.aircraft.EnemyFactory;

import java.awt.*;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 17:29
 */
public class HardGame extends Game {
    public HardGame(boolean hasMusic) {
        super(hasMusic);
        bossExistScore = 300;
    }



    @Override
    public Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE4;
    }

    @Override
    public String getMode() {
        return "hard";
    }

    @Override
    public void generateBoss() {
        Boolean bossExists = enemyAircrafts.stream().anyMatch(e -> e instanceof Boss);
        if (score >= bossExistScore && !bossExists) {
            EnemyFactory enemyFactory = new BossFactory();
            enemyAircrafts.add(enemyFactory.createEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    5,
                    0,
                    bossHp
            ));
            bossExistScore += 1000;
            bossHp += 100;
        }
    }


    @Override
    public void increaseDifficulty() {
        EnemyIncHp += 0.2;
        EnemyIncSpeed += 0.2;
        if (MobEnemyProbability > 0.02) {
            MobEnemyProbability -= 0.02;
            EliteEnemyProbability += 0.01;
            ElitePlusEnemyProbability += 0.01;
        }
        System.out.println(
                "increase difficulty: enemy hp x" + EnemyIncHp +
                        " enemy speed x" + EnemyIncSpeed +
                        " enemy probability " + MobEnemyProbability + " " + ElitePlusEnemyProbability + " " + EliteEnemyProbability
        );
    }
}
