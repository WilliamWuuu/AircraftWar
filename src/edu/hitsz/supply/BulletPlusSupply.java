package edu.hitsz.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicManager;
import edu.hitsz.shootmode.ShootModeEnum;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 17:41
 */
public class BulletPlusSupply extends BaseSupply {
    public BulletPlusSupply(int x, int y, int speedX, int speedY) {
        super(x, y, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft hero) {
        System.out.println("FireSupply Plus activate!");
        MusicManager.playOnce("src/videos/get_supply.wav");
        Runnable r = () -> {
            try {
                System.out.println("FireSupply Plus activate!");
                hero.SwitchShotMode(ShootModeEnum.Ring);
                Thread.sleep(10000);
                hero.SwitchShotMode(ShootModeEnum.Straight);
                System.out.println("FireSupply Plus deactivate!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(r).start();
    }
}
