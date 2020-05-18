package com.itzjacki.afterglow.models;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.itzjacki.afterglow.AfterglowGame;

public class CircleNote implements Note{
    private float size;
    private float velocity;  // Pixels per second
    private float areaSize;
    private boolean counted = false;  // Whether this note has hit the middle orb and been evaluated
    private int threshold;

    // timeAlive is how long it should take for the bullet to reach the center in milliseconds. Default = 600.
    public CircleNote(float timeAlive, int areaSize, int wedgeOrbRadius){

        this.areaSize = areaSize;
        threshold = wedgeOrbRadius;

        System.out.println("Circle note spawned");
        velocity = - AfterglowGame.ACTIVE_PLAY_SIZE / 2f / (timeAlive / 1000);
        size = areaSize/2f/(float)(Math.sin(Math.PI/4));
    }

    // Constructor that uses default values. This is the one used in practice so far.
    public CircleNote(){
        this(600, AfterglowGame.ACTIVE_PLAY_SIZE, 40);
    }

    // Constructor that uses default values, but with custom time alive.
    public CircleNote(int timeAlive){
        this(timeAlive, AfterglowGame.ACTIVE_PLAY_SIZE, 40);
    }

    // Updates the position of the bullet, and returns true if the bullet "activated" on this update.
    public boolean update(float dt){
        float scaledVelocity = velocity * dt;
        size += scaledVelocity;

        // Checks if a bullet is halfway covered by the middle orb. This is the point they get evaluated at.
        if(!counted && size <= threshold){
            counted = true;
            return true;
        }
        return false;
    }

    // Should be run after color has been set, and with the begin (fill) called.
    public void draw(ShapeRenderer shape){
        // Draws three circles next to each other for added thickness.
        // The middle one is the one that actually represents the activation point.
        shape.circle(areaSize/2f, areaSize/2f, size - 1, (int)size - 1);
        shape.circle(areaSize/2f, areaSize/2f, size, (int)size);
        shape.circle(areaSize/2f, areaSize/2f, size + 1, (int)size + 1);
    }

    public int getState(){
        return 8;
    }
}
