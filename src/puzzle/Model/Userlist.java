package puzzle.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Userlist {
    private List<User> users;
    private static final String FILE_NAME = "userlist.txt";

    public Userlist() {
        users = new ArrayList<>();
        loadFromFile();
    }

    public void addUser(User user) {
        users.add(user);
        saveToFile();
    }

    public List<User> getUsers() {
        return users;
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            for (User user : users) {  // user 대신 users 리스트의 각 User 객체를 참조합니다.
                writer.write(user.getNickname() + "," + user.getElapsedTime() + "," + user.getMoves() + "," + user.getDifficulty());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
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
                    int elapsedTime = Integer.parseInt(parts[1]);
                    int moves = Integer.parseInt(parts[2]);
                    String difficulty = parts[3];
                    users.add(new User(nickname, elapsedTime, moves, difficulty));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
