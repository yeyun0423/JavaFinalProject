package game.controller;

import game.model.PlayerListModel;
import game.view.RankingView;
import javax.swing.*;
import java.awt.*;

/**
 * RankingController: 퍼즐을 완성했을 때 성공 메시지를 표시하는 다이얼로그입니다.
 * 게임 종료 시 랭킹 페이지를 표시합니다.
 */
public class RankingController {
    private Frame mainFrame;
    private PlayerListModel playerList;

    public RankingController(Frame mainFrame, PlayerListModel playerList) {
        this.mainFrame = mainFrame;
        this.playerList = playerList;
    }

    public void rankingMessage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 228, 225));
        JLabel messageLabel = new JLabel("Congratulations! You succeeded.", JLabel.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 18));
        messageLabel.setForeground(new Color(255, 105, 180));
        panel.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(255, 182, 193));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Serif", Font.BOLD, 16));
        okButton.setFocusPainted(false);
        okButton.addActionListener(e -> {
            ((JDialog) okButton.getTopLevelAncestor()).dispose();
            new RankingView(mainFrame, playerList.getPlayers()).setVisible(true);
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
