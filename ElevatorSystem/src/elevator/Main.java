package elevator;

import elevator.models.ElevatorCar;

public class Main {
    public static void main(String[] args) {

        // A Single Elevator
//        ElevatorCar elevator = new ElevatorCar(1, 0);
//
//        elevator.addTarget(4);
//        elevator.addTarget(2);
//
//        for (int i = 0; i < 10; i++) {
//            elevator.step();
//            // System.out.println("Current floor: " + elevator.getCurrentFloor());
//
//            System.out.println("Floor: " + elevator.getCurrentFloor()
//                    + " | Direction: " + elevator.getDirection()
//                    + " | State: " + elevator.getState()
//            );
//        }

        System.out.println("===== TEST 1: UP Movement =====");
        testUpMovement();

        System.out.println("\n===== TEST 2: DOWN Movement =====");
        testDownMovement();
    }

    private static void testUpMovement() {
        ElevatorCar elevator = new ElevatorCar(1, 0);

        elevator.addTarget(6);
        elevator.addTarget(2);

        for (int i = 0; i < 10; i++) {
            elevator.step();
            System.out.println("Floor: " + elevator.getCurrentFloor()
                    + " | Direction: " + elevator.getDirection()
                    + " | State: " + elevator.getState()
                    + " | Fan: " + elevator.getFanState()
            );
        }
    }

    private static void testDownMovement() {
        ElevatorCar elevator = new ElevatorCar(1, 7);

        elevator.addTarget(1);
        elevator.addTarget(3);

        for (int i = 0; i < 10; i++) {
            elevator.step();
            System.out.println("Floor: " + elevator.getCurrentFloor()
                    + " | Direction: " + elevator.getDirection()
                    + " | State: " + elevator.getState()
                    + " | Fan: " + elevator.getFanState()
            );
        }
    }
}
