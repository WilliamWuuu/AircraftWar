package edu.hitsz.application;

import java.awt.*;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 17:29
 */
public class EasyGame extends Game {
    public EasyGame(boolean hasMusic) { super(hasMusic); }

    @Override
    public Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE2;
    }

    @Override
    public String getMode() {
        return "easy";
    }
}
