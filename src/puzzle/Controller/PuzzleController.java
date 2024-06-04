package puzzle.Controller;

import puzzle.Model.PuzzleModel;
import puzzle.Model.ScoreModel;
import puzzle.Model.User;
import puzzle.Model.Leaderboard;
import puzzle.View.PuzzleView;
import puzzle.View.GameEndDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import java.awt.Frame;

public class PuzzleController {
    private PuzzleModel model;
    private PuzzleView view;
    private ScoreModel scoreModel;
    private String nickname;
    private Leaderboard leaderboard;
    private Frame mainFrame;

    public PuzzleController(PuzzleModel model, PuzzleView view, ScoreModel scoreModel, String nickname, Leaderboard leaderboard, Frame mainFrame) {
        this.model = model;
        this.view = view;
        this.scoreModel = scoreModel;
        this.nickname = nickname;
        this.leaderboard = leaderboard;
        this.mainFrame = mainFrame;
        initializeController();
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
                                scoreModel.endGame();
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

    private void showSuccessMessage() {
        UIManager.put("OptionPane.background", new ColorUIResource(255, 228, 225));
        UIManager.put("Panel.background", new ColorUIResource(255, 228, 225));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(255, 105, 180));
        UIManager.put("OptionPane.messageFont", new javax.swing.plaf.FontUIResource(new java.awt.Font("Serif", java.awt.Font.BOLD, 16)));
        UIManager.put("Button.background", new ColorUIResource(255, 182, 193));
        UIManager.put("Button.foreground", new ColorUIResource(255, 255, 255));

        User user = new User(nickname, scoreModel.getElapsedTime(), scoreModel.getMoves(), model.getSize() + "x" + model.getSize());
        leaderboard.addUser(user);

        new GameEndDialog(mainFrame, user, leaderboard.getUsers()).setVisible(true);
    }
}
