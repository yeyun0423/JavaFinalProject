package puzzle.View;

import puzzle.Model.PuzzleModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class PuzzleView extends ImagePanel {
    private PuzzleModel model;
    private RoundButton[][] buttons;
    private ScorePanel scorePanel;

    public PuzzleView(PuzzleModel model) {
        super("/resource/images/puzzleGame1.jpg");  // 배경 이미지 경로 설정
        this.model = model;
        int size = model.getSize();
        buttons = new RoundButton[size][size];
        setLayout(new BorderLayout());

        scorePanel = new ScorePanel();
        scorePanel.setOpaque(false);  // ScorePanel의 투명도 설정
        add(scorePanel, BorderLayout.NORTH);

        JPanel puzzlePanel = new JPanel(new GridLayout(size, size, 0, 0));
        puzzlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // 퍼즐과 JFrame 사이에 여백 추가
        puzzlePanel.setOpaque(false);  // 퍼즐 패널의 투명도 설정
        initializeButtons(puzzlePanel);
        add(puzzlePanel, BorderLayout.CENTER);

        // 이미지 분할 및 설정
        try {
            URL imagePath = getClass().getResource("/resource/images/puzzleGame1.jpg");
            if (imagePath == null) {
                throw new IOException("Image not found");
            }
            BufferedImage image = ImageIO.read(imagePath);
            int chunkWidth = image.getWidth() / size;
            int chunkHeight = image.getHeight() / size;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    BufferedImage subImage = image.getSubimage(j * chunkWidth, i * chunkHeight, chunkWidth, chunkHeight);
                    model.setImageAt(i, j, subImage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateView();
    }

    private void initializeButtons(JPanel puzzlePanel) {
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                buttons[i][j] = new RoundButton("");
                buttons[i][j].setFont(new Font("Serif", Font.BOLD, 20));
                buttons[i][j].setBackground(new Color(255, 182, 193));
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setPreferredSize(new Dimension(80, 80));
                puzzlePanel.add(buttons[i][j]);
            }
        }
        updateView();
    }

    public void updateView() {
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                int value = model.getValueAt(i, j);
                if (value == 0) {
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(Color.WHITE);  // 빈 칸은 하얀색으로 표시
                    buttons[i][j].setIcon(null);
                } else {
                    BufferedImage image = model.getImageAt(i, j);
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

    public ScorePanel getScorePanel() {
        return scorePanel;
    }
}
