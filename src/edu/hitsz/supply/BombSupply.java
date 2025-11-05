package edu.hitsz.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicManager;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yangxin Wu
 * @date 2025/9/21 11:19
 */
public class BombSupply extends BaseSupply {
    private static List<BombObserver> observers = new ArrayList<>();

    public static void AddObserver(BombObserver observer) {
        observers.add(observer);
    }

    public static void removeObserver(BombObserver observer) {
        observers.remove(observer);
    }

    public BombSupply(int x, int y, int speedX, int speedY) {
        super(x, y, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft hero) {
        System.out.println("BombSupply active!");
        MusicManager.playOnce("src/videos/bomb_explosion.wav");
        for (BombObserver observer : observers) {
            observer.OnBombActivated();
        }
    }
}
