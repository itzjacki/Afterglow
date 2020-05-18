package com.itzjacki.afterglow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itzjacki.afterglow.AfterglowGame;
import com.itzjacki.afterglow.controllers.EventManager;
import com.itzjacki.afterglow.controllers.MusicPlayer;
import com.itzjacki.afterglow.models.*;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {
    // Song being played
    private Song song;

    // Music player for the song being played
    private MusicPlayer musicPlayer;

    // Camera and view
    private int playWorldSize;
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private Stage playStage;

    // HUD and shape drawing
    private ShapeRenderer shape;
    private PlayerWedge wedge;
    private PlayHUD hud;
    private int frameWidth = 3;

    private boolean[] longNoteHold;
    // Gameplay elements
    private List<ShortNote> shortNoteList;
    private List<CircleNote> circleNoteList;
    private List<LongNote> longNoteList;
    private int score;
    private int combo;
    private int highestCombo;
    // Buffers are used with long notes to save leftover points from using integer scores and combos.
    private float comboBuffer;
    private float scoreBuffer;
    private float health;
    // scoreModifier is decided by modifiers the player has activated.
    private float scoreModifier;

    // Colors used during the song. The same color value is often used for multiple of these at a time.
    private Color playerWedgeColor;
    private Color playerCircleColor;
    private Color backgroundColor;
    private Color textColor;
    private Color bulletColor;
    private Color frameColor;


    public PlayScreen(Song song) {
        this.song = song;

        this.musicPlayer = new MusicPlayer(song);

        // Colors are given in RGBA in hex format
        // These should all be loaded in automatically from the song file.
        playerWedgeColor = new Color(Color.valueOf("211d14ff"));
        playerCircleColor = new Color(Color.valueOf("f7f6edff"));
        backgroundColor = new Color(Color.valueOf("b5b49eff"));
        textColor =  new Color(Color.valueOf("211d14ff"));
        bulletColor =  new Color(Color.valueOf("211d14ff"));
        frameColor =  new Color(Color.valueOf("f7f6edff"));

        playWorldSize = AfterglowGame.ACTIVE_PLAY_SIZE;
        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(AfterglowGame.ACTIVE_PLAY_SIZE, AfterglowGame.ACTIVE_PLAY_SIZE, gameCamera);
        playStage = new Stage(gameViewport);

        shape = new ShapeRenderer();
        wedge = new PlayerWedge();
        hud = new PlayHUD(textColor);

        // Even though there are only 4 long note directions, this holds all 8. Both for forward expandability
        // and current code simplicity.
        longNoteHold = new boolean[8];
        shortNoteList = new ArrayList<>();
        circleNoteList = new ArrayList<>();
        longNoteList = new ArrayList<>();

        health = 100f;
        score = 0;
        combo = 0;
        highestCombo = 0;
        comboBuffer = 0;
        scoreBuffer = 0;
        scoreModifier = EventManager.getInstance().getScoreModifier();
    }

    // Runs before rendering happens every frame. Checks for keyboard inputs.

    private void handleInput(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            System.out.println("Escape");
            //TODO: Pause game and bring up menu
            EventManager.getInstance().endSongInstance(false, score, highestCombo); // To quickly end song for testing. Remove when menu is in place.
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            System.out.println("space");
            wedge.setState(8);
            circleNoteList.add(new CircleNote());
        }

        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.W)){
            wedge.setState(1);
            shortNoteList.add(new ShortNote(1));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.S)){
            wedge.setState(3);
            shortNoteList.add(new ShortNote(3));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.S)){
            wedge.setState(5);
            shortNoteList.add(new ShortNote(5));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.W)){
            wedge.setState(7);
            shortNoteList.add(new ShortNote(7));
        }

        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            wedge.setState(0);
            longNoteList.add(new LongNote(0, 1000));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            wedge.setState(2);
            shortNoteList.add(new ShortNote(2));
//            longNoteList.add(new LongNote(2, 200));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            wedge.setState(4);
//            bulletList.add(new Bullet(4));
            longNoteList.add(new LongNote(4, 2000));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            wedge.setState(6);
            shortNoteList.add(new ShortNote(6));
