package elevator.models;

import elevator.enums.DoorState;

public final class ElevatorDoor {
    private DoorState state;

    public ElevatorDoor() {
        this.state = DoorState.CLOSED;
    }

    public void openDoor() {
        if (!isOpen()) {
            state = DoorState.OPEN;
        }
    }

    public void closeDoor() {
        if (isOpen()) {
            state = DoorState.CLOSED;
        }
    }

    public boolean isOpen() {
        return state == DoorState.OPEN;
    }

    public DoorState getState() {
        return state;
    }
}
