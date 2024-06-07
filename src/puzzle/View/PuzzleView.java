package puzzle.View;

import puzzle.Model.PuzzleModel;
import puzzle.GameTimer;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class PuzzleView extends ImagePanel {
    private PuzzleModel model;
    private JButton[][] buttons;
    private ScorePanel scorePanel;
    private JButton hintButton;
    private GameTimer timer;

    public PuzzleView(PuzzleModel model, GameTimer timer, ScorePanel scorePanel) {
        super("/resource/images/puzzleGame1.jpg");
        this.model = model;
        this.timer = timer;
        this.scorePanel = scorePanel;
        int size = model.getSize();
        buttons = new JButton[size][size];
        setLayout(new BorderLayout());

        add(scorePanel, BorderLayout.NORTH);

        JPanel puzzlePanel = new JPanel(new GridLayout(size, size, 0, 0));
        puzzlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        puzzlePanel.setOpaque(false);
        initializeButtons(puzzlePanel);
        add(puzzlePanel, BorderLayout.CENTER);

        // 힌트 버튼 추가
        hintButton = new JButton("Hint");
        hintButton.setBackground(new Color(255, 182, 193));
        hintButton.setForeground(Color.WHITE);
        hintButton.setFocusPainted(false);
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHint();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(hintButton);
        add(buttonPanel, BorderLayout.SOUTH);

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
                    int x = j * chunkWidth;
                    int y = i * chunkHeight;
                    if (x + chunkWidth <= image.getWidth() && y + chunkHeight <= image.getHeight()) {
                        BufferedImage subImage = image.getSubimage(x, y, chunkWidth, chunkHeight);
                        model.setImageAt(i, j, subImage);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateView();
        timer.start(); // 타이머 시작
    }

    private void initializeButtons(JPanel puzzlePanel) {
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Serif", Font.BOLD, 20));
                buttons[i][j].setBackground(new Color(255, 182, 193));
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setPreferredSize(new Dimension(80, 80));
                final int x = i;
                final int y = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (model.moveTile(x, y)) {
                            playSoundEffect("/resource/sounds/PannelMoveBM.mp3");
                            updateView();
                            scorePanel.incrementMoves(); // 움직임 카운트 증가
                            if (model.isSolved()) {
                                showFinishDialog();
                            }
                        }
                    }
                });
                puzzlePanel.add(buttons[i][j]);
            }
        }
        updateView();
    }

    private void playSoundEffect(String filepath) {
        try {
            URL soundURL = getClass().getResource(filepath);
            if (soundURL == null) {
                throw new RuntimeException("Sound file not found: " + filepath);
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void updateView() {
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                int value = model.getValueAt(i, j);
                if (value == 0) {
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(Color.WHITE);
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

    private void showHint() {
        JDialog hintDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Hint", true);
        hintDialog.setSize(300, 300);
        hintDialog.setLayout(new BorderLayout());

        ImagePanel hintPanel = new ImagePanel("/resource/images/puzzleGame1.jpg");
        hintDialog.add(hintPanel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(255, 182, 193));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hintDialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        hintDialog.add(buttonPanel, BorderLayout.SOUTH);

        hintDialog.setLocationRelativeTo(this);
        hintDialog.setVisible(true);
    }

    private void showFinishDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Game Finished", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new BorderLayout());
        JLabel messageLabel = new JLabel("Congratulations! You solved the puzzle!", JLabel.CENTER);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 18));
        dialog.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(255, 182, 193));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Serif", Font.BOLD, 16));
        okButton.setFocusPainted(false);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 228, 225));
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public JButton getButton(int x, int y) {
        return buttons[x][y];
    }

    public ScorePanel getScorePanel() {
        return scorePanel;
    }
}
