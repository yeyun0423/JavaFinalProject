package game.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.model.PuzzleModel;
import game.view.PuzzleView;

/**
 * MoveController: 퍼즐 타일의 이동을 처리하는 클래스입니다.
 */
public class MoveController implements ActionListener {
    private PuzzleModel puzzleModel;
    private PuzzleView puzzleView;
    private PuzzleController puzzleController;

    public MoveController(PuzzleModel puzzleModel, PuzzleView puzzleView, PuzzleController puzzleController) {
        this.puzzleModel = puzzleModel;
        this.puzzleView = puzzleView;
        this.puzzleController = puzzleController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int x = -1;
        int y = -1;

        // 버튼의 좌표를 찾습니다.
        outer:
        for (int i = 0; i < puzzleModel.getSize(); i++) {
            for (int j = 0; j < puzzleModel.getSize(); j++) {
                if (e.getSource() == puzzleView.getButton(i, j)) {
                    x = i;
                    y = j;
                    break outer;
                }
            }
        }

        if (x != -1 && y != -1) {
            if (puzzleModel.canMove(x, y)) {
                puzzleModel.movePuzzle(x, y);
                puzzleController.updateMoveCount();
                puzzleView.updateView();
                puzzleView.getScoreView().countMove();
                if (puzzleModel.isSolved()) {
                    puzzleController.endGame();
                    puzzleController.successMessage();
                }
            }
        }
    }
}
