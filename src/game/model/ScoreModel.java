package game.model;

/**
 * ScoreModel: 퍼즐 게임의 점수와 이동 횟수 및 시간을 관리하는 클래스입니다.
 */
public class ScoreModel {
    private int moveCount;
    private int elapsedTime;

    public ScoreModel() {
        this.moveCount = 0;
        this.elapsedTime = 0;
    }

    public void countMove() {
        this.moveCount++;
    }

    public void endGame(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getMove() {
        return moveCount;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
}
