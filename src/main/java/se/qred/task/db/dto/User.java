package se.qred.task.db.dto;

import se.qred.task.core.model.enums.UserType;

import java.security.Principal;

public class User implements Principal {
    private String id;
    private String username;
    private String password;
    private UserType userType;

    public User(String id, String username, String password, UserType userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return "used-in-authenticator";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public boolean isAccountManager() {
        return UserType.ACCOUNT_MANAGER.equals(this.userType);
    }
}
