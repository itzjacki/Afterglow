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
import com.itzjacki.afterglow.models.*;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {
    // Song being played
    private Song song;

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

    // Gameplay elements
    private List<Bullet> bulletList;
    private List<CircleNote> circleNoteList;
    private List<LongNote> longNoteList;
    private int score;
    private int combo;
    private float health;


    // Colors used during the song. The same color value is often used for multiple of these at a time.
    private Color playerWedgeColor;
    private Color playerCircleColor;
    private Color backgroundColor;
    private Color textColor;
    private Color bulletColor;
    private Color frameColor;


    public PlayScreen(Song song) {
        this.song = song;

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

        bulletList = new ArrayList<>();
        circleNoteList = new ArrayList<>();
    }

    // Runs before rendering happens every frame. Checks for keyboard inputs.
    private void handleInput(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            System.out.println("Escape");
            //TODO: Pause game and bring up menu
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            System.out.println("space");
            wedge.setState(8);
            circleNoteList.add(new CircleNote());
        }

        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.W)){
            wedge.setState(1);
            bulletList.add(new Bullet(1));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.S)){
            wedge.setState(3);
            bulletList.add(new Bullet(3));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.S)){
            wedge.setState(5);
            bulletList.add(new Bullet(5));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.W)){
            wedge.setState(7);
            bulletList.add(new Bullet(7));
        }

        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            wedge.setState(0);
            bulletList.add(new Bullet(0));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            wedge.setState(2);
            bulletList.add(new Bullet(2));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            wedge.setState(4);
            bulletList.add(new Bullet(4));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            wedge.setState(6);
            bulletList.add(new Bullet(6));
        }
    }

    // When the player successfully catches a bullet
    private void successfulBulletCatch(){
        System.out.println("Bullet caught!");
    }

    // When the player is hit by a bullet they didn't catch.
    private void unsuccessfulBulletCatch(){
        System.out.println("Ouch! Bullet not caught");
    }

    // When the player successfully catches a bullet
    private void successfulCircleCatch(){
        System.out.println("Circle caught!");
    }

    // When the player is hit by a bullet they didn't catch.
    private void unsuccessfulCircleCatch(){
        System.out.println("Ouch! Circle not caught");
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
        if(!bulletList.isEmpty()) {
            ArrayList<Integer> bulletDeletionList = new ArrayList<>();
            for (int i = 0; i < bulletList.size(); i++) {
                if (bulletList.get(i).update(delta)) {
                    // Checks if wedge and bullet state matches
                    if (bulletList.get(i).getState() == wedge.getState()) {
                        successfulBulletCatch();
                    } else {
                        unsuccessfulBulletCatch();
                    }
                    // Adds bullet to deletion list.
                    bulletDeletionList.add(i);
                }
            }

            // Purges bullets that are on the deletion list.
            for (Integer i : bulletDeletionList) {
                bulletList.remove((int) i);
            }
        }

        // Checks and deletes circle notes
        if(!circleNoteList.isEmpty()) {
            ArrayList<Integer> circleDeletionList = new ArrayList<>();
            for (int i = 0; i < circleNoteList.size(); i++) {
                if (circleNoteList.get(i).update(delta)) {
                    // Checks if wedge state is circle
                    if (wedge.getState() == 8) {
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



        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draws HUD
        hud.getStage().getViewport().apply();
        hud.getStage().draw();

        // Initial shape drawing stuff
        playStage.getViewport().apply();
        shape.setProjectionMatrix(gameCamera.combined);
        // Filled shapes go here (most shapes)
        shape.begin(ShapeRenderer.ShapeType.Filled);

        // Draws the bullets
        shape.setColor(bulletColor);
        for(Bullet bullet:bulletList){
            bullet.draw(shape);
        }

        // Draws the player's circle and wedge
        wedge.drawCircle(shape, playerCircleColor, playWorldSize);
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

    }
}
