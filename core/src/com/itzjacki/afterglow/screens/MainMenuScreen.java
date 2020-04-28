package com.itzjacki.afterglow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itzjacki.afterglow.AfterglowGame;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Table table;
    private Skin skin;
    private SpriteBatch batch;
    private Camera menuCamera;
    private Viewport menuViewport;

    public MainMenuScreen(){
        skin = new Skin(Gdx.files.internal("neon_skin/neon-ui.json"));
        stage = new Stage();
        batch = new SpriteBatch();
        menuCamera = new OrthographicCamera();
        menuViewport = new FitViewport(AfterglowGame.V_WIDTH, AfterglowGame.V_HEIGHT, menuCamera);

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true); // Sets the table to fill the entire window
        table.top();

        table.setDebug(true); // for debugging

        final TextButton playButton = new TextButton("Play", skin, "default");
        final TextButton optionsButton = new TextButton("Options", skin, "default");
        final TextButton highScoresButton = new TextButton("High scores", skin, "default");
        final TextButton exitButton = new TextButton("Exit", skin, "default");

        table.add(playButton);
        table.row();
        table.add(optionsButton);
        table.row();
        table.add(highScoresButton);
        table.row();
        table.add(exitButton);

        stage.addActor(table);

//        Label nameLabel = new Label("Name:", skin);
//        TextField nameText = new TextField("", skin);
//        Label addressLabel = new Label("Address:", skin);
//        TextField addressText = new TextField("", skin);
//
//        Table table = new Table();
//        table.add(nameLabel);
//        table.add(nameText).width(100);
//        table.row();
//        table.add(addressLabel);
//        table.add(addressText).width(100);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        menuViewport.update(width, height);
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
