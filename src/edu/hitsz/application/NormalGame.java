package edu.hitsz.application;

import edu.hitsz.aircraft.Boss;
import edu.hitsz.aircraft.BossFactory;
import edu.hitsz.aircraft.EnemyFactory;

import java.awt.*;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 17:29
 */
public class NormalGame extends Game {

    public NormalGame(boolean hasMusic) {
        super(hasMusic);
    }

    @Override
    public Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE3;
    }

    @Override
    public String getMode() {
        return "normal";
    }

    @Override
    public void increaseDifficulty() {
        EnemyIncHp += 0.1;
        EnemyIncSpeed += 0.1;
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
        }
    }
}
