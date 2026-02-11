

# Elevator System – Low Level Design (Java)

## Overview

This project implements a real-world Elevator System using Java, focusing on Low-Level Design (LLD) principles.  
It simulates elevator behavior step-by-step, handles hall and elevator requests, and supports pluggable scheduling algorithms using clean object-oriented design.

This project is built incrementally to demonstrate Core Java, Collections, Design Patterns, and extensibility toward multithreading.

---

## Key Features

- Supports N floors and K elevators
- Handles hall pickup requests (UP / DOWN)
- Handles destination selection inside elevators
- Step-by-step movement simulation
- Elevator door open / close logic
- Elevator fan ON / OFF logic
- Request assignment using pluggable strategies
- Designed with clean separation of concerns
- Easily extensible to multi-threaded execution

---

## Functional Requirements

- Move elevators to requested floors
- Assign hall requests to the most suitable elevator
- Stop at appropriate floors based on direction and targets
- Open and close doors correctly
- ON and OFF fan correctly
- Avoid duplicate or invalid requests

---

## Non-Functional Requirements

- **Scalability** – supports multiple elevators and floors
- **Extensibility** – new scheduling algorithms can be added
- **Reliability** – avoids duplicate and invalid requests
- **Testability** – deterministic step-based simulation
- **Maintainability** – clean responsibilities and boundaries

---

## High-Level Design

### Core Components

```text
ElevatorSystem (Facade)
│
├── Dispatcher
│   └── SchedulingStrategy
│       ├── NearestElevatorStrategy
│
├── ElevatorCar
│   ├── Movement Logic
│   ├── Door Logic
│   ├── Fan Logic
│   └── Target Management
│
├── Requests
│   ├── HallRequest
│   └── ElevatorRequest
│
└── Enums & States
````

---

## Design Patterns Used

| Pattern | Usage |
|------|------|
| Facade | ElevatorSystem exposes clean APIs |
| Strategy | Pluggable scheduling algorithms |
| State | Elevator, door and fan states |
| Single Responsibility | Clear ownership of logic |

---

## Core Domain Models

### ElevatorCar

Represents a single elevator.

#### Responsibilities

- Track current floor, direction, and state
- Maintain target floors
- Move step-by-step
- Decide when to stop
- Handle door open/close logic
- Handle fan ON/OFF logic

#### Key Methods

- `addTarget(floor)`
- `nextStep()`
- `shouldStopAtCurrentFloor()`
- `openDoor()`
- `closeDoor()`
- `fanON()`
- `fanOFF()`

---

### Dispatcher

Acts as a request assignment coordinator.

#### Responsibilities

- Maintain unassigned hall requests
- Assign requests using scheduling strategies
- Update elevator target queues

#### Why separate Dispatcher?

Keeps ElevatorSystem thin and allows scheduling logic to evolve independently.

---

## SchedulingStrategy (Strategy Pattern)

```java
interface SchedulingStrategy {
    int chooseElevator(HallRequest request, List<ElevatorCar> elevators);
}
```


### Implementations

#### Nearest Elevator Strategy

* Calculates cost based on distance
* Adds penalty if elevator is moving away
* Chooses minimum score

#### SCAN Strategy

* Elevator continues in current direction
* Serves compatible requests along the path
* Defers incompatible requests until direction reversal

---

## Request Types

### HallRequest

Pickup request from a floor.

Includes:

* Source floor
* Direction (UP / DOWN)
* Timestamp (for future fairness)

### ElevatorRequest

Destination selected inside an elevator.

Includes:

* Elevator ID
* Target floor
* Fan (ON / OFF)
* Timestamp

---

## State Modeling

### Elevator State

* IDLE
* MOVING
* DOOR_OPEN

### Direction

* UP
* DOWN
* IDLE

### Door State

* OPEN
* CLOSED

### Fan State

* ON
* OFF

---

## Step-Based Simulation

The system runs using discrete ticks:

```java
step();
```

### Each step:

* Elevators move one floor (if moving)
* Check if elevator should stop
* Open / close doors
* Fan ON / OFF
* Update states

### Why step-based?

* Deterministic behavior
* Easy to test
* Avoids premature concurrency

---

## Edge Case Handling

### Duplicate Requests

* Deduplicated using `(floor, direction)`

### Invalid Input

* Floor range and elevator ID validated

### Target Already Exists

* Ignored to avoid redundant stops

---

## Concurrency Design (Planned Extension)

Initial version is single-threaded by design.

### Why?

* Correctness before optimization
* Easier debugging and testing

### Future Extension

* Each elevator can act as an independent actor
* Thread confinement per elevator
* Dispatcher becomes thread-safe
* Optional event-driven state notifications

---

## Why This Project?

* Demonstrates real-world system modeling
* Shows understanding of LLD & OOP
* Avoids overengineering
* Interview-friendly and explainable
* Designed for incremental growth

---

## Future Enhancements

* Capacity constraints
* VIP priority requests
* Emergency modes
* Zoning for low-rise / high-rise buildings
* Multithreaded execution

---

## Author

Built as a learning-oriented, interview-ready LLD project to demonstrate Java fundamentals, design patterns, and system thinking.
