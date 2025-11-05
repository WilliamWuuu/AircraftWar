package edu.hitsz.aircraft;

import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.supply.BaseSupply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 21:50
 */
class HeroAircraftTest {

    private HeroAircraft hero;

    @BeforeEach
    void setUp() {
        hero = HeroAircraft.getInstance();
    }

    @Test
    void Singleton() {
        HeroAircraft another =  HeroAircraft.getInstance();
        assertSame(hero, another, "Hero should be singleton");
    }

    @org.junit.jupiter.api.Test
    void createBullet() {
        assertInstanceOf(HeroBullet.class, hero.createBullet(0, 0, 0, 0, 0));
    }

    @org.junit.jupiter.api.Test
    void getProp() {
        List<BaseSupply> props = hero.getSupply();
        assertNotNull(props);
        assertTrue(props.isEmpty());
    }
}