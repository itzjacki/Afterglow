package com.itzjacki.afterglow.controllers;

import com.itzjacki.afterglow.models.Song;
import com.itzjacki.afterglow.screens.PlayScreen;

import java.util.ArrayList;
import java.util.List;

// Is used by the play screen to keep track of time, bullet patterns and visual patterns.
public class TimeManager {
    // time is the time elapsed since song start in seconds(!).
    private float time;
    private PlayScreen playScreen;
    private NoteFactory factory;
    // Houses a copy of the list in the Song object, which can be edited safely as we progress through it.
    private List<int[]> noteList;

    // Needs a reference to the song to get bullet and visual maps.
    private Song song;

    public TimeManager(Song song, PlayScreen playScreen){
        this.song = song;
        this.time = 0;
        this.factory = playScreen.getNoteFactory();
        this.playScreen = playScreen;
        this.noteList = new ArrayList<>(song.getNoteList());
    }

    public void update(float delta){
        time += delta;
        generateNotes();
    }

    // Note format: [time, type, state, timeActive, timeAlive]
    private void generateNotes(){
        // if the note list is empty, do nothing
        if(noteList.isEmpty()){
            return;
        }

        boolean defaultTimeAlive = noteList.get(0)[4] <= 0;
        int timeToCenter;
        if(defaultTimeAlive) {
            timeToCenter = (int) factory.getDefaultTimeAlive();
        }
        else{
            timeToCenter = noteList.get(0)[4];
        }

        // Will keep fetching new notes until there are no more that are overdue at the current time stamp.
        // Notes are spawned a certain time "timeAlive" before they're due to hit the centre, which is given by "time".
        while(!noteList.isEmpty() && noteList.get(0)[0] <= time * 1000){
            System.out.println("TIME COMPARISON: " + (noteList.get(0)[0] - noteList.get(0)[4]) + " must be bigger than " + (time * 1000));
            int[] note = noteList.remove(0);
            int type = note[1];
            int state = note[2];
            int timeActive = note[3];
            int timeAlive = note[4];

            switch(type){
                // Short note
                case 1:
                    // If timeAlive should be default value
                    if(defaultTimeAlive){
                        playScreen.addShortNote(factory.newShortNote(state));
                    }
                    // If timeAlive is a specific value
                    else{
                        playScreen.addShortNote(factory.newShortNote(state, timeAlive));
                    }
                    break;

                // Long note
                case 2:
                    if(defaultTimeAlive){
                        playScreen.addLongNote(factory.newLongNote(state, timeActive));
                    }
                    else{
                        playScreen.addLongNote(factory.newLongNote(state, timeActive, timeAlive));
                    }
                    break;

                // Circle note
                case 3:
                    if(defaultTimeAlive){
                        playScreen.addCircleNote(factory.newCircleNote());
                    }
                    else{
                        playScreen.addCircleNote(factory.newCircleNote(timeAlive));
                    }
                    break;

                // Unrecognized note type
                default:
                    throw new IllegalArgumentException("Tried to create note of type " + type + ", which does not exist.");
            }
        }
    }

    public float getTime(){
        return time;
    }
}
