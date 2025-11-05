package edu.hitsz.aircraft;

/**
 * @author Yangxin Wu
 * @date 2025/10/8 20:11
 */
public interface EnemyFactory {
    public abstract BaseEnemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp);
}
