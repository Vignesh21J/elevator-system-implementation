package elevator.core;

import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.models.HallRequest;
import elevator.strategy.SchedulingStrategy;

import java.util.List;

public final class ElevatorSystem {

    private final List<ElevatorCar> elevators;
    private final Dispatcher dispatcher;

    public ElevatorSystem(List<ElevatorCar> elevators,
                          SchedulingStrategy strategy) {
        this.elevators = elevators;
        this.dispatcher = new Dispatcher(strategy);
    }

    public void requestPickUp(int floor, Direction direction) {
        HallRequest request = new HallRequest(floor, direction);
        dispatcher.onHallRequest(request, elevators);
    }

    // New Cabin Request for Drop off
    public void requestDropOff(int elevatorId, int destFloor) {

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

    private ElevatorCar getElevatorById(int id) {
        for (ElevatorCar car : elevators) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }
}