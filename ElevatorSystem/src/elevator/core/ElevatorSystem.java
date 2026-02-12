package elevator.core;

import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.models.HallRequest;
import elevator.strategy.SchedulingStrategy;

import java.util.List;

public final class ElevatorSystem {
    private final List<ElevatorCar> elevators;
    private final Dispatcher dispatcher;

    public ElevatorSystem(List<ElevatorCar> elevators, SchedulingStrategy strategy) {
        this.elevators = elevators;
        this.dispatcher = new Dispatcher(strategy);
    }

    public void requestPickUp(int floor, Direction direction) {
        HallRequest request = new HallRequest(floor, direction);
        dispatcher.onHallRequest(request, elevators);
    }

    public void step() {

        dispatcher.tryAssignAll(elevators);  // try assigning pending requests

        for (ElevatorCar elevator : elevators) {
            elevator.step();
        }
    }
}
