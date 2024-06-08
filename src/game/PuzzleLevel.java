package game;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntConsumer;

/**
 * PuzzleLevel: 퍼즐 크기를 선택하는 다이얼로그입니다.
 */
public class PuzzleLevel {
    private JDialog dialog;

    public PuzzleLevel(JFrame frame, IntConsumer levelCallback) {
        dialog = new JDialog(frame, "Choose Puzzle Level", true);
        initLevel(levelCallback);
        dialog.setLocationRelativeTo(frame);
    }

    private void initLevel(IntConsumer levelCallback) {
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 1));
        dialog.getContentPane().setBackground(new Color(255, 228, 225));

        JLabel messageLabel = new JLabel("Choose Puzzle Level", JLabel.CENTER);
        messageLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        messageLabel.setForeground(new Color(255, 105, 180));

        JButton button3x3 = createStyledButton("3x3");
        button3x3.addActionListener(e -> ButtonClick(levelCallback, 3));

        JButton button4x4 = createStyledButton("4x4");
        button4x4.addActionListener(e -> ButtonClick(levelCallback, 4));

        JButton button5x5 = createStyledButton("5x5");
        button5x5.addActionListener(e -> ButtonClick(levelCallback, 5));

        dialog.add(messageLabel);
        dialog.add(button3x3);
        dialog.add(button4x4);
        dialog.add(button5x5);

        dialog.setLocationRelativeTo(dialog.getParent()); // 메인 프레임의 중앙에 위치하도록 설정
        dialog.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 182, 193));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Serif", Font.BOLD, 16));
        button.setFocusPainted(false);
        return button;
    }

    private void ButtonClick(IntConsumer levelCallback, int level) {
        dialog.dispose();
        levelCallback.accept(level);
    }
}
