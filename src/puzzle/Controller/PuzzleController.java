package puzzle.Controller;

import puzzle.Model.PuzzleModel;
import puzzle.Model.ScoreModel;
import puzzle.Model.User;
import puzzle.Model.Userlist;
import puzzle.View.PuzzleView;
import puzzle.View.ScorePanel;
import puzzle.View.Ranking;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class PuzzleController {
    private PuzzleModel model;
    private PuzzleView view;
    private ScoreModel scoreModel;
    private String nickname;
    private Userlist userlist;
    private Frame mainFrame;
    private Timer timer;

    public PuzzleController(PuzzleModel model, PuzzleView view, ScoreModel scoreModel, String nickname, Userlist userlist, Frame mainFrame) {
        this.model = model;
        this.view = view;
        this.scoreModel = scoreModel;
        this.nickname = nickname;
        this.userlist = userlist;
        this.mainFrame = mainFrame;
        initializeController();
        startTimer();
    }

    private void initializeController() {
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                final int x = i;
                final int y = j;
                view.getButton(x, y).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (model.canMove(x, y)) {
                            moveTile(x, y);
                            scoreModel.incrementMoves();
                            view.updateView();
                            view.getScorePanel().incrementMoves(); // 이동 횟수 업데이트
                            if (model.isSolved()) {
                                endGame();
                                showSuccessMessage();
                            }
                        }
                    }
                });
            }
        }
    }

    private void moveTile(int x, int y) {
        int blankX = -1;
        int blankY = -1;
        int size = model.getSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (model.getValueAt(i, j) == 0) {
                    blankX = i;
                    blankY = j;
                    break;
                }
            }
        }

        if (blankX != -1 && blankY != -1) {
            model.setValueAt(blankX, blankY, model.getValueAt(x, y));
            model.setValueAt(x, y, 0);
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreModel.incrementTime();
                view.getScorePanel().updateTime(scoreModel.getElapsedTime()); // 시간 업데이트
            }
        });
        timer.start();
    }

    private void endGame() {
        timer.stop();
        scoreModel.endGame();
        view.getScorePanel().updateTime(scoreModel.getElapsedTime()); // 종료 시 정확한 시간 설정
    }

    private void showSuccessMessage() {
        endGame();
        User user = new User(nickname, scoreModel.getElapsedTime(), scoreModel.getMoves(), model.getSize() + "x" + model.getSize());
        userlist.addUser(user);
        JOptionPane.showMessageDialog(mainFrame, "축하합니다! 퍼즐을 해결했습니다.", "게임 종료", JOptionPane.INFORMATION_MESSAGE);
        new Ranking(mainFrame, userlist.getUsers()).setVisible(true);
    }
}
