package se.qred.task.db;

import com.google.common.base.Objects;
import se.qred.task.db.dto.User;
import se.qred.task.core.model.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    //TODO connect to db
    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public User findByUsername(String username) {
        for (User user : users) {
            if (Objects.equal(user.getUsername(), username)){
                return user;
            }
        }
        return null;
    }

    public void addDummyData() {
        users.add(new User("1", "fay", "1234", UserType.NORMAL));
        users.add(new User("2", "dave", "4321", UserType.ACCOUNT_MANAGER));
    }
}
