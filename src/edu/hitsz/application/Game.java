package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.application.Score.NameDialog;
import edu.hitsz.application.Score.ScoreForm;
import edu.hitsz.application.Score.ScoreManager;
import edu.hitsz.application.Score.ScoreRecord;
import edu.hitsz.supply.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    public final HeroAircraft heroAircraft;
    public final List<BaseEnemy> enemyAircrafts;
    public final List<BaseBullet> heroBullets;
    public final List<BaseBullet> enemyBullets;
    public final List<BaseSupply> Props;

    /**
     * 游戏难度提升（敌机属性）
     */
    private int RoundCounter = 0;
    public double EnemyIncHp = 1;
    public double EnemyIncSpeed = 1;
    public double MobEnemyProbability = 0.3;
    public double EliteEnemyProbability = 0.3;
    public double ElitePlusEnemyProbability = 0.3;
    // public double BossProbability = 0.1;
    public int bossMaxNumber = 1;
    public int bossExistScore = 400;
    public int bossHp = 400;

    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;

    /**
     * 当前得分
     */
    public int score = 0;
    /**
     * 当前时刻
     */
    public int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;
    private ScoreManager scoreManager;

    private MusicThread BGM;

    public Game(boolean hasMusic) {
        heroAircraft = HeroAircraft.getInstance();

        MusicManager.enabled = hasMusic;
        BGM = MusicManager.playLoop("src/videos/bgm.wav");

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        Props = new LinkedList<>();

        scoreManager = ScoreManager.getInstance();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    public void increaseDifficulty() {

    }

    public void generateBoss() {

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;


            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                RoundCounter++;
                if (RoundCounter % 10 == 0)
                    increaseDifficulty();
                // 新敌机产生
                generateBoss();
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    // random generate different enemy
                    double r =  Math.random();
                    EnemyFactory enemyFactory;
                    if (r < MobEnemyProbability) {
                        enemyFactory = new MobEnemyFactory();
                        enemyAircrafts.add(enemyFactory.createEnemy(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                                (int)(0 * EnemyIncSpeed),
                                (int)(10 * EnemyIncSpeed),
                                (int)(30 * EnemyIncHp)
                        ));
                    } else if (r < MobEnemyProbability + EliteEnemyProbability) {
                        enemyFactory = new EliteEnemyFactory();
                        enemyAircrafts.add(enemyFactory.createEnemy(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                                (int)(10 * EnemyIncSpeed),
                                (int)(5 * EnemyIncSpeed),
                                (int)(40 * EnemyIncHp)
                        ));
                    } else {
                        enemyFactory = new ElitePlusEnemyFactory();
                        enemyAircrafts.add(enemyFactory.createEnemy(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                                (int)(10 * EnemyIncSpeed),
                                (int)(5 * EnemyIncSpeed),
                                (int)(40 * EnemyIncHp)
                        ));
                    }


                }
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 掉落物移动
            propsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                gameOver();
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private void gameOver() {
        MusicManager.stopall();
        executorService.shutdown();
        gameOverFlag = true;
        System.out.println("Game Over!");
        MusicManager.playOnce("src/videos/game_over.wav");

        NameDialog dialog = new NameDialog(score);
        String playerName = dialog.showDialog();

        // persist the score
        if (playerName != null) {
            ScoreRecord record = new ScoreRecord(playerName, score, LocalDateTime.now());
            scoreManager.addScoreRecord(record, getMode());
        }

        ScoreForm form = new ScoreForm(getMode());
        Main.cardPanel.add(form.getPanel());
        Main.cardLayout.last(Main.cardPanel);
    }

    public String getMode() {
        return null;
    }

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击
        for (var e: enemyAircrafts) {
            enemyBullets.addAll(e.shoot());
        }

        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void propsMoveAction() {
        for (var prop : Props) {
            prop.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid())
                continue;
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    MusicManager.playOnce("src/videos/bullet_hit.wav");
                    bullet.vanish();
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (var prop : Props) {
            if (prop.notValid())
                continue;
            if (prop.crash(heroAircraft)) {
                // todo: exclude the boss
                prop.activate(heroAircraft);
                prop.vanish();
            }
        }

        for (var enemy: enemyAircrafts) {
            if (enemy.notValid()) {
                // 获得分数，产生道具补给
                Props.addAll(enemy.getSupply());
                score += 10;
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        Props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    public Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE;
    }

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(getBackgroundImage(), 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(getBackgroundImage(), 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, Props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }
}
