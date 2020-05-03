package com.itzjacki.afterglow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itzjacki.afterglow.AfterglowGame;
import com.itzjacki.afterglow.controllers.EventManager;
import com.itzjacki.afterglow.models.PlayerWedge;

public class PlayScreen implements Screen {
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private ShapeRenderer shape;
    private Stage playStage;
    private PlayerWedge wedge;

    public PlayScreen() {
        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(EventManager.getInstance().getScreenWidth(), EventManager.getInstance().getScreenHeight(), gameCamera);
//        gameViewport = new FitViewport(AfterglowGame.ACTIVE_PLAY_WIDTH, AfterglowGame.ACTIVE_PLAY_HEIGHT, gameCamera);
        shape = new ShapeRenderer();
        playStage = new Stage(gameViewport);
        wedge = new PlayerWedge();
    }

    private void handleInput(){
        //TODO: Handle key presses here. Called every time screen is about to render
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(playStage);
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shape.setProjectionMatrix(gameCamera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.WHITE);
        shape.ellipse((gameViewport.getWorldWidth()-wedge.getRadius())/2f, (gameViewport.getWorldHeight()-wedge.getRadius())/2f, wedge.getRadius(), wedge.getRadius());
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
