package com.itzjacki.afterglow.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.itzjacki.afterglow.screens.PlayScreen;

import java.util.ArrayList;
import java.util.List;

public class Song {
    // Triggers if a necessary config field doesn't exist. Makes the song non-selectable from the menu.
    // TODO: Ensure that there are default values everywhere it makes sense
    // TODO: Make sure song is displayed as erroneous and can't be played
    private boolean hasConfigError = false;

    // Will probably often be the same in practice, but this allows for different values if needed.
    private String directoryName;
    private String name;

    private PlayScreen screen;

    // A song's individual base volume. To help adjust song files which are too loud or quiet.
    // Is multiplied with user's volume choice. 0.5 is default value.
    // (for now at least. (if you read this in the future (after 2020), that probably stayed as the default value)).
    private float baseVolume;

    // The time it takes for a bullet to spawn until it hits the middle in milliseconds.
    // Lower time alive means higher speed. A good default value is 600 ms. Must be integer.
    private float defaultTimeAlive;

    private List<int[]> noteList;

    private Color playerWedgeColor;
    private Color playerCircleColor;
    private Color backgroundColor;
    private Color textColor;
    private Color noteColor;
    private Color frameColor;

    public Song(String directoryName) {
        this.directoryName = directoryName;

        // Setting default colors, in case something goes wrong with colors loaded from song file
        setDefaultColors();

        // Gets config text, splits by category, and applies settings accordingly
        String rawString = readInfoFile();
        String[] basicTrim = splitAndTrim(rawString, "}");
        for (String s : basicTrim) {
            setBaseInfo(s);
            setDefaultTheme(s);
            setBulletMap(s);
        }
        //TODO: Check that all setup methods were successfully run, else flag with hasConfigError
    }

    private String readInfoFile() {
        FileHandle infoFile = Gdx.files.local(getFileDirectory() + "song_map.txt");
        return infoFile.readString();
    }

    // Helper method that splits a String on a character, then trims all items in the resulting array
    private String[] splitAndTrim(String input, String splitDivider) {
        String[] splitString = input.split(splitDivider);
        String[] result = splitString.clone();
        for (int i = 0; i < splitString.length; i++) {
            result[i] = splitString[i].trim();
        }
        return result;
    }

    // Reads the value of a color and returns it as a Color object
    private Color colorFromConfigString(String[] configArray, String configKey){
        return new Color(Color.valueOf(getNextAfterMatch(configArray, configKey)));
    }

    // Sets basic info of song on creation
    private void setBaseInfo(String inputString) {
        final String categoryName = "baseInfo{";
        if (inputString.startsWith(categoryName)) {
            String trimmedString = inputString.substring(categoryName.length());
            String[] configArray = splitAndTrim(trimmedString, "'");
            this.name = getNextAfterMatch(configArray, "name=");
            this.baseVolume = Float.parseFloat(getNextAfterMatch(configArray, "baseVolume="));
            this.defaultTimeAlive = Integer.parseInt(getNextAfterMatch(configArray, "defaultTimeAlive="));
        }
    }

    // Used as backup if song colors aren't loaded for whatever reason
    private void setDefaultColors(){
        playerWedgeColor = Color.BLACK;
        playerCircleColor = Color.WHITE;
        backgroundColor = Color.GRAY;
        textColor = Color.BLACK;
        noteColor = Color.BLACK;
        frameColor = Color.GRAY;
    }

    // Sets visual theme info of song on creation (does not handle visual events, only used on creation of Song)
    private void setDefaultTheme(String inputString) {
        final String categoryName = "defaultTheme{";
        if (inputString.startsWith(categoryName)) {
            String trimmedString = inputString.substring(categoryName.length());
            String[] configArray = splitAndTrim(trimmedString, "'");
            // Colors are given in RGBA hex format.
            playerWedgeColor = colorFromConfigString(configArray, "playerWedge=");
            playerCircleColor = colorFromConfigString(configArray, "playerCircle=");
            backgroundColor = colorFromConfigString(configArray, "background=");
            textColor = colorFromConfigString(configArray, "textColor=");
            noteColor = colorFromConfigString(configArray, "noteColor=");
            frameColor = colorFromConfigString(configArray, "frameColor=");
        }
    }

    // Once colors have been loaded from song file this method applies colors to the screen
    public void applyColorToScreen() {
        if (screen != null) {
            this.screen.setPlayerWedgeColor(playerWedgeColor);
            this.screen.setPlayerCircleColor(playerCircleColor);
            this.screen.setBackgroundColor(backgroundColor);
            this.screen.setTextColor(textColor);
            this.screen.setNoteColor(noteColor);
            this.screen.setFrameColor(frameColor);
        }
    }

    // Sets visual theme info of song on creation (does not handle visual events, only used on creation of Song)
    private void setBulletMap(String inputString) {
        final String categoryName = "bullets{";
        if (inputString.startsWith(categoryName)) {
            this.noteList = new ArrayList<>();

            String trimmedString = inputString.substring(categoryName.length());
            String[] unprocessedNoteArray = splitAndTrim(trimmedString, "]");

            for (String noteString : unprocessedNoteArray){
                String[] splitNoteString = splitAndTrim(noteString.replace("[", ""), ",");

                int[] parsedNote = new int[splitNoteString.length];
                for (int i = 0; i < splitNoteString.length; i++) {
                    parsedNote[i] = Integer.parseInt(splitNoteString[i]);
                }

                noteList.add(parsedNote);
            }
        }
    }

    // Returns the element that comes next in the list after the one that matches
    private String getNextAfterMatch(String[] configArray, String configKey) {
        boolean returnNext = false;
        for (String s : configArray) {
            if (returnNext) {
                return s;
            }
            if (s.equals(configKey)) {
                returnNext = true;
            }
        }
        hasConfigError = true;
        return "";
    }

    public float getBaseVolume() {
        return baseVolume;
    }

    public String getFileDirectory() {
        return "songs/" + directoryName + "/";
    }

    public FileHandle getMusicFile() {
        return Gdx.files.internal(getFileDirectory() + "music.mp3");
    }

    public float getDefaultTimeAlive() {
        return defaultTimeAlive;
    }

    public List<int[]> getNoteList() {
        return noteList;
    }

    // Must be run before song starts initializing from txt file to enable proper color effects
    public void setScreen(PlayScreen screen) {
        this.screen = screen;
    }
}
