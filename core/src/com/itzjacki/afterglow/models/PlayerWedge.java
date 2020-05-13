package com.itzjacki.afterglow.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.itzjacki.afterglow.controllers.EventManager;

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

    private final int radius = 40;

    public PlayerWedge(){
        state = 0;
    }

    // Should be run after calling .begin on a ShapeRenderer, with fill
    public void drawCircle(ShapeRenderer shape, Color secondaryColor, Color primaryColor, boolean[] longNoteHold, int worldSize){
        // Draws the graphic for long notes being currently held
        shape.setColor(primaryColor);
        for(int i = 0; i < longNoteHold.length; i++){
            if(longNoteHold[i] && EventManager.getInstance().isArrowPressed(i)){
                shape.arc(worldSize/2f, worldSize/2f, getRadius() + 10, 405 - i * 45 , 90, getRadius());
            }
        }
        // Draws the circle under the player wedge
        shape.setColor(secondaryColor);
        shape.circle(worldSize/2f, worldSize/2f, getRadius(), getRadius());
    }

    // Should be run after calling .begin on a ShapeRenderer, with fill
    public void drawWedge(ShapeRenderer shape, Color primaryColor, Color secondaryColor, int worldSize){
        shape.setColor(primaryColor);
        if(getState() == 8){
            shape.circle(worldSize/2f, worldSize/2f, getRadius() + 1, getRadius());
            shape.setColor(secondaryColor);
            shape.circle(worldSize/2f, worldSize/2f, getRadius() - 10, getRadius());
        }
        else if(getState() >= 0 && getState() <= 7){
            shape.arc(worldSize/2f, worldSize/2f, getRadius() + 1, 405 - getState() * 45 , 90, getRadius());
        }
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
