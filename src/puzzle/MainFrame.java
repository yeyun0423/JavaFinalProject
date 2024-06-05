package puzzle;

import puzzle.Model.PuzzleModel;
import puzzle.Model.ScoreModel;
import puzzle.Model.User;
import puzzle.Model.Leaderboard;
import puzzle.View.PuzzleView;
import puzzle.Controller.PuzzleController;
import puzzle.View.Ranking;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JButton startButton;
    private JButton rankingButton;
    private String nickname;
    private Leaderboard leaderboard;

    public MainFrame() {
        leaderboard = new Leaderboard();
        setTitle("귀여운 슬라이드 퍼즐 게임");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeMainPage();
        setVisible(true);
    }

    private void initializeMainPage() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 228, 225));

        JLabel titleLabel = new JLabel("귀여운 슬라이드 퍼즐 게임", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180));

        startButton = createStyledButton("Start");
        startButton.setFont(new Font("Serif", Font.BOLD, 20));
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptForNickname();
            }
        });

        rankingButton = createStyledButton("Ranking");
        rankingButton.setFont(new Font("Serif", Font.BOLD, 20));
        rankingButton.setPreferredSize(new Dimension(150, 50));
        rankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRankingPage();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 228, 225));
        buttonPanel.add(startButton);
        buttonPanel.add(rankingButton);

        mainPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 182, 193));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void promptForNickname() {
        UIManager.put("OptionPane.background", new ColorUIResource(255, 228, 225));
        UIManager.put("Panel.background", new ColorUIResource(255, 228, 225));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(255, 105, 180));
        UIManager.put("OptionPane.messageFont", new javax.swing.plaf.FontUIResource(new java.awt.Font("Serif", java.awt.Font.BOLD, 16)));
        UIManager.put("Button.background", new ColorUIResource(255, 182, 193));
        UIManager.put("Button.foreground", new ColorUIResource(255, 255, 255));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 228, 225));
        JTextField textField = new JTextField(10);
        textField.setFont(new Font("Serif", Font.BOLD, 16));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 105, 180), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(new JLabel("닉네임을 입력하세요:"), gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "닉네임 입력", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            nickname = textField.getText();
            if (nickname != null && !nickname.trim().isEmpty()) {
                showPuzzleSizeOptions();
            } else {
                showMessage("닉네임을 입력해주세요.");
            }
        }
    }

    private void showPuzzleSizeOptions() {
        JDialog dialog = new JDialog(this, "퍼즐 크기 선택", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 1));
        dialog.getContentPane().setBackground(new Color(255, 228, 225));

        JLabel messageLabel = new JLabel("퍼즐 크기를 선택하세요", JLabel.CENTER);
        messageLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        messageLabel.setForeground(new Color(255, 105, 180));

        JButton button3x3 = createStyledButton("3x3");
        button3x3.setFont(new Font("Serif", Font.BOLD, 16));
        button3x3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(3);
                dialog.dispose();
            }
        });

        JButton button4x4 = createStyledButton("4x4");
        button4x4.setFont(new Font("Serif", Font.BOLD, 16));
        button4x4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(4);
                dialog.dispose();
            }
        });

        JButton button5x5 = createStyledButton("5x5");
        button5x5.setFont(new Font("Serif", Font.BOLD, 16));
        button5x5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(5);
                dialog.dispose();
            }
        });

        dialog.add(messageLabel);
        dialog.add(button3x3);
        dialog.add(button4x4);
        dialog.add(button5x5);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showMessage(String message) {
        UIManager.put("OptionPane.okButtonText", "확인");
        UIManager.put("Button.background", new ColorUIResource(255, 182, 193));
        UIManager.put("Button.foreground", new ColorUIResource(255, 255, 255));
        JOptionPane.showMessageDialog(this, message, "메시지", JOptionPane.PLAIN_MESSAGE);
    }

    private void showRankingPage() {
        new Ranking(this, leaderboard.getUsers()).setVisible(true);
    }

    private void startGame(int size) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        PuzzleModel model = new PuzzleModel(size);
        PuzzleView view = new PuzzleView(model);
        ScoreModel scoreModel = new ScoreModel();
        PuzzleController controller = new PuzzleController(model, view, scoreModel, nickname, leaderboard, this);

        add(view, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
