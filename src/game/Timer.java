package game;

import game.view.ScoreView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Timer: 게임의 경과 시간을 측정하고 ScoreView에 업데이트하는 클래스입니다.
 */
public class Timer {
    private javax.swing.Timer timer;
    private int startTime;
    private int elapsedTime;
    private ScoreView scoreView;

    public Timer(ScoreView scoreView) {
        this.scoreView = scoreView;
        this.startTime = (int) (System.currentTimeMillis() / 1000);
        this.elapsedTime = 0;
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime = (int) (System.currentTimeMillis() / 1000) - startTime;
                scoreView.updateTime(elapsedTime);
            }
        });
    }

    public void start() {
        startTime = (int) (System.currentTimeMillis() / 1000);
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
}
