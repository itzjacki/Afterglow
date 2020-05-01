package com.itzjacki.afterglow.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itzjacki.afterglow.AfterglowGame;

public class PlayScreen implements Screen {
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;

    Texture texture;

    public PlayScreen() {
        texture = new Texture("badlogic.jpg");
        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(AfterglowGame.ACTIVE_PLAY_WIDTH, AfterglowGame.ACTIVE_PLAY_HEIGHT, gameCamera);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        game.batch.setProjectionMatrix(gameCamera.combined);
//        game.batch.begin();
//        game.batch.draw(texture, 0, 0);
//        game.batch.end();

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

    }

    @Override
    public void dispose() {

    }
}
