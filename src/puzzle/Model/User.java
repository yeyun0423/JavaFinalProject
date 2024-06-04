package puzzle.Model;

public class User {
    private String nickname;
    private long elapsedTime;
    private int moves;
    private String difficulty;

    public User(String nickname, long elapsedTime, int moves, String difficulty) {
        this.nickname = nickname;
        this.elapsedTime = elapsedTime;
        this.moves = moves;
        this.difficulty = difficulty;
    }

    public String getNickname() {
        return nickname;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public int getMoves() {
        return moves;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
