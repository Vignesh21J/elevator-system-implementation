package elevator;

import elevator.core.ElevatorSystem;
import elevator.enums.Direction;
import elevator.strategy.NearestCarStrategy;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Multi-Elevator Tick-Based Simulation ===\n");

        ElevatorSystem system =
                new ElevatorSystem(0, 9, 2, new NearestCarStrategy());

        // ---------------- PHASE 1 ----------------
        System.out.println("PHASE 1: Initial Hall Request");
        system.requestPickUp(3, Direction.UP);
        system.run(2);

        // ---------------- PHASE 2 ----------------
        System.out.println("\nPHASE 2: Another Hall Request While Moving");
        system.requestPickUp(8, Direction.DOWN);
        system.run(3);

        // ---------------- PHASE 3 ----------------
        System.out.println("\nPHASE 3: Cabin Requests During Movement");
        system.requestDropOff(1, 7);
        system.requestDropOff(2, 2);
        system.run(5);

        // ---------------- PHASE 4 ----------------
        System.out.println("\nPHASE 4: New Hall Request Mid-Simulation");
        system.requestPickUp(5, Direction.UP);
        system.run(9);

        System.out.println("\n=== Simulation Complete ===");
    }
}
