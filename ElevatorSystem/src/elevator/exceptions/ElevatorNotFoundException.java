package elevator.exceptions;

public class ElevatorNotFoundException extends ElevatorSystemException {

    private final int requestedElevatorId;
    private final int totalElevators;

    public ElevatorNotFoundException(int requestedElevatorId, int totalElevators) {
        super("Elevator not found: " + requestedElevatorId, "ELEVATOR_NOT_FOUND");
        this.requestedElevatorId = requestedElevatorId;
        this.totalElevators = totalElevators;
    }

    public int getRequestedElevatorId() {
        return requestedElevatorId;
    }

    public int getTotalElevators() {
        return totalElevators;
    }
}
