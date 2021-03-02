package cn.workde.core.exception;


public final class WorkdeException extends RuntimeException {

    public WorkdeException() {
        super();
    }

    public WorkdeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkdeException(String message) {
        super(message);
    }

    public WorkdeException(Throwable cause) {
        super(cause);
    }

}