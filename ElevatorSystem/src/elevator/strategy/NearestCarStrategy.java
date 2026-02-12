package elevator.strategy;

import elevator.enums.Direction;
import elevator.enums.ElevatorState;
import elevator.models.ElevatorCar;
import elevator.models.HallRequest;

import java.util.List;

public final class NearestCarStrategy implements SchedulingStrategy {

    @Override
    public ElevatorCar chooseElevator(HallRequest req, List<ElevatorCar> elevators) {

        ElevatorCar best = null;
        long bestScore = Long.MAX_VALUE;

        for (ElevatorCar car : elevators) {
            long score = getScoreForCar(car, req);

            if (score < bestScore || (score == bestScore && best != null && car.getId() < best.getId())) {
                bestScore = score;
                best = car;
            }
        }
        return best;
    }

    private long getScoreForCar(ElevatorCar car, HallRequest req) {
        int dist = Math.abs(car.getCurrentFloor() - req.getFloor());

        // Idle cars are the best to serve
        if (car.getDirection() == Direction.IDLE && car.getState() == ElevatorState.IDLE) {
            return dist;
        }

        boolean sameDir = car.getDirection() == req.getDirection();
        boolean onTheWay =
                (car.getDirection() == Direction.UP && car.getCurrentFloor() <= req.getFloor())
                ||
                (car.getDirection() == Direction.DOWN && car.getCurrentFloor() >= req.getFloor());

        if (sameDir && onTheWay) {
            return dist;
        }

        return dist + 1000L;
    }
}
