package edu.hitsz.aircraft;

import edu.hitsz.supply.BombSupply;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 21:53
 */
public class ElitePlusEnemyFactory implements EnemyFactory {
    @Override
    public BaseEnemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        ElitePlusEnemy ret = new ElitePlusEnemy(locationX, locationY, speedX, speedY, hp);
        BombSupply.AddObserver(ret);
        return ret;
    };
}
