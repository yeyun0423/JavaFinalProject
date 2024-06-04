package puzzle;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JButton startButton;

    public MainFrame() {
        setTitle("Slide Puzzle game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeMainPage();
        setVisible(true);
    }

    private void initializeMainPage() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 228, 225));

        JLabel titleLabel = new JLabel("Slide Puzzle game", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180));

        startButton = new JButton("Start");
        startButton.setFont(new Font("Serif", Font.BOLD, 20));
        startButton.setBackground(new Color(255, 182, 193));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPuzzleSizeOptions();
            }
        });

        mainPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(startButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void showPuzzleSizeOptions() {
        JDialog dialog = new JDialog(this, "Choose puzzle size", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 1));
        dialog.getContentPane().setBackground(new Color(255, 228, 225));

        JLabel messageLabel = new JLabel("Choose puzzle size", JLabel.CENTER);
        messageLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        messageLabel.setForeground(new Color(255, 105, 180));
        
        JButton button3x3 = new JButton("3x3");
        button3x3.setBackground(new Color(255, 182, 193));
        button3x3.setForeground(Color.WHITE);
        button3x3.setFont(new Font("Serif", Font.BOLD, 16));
        button3x3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(3);
                dialog.dispose();
            }
        });

        JButton button4x4 = new JButton("4x4");
        button4x4.setBackground(new Color(255, 182, 193));
        button4x4.setForeground(Color.WHITE);
        button4x4.setFont(new Font("Serif", Font.BOLD, 16));
        button4x4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(4);
                dialog.dispose();
            }
        });

        JButton button5x5 = new JButton("5x5");
        button5x5.setBackground(new Color(255, 182, 193));
        button5x5.setForeground(Color.WHITE);
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

    private void startGame(int size) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        PuzzleModel model = new PuzzleModel(size);
        PuzzleView view = new PuzzleView(model);
        ScoreModel scoreModel = new ScoreModel();
        PuzzleController controller = new PuzzleController(model, view, scoreModel);
        GameTimer timer = new GameTimer();

        add(view, BorderLayout.CENTER);
        timer.start();

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
