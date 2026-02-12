package elevator.core;

import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.models.HallRequest;
import elevator.strategy.SchedulingStrategy;

import java.util.ArrayList;
import java.util.List;

import elevator.exceptions.InvalidFloorException;
import elevator.exceptions.InvalidDirectionException;
import elevator.exceptions.ElevatorNotFoundException;

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

        validateFloor(floor);
        validateHallDirection(floor, direction);

        HallRequest request = new HallRequest(floor, direction);
        dispatcher.onHallRequest(request, elevators);
    }

    // New Cabin Request for Drop off
    public void requestDropOff(int elevatorId, int destFloor) {

        validateFloor(destFloor);

        ElevatorCar car = getElevatorById(elevatorId);

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


    private void validateFloor(int floor) {
        if (floor < minFloor || floor > maxFloor) {
            throw new InvalidFloorException(floor, minFloor, maxFloor);
        }
    }

    private void validateHallDirection(int floor, Direction direction) {

        if (direction == Direction.IDLE) {
            throw new InvalidDirectionException(direction, "Hall Request");
        }

        if (floor == minFloor && direction == Direction.DOWN) {
            throw new InvalidDirectionException(direction, "Hall Request at min floor");
        }

        if (floor == maxFloor && direction == Direction.UP) {
            throw new InvalidDirectionException(direction, "Hall Request at max floor");
        }
    }



    private ElevatorCar getElevatorById(int id) {
        for (ElevatorCar car : elevators) {
            if (car.getId() == id) {
                return car;
            }
        }
        throw new ElevatorNotFoundException(id, elevators.size());
    }

    public List<ElevatorCar> getElevators() {
        return List.copyOf(elevators);
    }
}