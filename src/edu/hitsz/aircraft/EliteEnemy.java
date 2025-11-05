package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.shootmode.StraightMode;
import edu.hitsz.supply.*;
import edu.hitsz.supply.BaseSupply;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yangxin Wu
 * @date 2025/9/21 11:42
 */
public class EliteEnemy extends BaseEnemy implements BombObserver {

    private int shootNum = 1;
    private int power = 5;
    private int direction = 1;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {

        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        StraightMode straightMode = new StraightMode();
        return straightMode.shoot(this, direction, shootNum, power);
    }

    @Override
    public List<BaseSupply> getSupply() {
        // generate different props
        List<BaseSupply> res = new LinkedList<>();
        double r = Math.random();

        SupplyFactory propFactory;
        if (r < 0.3) {
            propFactory = new BloodSupplyFactory();
        } else if (r < 0.6) {
            propFactory = new BombSupplyFactory();
        } else {
            propFactory = new BulletSupplyFactory();
        }

        res.add(propFactory.createSupply(locationX, locationY, 0, 10));
        return res;
    }

    @Override
    public void OnBombActivated() {
        decreaseHp(Integer.MAX_VALUE);
    }
}
