package elevator.models;

import elevator.enums.FanState;

public final class ElevatorFan {
    private FanState state;

    public ElevatorFan() {
        this.state = FanState.OFF;
    }

    public void turnOn() {
        if (!isOn()) {
            state = FanState.ON;
        }
    }

    public void turnOff() {
        if (isOn()) {
            state = FanState.OFF;
        }
    }

    public boolean isOn() {
        return state == FanState.ON;
    }

    public FanState getState() {
        return state;
    }
}
