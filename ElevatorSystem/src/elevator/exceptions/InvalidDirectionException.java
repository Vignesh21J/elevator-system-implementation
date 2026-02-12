package elevator.exceptions;

import elevator.enums.Direction;

public class InvalidDirectionException extends ElevatorSystemException {

    private final Direction invalidDirection;
    private final String context;

    public InvalidDirectionException(Direction invalidDirection, String context) {
        super("Invalid direction: " + invalidDirection, "INVALID_DIRECTION");
        this.invalidDirection = invalidDirection;
        this.context = context;
    }

    public Direction getInvalidDirection() {
        return invalidDirection;
    }

    public String getContext() {
        return context;
    }
}
