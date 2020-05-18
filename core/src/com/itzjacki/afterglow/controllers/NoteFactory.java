package com.itzjacki.afterglow.controllers;

import com.badlogic.gdx.graphics.Color;
import com.itzjacki.afterglow.models.CircleNote;
import com.itzjacki.afterglow.models.LongNote;
import com.itzjacki.afterglow.models.ShortNote;

public class NoteFactory {
    private float defaultTimeAlive;
    private int arenaSize;
    private int wedgeOrbRadius;

    public NoteFactory(float defaultTimeAlive, int areaSize, int wedgeOrbRadius){
        this.defaultTimeAlive = defaultTimeAlive;
        this.arenaSize = areaSize;
        this.wedgeOrbRadius = wedgeOrbRadius;
    }

    // Methods for custom speed notes is provided, are only used when speed is specified in bullet map,
    // otherwise default speed, which is also found in the bullet map, is used.

    public ShortNote newShortNote(int state, float timeAlive){
        return new ShortNote(state, timeAlive, this.arenaSize, this.wedgeOrbRadius);
    }
    public ShortNote newShortNote(int state){
        return newShortNote(state, this.defaultTimeAlive);
    }

    public LongNote newLongNote(int state, float timeActive, float timeAlive){
        return new LongNote(state, timeActive, timeAlive, this.arenaSize, this.wedgeOrbRadius);
    }
    public LongNote newLongNote(int state, float timeActive){
        return newLongNote(state, timeActive, this.defaultTimeAlive);
    }

    public CircleNote newCircleNote(float timeAlive){
        return new CircleNote(timeAlive, this.arenaSize, this.wedgeOrbRadius);
    }

    public CircleNote newCircleNote(){
        return newCircleNote(this.defaultTimeAlive);
    }

    // This getters are used by the time manager to know when "default time" bullets need to spawn in order to
    // reach the centre at their designated time.
    public float getDefaultTimeAlive(){
        return defaultTimeAlive;
    }
}
