package game.view;

import javax.swing.*;
import java.awt.*;

/**
 * ScoreView: 이동 횟수와 시간을 화면에 표시하는 클래스입니다.
 */
public class ScoreView extends JPanel {
    private JLabel movesLabel;
    private JLabel timeLabel;
    private int moveCount;
    private int endTime;

    public ScoreView() {
        setLayout(new GridLayout(1, 2));
        setBackground(new Color(255, 228, 225));
        
        movesLabel = new JLabel("Moves: 0", JLabel.CENTER);
        movesLabel.setFont(new Font("Serif", Font.BOLD, 16));
        movesLabel.setForeground(new Color(255, 105, 180));
        
        timeLabel = new JLabel("Time: 0", JLabel.CENTER);
        timeLabel.setFont(new Font("Serif", Font.BOLD, 16));
        timeLabel.setForeground(new Color(255, 105, 180));
        
        add(movesLabel);
        add(timeLabel);
    }

    public void countMove() {
        moveCount++;
        movesLabel.setText("Moves: " + moveCount);
    }

    public void updateTime(int elapsedTime) {
        this.endTime = elapsedTime;
        timeLabel.setText("Time: " + elapsedTime);
    }
}
