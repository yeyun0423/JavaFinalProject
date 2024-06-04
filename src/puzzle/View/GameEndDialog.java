package puzzle.View;

import javax.swing.*;
import java.awt.*;
import puzzle.Model.User;

public class GameEndDialog extends JDialog {
    public GameEndDialog(Frame owner, User user, java.util.List<User> allUsers) {
        super(owner, "게임 종료", true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel userInfoPanel = new JPanel(new GridLayout(4, 1));
        userInfoPanel.add(new JLabel("닉네임: " + user.getNickname()));
        userInfoPanel.add(new JLabel("소요 시간: " + user.getElapsedTime() + "초"));
        userInfoPanel.add(new JLabel("이동 횟수: " + user.getMoves()));
        userInfoPanel.add(new JLabel("난이도: " + user.getDifficulty()));

        add(userInfoPanel, BorderLayout.NORTH);

        JPanel leaderboardPanel = new JPanel(new GridLayout(allUsers.size() + 1, 1));
        leaderboardPanel.add(new JLabel("순위표:"));

        int rank = 1;
        for (User u : allUsers) {
            leaderboardPanel.add(new JLabel(rank + ". " + u.getNickname() + " - " + u.getElapsedTime() + "초"));
            rank++;
        }

        add(new JScrollPane(leaderboardPanel), BorderLayout.CENTER);

        JButton closeButton = new JButton("닫기");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
    }
}
