package elevator.models;

import elevator.Main;
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

    public DoorState getDoorState() {
        return door.getState();
    }

    public FanState getFanState() {
        return fan.getState();
    }


    public boolean hasStops() {
        return !upStops.isEmpty() || !downStops.isEmpty();
    }

    public void addStop(int floor) {
        if (floor == currentFloor) {
            upStops.add(floor);  // Insert floor here to trigger stop logic in next tick
            return;
        }

        if (floor > currentFloor)
            upStops.add(floor);
        else
            downStops.add(floor);
    }

    public void step() {
        // If door is open, close it first.
        if (state == ElevatorState.DOOR_OPEN) {
            closeDoor();

            if (!hasStops()) {
                direction = Direction.IDLE;
                state = ElevatorState.IDLE;
                fan.turnOff();
            } else {
                state = ElevatorState.MOVING;
            }
            return;
        }

        // If there's no stops left, then
        if (!hasStops()) {
            direction = Direction.IDLE;
            state = ElevatorState.IDLE;
            fan.turnOff();
            return;
        }

        decideDirectionIfIdle();

        state = ElevatorState.MOVING;
        fan.turnOn();
        moveOneFloor();

        // Check here we should stop or need to stop in this floor
        if (shouldStopHere()) {
            upStops.remove(currentFloor);
            downStops.remove(currentFloor);
            openDoor();
        }
    }

    private void closeDoor() {
        door.closeDoor();;
    }

    private void decideDirectionIfIdle() {
        if (direction != Direction.IDLE) return;

        Integer upCandidate = upStops.isEmpty() ? null : upStops.first();
        Integer downCandidate = downStops.isEmpty() ? null : downStops.last();

        if (upCandidate == null && downCandidate == null) {
            direction = Direction.IDLE;
            return;
        }

        if (upCandidate == null) {
            direction = Direction.DOWN;
            return;
        }

        if (downCandidate == null) {
            direction = Direction.UP;
            return;
        }

        // Both Sets having values, then where/whom to serve?
        int distanceUp = Math.abs(upCandidate - currentFloor);
        int distanceDown = Math.abs(downCandidate - currentFloor);

        direction = (distanceUp <= distanceDown) ? Direction.UP : Direction.DOWN;
    }

    private void moveOneFloor() {
        if (direction == Direction.UP)
            currentFloor++;
        else if (direction == Direction.DOWN)
            currentFloor--;
    }

    private boolean shouldStopHere() {
        return upStops.contains(currentFloor) || downStops.contains(currentFloor);
    }

    private void openDoor() {
        door.openDoor();
        state = ElevatorState.DOOR_OPEN;
        fan.turnOn();

        // SCAN-like Direction Switching Algorithm
        if (direction == Direction.UP &&
                upStops.tailSet(currentFloor + 1).isEmpty()
                && !downStops.isEmpty()) {
            direction = Direction.DOWN;
        }
        else if (direction == Direction.DOWN &&
                    downStops.headSet(currentFloor).isEmpty()
                    && !upStops.isEmpty()) {
            direction = Direction.UP;
        }
        else if (!hasStops()) {
            direction = Direction.IDLE;
        }
    }

}
