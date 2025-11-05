package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 21:56
 */
public class BossFactory implements EnemyFactory {
    @Override
    public BaseEnemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new Boss(locationX, ImageManager.BOSS_IMAGE.getHeight() + 10, 5, 0, 400);
    }
}

