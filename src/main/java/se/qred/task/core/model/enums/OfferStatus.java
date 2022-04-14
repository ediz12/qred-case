package se.qred.task.core.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OfferStatus {

    PENDING("PENDING"),
    NEGOTIATED("NEGOTIATED"),
    SIGNED("SIGNED"),
    CANCELLED("CANCELLED"),
    EXPIRED("EXPIRED");

    private final String value;

    OfferStatus(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
