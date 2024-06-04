package puzzle;

import javax.swing.*;

import java.awt.*;

public class PuzzleView extends JPanel {
    private PuzzleModel model;
    private RoundButton[][] buttons;
    private ScorePanel scorePanel;

    public PuzzleView(PuzzleModel model) {
        this.model = model;
        int size = model.getSize();
        buttons = new RoundButton[size][size];
        setLayout(new BorderLayout());
        
        scorePanel = new ScorePanel();
        add(scorePanel, BorderLayout.NORTH);

        JPanel puzzlePanel = new JPanel(new GridLayout(size, size, 0, 0));
        puzzlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 퍼즐과 JFrame 사이에 여백 추가
        initializeButtons(puzzlePanel);
        puzzlePanel.setBackground(Color.WHITE); // 배경을 하얀색으로 설정
        
        add(puzzlePanel, BorderLayout.CENTER);
    }

    private void initializeButtons(JPanel puzzlePanel) {
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                buttons[i][j] = new RoundButton("");
                buttons[i][j].setFont(new Font("Serif", Font.BOLD, 20));
                buttons[i][j].setBackground(new Color(255, 182, 193));
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setPreferredSize(new Dimension(80, 80));
                puzzlePanel.add(buttons[i][j]);
            }
        }
        updateView();
    }

    public void updateView() {
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                int value = model.getValueAt(i, j);
                if (value == 0) {
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(Color.WHITE); // 빈 칸은 하얀색으로 표시
                } else {
                    buttons[i][j].setText(String.valueOf(value));
                    buttons[i][j].setBackground(new Color(255, 182, 193));
                }
            }
        }
    }

    public JButton getButton(int x, int y) {
        return buttons[x][y];
    }

    public ScorePanel getScorePanel() {
        return scorePanel;
    }
}
