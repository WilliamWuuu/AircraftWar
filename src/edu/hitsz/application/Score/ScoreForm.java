package edu.hitsz.application.Score;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 18:06
 */
public class ScoreForm {
    private JPanel panel1;
    private JTable scoreTable;
    private JScrollPane tableScroll;
    private JButton deleteButton;

    private DefaultTableModel tableModel;

    public ScoreForm(String mode) {
        String[] columns = {"Rank"," Name", "Score", "Time"};
        ScoreManager scoreManager = ScoreManager.getInstance();
        List<ScoreRecord> scores = scoreManager.getScoreRecords(mode);
        scores.sort(Comparator.comparingInt(ScoreRecord::getScore).reversed());
        String[][] data = new String[scores.size()][4];
        for(int i = 0; i < scores.size(); i++) {
            data[i][0] = String.valueOf(i);
            data[i][1] = String.valueOf(scores.get(i).name);
            data[i][2] = String.valueOf(scores.get(i).score);
            data[i][3] = scores.get(i).time.toString();
        }
        tableModel = new DefaultTableModel(data, columns);
        scoreTable.setModel(tableModel);
        tableScroll.setViewportView(scoreTable);

        deleteButton.addActionListener(e -> onDelete(mode));
    }

    private void onDelete(String mode) {
        int selectedRow = scoreTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel1, "请先选择要删除的记录！");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(panel1, "确定删除该条记录吗？", "确认删除",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            int score = Integer.parseInt((String) tableModel.getValueAt(selectedRow, 2));
            String timeStr = (String) tableModel.getValueAt(selectedRow, 3);
            LocalDateTime time = LocalDateTime.parse(timeStr);
            ScoreRecord scoreRecord = new ScoreRecord(name, score, time);
            ScoreManager.getInstance().deleteScoreRecord(scoreRecord, mode);
            tableModel.removeRow(selectedRow);
        }
    }

    public JPanel getPanel() {
        return panel1;
    }
}
