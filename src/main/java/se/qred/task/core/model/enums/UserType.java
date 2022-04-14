package se.qred.task.core.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserType {

    NORMAL("NORMAL"),
    ACCOUNT_MANAGER("ACCOUNT_MANAGER");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(type);
    }
}
