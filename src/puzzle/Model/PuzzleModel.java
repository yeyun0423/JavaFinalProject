package puzzle.Model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class PuzzleModel {
    private int[][] puzzle;
    private int size;

    public PuzzleModel(int size) {
        this.size = size;
        puzzle = new int[size][size];
        initializePuzzle();
    }

    private void initializePuzzle() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        int count = 0;
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

    public boolean isSolved() {
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    return puzzle[i][j] == 0;
                }
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

    public void moveTile(int x, int y) {
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
            puzzle[blankX][blankY] = puzzle[x][y];
            puzzle[x][y] = 0;
        }
    }
}
