package elevator.exceptions;

public class ElevatorSystemException extends RuntimeException {
    private final String errorCode;
    private final long timestamp;

    protected ElevatorSystemException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
