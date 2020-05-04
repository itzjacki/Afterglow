package com.itzjacki.afterglow.models;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.itzjacki.afterglow.AfterglowGame;

public class Bullet {
    private Vector2 position;
    private Vector2 velocity;  // Pixels per second
    private int state;
    private float areaSize;
    private static final int radius = 10;

    // timeAlive is how long it should take for the bullet to reach the center in milliseconds. Default = 600.
    public Bullet(int state, float timeAlive, int areaSize){
        this.state = state;
        this.areaSize = areaSize;
        float speed = AfterglowGame.ACTIVE_PLAY_SIZE / 2f / (timeAlive / 1000);

        switch (state) {
            case 0:
                System.out.println("N: Bullet spawned.");
                position = new Vector2(areaSize/2f, areaSize + radius);
                velocity = new Vector2(0, -speed);
                break;
            case 1:
                System.out.println("NE: Bullet spawned");
                position = new Vector2(areaSize + radius, areaSize + radius);
                velocity = new Vector2(-speed, -speed);
                break;
            case 2:
                System.out.println("E: Bullet spawned");
                position = new Vector2(areaSize + radius, areaSize/2f);
                velocity = new Vector2(-speed, 0);
                break;
            case 3:
                System.out.println("SE: Bullet spawned");
                position = new Vector2(areaSize + radius, -radius);
                velocity = new Vector2(-speed, speed);
                break;
            case 4:
                System.out.println("S: Bullet spawned");
                position = new Vector2(areaSize/2f, -radius);
                velocity = new Vector2(0, speed);
                break;
            case 5:
                System.out.println("SW: Bullet spawned");
                position = new Vector2(-radius, -radius);
                velocity = new Vector2(speed, speed);
                break;
            case 6:
                System.out.println("W: Bullet spawned");
                position = new Vector2(-radius, areaSize/2f);
                velocity = new Vector2(speed, 0);
                break;
            case 7:
                System.out.println("NW: Bullet spawned");
                position = new Vector2( -radius, areaSize + radius);
                velocity = new Vector2(speed, -speed);
                break;
            default:
                throw new IllegalArgumentException("Tried to create bullet type that doesn't exist: " + state);
        }
    }

    // Constructor that uses default values. This is the one used in practice so far.
    public Bullet(int state){
        this(state, 600, AfterglowGame.ACTIVE_PLAY_SIZE);
    }

    // Updates the position of the bullet, and returns true if the bullet "activated" on this update.
    public boolean update(float dt){
        Vector2 scaledVelocity = new Vector2(velocity);
        Vector2 oldPosition = new Vector2(position);

        scaledVelocity.scl(dt);
        position.add(scaledVelocity);

        // If the bullet crosses over the middle somehow, kill the speed and put it in the middle of the board.
        if(Math.signum(position.x - areaSize/2f) != Math.signum(oldPosition.x - areaSize/2f) || Math.signum(position.y - areaSize/2f) != Math.signum(oldPosition.y - areaSize/2f)){
            position.x = position.y = 0;
            velocity.x = velocity.y = 0;
            return true;
        }

        return false;
    }

    // Should be run after color has been set, and with the begin (fill) called.
    public void draw(ShapeRenderer shape){
        shape.circle(position.x, position.y, radius);
    }
}
