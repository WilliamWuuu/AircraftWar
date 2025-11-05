package edu.hitsz.aircraft;

import edu.hitsz.supply.BombSupply;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 20:10
 */
public class EliteEnemyFactory implements EnemyFactory {
    @Override
    public BaseEnemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        EliteEnemy enemy = new EliteEnemy(locationX, locationY, speedX, speedY, hp);
        BombSupply.AddObserver(enemy);
        return enemy;
    };
}
