package com.itzjacki.afterglow.models;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.itzjacki.afterglow.AfterglowGame;

public class LongNote {
    private Vector2 position;
    private Vector2 velocity;  // Pixels per second
    private float speed;
    private float timeAlive;
    private int state;
    private float timeRemaining;
    private float areaSize;
    private float middle;
    // Whether this bullet has hit the middle orb and been evaluated
    private boolean active = false;
    private static final int radius = 10;
    private int threshold;

    // timeAlive is how long it should take for the bullet to reach the center in milliseconds. Default = 600. Effectively the speed of the bullet.
    // timeActive is the length of the long note. Its the time from the start of it hits the middle until the end of it hits the middle.
    public LongNote(int state, float timeActive, float timeAlive, int areaSize, int wedgeOrbRadius){
        this.state = state;
        this.timeRemaining = timeActive;
        this.areaSize = areaSize;
        this.middle = areaSize/2f;
        this.timeAlive = timeAlive;
        this.speed = AfterglowGame.ACTIVE_PLAY_SIZE / 2f / (timeAlive / 1000);
        threshold = wedgeOrbRadius;


        switch (state) {
            case 0:
                System.out.println("N: Long note spawned.");
                position = new Vector2(areaSize/2f, areaSize + radius);
                velocity = new Vector2(0, -speed);
                break;
            case 2:
                System.out.println("E: Long note spawned");
                position = new Vector2(areaSize + radius, areaSize/2f);
                velocity = new Vector2(-speed, 0);
                break;
            case 4:
                System.out.println("S: Long note spawned");
                position = new Vector2(areaSize/2f, -radius);
                velocity = new Vector2(0, speed);
                break;
            case 6:
                System.out.println("W: Long note spawned");
                position = new Vector2(-radius, areaSize/2f);
                velocity = new Vector2(speed, 0);
                break;
            case 1:
            case 3:
            case 5:
            case 7:
                throw new IllegalArgumentException("Can't create diagonal long note.");
            default:
                throw new IllegalArgumentException("Tried to create long note type that doesn't exist: " + state);
        }
    }

    // Constructor that uses default values. This is the one used in practice so far.
    public LongNote(int state, int timeActive){
        this(state, timeActive, 600, AfterglowGame.ACTIVE_PLAY_SIZE, 40);
    }

    // Constructor that uses default values, but with custom time alive.
    public LongNote(int state, int timeActive, int timeAlive){
        this(state, timeActive, timeAlive, AfterglowGame.ACTIVE_PLAY_SIZE, 40);
    }

    // Updates the position of the bullet, and returns true if the bullet "activated" on this update.
    public boolean update(float dt){
        timeRemaining -= dt;
        if(timeRemaining <= 0){
            return true;
        }
        Vector2 scaledVelocity = new Vector2(velocity);
        Vector2 oldPosition = new Vector2(position);
        // Turns out I needed to use areaSize/2f a whole lot this method.

        scaledVelocity.scl(dt);
        position.add(scaledVelocity);

        // When/if the bullet crosses over the middle, kill the speed and put it in the middle of the board.
        // This ensures the bullet in the front gets out of the way before being frozen.
        if(Math.signum(position.x - middle) != Math.signum(oldPosition.x - middle) || Math.signum(position.y - middle) != Math.signum(oldPosition.y - middle)){
            position.x = position.y = middle;
            velocity.x = velocity.y = 0;
        }

        // Checks if a bullet is halfway covered by the middle orb. This is the point they get evaluated at.
        else if(!active && (
                position.x > middle - threshold && position.x < middle + threshold && position.y == middle ||  // From the sides
                position.y > middle - threshold && position.y < middle + threshold && position.x == middle  // From the top or bottom
        )){
            active = true;
            // Bullets can safely be removed when they're half-way covered by the orb.
            // The next frame they would've been far inside anyway. At 600 ms times with 60 fps and
            // a 900px play area bullets move 12-13 pixels per frame. Therefore there's no need to wait until
            // they're fully covered by the middle orb.
        }
        return false;
    }

    // Should be run after color has been set, and with the begin (fill) called.
    public void draw(ShapeRenderer shape){
        shape.circle(position.x, position.y, radius);

        float pos;
        if(getState() == 0 || getState() == 4){
            pos = position.y;
        }
        else{
            pos = position.x;
        }

        // If the note should extend past the screen border
        if(Math.abs(pos - middle) + timeToPixels(timeRemaining) > areaSize/2f){
            // TODO: Write draw method for extending past screen edge
        }
        // TODO: Write draw method for not extending past edge
    }

    // Helper method used to calculate the length of the note at the current remaining time.
    private float timeToPixels(float time){
        return speed/1000 * time;
    }

    public int getState(){
        return state;
    }
}
