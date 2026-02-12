package elevator.core;

import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.models.HallRequest;
import elevator.strategy.SchedulingStrategy;

import java.util.List;

public final class Dispatcher {
    private final List<ElevatorCar> elevators;
    private final SchedulingStrategy strategy;

    public Dispatcher(List<ElevatorCar> elevators, SchedulingStrategy strategy) {
        this.elevators = elevators;
        this.strategy = strategy;
    }

    public void onHallRequest(int floor, Direction direction) {
        HallRequest request = new HallRequest(floor, direction);

        ElevatorCar selected = strategy.chooseElevator(request, elevators);

        if (selected != null) {
            selected.addTarget(floor);
        }
    }
}
