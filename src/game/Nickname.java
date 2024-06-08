package game;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

/**
 * Nickname: 닉네임 입력을 위한 다이얼로그입니다.
 */
public class Nickname {
    private JDialog dialog;
    private JTextField textField;

    public Nickname(JFrame frame, BiConsumer<String, Integer> startGameCallback) {
        dialog = new JDialog(frame, "Enter a nickname", true);
        initInput(startGameCallback);
        dialog.setLocationRelativeTo(frame);
    }

    private void initInput(BiConsumer<String, Integer> startGame) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 228, 225));
        textField = new JTextField(10);
        textField.setFont(new Font("Serif", Font.BOLD, 16));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 105, 180), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(new JLabel("Enter a nickname:"), gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(255, 182, 193));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Serif", Font.BOLD, 16));
        okButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(255, 182, 193));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Serif", Font.BOLD, 16));
        cancelButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 228, 225));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dialog.getContentPane().add(panel, BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getParent()); // 메인 프레임의 중앙에 위치하도록 설정

        okButton.addActionListener(e -> okButtonClick(startGame));
        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void okButtonClick(BiConsumer<String, Integer> startGame) {
        String nickname = textField.getText();
        if (nickname != null && !nickname.trim().isEmpty()) {
            dialog.dispose();
            startGame.accept(nickname, 3);  // 기본 퍼즐 크기 설정
        } else {
            showMessage("Enter a nickname");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(dialog, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
