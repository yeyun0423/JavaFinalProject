package puzzle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class PuzzleController {
    private PuzzleModel model;
    private PuzzleView view;
    private ScoreModel scoreModel;

    public PuzzleController(PuzzleModel model, PuzzleView view, ScoreModel scoreModel) {
        this.model = model;
        this.view = view;
        this.scoreModel = scoreModel;
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
                                JOptionPane.showMessageDialog(view, "퍼즐을 해결했습니다! 점수: " + scoreModel.calculateScore());
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
}
