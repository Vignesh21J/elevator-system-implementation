package elevator.core;

import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.models.HallRequest;
import elevator.strategy.SchedulingStrategy;

import java.util.*;

public final class Dispatcher {

    private final SchedulingStrategy strategy;
    private final Map<UUID, HallRequest> pendingRequests = new LinkedHashMap<>();

    public Dispatcher(SchedulingStrategy strategy) {
        this.strategy = strategy;
    }

    public void onHallRequest(HallRequest req, List<ElevatorCar> elevators) {
        pendingRequests.put(req.getId(),req);
        tryAssignAll(elevators);
    }

    public void tryAssignAll(List<ElevatorCar> elevators) {
        Iterator<Map.Entry<UUID, HallRequest>> iterator = pendingRequests.entrySet().iterator();

        while (iterator.hasNext()) {
            HallRequest request = iterator.next().getValue();

            ElevatorCar chosen =
                    strategy.chooseElevator(request, elevators);

            if (chosen == null) {
                continue;
            }

            chosen.addStop(request.getFloor());

            System.out.println("Assigned request at floor " + request.getFloor()
                    + " to Elevator : " + chosen.getId());

            iterator.remove();
        }
    }
}
