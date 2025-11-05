package edu.hitsz.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicManager;
import edu.hitsz.basic.AbstractFlyingObject;

/**
 * @author Yangxin Wu
 * @date 2025/9/21 11:16
 */
public class BloodSupply extends BaseSupply {

    public BloodSupply(int x, int y, int speedX, int speedY) {
        super(x, y, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft hero) {
        MusicManager.playOnce("src/videos/get_supply.wav");
        int increase = (int)(Math.random()*100);
        hero.increaseHp(increase);
    }

    @Override
    public void forward() {
        super.forward();
    }

    @Override
    public boolean notValid() {
        return super.notValid();
    }

    @Override
    public boolean crash(AbstractFlyingObject flyingObject) {
        return super.crash(flyingObject);
    }

    @Override
    public void vanish() {
        super.vanish();
    }
}
