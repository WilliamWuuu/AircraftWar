package edu.hitsz.shootmode;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 22:00
 */
public class RingMode implements ShootMode {
    private final int bulletCount = 20;  // 一次发射的子弹数
    private final int baseSpeed = 5;    // 子弹的初始速度模长

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft, int direction, int shootNum, int power) {
        List<BaseBullet> bullets = new LinkedList<>();

        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();

        // 每个子弹的角度间隔（弧度）
        double angleStep = 2 * Math.PI / bulletCount;

        for (int i = 0; i < bulletCount; i++) {
            double angle = i * angleStep;

            // 计算速度分量
            int speedX = (int) (baseSpeed * Math.cos(angle));
            int speedY = (int) (baseSpeed * Math.sin(angle));

            BaseBullet bullet = aircraft.createBullet(
                    x, y,
                    speedX,
                    speedY,
                    power
            );

            bullets.add(bullet);
        }

        return bullets;
    }
}

