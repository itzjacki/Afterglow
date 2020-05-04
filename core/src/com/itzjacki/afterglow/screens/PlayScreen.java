package com.itzjacki.afterglow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itzjacki.afterglow.AfterglowGame;
import com.itzjacki.afterglow.controllers.EventManager;
import com.itzjacki.afterglow.models.PlayHUD;
import com.itzjacki.afterglow.models.PlayerWedge;

public class PlayScreen implements Screen {
    private int playWorldSize;
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private ShapeRenderer shape;
    private Stage playStage;
    private PlayerWedge wedge;
    private PlayHUD hud;
    private int frameWidth = 3;

    private Color wedgeColor;
    private Color circleColor;
    private Color backgroundColor;
    private Color textColor;
    private Color bulletColor;
    private Color frameColor;

    public PlayScreen() {
        // Colors are given in RGBA in hex format
        wedgeColor = new Color(Color.valueOf("211d14ff"));
        circleColor = new Color(Color.valueOf("f7f6edff"));
        backgroundColor = new Color(Color.valueOf("b5b49eff"));
        textColor =  new Color(Color.valueOf("211d14ff"));
        frameColor =  new Color(Color.valueOf("f7f6edff"));

        playWorldSize = AfterglowGame.ACTIVE_PLAY_SIZE;

        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(AfterglowGame.ACTIVE_PLAY_SIZE, AfterglowGame.ACTIVE_PLAY_SIZE, gameCamera);

        shape = new ShapeRenderer();
        playStage = new Stage(gameViewport);

        wedge = new PlayerWedge();

        hud = new PlayHUD(shape, textColor);
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

        }

        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.W)){
            System.out.println("up right");
            wedge.setState(1);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.S)){
            System.out.println("down right");
            wedge.setState(3);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.S)){
            System.out.println("down left");
            wedge.setState(5);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.W)){
            System.out.println("up left");
            wedge.setState(7);
        }

        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            System.out.println("up");
            wedge.setState(0);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            System.out.println("right");
            wedge.setState(2);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            System.out.println("down");
            wedge.setState(4);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            System.out.println("left");
            wedge.setState(6);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(playStage);
    }

    @Override
    public void render(float delta) {
        handleInput();
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draws HUD
        hud.getStage().getViewport().apply();
        hud.getStage().draw();

        // Initial shape drawing stuff
        playStage.getViewport().apply();
        shape.setProjectionMatrix(gameCamera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        // Draws the bullets
        // TODO: Bullets

        // Draws the player's circle and wedge
        wedge.drawCircle(shape, circleColor, playWorldSize);
        wedge.drawWedge(shape, wedgeColor, circleColor, playWorldSize);

        // Draws the frame
        shape.setColor(frameColor);
        shape.rect(0, 0, frameWidth, playWorldSize);
        shape.rect(playWorldSize - frameWidth, 0, frameWidth, playWorldSize);

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
