package game.view;

import game.model.PuzzleModel;
import game.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

/**
 * PuzzleView: 퍼즐 게임의 뷰를 관리하는 클래스입니다.
 * 퍼즐 타일과 UI 요소를 화면에 표시하고 업데이트합니다.
 */
public class PuzzleView extends JPanel {
    private PuzzleModel puzzleModel;
    private JButton[][] buttons;
    private ScoreView scoreView;
    private Timer timer;

    public PuzzleView(PuzzleModel puzzleModel, Timer timer, ScoreView scoreView) {
        this.puzzleModel = puzzleModel;
        this.timer = timer;
        this.scoreView = scoreView;
        int level = puzzleModel.getSize();
        buttons = new JButton[level][level];
        setLayout(new BorderLayout());

        add(scoreView, BorderLayout.NORTH);

        JPanel puzzlePanel = new JPanel(new GridLayout(level, level, 0, 0));
        puzzlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        puzzlePanel.setOpaque(false);
        initializeButtons(puzzlePanel);
        add(puzzlePanel, BorderLayout.CENTER);

        try {
            URL imagePath = getClass().getResource("/resource/images/puzzleGame1.jpg");
            BufferedImage image = ImageIO.read(imagePath);
            int chunkWidth = image.getWidth() / level;
            int chunkHeight = image.getHeight() / level;
            for (int i = 0; i < level; i++) {
                for (int j = 0; j < level; j++) {
                    int x = j * chunkWidth;
                    int y = i * chunkHeight;
                    if (x + chunkWidth <= image.getWidth() && y + chunkHeight <= image.getHeight()) {
                        BufferedImage subImage = image.getSubimage(x, y, chunkWidth, chunkHeight);
                        puzzleModel.setImage(i, j, subImage);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateView();
        timer.start();
    }

    private void initializeButtons(JPanel puzzlePanel) {
        for (int i = 0; i < puzzleModel.getSize(); i++) {
            for (int j = 0; j < puzzleModel.getSize(); j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Serif", Font.BOLD, 20));
                buttons[i][j].setBackground(new Color(255, 182, 193));
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setPreferredSize(new Dimension(80, 80));
                final int x = i;
                final int y = j;
                buttons[i][j].addActionListener(e -> {
                    if (puzzleModel.movePuzzle(x, y)) {
                        updateView();
                        scoreView.countMove();
                    }
                });
                puzzlePanel.add(buttons[i][j]);
            }
        }
    }

    public void updateView() {
        for (int i = 0; i < puzzleModel.getSize(); i++) {
            for (int j = 0; j < puzzleModel.getSize(); j++) {
                int value = puzzleModel.getValue(i, j);
                if (value == 0) {
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(Color.WHITE);
                    buttons[i][j].setIcon(null);
                } else {
                    BufferedImage image = puzzleModel.getImage(i, j);
                    if (image != null) {
                        buttons[i][j].setIcon(new ImageIcon(image));
                        buttons[i][j].setText("");
                    } else {
                        buttons[i][j].setText(String.valueOf(value));
                        buttons[i][j].setBackground(new Color(255, 182, 193));
                    }
                }
            }
        }
    }

    public JButton getButton(int x, int y) {
        return buttons[x][y];
    }

    public ScoreView getScoreView() {
        return scoreView;
    }
}
