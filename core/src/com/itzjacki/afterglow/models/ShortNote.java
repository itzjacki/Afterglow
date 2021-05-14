package com.itzjacki.afterglow.models;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.itzjacki.afterglow.AfterglowGame;

public class ShortNote implements Note {
    private Vector2 position;
    private Vector2 velocity;  // Pixels per second
    private int state;
    private float areaSize;
    // Whether this bullet has hit the middle orb and been evaluated
    private boolean counted = false;
    private static final int radius = 10;
    private int threshold;
    // the coordinates the 45-degree bullets need to be within to be "inside" the circle
    private int cornerThreshold;

    // timeAlive is how long it should take for the bullet to reach the center in milliseconds. Default = 600.
    public ShortNote(int state, float timeAlive, int areaSize, int wedgeOrbRadius){
        this.state = state;

        this.areaSize = areaSize;
        float speed = (AfterglowGame.ACTIVE_PLAY_SIZE - wedgeOrbRadius) / 2f / (timeAlive / 1000);
        threshold = wedgeOrbRadius;
        cornerThreshold = (int)(Math.sin(Math.PI/4) * threshold);


        switch (state) {
            case 0:
                position = new Vector2(areaSize/2f, areaSize + radius);
                velocity = new Vector2(0, -speed);
                break;
            case 1:
                position = new Vector2(areaSize + radius, areaSize + radius);
                velocity = new Vector2(-speed, -speed);
                break;
            case 2:
                position = new Vector2(areaSize + radius, areaSize/2f);
                velocity = new Vector2(-speed, 0);
                break;
            case 3:
                position = new Vector2(areaSize + radius, -radius);
                velocity = new Vector2(-speed, speed);
                break;
            case 4:
                position = new Vector2(areaSize/2f, -radius);
                velocity = new Vector2(0, speed);
                break;
            case 5:
                position = new Vector2(-radius, -radius);
                velocity = new Vector2(speed, speed);
                break;
            case 6:
                position = new Vector2(-radius, areaSize/2f);
                velocity = new Vector2(speed, 0);
                break;
            case 7:
                position = new Vector2( -radius, areaSize + radius);
                velocity = new Vector2(speed, -speed);
                break;
            default:
                throw new IllegalArgumentException("Tried to create bullet type that doesn't exist: " + state);
        }
    }

    // Updates the position of the bullet, and returns true if the bullet "activated" on this update.
    public boolean update(float dt){
        Vector2 scaledVelocity = new Vector2(velocity);
        Vector2 oldPosition = new Vector2(position);
        // Turns out I needed to use areaSize/2f a whole lot this method.
        float middle = areaSize/2f;

        scaledVelocity.scl(dt);
        position.add(scaledVelocity);

        // If the bullet crosses over the middle somehow, kill the speed and put it in the middle of the board.
        if(Math.signum(position.x - middle) != Math.signum(oldPosition.x - middle) || Math.signum(position.y - middle) != Math.signum(oldPosition.y - middle)){
            position.x = position.y = middle;
            velocity.x = velocity.y = 0;
            counted = true;
            return true;
        }

        // Checks if a bullet is halfway covered by the middle orb. This is the point they get evaluated at.
        else if(!counted && (
            position.x > middle - threshold && position.x < middle + threshold && position.y == middle ||  // From the sides
            position.y > middle - threshold && position.y < middle + threshold && position.x == middle ||  // From the top or bottom
            position.x > middle - cornerThreshold && position.x < middle + cornerThreshold && position.y > middle - cornerThreshold && position.y < middle + cornerThreshold  // From one of the four corners
        )){
            counted = true;
            return true;
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
    }

    public int getState(){
        return state;
    }
}
