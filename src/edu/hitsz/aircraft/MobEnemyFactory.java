package edu.hitsz.aircraft;

import edu.hitsz.supply.BombSupply;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 20:10
 */
public class MobEnemyFactory implements EnemyFactory {
    @Override
    public BaseEnemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        MobEnemy ret = new MobEnemy(locationX, locationY, speedX, speedY, hp);
        BombSupply.AddObserver(ret);
        return ret;
    }
}
