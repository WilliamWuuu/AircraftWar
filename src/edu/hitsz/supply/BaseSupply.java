package edu.hitsz.supply;

import edu.hitsz.basic.AbstractFlyingObject;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 20:19
 */
public abstract class BaseSupply extends AbstractFlyingObject implements Supply {

    public BaseSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
}