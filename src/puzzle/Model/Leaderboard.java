package puzzle.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class Leaderboard {
    private List<User> users;

    public Leaderboard() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
        Collections.sort(users, Comparator.comparingLong(User::getElapsedTime));
    }

    public List<User> getUsers() {
        return users;
    }
}
