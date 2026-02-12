package elevator;

import elevator.core.ElevatorSystem;
import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.strategy.NearestCarStrategy;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("\n===== Simulation Test =====");

        List<ElevatorCar> elevators = List.of(
                new ElevatorCar(1, 0),
                new ElevatorCar(2, 4)
        );

        ElevatorSystem system =
                new ElevatorSystem(elevators, new NearestCarStrategy());


        System.out.println("\n---------- Simulation Start ----------");

        for (int tick = 0; tick < 25; tick++) {

            // External Hall Requests
            if (tick == 0) {
                system.requestPickUp(3, Direction.UP);
            }

            if (tick == 1) {
                system.requestPickUp(6, Direction.DOWN);
            }

            if (tick == 5) {
                system.requestPickUp(1, Direction.UP);
            }

            // Internal Cabin Requests
            if (tick == 3) {
                system.requestDropOff(1, 8);
            }

            if (tick == 7) {
                system.requestDropOff(2, 0);
            }

            System.out.println("\nTICK: " + tick);

            system.step();

            for (ElevatorCar e : elevators) {
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
