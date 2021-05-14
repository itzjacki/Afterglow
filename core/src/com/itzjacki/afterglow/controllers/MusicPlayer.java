package com.itzjacki.afterglow.controllers;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.itzjacki.afterglow.models.Song;

public class MusicPlayer {
    private Song song;
    private Music music;

    public MusicPlayer(Song song){
        this.song = song;
        music = Gdx.audio.newMusic(song.getMusicFile());
        music.setVolume(EventManager.getInstance().getMusicVolume() * song.getBaseVolume());
        music.play();
    }

    public float getPlaybackTime(){
        return music.getPosition();
    }

    public void dispose(){
        music.dispose();
    }
}
