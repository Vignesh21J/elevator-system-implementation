package elevator;

import elevator.core.Dispatcher;
import elevator.core.ElevatorSystem;
import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.strategy.NearestCarStrategy;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("===== Multiple Elevator Testing =====");

        List<ElevatorCar> elevators = List.of(
                new ElevatorCar(1, 0),
                new ElevatorCar(2, 8),
                new ElevatorCar(3, 1)
        );

        ElevatorSystem elevatorSystem = new ElevatorSystem(
                elevators, new NearestCarStrategy());

        elevatorSystem.requestPickUp(4, Direction.UP);
        elevatorSystem.requestPickUp(5, Direction.UP);

        System.out.println("----------");

        for (int i = 0; i < 20; i++) {
            elevatorSystem.step();

            for (ElevatorCar e : elevators) {
                System.out.println(
                        "Elevator " + e.getId() +
                        " | Floor: " + e.getCurrentFloor()
                        + " | Direction: " + e.getDirection()
                        + " | State: " + e.getState()
                        + " | Fan Turned: " + e.getFanState()
                );
            }
            System.out.println("----------");
        }
    }
}
