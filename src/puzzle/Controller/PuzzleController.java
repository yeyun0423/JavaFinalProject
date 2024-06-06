package puzzle.Controller;

import puzzle.Model.PuzzleModel;
import puzzle.Model.ScoreModel;
import puzzle.Model.User;
import puzzle.Model.Userlist;
import puzzle.View.PuzzleView;
import puzzle.View.ScorePanel;
import puzzle.View.Ranking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
                            model.moveTile(x, y);
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

        // 메시지 패널 생성
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 228, 225));
        panel.setLayout(new BorderLayout());
        
        // 메시지 라벨 설정
        JLabel messageLabel = new JLabel("Congratulations! You succeeded.", JLabel.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 18));
        messageLabel.setForeground(new Color(255, 105, 180));
        panel.add(messageLabel, BorderLayout.CENTER);

        // OK 버튼 설정
        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(255, 182, 193));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Serif", Font.BOLD, 16));
        okButton.setFocusPainted(false);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JDialog) okButton.getTopLevelAncestor()).dispose();
                new Ranking(mainFrame, userlist.getUsers()).setVisible(true);
            }
        });

        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 228, 225));
        buttonPanel.add(okButton);

        // 메시지 다이얼로그 설정
        JDialog dialog = new JDialog(mainFrame, "Finish", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel, BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }
}
