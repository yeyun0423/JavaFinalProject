package puzzle.Model;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class PuzzleModel {
    private int[][] puzzle;
    private BufferedImage[][] images;  // 이미지 조각을 저장할 배열
    private int size;
    private int[][] originalX;  // 각 조각의 원래 X 위치
    private int[][] originalY;  // 각 조각의 원래 Y 위치

    public PuzzleModel(int size) {
        this.size = size;
        puzzle = new int[size][size];
        images = new BufferedImage[size][size];
        originalX = new int[size][size];
        originalY = new int[size][size];
        initializePuzzle();
    }

    private void initializePuzzle() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            numbers.add(i);
        }

        // 퍼즐을 섞기 전에 이미지 배열 설정 및 원래 위치 기록
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                puzzle[i][j] = count;
                originalX[i][j] = i;
                originalY[i][j] = j;
                count++;
            }
        }

        // 퍼즐 섞기
        Collections.shuffle(numbers);
        count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                puzzle[i][j] = numbers.get(count++);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public int getValueAt(int x, int y) {
        return puzzle[x][y];
    }

    public void setValueAt(int x, int y, int value) {
        puzzle[x][y] = value;
    }

    public BufferedImage getImageAt(int x, int y) {
        int value = puzzle[x][y];
        int origX = value / size;
        int origY = value % size;
        return images[origX][origY];
    }

    public void setImageAt(int x, int y, BufferedImage image) {
        images[x][y] = image;
    }

    public boolean isSolved() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (puzzle[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
        }
        return (Math.abs(blankX - x) == 1 && blankY == y) || (Math.abs(blankY - y) == 1 && blankX == x);
    }

    public boolean moveTile(int x, int y) {
        int blankX = -1;
        int blankY = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (puzzle[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
        }
        if (canMove(x, y)) {
            // Swap values
            puzzle[blankX][blankY] = puzzle[x][y];
            puzzle[x][y] = 0;
            return true;
        }
        return false;
    }
}
