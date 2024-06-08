package game;

import game.model.PlayerListModel;
import game.view.ImagePanelView;
import game.view.RankingView;
import game.controller.PuzzleController;
import game.model.PuzzleModel;
import game.model.ScoreModel;
import game.view.PuzzleView;
import game.view.ScoreView;
import game.sound.MusicPlayer;
import game.Nickname;
import game.PuzzleLevel;

import javax.swing.*;
import java.awt.*;

/**
 * Main: 게임의 메인 프레임으로, 시작 페이지와 게임 페이지를 관리합니다.
 */
public class Main extends JFrame {
    private JPanel mainPanel;
    private JButton startButton;
    private JButton rankingButton;
    private String nickname;
    private PlayerListModel playerList;
    private MusicPlayer musicPlayer;

    public Main() {
        playerList = new PlayerListModel();
        setTitle("Slide Puzzle Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initMainPage();
        musicPlayer = new MusicPlayer();
        musicPlayer.start();
        setVisible(true);
    }

    private void initMainPage() {
        mainPanel = new ImagePanelView("/resource/images/puzzleGame1.jpg");
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Slide Puzzle Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 105, 180));

        startButton = createStyledButton("Start");
        startButton.setFont(new Font("Serif", Font.BOLD, 20));
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(e -> askNickname());

        rankingButton = createStyledButton("Ranking");
        rankingButton.setFont(new Font("Serif", Font.BOLD, 20));
        rankingButton.setPreferredSize(new Dimension(150, 50));
        rankingButton.addActionListener(e -> rankingPage());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
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

    private void askNickname() {
        new Nickname(this, (name, level) -> {
            nickname = name;
            askLevel(level);
        });
    }

    private void askLevel(int level) {
        new PuzzleLevel(this, levelSelected -> startGame(nickname, levelSelected));
    }

    private void rankingPage() {
        new RankingView(this, playerList.getPlayers()).setVisible(true);
    }

    private void startGame(String name, int puzzleLevel) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        PuzzleModel puzzleModel = new PuzzleModel(puzzleLevel);
        ScoreView scoreView = new ScoreView();
        Timer timer = new Timer(scoreView);
        PuzzleView puzzleView = new PuzzleView(puzzleModel, timer, scoreView);
        ScoreModel score = new ScoreModel();
        new PuzzleController(puzzleModel, puzzleView, score, name, playerList, this, timer);

        add(puzzleView, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
