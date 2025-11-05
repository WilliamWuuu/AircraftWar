package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 18:07
 */
public class Menu {
    private JPanel panel1;
    private JButton EasyButton;
    private JButton NormalButton;
    private JButton HardButton;
    private JCheckBox music;

    public Menu() {
        EasyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                EasyGame game = new EasyGame(music.isSelected());
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.action();
            }
        });

        NormalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                NormalGame game = new NormalGame(music.isSelected());
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.action();
            }
        });

        HardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                HardGame game = new HardGame(music.isSelected());
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.action();
            }
        });
    }

    public JPanel getMainPanel() {
        return panel1;
    }
}
