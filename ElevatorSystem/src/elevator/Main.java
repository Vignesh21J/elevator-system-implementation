package elevator;

import elevator.core.ElevatorSystem;
import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.strategy.NearestCarStrategy;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("\n===== Multi-Elevator Test =====");

        ElevatorSystem system = new ElevatorSystem(0, 9, 2, new NearestCarStrategy());

        system.requestPickUp(3, Direction.UP);
        system.requestPickUp(9, Direction.DOWN);

        // Invalid testcases (will be rejected)
        system.requestPickUp(9, Direction.UP);
        system.requestPickUp(0, Direction.DOWN);
        system.requestPickUp(15, Direction.UP);

        System.out.println("---------- Simulation Start ----------");

        for (int tick = 0; tick < 20; tick++) {

            if (tick == 3) {
                system.requestDropOff(1, 7);
            }

            if (tick == 6) {
                system.requestDropOff(2, 3);
            }

            system.step();

            for (ElevatorCar e : system.getElevators()) {
                System.out.println(
                        "Elevator " + e.getId() +
                                " | Floor: " + e.getCurrentFloor() +
                                " | Direction: " + e.getDirection() +
                                " | State: " + e.getState() +
                                " | Door: " + e.getDoorState() +
                                " | Fan: " + e.getFanState()
                );
            }

            System.out.println("--------------------------------------");
        }
    }
}
