package com.itzjacki.afterglow.controllers;

import com.itzjacki.afterglow.models.Song;

// Is used by the play screen to keep track of time, bullet patterns and visual patterns.
public class TimeManager {
    private float time;

    // Needs a reference to the song to get bullet and visual maps.
    private Song song;

    public TimeManager(Song song){
        this.song = song;
        this.time = 0;
    }

    public void update(float delta){
        time += delta;
    }

    public float getTime(){
        return time;
    }
}
