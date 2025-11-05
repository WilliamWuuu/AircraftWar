package edu.hitsz.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicManager;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.shootmode.ShootModeEnum;

/**
 * @author Yangxin Wu
 * @date 2025/9/21 11:17
 */
public class BulletSupply extends BaseSupply {
    public BulletSupply(int x, int y, int speedX, int speedY) {
        super(x, y, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft hero) {
        System.out.println("FireSupply active!");
        MusicManager.playOnce("src/videos/get_supply.wav");
        Runnable r = () -> {
            try {
                System.out.println("FireSupply activate!");
                hero.SwitchShotMode(ShootModeEnum.ThreeWay);
                Thread.sleep(10000);
                hero.SwitchShotMode(ShootModeEnum.Straight);
                System.out.println("FireSupply deactivate!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(r).start();
    }
}
