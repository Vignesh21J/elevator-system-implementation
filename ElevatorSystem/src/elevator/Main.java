package elevator;

import elevator.core.ElevatorSystem;
import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.strategy.NearestCarStrategy;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        System.out.println("===== Multi Elevator Test =====");

        List<ElevatorCar> elevators = List.of(
                new ElevatorCar(1, 0),
                new ElevatorCar(2, 8)
        );

        ElevatorSystem coreSystem =
                new ElevatorSystem(elevators, new NearestCarStrategy());

        coreSystem.requestPickUp(3, Direction.UP);
        coreSystem.requestPickUp(2, Direction.DOWN);
        coreSystem.requestPickUp(9, Direction.DOWN);

        System.out.println();

        System.out.println("---------- Simulation Start ----------");

        for (int tick = 0; tick < 20; tick++) {

            System.out.println("TICK: " + tick);

            coreSystem.step();

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
