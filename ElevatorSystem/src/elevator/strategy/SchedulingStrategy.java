package elevator.strategy;

import elevator.models.ElevatorCar;
import elevator.models.HallRequest;

import java.util.List;

public interface SchedulingStrategy {

    public ElevatorCar chooseElevator(HallRequest req, List<ElevatorCar> elevators);
}
