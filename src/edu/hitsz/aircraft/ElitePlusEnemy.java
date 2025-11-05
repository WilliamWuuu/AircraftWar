package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.shootmode.ThreeWayMode;
import edu.hitsz.supply.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 21:53
 */
public class ElitePlusEnemy extends BaseEnemy implements BombObserver {
    private int shootNum = 1;
    private int power = 5;
    private int direction = 1;

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
        ThreeWayMode threeWayMode = new ThreeWayMode();
        return threeWayMode.shoot(this, direction, shootNum, power);
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
        } else if (r < 0.9) {
            propFactory = new BulletPlusSupplyFactory();
        } else {
            return res;
        }

        res.add(propFactory.createSupply(locationX, locationY, 0, 10));
        return res;
    }

    @Override
    public void OnBombActivated() {
        decreaseHp(20);
    }
}
