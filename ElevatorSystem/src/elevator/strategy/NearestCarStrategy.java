package elevator.strategy;

import elevator.enums.Direction;
import elevator.enums.ElevatorState;
import elevator.models.ElevatorCar;
import elevator.models.HallRequest;

import java.util.List;

public final class NearestCarStrategy implements SchedulingStrategy {

    @Override
    public int chooseElevator(HallRequest req, List<ElevatorCar> elevators) {

        int bestId = -1;
        long bestScore = Long.MAX_VALUE;

        for (ElevatorCar car : elevators) {
            long score = getScoreForCar(car, req);

            if (score < bestScore || (score == bestScore && car.getId() < bestId)) {
                bestScore = score;
                bestId = car.getId();
            }
        }
        return bestId;
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
