package edu.hitsz.supply;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 20:24
 */
public interface SupplyFactory {
    public abstract BaseSupply createSupply(int locationX, int locationY, int speedX, int speedY);
}
