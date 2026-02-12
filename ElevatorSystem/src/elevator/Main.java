package elevator;

import elevator.core.ElevatorSystem;
import elevator.enums.Direction;
import elevator.exceptions.*;
import elevator.strategy.NearestCarStrategy;

public class Main {

    public static void main(String[] args) {

        ElevatorSystem system =
                new ElevatorSystem(0, 9, 2, new NearestCarStrategy());

        System.out.println("=== Multi-Elevator Tick-Based Simulation ===\n");

        try {

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

        } catch (ElevatorSystemException e) {
            System.err.println("Error during simulation: " + e.getMessage());
        }

        System.out.println("\n=== Simulation Complete ===");


        // =========================EXCEPTION HANDLING ==================================

        System.out.println("\n=== Exception Handling Demonstration ===\n");

        // Invalid Floor
        try {
            System.out.println("Test 1: Invalid floor request (15)");
            system.requestPickUp(15, Direction.UP);
        } catch (InvalidFloorException e) {
            System.err.println("✓ Caught InvalidFloorException:");
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Requested Floor: " + e.getRequestedFloor());
            System.err.println("  Valid Range: " + e.getMinFloor() + " to " + e.getMaxFloor());
        }

        // Invalid Direction
        try {
            System.out.println("\nTest 2: Invalid direction (IDLE)");
            system.requestPickUp(5, Direction.IDLE);
        } catch (InvalidDirectionException e) {
            System.err.println("✓ Caught InvalidDirectionException:");
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Invalid Direction: " + e.getInvalidDirection());
            System.err.println("  Context: " + e.getContext());
        }

        // Elevator Not Found
        try {
            System.out.println("\nTest 3: Non-existent elevator (99)");
            system.requestDropOff(99, 4);
        } catch (ElevatorNotFoundException e) {
            System.err.println("✓ Caught ElevatorNotFoundException:");
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Requested Elevator ID: " + e.getRequestedElevatorId());
            System.err.println("  Total Elevators: " + e.getTotalElevators());
        }

        // Base Exception Catch
        try {
            System.out.println("\nTest 4: Base exception catch (Invalid floor 20)");
            system.requestPickUp(20, Direction.UP);
        } catch (ElevatorSystemException e) {
            System.err.println("✓ Caught using base ElevatorSystemException:");
            System.err.println("  Exception Type: " + e.getClass().getSimpleName());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Timestamp: " + e.getTimestamp());
            System.err.println("  Message: " + e.getMessage());
        }

        System.out.println("\n=== Exception Handling Demo Complete ===");
    }
}