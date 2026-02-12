package elevator.core;

import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.models.HallRequest;
import elevator.strategy.SchedulingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ElevatorSystem {

    private final int minFloor;
    private final int maxFloor;

    private final List<ElevatorCar> elevators;
    private final Dispatcher dispatcher;

    public ElevatorSystem(int minFloor, int maxFloor, int numElevator, SchedulingStrategy strategy) {

        if (minFloor > maxFloor)
            throw new IllegalArgumentException("minFloor can't be > maxFloor");
        if (numElevator <= 0)
            throw new IllegalArgumentException("Number of Elevators must be > 0");

        this.minFloor = minFloor;
        this.maxFloor = maxFloor;

        this.elevators = new ArrayList<>();
        int startFloor = minFloor;

        for (int i = 1; i <= numElevator; i++) {
            elevators.add(new ElevatorCar(i, startFloor));
        }

        this.dispatcher = new Dispatcher(strategy);
    }

    public void requestPickUp(int floor, Direction direction) {

        if (!isValidFloor(floor)) {
            System.out.println("Invalid floor: " + floor);
            return;
        }

        if (!isValidHallDirection(floor, direction)) {
            System.out.println("Invalid direction at floor " + floor);
            return;
        }

        HallRequest request = new HallRequest(floor, direction);
        dispatcher.onHallRequest(request, elevators);
    }

    // New Cabin Request for Drop off
    public void requestDropOff(int elevatorId, int destFloor) {

        if (!isValidFloor(destFloor)) {
            System.out.println("Invalid drop-off floor: " + destFloor);
            return;
        }

        ElevatorCar car = getElevatorById(elevatorId);

        if (car == null) {
            System.out.println("Invalid elevator ID: " + elevatorId);
            return;
        }

        car.addStop(destFloor);

        System.out.println("Cabin request: Elevator " + elevatorId + " -> floor " + destFloor);
    }

    public void step() {

        dispatcher.tryAssignAll(elevators);

        for (ElevatorCar elevator : elevators) {
            elevator.step();
        }
    }

    public void run(int ticks) {

        for (int t = 1; t <= ticks; t++) {

            step();

            System.out.println("Tick " + t);

            for (ElevatorCar car : elevators) {
                System.out.println("  Elevator "
                        + car.getId()
                        + " | Floor: " + car.getCurrentFloor()
                        + " | Direction: " + car.getDirection()
                        + " | State: " + car.getState()
                        + " | Door: " + car.getDoorState()
                        + " | Fan: " + car.getFanState());
            }

            System.out.println("--------------------------------------");
        }
    }



    private boolean isValidFloor(int floor) {
        return floor >= minFloor && floor <= maxFloor;
    }

    private boolean isValidHallDirection(int floor, Direction direction) {

        if (direction == Direction.IDLE)
            return false;

        if (floor == minFloor && direction == Direction.DOWN)
            return false;

        if (floor == maxFloor && direction == Direction.UP)
            return false;

        return true;
    }


    private ElevatorCar getElevatorById(int id) {
        for (ElevatorCar car : elevators) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }

    public List<ElevatorCar> getElevators() {
        return List.copyOf(elevators);
    }
}