package game.model;

/**
 * PlayerModel: 플레이어의 닉네임, 시간, 이동 횟수 및 난이도를 저장하는 클래스입니다.
 */
public class PlayerModel {
    private String nickname;
    private int endTime;
    private int moveCount;
    private String level;

    public PlayerModel(String nickname, int endTime, int moveCount, String level) {
        this.nickname = nickname;
        this.endTime = endTime;
        this.moveCount = moveCount;
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getMove() {
        return moveCount;
    }

    public String getLevel() {
        return level;
    }
}
