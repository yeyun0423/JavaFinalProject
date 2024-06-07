package puzzle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import puzzle.View.ScorePanel;

public class GameTimer {
    private Timer timer;
    private long startTime;
    private ScorePanel scorePanel;

    public GameTimer(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                scorePanel.updateTime(elapsedTime);
            }
        });
    }

    public void start() {
        startTime = System.currentTimeMillis();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }
}
