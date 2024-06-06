package puzzle.View;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private JLabel movesLabel;
    private JLabel timeLabel;
    private int moves;
    private long elapsedTime;

    public ScorePanel() {
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

    public void incrementMoves() {
        moves++;
        movesLabel.setText("Moves: " + moves);
    }

    public void updateTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
        timeLabel.setText("Time: " + elapsedTime);
    }
}
