package edu.hitsz.shootmode;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 22:00
 */
public interface ShootMode {
    List<BaseBullet> shoot(AbstractAircraft aircraft, int direction, int shootNum, int power);
}

