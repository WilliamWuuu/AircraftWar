package edu.hitsz.supply;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 20:23
 */
public class BloodSupplyFactory implements SupplyFactory {

    @Override
    public BaseSupply createSupply(int locationX, int locationY, int speedX, int speedY) {
        return new BloodSupply(locationX, locationY, speedX, speedY);
    }
}
