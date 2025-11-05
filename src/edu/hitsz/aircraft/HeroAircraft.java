package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.shootmode.RingMode;
import edu.hitsz.shootmode.ShootModeEnum;
import edu.hitsz.shootmode.StraightMode;
import edu.hitsz.shootmode.ThreeWayMode;
import edu.hitsz.supply.BaseSupply;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private static HeroAircraft instance = new HeroAircraft(
            Main.WINDOW_WIDTH / 2,
            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
            0, 0, 1000);

    public static HeroAircraft getInstance() {
        return instance;
    }


    public ShootModeEnum shootMode = ShootModeEnum.Straight;
    private int shootCount = 0;

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：-1，向下发射：1)
     */
    private int direction = -1;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        switch (shootMode) {
            case Straight: {
                StraightMode straightMode = new StraightMode();
                return straightMode.shoot(this, direction, shootNum, power);
            }
            case ThreeWay: {
                ThreeWayMode threeWayMode = new ThreeWayMode();
                return threeWayMode.shoot(this, direction, shootNum, power);
            }
            case Ring: {
                RingMode ringMode = new RingMode();
                return ringMode.shoot(this, direction, shootNum, power);
            }
            default: return new LinkedList<BaseBullet>();
        }
    }

    public void SwitchShotMode(ShootModeEnum mode) {
        // update shootmode and reset expire time
        shootMode = mode;
    }

    @Override
    public BaseBullet createBullet(int x, int y, int speedX, int speedY, int power) {
        return new HeroBullet(x, y, speedX, speedY, power);
    }

    @Override
    public List<BaseSupply> getSupply() {
        return new LinkedList<>();
    }
}