//            longNoteList.add(new LongNote(6, 50));
        }
    }
    private void changeHealth(float healthChange){
        float newHealth = health + healthChange;
        // Makes sure player doesn't go over 100 health
        if(newHealth > 100){
            health = 100;
        }
        // If the player dies
        else if(newHealth <= 0){
            health = 0;
            // Ends song
            EventManager.getInstance().endSongInstance(false, score, highestCombo);
        }
        else{
            health = newHealth;
        }
        hud.updateHealth(health);
    }

    private void increaseCombo(){
        increaseCombo(1);
    }

    private void increaseCombo(int increaseBy){
        combo += increaseBy;
        if(combo > highestCombo){
            highestCombo = combo;
        }
        hud.updateCombo(combo);
    }

    private void resetCombo(){
        combo = 0;
        hud.updateCombo(combo);
    }

    // Increases score. baseIncrease is the increase in score before combo is applied.

    private void increaseScore(int baseIncrease){
        int comboModifier = combo;
        if(combo == 0){
            comboModifier = 1;
        }
        score += baseIncrease * comboModifier;
        hud.updateScore(score);
    }

    // Methods for successful and unsuccessful catches for all projectiles
    // TODO: Remove debug print statements.
    // TODO: Balancing
    // When the player successfully catches a bullet

    private void successfulBulletCatch(){
        System.out.println("Bullet caught!");

        changeHealth(1);
        increaseCombo();
        // Score modifier has a maximum of one decimal, so this should never produce non-integers. If it somehow does,
        // any decimals are curtailed when it's casted to an int anyway, at worst leading to a tiny loss of score.
        increaseScore((int) (100 * scoreModifier));
    }
    // When the player is hit by a bullet they didn't catch.

    private void unsuccessfulBulletCatch(){
        System.out.println("Ouch! Bullet not caught");

        changeHealth(-10);
        resetCombo();
    }
    // When the player successfully catches a circle note

    private void successfulCircleCatch(){
        System.out.println("Circle caught!");
        changeHealth(2);
        increaseCombo();
        // Score modifier system works the same way as it does with the normal bullets
        increaseScore((int) (500 * scoreModifier));
    }
    // When the player is hit by a circle note they didn't catch.

    private void unsuccessfulCircleCatch(){

        System.out.println("Ouch! Circle not caught");
        changeHealth(-10);
        resetCombo();
    }
    // When the player successfully catches a long note. Called each frame they're being hit by one.
    // Delta time is needed in the long note catches, so the effect of them is independent of frame rate.

    private void successfulLongCatch(float dt){
        System.out.println("Long note caught!");
        // Values that dictate how much the player should gain after one second of successfully catching a long note.
        float comboPerSecond = 5;
        float healthGainPerSecond = 5;
        float scorePerSecond = 1000 * scoreModifier;

        // Moves excess combo into buffer (everything after the decimal point)
        comboBuffer += comboPerSecond * dt;
        // If there's a combo ready in the buffer, add this to the new combo counter.
        increaseCombo((int) comboBuffer);
        // remove the extra combo that has been moved into the buffer from the new combo score
        comboBuffer -= Math.floor(comboBuffer);

        // Performs the same process for score.
        scoreBuffer += scorePerSecond * dt;
        increaseScore((int) scoreBuffer);
        scoreBuffer -= Math.floor(scoreBuffer);

        changeHealth(healthGainPerSecond * dt);
    }
    // When the player is hit by a long note they didn't catch. Called each frame they're being hit.

    private void unsuccessfulLongCatch(float dt){
        System.out.println("Ouch! Long note not caught");
        float healthLossPerSecond = 30;

        resetCombo();
        changeHealth(-healthLossPerSecond * dt);
    }

    public List<ShortNote> getShortNoteList() {
        return shortNoteList;
    }

    public List<CircleNote> getCircleNoteList() {
        return circleNoteList;
    }

    public List<LongNote> getLongNoteList() {
        return longNoteList;
    }

    public Color getPlayerWedgeColor() {
        return playerWedgeColor;
    }

    public Color getPlayerCircleColor() {
        return playerCircleColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getBulletColor() {
        return bulletColor;
    }

    public Color getFrameColor() {
        return frameColor;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(playStage);
    }

    // Main render loop, runs every frame.
    @Override
    public void render(float delta) {
        handleInput();

        // Checks and deletes bullets
        if(!shortNoteList.isEmpty()) {
            ArrayList<Integer> shortNoteDeletionList = new ArrayList<>();

            for (int i = 0; i < shortNoteList.size(); i++) {

                if (shortNoteList.get(i).update(delta)) {
                    // Checks if wedge and bullet state matches
                    if (shortNoteList.get(i).getState() == wedge.getState()) {
                        successfulBulletCatch();
                    } else {
                        unsuccessfulBulletCatch();
                    }

                    // Adds bullet to deletion list.
                    shortNoteDeletionList.add(i);
                }
            }

            // Purges bullets that are on the deletion list.
            for (Integer i : shortNoteDeletionList) {
                shortNoteList.remove((int) i);
            }
        }

        // Checks and deletes circle notes
        if(!circleNoteList.isEmpty()) {
            ArrayList<Integer> circleDeletionList = new ArrayList<>();

            for (int i = 0; i < circleNoteList.size(); i++) {

                if (circleNoteList.get(i).update(delta)) {
                    // Checks if wedge state is circle
                    if (wedge.getState() == circleNoteList.get(i).getState()) {
                        successfulCircleCatch();
                    } else {
                        unsuccessfulCircleCatch();
                    }

                    // Adds circle note to deletion list.
                    circleDeletionList.add(i);
                }
            }

            // Purges bullets that are on the deletion list.
            for (Integer i : circleDeletionList) {
                circleNoteList.remove((int) i);
            }
        }

        // this variable is set to all false every frame, but if the player is holding a long note, its set to true
        // on the applicable side. After dealing with long notes, longNoteHold is set to this list.
        boolean[] keepHoldingLongNote = new boolean[8];
        // Checks and deletes long notes
        if(!longNoteList.isEmpty()) {
            ArrayList<Integer> longDeletionList = new ArrayList<>();

            for (int i = 0; i < longNoteList.size(); i++) {

                boolean markedToDelete = longNoteList.get(i).update(delta);

                if (longNoteList.get(i).isActive()) {
                    int noteState = longNoteList.get(i).getState();
                    // Checks if wedge state is circle
                    if (noteState == wedge.getState()) {
                        successfulLongCatch(delta);
                        longNoteHold[noteState] = true;
                        keepHoldingLongNote[noteState] = true;
                    }
                    else if(longNoteHold[noteState] && EventManager.getInstance().isArrowPressed(noteState)){
                        successfulLongCatch(delta);
                        keepHoldingLongNote[noteState] = true;
                    }
                    else {
                        unsuccessfulLongCatch(delta);
                        longNoteHold[longNoteList.get(i).getState()] = false;
                    }
                }

                if(markedToDelete){
                    // Adds circle note to deletion list.
                    longDeletionList.add(i);
                }
            }

            // Purges bullets that are on the deletion list.
            for (Integer i : longDeletionList) {
                longNoteList.remove((int) i);
            }
        }
        longNoteHold = keepHoldingLongNote;

        // Actual graphics rendering starts here.
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Initial shape drawing stuff
        playStage.getViewport().apply();
        shape.setProjectionMatrix(gameCamera.combined);
        // Filled shapes go here (most shapes)
        shape.begin(ShapeRenderer.ShapeType.Filled);

        // Draws the bullets
        shape.setColor(bulletColor);
        for(ShortNote shortNote : shortNoteList){
            shortNote.draw(shape);
        }
        for(LongNote longNote:longNoteList){
            longNote.draw(shape);
        }

        // Draws the player's circle and wedge
        wedge.drawCircle(shape, playerCircleColor, playerWedgeColor, longNoteHold, playWorldSize);
        wedge.drawWedge(shape, playerWedgeColor, playerCircleColor, playWorldSize);

        // Draws the frame
        shape.setColor(frameColor);
        shape.rect(0, 0, frameWidth, playWorldSize);
        shape.rect(playWorldSize - frameWidth, 0, frameWidth, playWorldSize);

        // Flushes shapes
        shape.end();


        // Line shapes go here (Like the circle note)
        shape.begin(ShapeRenderer.ShapeType.Line);

        shape.setColor(bulletColor);
        for(CircleNote circleNote:circleNoteList){
            circleNote.draw(shape);
        }

        // Flushes shapes
        shape.end();

        // Draws HUD on top of everything
        hud.getStage().getViewport().apply();
        hud.getStage().draw();

        // Drawing the HUD last also ensures that a "full" resolution viewport is always the one that is applied when
        // the game moves to a different screen. Other screens don't constantly apply their viewports.
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        musicPlayer.dispose();
    }
}
