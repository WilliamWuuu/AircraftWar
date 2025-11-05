package edu.hitsz.aircraft;

import edu.hitsz.application.MusicManager;
import edu.hitsz.application.MusicThread;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.shootmode.RingMode;
import edu.hitsz.supply.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 21:56
 */
public class Boss extends BaseEnemy {
    private int shootNum = 1;
    private int power = 5;
    private int direction = 1;

    private MusicThread BGM;

    public Boss(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        BGM = MusicManager.playLoop("src/videos/bgm_boss.wav");
    }

    @Override
    public List<BaseBullet> shoot() {
        RingMode ringMode = new RingMode();
        return ringMode.shoot(this, direction, shootNum, power);
    }

    @Override
    public List<BaseSupply> getSupply() {
        // generate different props
        List<BaseSupply> res = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            double r = Math.random();
            SupplyFactory propFactory;
            if (r < 0.3) {
                propFactory = new BloodSupplyFactory();
            } else if (r < 0.6) {
                propFactory = new BombSupplyFactory();
            } else if (r < 0.9) {
                propFactory = new BulletSupplyFactory();
            } else {
                continue;
            }
            res.add(propFactory.createSupply(locationX, locationY, 0, 10));
        }

        return res;
    }

    @Override
    public void vanish() {
        super.vanish();
        if (BGM != null) {
            BGM.stopMusic();
        }
    }
}
