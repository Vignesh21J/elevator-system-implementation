package elevator.models;

import elevator.enums.Direction;

import java.util.UUID;

public final class HallRequest {
    public final UUID id;
    public final int floor;
    public final Direction direction;
    public final long createdAt;

    public HallRequest(int floor, Direction direction) {
        this.id = UUID.randomUUID();
        this.floor = floor;
        this.direction = direction;
        this.createdAt = System.currentTimeMillis();
    }

    public UUID getId() {
        return id;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
