package puzzle.Model;

public class ScoreModel {
    private int moves;
    private long startTime;
    private long endTime;

    public ScoreModel() {
        moves = 0;
        startTime = System.currentTimeMillis();
    }

    public void incrementMoves() {
        moves++;
    }

    public int getMoves() {
        return moves;
    }

    public void endGame() {
        endTime = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        return (endTime - startTime) / 1000; 
    }

    public int calculateScore() {
        long elapsedTime = getElapsedTime();
        return (int) (10000 / (moves * elapsedTime + 1)); 
    }
}
