package puzzle;

import puzzle.Model.PuzzleModel;
import puzzle.Model.ScoreModel;
import puzzle.Model.User;
import puzzle.Model.Userlist;
import puzzle.View.ImagePanel;  // 추가
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
    private Userlist userlist;

    public MainFrame() {
        userlist = new Userlist();
        setTitle("Slide Puzzle Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeMainPage();
        setVisible(true);
    }

    private void initializeMainPage() {
        mainPanel = new ImagePanel("/resource/images/puzzleGame1.jpg");  // 이미지 패널로 변경
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Slide Puzzle Game", JLabel.CENTER);
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
        buttonPanel.setOpaque(false);  // 버튼 패널 배경 투명으로 설정
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
        panel.add(new JLabel("Enter a nickname:"), gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);

        // 커스터마이징된 버튼 생성
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

        // 옵션 다이얼로그 설정
        Object[] options = {okButton, cancelButton};
        final JDialog dialog = new JDialog(this, "Enter a nickname", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 228, 225));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);

        // OK 버튼 액션 리스너
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nickname = textField.getText();
                if (nickname != null && !nickname.trim().isEmpty()) {
                    dialog.dispose();
                    showPuzzleSizeOptions();
                } else {
                    showMessage("Enter a nickname");
                }
            }
        });

        // Cancel 버튼 액션 리스너
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void showPuzzleSizeOptions() {
        JDialog dialog = new JDialog(this, "Choose Puzzle Size", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 1));
        dialog.getContentPane().setBackground(new Color(255, 228, 225));

        JLabel messageLabel = new JLabel("Choose Puzzle Size", JLabel.CENTER);
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
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 228, 225));
        panel.add(new JLabel(message));

        JButton okButton = new JButton("확인");
        okButton.setBackground(new Color(255, 182, 193));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Serif", Font.BOLD, 16));
        okButton.setFocusPainted(false);

        final JDialog dialog = new JDialog(this, "Message", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 228, 225));
        buttonPanel.add(okButton);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void showRankingPage() {
        new Ranking(this, userlist.getUsers()).setVisible(true);
    }

    private void startGame(int size) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        PuzzleModel model = new PuzzleModel(size);
        PuzzleView view = new PuzzleView(model);
        ScoreModel scoreModel = new ScoreModel();
        PuzzleController controller = new PuzzleController(model, view, scoreModel, nickname, userlist, this);

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
