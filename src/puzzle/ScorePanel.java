package puzzle;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private JLabel moveLabel;
    private JLabel timeLabel;
    private int moves;
    private long startTime;

    public ScorePanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 228, 225));

        moveLabel = new JLabel("이동 횟수: 0");
        moveLabel.setFont(new Font("Serif", Font.BOLD, 16));
        moveLabel.setForeground(new Color(255, 105, 180));

        timeLabel = new JLabel("소요 시간: 0초");
        timeLabel.setFont(new Font("Serif", Font.BOLD, 16));
        timeLabel.setForeground(new Color(255, 105, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 20); // 공백 추가
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(moveLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(timeLabel, gbc);

        moves = 0;
        startTime = System.currentTimeMillis();
        startTimer();
    }

    public void incrementMoves() {
        moves++;
        moveLabel.setText("이동 횟수: " + moves);
    }

    private void startTimer() {
        Timer timer = new Timer(1000, e -> {
            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            timeLabel.setText("소요 시간: " + elapsedTime + "초");
        });
        timer.start();
    }
}
