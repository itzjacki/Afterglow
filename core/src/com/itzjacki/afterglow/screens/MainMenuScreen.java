package com.itzjacki.afterglow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itzjacki.afterglow.AfterglowGame;
import com.itzjacki.afterglow.controllers.EventManager;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private Table menuTable;
    private Skin skin;
    private SpriteBatch batch;
    private Camera menuCamera;
    private Viewport menuViewport;

    public MainMenuScreen(){
        skin = new Skin(Gdx.files.internal("neon_skin/neon-ui.json"));
        batch = new SpriteBatch(); //TODO: Change this to a single game-wide sprite batch
        menuCamera = new OrthographicCamera();
        menuViewport = new FitViewport(EventManager.getInstance().getScreenWidth(), EventManager.getInstance().getScreenHeight());
        stage = new Stage(menuViewport);



        menuTable = new Table(); // Table to hold menu items
        menuTable.setFillParent(true); // Sets the table to fill the entire window

        final Label titleLabel = new Label("Afterglow", skin); //TODO: Change to game logo
        final TextButton playButton = new TextButton("Play", skin, "default");
        final TextButton optionsButton = new TextButton("Options", skin, "default");
        final TextButton highScoresButton = new TextButton("High scores", skin, "default");
        final TextButton exitButton = new TextButton("Exit", skin, "default");


        menuTable.add(titleLabel).row();
        menuTable.add(playButton).row();
        menuTable.add(optionsButton).row();
        menuTable.add(highScoresButton).row();
        menuTable.add(exitButton);

        stage.addActor(menuTable);

        // Adds listeners to the UI
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: This button should lead to song list. Currently starts a song instance for development purposes.
                EventManager.getInstance().createSongInstance(AfterglowGame.songs.get(0));
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EventManager.getInstance().changeScreen("Options");
            }
        });

        highScoresButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EventManager.getInstance().changeScreen("HighScores");
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        menuViewport.apply();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setTransformMatrix(menuCamera.view);
        batch.setProjectionMatrix(menuCamera.projection);

        batch.begin();
        stage.act();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        menuViewport.update(width, height);
        menuCamera.update();
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
