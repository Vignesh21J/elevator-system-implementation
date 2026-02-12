package elevator.exceptions;

public class InvalidFloorException extends ElevatorSystemException {

    private final int requestedFloor;
    private final int minFloor;
    private final int maxFloor;

    public InvalidFloorException(int requestedFloor, int minFloor, int maxFloor) {
        super("Invalid floor: " + requestedFloor, "INVALID_FLOOR");
        this.requestedFloor = requestedFloor;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    public int getRequestedFloor() {
        return requestedFloor;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }
}
