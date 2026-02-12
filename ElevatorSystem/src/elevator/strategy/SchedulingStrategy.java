package elevator.strategy;

import elevator.models.ElevatorCar;
import elevator.models.HallRequest;

import java.util.List;

public interface SchedulingStrategy {

    public int chooseElevator(HallRequest req, List<ElevatorCar> elevators);
}
