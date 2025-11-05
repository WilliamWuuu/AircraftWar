package edu.hitsz.shootmode;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 22:00
 */
public class StraightMode implements ShootMode {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft, int direction, int shootNum, int power) {
        List<BaseBullet> bullets = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + direction*5;
        for(int i=0; i< shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            BaseBullet bullet = aircraft.createBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            bullets.add(bullet);
        }
        return bullets;
    }
}
