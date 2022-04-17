package se.qred.task.core.model.exceptions;

public class AllabolagUnreachableException extends Exception {
    public AllabolagUnreachableException() {
        super();
    }

    public AllabolagUnreachableException(String message) {
        super(message);
    }

    public AllabolagUnreachableException(String message, Throwable cause) {
        super(message, cause);
    }

    public AllabolagUnreachableException(Throwable cause) {
        super(cause);
    }

    protected AllabolagUnreachableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
