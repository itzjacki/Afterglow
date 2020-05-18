package com.itzjacki.afterglow.controllers;

import com.itzjacki.afterglow.models.Song;
import com.itzjacki.afterglow.screens.PlayScreen;

// Is used by the play screen to keep track of time, bullet patterns and visual patterns.
public class TimeManager {
    private float time;
    private PlayScreen playScreen;

    // Needs a reference to the song to get bullet and visual maps.
    private Song song;

    public TimeManager(Song song, PlayScreen playScreen){
        this.song = song;
        this.time = 0;
    }

    public void update(float delta){
        time += delta;
    }

    private void generateBullets(){

    }

    public float getTime(){
        return time;
    }
}
