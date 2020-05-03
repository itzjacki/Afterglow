package com.itzjacki.afterglow.models;

public class PlayerWedge {
    // The direction the wedge is facing:
    // -1: Nowhere
    // 0: Up
    // 1: Up-right
    // 2: Right
    // 3: Down-right (darius)
    // 4: Down
    // 5: Down-left
    // 6: Left
    // 7: Up-left
    // 8: Circle

    private int state;

    private final int radius = 80;

    public PlayerWedge(){
        state = -1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getRadius() {
        return radius;
    }
}
