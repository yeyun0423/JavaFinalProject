package puzzle.View;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import puzzle.Model.User;

public class Ranking extends JDialog {
    public Ranking(Frame owner, List<User> allUsers) {
        super(owner, "Ranking", true);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // 배경 색상 설정
        getContentPane().setBackground(new Color(255, 228, 225));

        // 헤더 패널 설정
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 228, 225));
        JLabel headerLabel = new JLabel("Ranking");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        headerLabel.setForeground(new Color(255, 105, 180));
        headerPanel.add(headerLabel);

        // 컬럼 패널 설정
        JPanel columnPanel = new JPanel(new GridLayout(1, 5));
        columnPanel.setBackground(new Color(255, 228, 225));
        columnPanel.add(createLabel("Rank"));
        columnPanel.add(createLabel("Nickname"));
        columnPanel.add(createLabel("Time"));
        columnPanel.add(createLabel("Count"));
        columnPanel.add(createLabel("Level"));

        // 순위 정보 패널 설정
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));
        leaderboardPanel.setBackground(new Color(255, 228, 225));

        int rank = 1;
        for (User u : allUsers) {
            leaderboardPanel.add(createRankPanel(rank, u));
            rank++;
        }

        // 헤더와 컬럼 패널을 포함한 상단 패널을 생성
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(255, 228, 225));
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(columnPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH); // 상단 패널 추가
        add(new JScrollPane(leaderboardPanel), BorderLayout.CENTER); // 스크롤 가능한 순위표 패널 추가

        // 닫기 버튼 설정
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Serif", Font.BOLD, 16));
        closeButton.setBackground(new Color(255, 182, 193));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
    }

    // 라벨 생성 헬퍼 메소드
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 16));
        label.setForeground(new Color(255, 105, 180));
        return label;
    }

    // 순위 패널 생성 헬퍼 메소드
    private JPanel createRankPanel(int rank, User user) {
        JPanel rankPanel = new JPanel(new GridLayout(1, 5));
        rankPanel.setBackground(new Color(255, 228, 225));

        JLabel rankLabel = new JLabel(String.valueOf(rank), JLabel.CENTER);
        rankLabel.setFont(new Font("Serif", Font.BOLD, 16));
        rankLabel.setForeground(new Color(255, 105, 180));

        JLabel nameLabel = new JLabel(user.getNickname(), JLabel.CENTER);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        nameLabel.setForeground(new Color(255, 105, 180));

        JLabel timeLabel = new JLabel(String.valueOf(user.getElapsedTime()), JLabel.CENTER);
        timeLabel.setFont(new Font("Serif", Font.BOLD, 16));
        timeLabel.setForeground(new Color(255, 105, 180));

        JLabel countLabel = new JLabel(String.valueOf(user.getMoves()), JLabel.CENTER);
        countLabel.setFont(new Font("Serif", Font.BOLD, 16));
        countLabel.setForeground(new Color(255, 105, 180));

        JLabel levelLabel = new JLabel(user.getDifficulty(), JLabel.CENTER);
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
