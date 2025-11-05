package edu.hitsz.application.Score;

import javax.swing.*;
import java.awt.event.*;

public class NameDialog extends JDialog {
    private JPanel pane;
    private JPanel ButtonPanel;
    private JPanel TextPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel Label;

    private String playerName = null;

    public NameDialog(int score) {
        setContentPane(pane);
        setModal(true);
        setTitle("记录得分");
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 设置分数显示
        Label.setText("本局得分：" + score);

        // 按钮事件
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // 支持回车和 Esc
        getRootPane().setDefaultButton(buttonOK);
        addKeyBindings();
    }

    private void onOK() {
        String input = textField1.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "名字不能为空！");
            return;
        }
        playerName = input;
        dispose(); // 关闭对话框
    }

    private void onCancel() {
        playerName = null; // 用户取消
        dispose();
    }

    private void addKeyBindings() {
        KeyStroke esc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(esc, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    /** 调用该方法显示对话框，并等待返回 */
    public String showDialog() {
        setVisible(true); // 模态对话框会阻塞在这里
        return playerName;
    }
}
