package game.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import game.model.PlayerModel;

/**
 * RankingView: 플레이어의 순위를 표시하는 다이얼로그입니다.
 */
public class RankingView extends JDialog {
    public RankingView(Frame owner, List<PlayerModel> allPlayers) {
        super(owner, "Ranking", true);
        setSize(500, 400);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(255, 228, 225));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 228, 225));
        JLabel headerLabel = new JLabel("Ranking");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        headerLabel.setForeground(new Color(255, 105, 180));
        headerPanel.add(headerLabel);

        JPanel columnPanel = new JPanel(new GridLayout(1, 5));
        columnPanel.setBackground(new Color(255, 228, 225));
        columnPanel.add(createLabel("Rank"));
        columnPanel.add(createLabel("Nickname"));
        columnPanel.add(createLabel("Time"));
        columnPanel.add(createLabel("Count"));
        columnPanel.add(createLabel("Level"));

        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));
        leaderboardPanel.setBackground(new Color(255, 228, 225));

        int rank = 1;
        for (PlayerModel player : allPlayers) {
            leaderboardPanel.add(createRankPanel(rank, player));
            rank++;
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(255, 228, 225));
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(columnPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(leaderboardPanel), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Serif", Font.BOLD, 16));
        closeButton.setBackground(new Color(255, 182, 193));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 16));
        label.setForeground(new Color(255, 105, 180));
        return label;
    }

    private JPanel createRankPanel(int rank, PlayerModel player) {
        JPanel rankPanel = new JPanel(new GridLayout(1, 5));
        rankPanel.setBackground(new Color(255, 228, 225));

        JLabel rankLabel = new JLabel(String.valueOf(rank), JLabel.CENTER);
        rankLabel.setFont(new Font("Serif", Font.BOLD, 16));
        rankLabel.setForeground(new Color(255, 105, 180));

        JLabel nameLabel = new JLabel(player.getNickname(), JLabel.CENTER);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        nameLabel.setForeground(new Color(255, 105, 180));

        JLabel timeLabel = new JLabel(String.valueOf(player.getEndTime()), JLabel.CENTER);
        timeLabel.setFont(new Font("Serif", Font.BOLD, 16));
        timeLabel.setForeground(new Color(255, 105, 180));

        JLabel countLabel = new JLabel(String.valueOf(player.getMove()), JLabel.CENTER);
        countLabel.setFont(new Font("Serif", Font.BOLD, 16));
        countLabel.setForeground(new Color(255, 105, 180));

        JLabel levelLabel = new JLabel(player.getLevel(), JLabel.CENTER);
        levelLabel.setFont(new Font("Serif", Font.BOLD, 16));
        levelLabel.setForeground(new Color(255, 105, 180));

        rankPanel.add(rankLabel);
        rankPanel.add(nameLabel);
        rankPanel.add(timeLabel);
        rankPanel.add(countLabel);
        rankPanel.add(levelLabel);

        return rankPanel;
    }
}
