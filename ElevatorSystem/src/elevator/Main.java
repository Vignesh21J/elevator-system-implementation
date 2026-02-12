package elevator;

import elevator.core.Dispatcher;
import elevator.core.ElevatorSystem;
import elevator.enums.Direction;
import elevator.models.ElevatorCar;
import elevator.strategy.NearestCarStrategy;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("===== TEST 1: UP Movement =====");
        testUpMovement();

        System.out.println("\n===== TEST 2: DOWN Movement =====");
        testDownMovement();
    }

    private static void testUpMovement() {
        ElevatorCar elevator = new ElevatorCar(1, 0);  // A single Elevator

        List<ElevatorCar> elevators = List.of(elevator);  // 1 Elevator as List

        ElevatorSystem elevatorSystem = new ElevatorSystem(
                elevators, new NearestCarStrategy());

        elevatorSystem.requestPickUp(4, Direction.UP);
        elevatorSystem.requestPickUp(7, Direction.UP);

        for (int i = 0; i < 10; i++) {
            elevatorSystem.step();
            System.out.println("Floor: " + elevator.getCurrentFloor()
                    + " | Direction: " + elevator.getDirection()
                    + " | State: " + elevator.getState()
                    + " | Fan: " + elevator.getFanState()
            );
        }
    }

    private static void testDownMovement() {
        ElevatorCar elevator = new ElevatorCar(1, 7);  // A single Elevator

        List<ElevatorCar> elevators = List.of(elevator);  // 1 Elevator as List

        ElevatorSystem elevatorSystem = new ElevatorSystem(
                elevators, new NearestCarStrategy());

        elevatorSystem.requestPickUp(1, Direction.DOWN);
        elevatorSystem.requestPickUp(3, Direction.DOWN);


        for (int i = 0; i < 10; i++) {
            elevatorSystem.step();
            System.out.println("Floor: " + elevator.getCurrentFloor()
                    + " | Direction: " + elevator.getDirection()
                    + " | State: " + elevator.getState()
                    + " | Fan: " + elevator.getFanState()
            );
        }
    }
}
