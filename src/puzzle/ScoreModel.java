package puzzle;

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
        return (endTime - startTime) / 1000; // 초 단위로 반환
    }

    public int calculateScore() {
        // 점수 계산 로직 (이동 횟수와 소요 시간 기반)
        long elapsedTime = getElapsedTime();
        return (int) (10000 / (moves * elapsedTime + 1)); // 예제 점수 계산식
    }
}