package com.itzjacki.afterglow.controllers;

import com.itzjacki.afterglow.models.Song;
import com.itzjacki.afterglow.screens.PlayScreen;

// When a song is selected to be played, an InstanceManager is created for that play instance.
// The Instance manager controls things such as the song playback, the bullet spawning, score keeping, gameplay HUD,
// background, visual effects and PlayScreen. Effectively everything that is needed to play a song.
public class SongInstanceManager {
    private Song song;
    private PlayScreen screen;

    protected SongInstanceManager(Song song){
        this.screen = new PlayScreen();
        this.song = song;
    }

    public PlayScreen getScreen(){
        return screen;
    }
}
