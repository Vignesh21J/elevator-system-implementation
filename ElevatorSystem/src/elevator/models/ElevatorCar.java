package elevator.models;

import elevator.enums.Direction;
import elevator.enums.DoorState;
import elevator.enums.ElevatorState;
import elevator.enums.FanState;

import java.util.NavigableSet;
import java.util.TreeSet;

public final class ElevatorCar {
    private final int id;
    private int currentFloor;

    private Direction direction = Direction.IDLE;
    private ElevatorState state = ElevatorState.IDLE;

    private final ElevatorDoor door = new ElevatorDoor();
    private final ElevatorFan fan = new ElevatorFan();

    private final NavigableSet<Integer> upStops = new TreeSet<>();
    private final NavigableSet<Integer> downStops = new TreeSet<>();

    public ElevatorCar(int id, int startFloor) {
        this.id = id;
        this.currentFloor = startFloor;
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }

    public void openDoor() {
        if (state == ElevatorState.MOVING) {
            throw new IllegalStateException("Cannot open door while elevator is moving.");
        }
        door.openDoor();
        state = ElevatorState.DOOR_OPEN;
    }

    public void closeDoor() {
        door.closeDoor();
    }

    public DoorState getDoorState() {
        return door.getState();
    }

    public void turnFanOn() {
        fan.turnOn();
    }

    public void turnFanOff() {
        fan.turnOff();
    }

    public FanState getFanState() {
        return fan.getState();
    }
}
