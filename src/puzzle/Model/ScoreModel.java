package puzzle.Model;

public class ScoreModel {
    private int moves;
    private long startTime;
    private long elapsedTime;

    public ScoreModel() {
        this.moves = 0;
        this.startTime = System.currentTimeMillis();
        this.elapsedTime = 0;
    }

    public void incrementMoves() {
        this.moves++;
    }

    public void incrementTime() {
        long currentTime = System.currentTimeMillis();
        this.elapsedTime = (currentTime - startTime) / 1000;
    }

    public void endGame() {
        long currentTime = System.currentTimeMillis();
        this.elapsedTime = (currentTime - startTime) / 1000;
    }

    public int getMoves() {
        return moves;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
