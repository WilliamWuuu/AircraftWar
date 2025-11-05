package edu.hitsz.supply;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 17:41
 */
public class BulletPlusSupplyFactory implements SupplyFactory {
    @Override
    public BaseSupply createSupply(int locationX, int locationY, int speedX, int speedY) {
        return new BulletPlusSupply(locationX, locationY, speedX, speedY);
    }
}
