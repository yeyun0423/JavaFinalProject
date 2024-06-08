package game.controller;

import game.model.PuzzleModel;
import game.model.ScoreModel;
import game.model.PlayerModel;
import game.model.PlayerListModel;
import game.view.PuzzleView;
import game.Timer;
import game.view.ScoreView;
import game.view.RankingView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * PuzzleController: 퍼즐 게임의 컨트롤러로, 게임 로직을 관리합니다.
 */
public class PuzzleController {
    private PuzzleModel puzzleModel;
    private PuzzleView puzzleView;
    private ScoreModel score;
    private String nickname;
    private PlayerListModel playerList;
    private Frame mainFrame;
    private Timer timer;

    public PuzzleController(PuzzleModel puzzleModel, PuzzleView puzzleView, ScoreModel score, String nickname, PlayerListModel playerList, Frame mainFrame, Timer timer) {
        this.puzzleModel = puzzleModel;
        this.puzzleView = puzzleView;
        this.score = score;
        this.nickname = nickname;
        this.playerList = playerList;
        this.mainFrame = mainFrame;
        this.timer = timer;
        initPuzzleController();
        timer.start();
    }

    private void initPuzzleController() {
        for (int i = 0; i < puzzleModel.getSize(); i++) {
            for (int j = 0; j < puzzleModel.getSize(); j++) {
                final int x = i;
                final int y = j;
                puzzleView.getButton(x, y).addActionListener(new MoveController(puzzleModel, puzzleView, this));
            }
        }
    }

    public void updateMoveCount() {
        score.countMove();
    }

    public void endGame() {
        timer.stop();
        int finalTime = timer.getElapsedTime();
        score.endGame(finalTime);
        puzzleView.getScoreView().updateTime(finalTime);
    }

    public void successMessage() {
        endGame();
        PlayerModel player = new PlayerModel(nickname, score.getElapsedTime(), score.getMove(), puzzleModel.getSize() + "x" + puzzleModel.getSize());
        playerList.addPlayer(player);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 228, 225));
        panel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Congratulations! You succeeded.", JLabel.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 18));
        messageLabel.setForeground(new Color(255, 105, 180));
        panel.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(255, 182, 193));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Serif", Font.BOLD, 16));
        okButton.setFocusPainted(false);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JDialog) okButton.getTopLevelAncestor()).dispose();
                new RankingView(mainFrame, playerList.getPlayers()).setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 228, 225));
        buttonPanel.add(okButton);

        JDialog dialog = new JDialog(mainFrame, "Finish", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel, BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }
}
