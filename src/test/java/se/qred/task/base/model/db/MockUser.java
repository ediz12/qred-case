package se.qred.task.base.model.db;

import se.qred.task.core.model.enums.UserType;
import se.qred.task.db.dto.User;

public class MockUser {

    private MockUser() {

    }

    public static User simpleUser() {
        return new User("1", "fay", "1234", UserType.NORMAL);
    }

    public static User simpleManager() {
        return new User("2", "kayle", "4321", UserType.ACCOUNT_MANAGER);
    }
}
