package game.model;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * PuzzleModel: 퍼즐 게임의 모델로, 퍼즐의 상태와 로직을 관리합니다.
 */
public class PuzzleModel {
    private int[][] puzzle;
    private BufferedImage[][] imagePuzzle;
    private int puzzleLevel;
    private int[][] originalX;
    private int[][] originalY;

    public PuzzleModel(int puzzleLevel) {
        this.puzzleLevel = puzzleLevel;
        puzzle = new int[puzzleLevel][puzzleLevel];
        imagePuzzle = new BufferedImage[puzzleLevel][puzzleLevel];
        originalX = new int[puzzleLevel][puzzleLevel];
        originalY = new int[puzzleLevel][puzzleLevel];
        initPuzzle();
    }

    private void initPuzzle() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < puzzleLevel * puzzleLevel; i++) {
            numbers.add(i);
        }

        int count = 0;
        for (int i = 0; i < puzzleLevel; i++) {
            for (int j = 0; j < puzzleLevel; j++) {
                puzzle[i][j] = count;
                originalX[i][j] = i;
                originalY[i][j] = j;
                count++;
            }
        }

        Collections.shuffle(numbers);
        count = 0;
        for (int i = 0; i < puzzleLevel; i++) {
            for (int j = 0; j < puzzleLevel; j++) {
                puzzle[i][j] = numbers.get(count++);
            }
        }
    }

    public int getSize() {
        return puzzleLevel;
    }

    public int getValue(int x, int y) {
        return puzzle[x][y];
    }

    public void setValue(int x, int y, int value) {
        puzzle[x][y] = value;
    }

    public BufferedImage getImage(int x, int y) {
        int value = puzzle[x][y];
        int origX = value / puzzleLevel;
        int origY = value % puzzleLevel;
        return imagePuzzle[origX][origY];
    }

    public void setImage(int x, int y, BufferedImage image) {
        imagePuzzle[x][y] = image;
    }

    public boolean isSolved() {
        int count = 0;
        for (int i = 0; i < puzzleLevel; i++) {
            for (int j = 0; j < puzzleLevel; j++) {
                if (puzzle[i][j] != count++) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMove(int x, int y) {
        int blankX = -1;
        int blankY = -1;
        for (int i = 0; i < puzzleLevel; i++) {
            for (int j = 0; j < puzzleLevel; j++) {
                if (puzzle[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
        }
        return (Math.abs(blankX - x) == 1 && blankY == y) || (Math.abs(blankY - y) == 1 && blankX == x);
    }

    public boolean movePuzzle(int x, int y) {
        int blankX = -1;
        int blankY = -1;
        for (int i = 0; i < puzzleLevel; i++) {
            for (int j = 0; j < puzzleLevel; j++) {
                if (puzzle[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
        }
        if (canMove(x, y)) {
            puzzle[blankX][blankY] = puzzle[x][y];
            puzzle[x][y] = 0;
            return true;
        }
        return false;
    }
}
