package game.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerListModel: 플레이어 리스트를 관리하고 파일에 저장/로드하는 클래스입니다.
 */
public class PlayerListModel {
    private List<PlayerModel> players;
    private static final String FILE_NAME = "playerlist.txt";

    public PlayerListModel() {
        players = new ArrayList<>();
        loadPlayer();
    }

    public void addPlayer(PlayerModel player) {
        players.add(player);
        savePlayer();
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    private void savePlayer() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            for (PlayerModel player : players) {
                writer.write(player.getNickname() + "," + player.getEndTime() + "," + player.getMove() + "," + player.getLevel());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPlayer() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String nickname = parts[0];
                    int endTime = Integer.parseInt(parts[1]);
                    int moveCount = Integer.parseInt(parts[2]);
                    String level = parts[3];
                    players.add(new PlayerModel(nickname, endTime, moveCount, level));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
