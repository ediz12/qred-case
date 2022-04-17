package se.qred.task.core.model.exceptions;

public class OfferAlreadyNegotiatedException extends Exception {

    public OfferAlreadyNegotiatedException() {
        super();
    }

    public OfferAlreadyNegotiatedException(String message) {
        super(message);
    }

    public OfferAlreadyNegotiatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OfferAlreadyNegotiatedException(Throwable cause) {
        super(cause);
    }

    protected OfferAlreadyNegotiatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
