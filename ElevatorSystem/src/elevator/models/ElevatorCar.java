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

    public void addTarget(int floor) {
        if (floor == getCurrentFloor()) {
            return;
        }

        if (floor > getCurrentFloor()) {
            upStops.add(floor);
        } else {
            downStops.add(floor);
        }
    }

    public Integer nextStop() {
        if (getDirection() == Direction.UP) {
            if (!upStops.isEmpty()) {
                return upStops.first();
            } else if (!downStops.isEmpty()) {
                direction = Direction.DOWN;
                return downStops.last();
            }
        }

        if (getDirection() == Direction.DOWN) {
            if (!downStops.isEmpty()) {
                return downStops.last();
            } else if (!upStops.isEmpty()) {
                direction = Direction.UP;
                return upStops.first();
            }
        }

        if (getDirection() == Direction.IDLE) {
            if (!upStops.isEmpty()) {
                direction = Direction.UP;
                return upStops.first();
            } else if (!downStops.isEmpty()) {
                direction = Direction.DOWN;
                return downStops.last();
            }
        }

        return null; // no stops in both sets.
    }

    public void moveOneFloor() {
        if (getDirection() == Direction.IDLE) {
            return;
        }

        if (getDirection() == Direction.UP) {
            currentFloor++;
        } else if (getDirection() == Direction.DOWN) {
            currentFloor--;
        }
    }

    public boolean shouldStopAtCurrentFloor() {
        return upStops.contains(currentFloor) || downStops.contains(currentFloor);
    }

    public void removeStopAtCurrentFloor() {
        upStops.remove(currentFloor);
        downStops.remove(currentFloor);
    }

    public void step() {
        Integer target = nextStop();

        if (target == null) {
            direction = Direction.IDLE;
            state = ElevatorState.IDLE;
            return;
        }

        state = ElevatorState.MOVING;

        moveOneFloor();

        if (shouldStopAtCurrentFloor()) {

            state = ElevatorState.DOOR_OPEN;

            removeStopAtCurrentFloor();

            state = ElevatorState.IDLE;

            // Go idle ONLY if no pending requests
            if (upStops.isEmpty() && downStops.isEmpty()) {
                direction = Direction.IDLE;
            }
        }
    }
}
