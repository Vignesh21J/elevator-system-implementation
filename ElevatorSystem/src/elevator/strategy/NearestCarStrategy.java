package elevator.strategy;

import elevator.models.ElevatorCar;
import elevator.models.HallRequest;

import java.util.List;

public final class NearestCarStrategy implements SchedulingStrategy {

    @Override
    public ElevatorCar chooseElevator(HallRequest req, List<ElevatorCar> elevators) {

        ElevatorCar best = null;
        int minDist = Integer.MAX_VALUE;

        for (ElevatorCar elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - req.getFloor());

            if (distance < minDist) {
                minDist = distance;
                best = elevator;
            }
        }
        return best;
    }
}
