package com.itzjacki.afterglow.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itzjacki.afterglow.AfterglowGame;
import com.itzjacki.afterglow.controllers.EventManager;

public class PlayHUD {
    private Stage stage;
    private Table menuTable;
    private Skin skin;
    private SpriteBatch batch;
    private Camera menuCamera;
    private Viewport menuViewport;

    public PlayHUD(){
        skin = new Skin(Gdx.files.internal("neon_skin/neon-ui.json"));
        batch = new SpriteBatch(); //TODO: Change this to a single game-wide sprite batch
        menuCamera = new OrthographicCamera();
        menuViewport = new FitViewport(EventManager.getInstance().getScreenWidth(), EventManager.getInstance().getScreenHeight());
        stage = new Stage(menuViewport);

        //TODO: Bruk denne koden som baseline til å lage HUD :)

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
                //TODO: Change this to open pause menu when escape key is clicked.
                EventManager.getInstance().createSongInstance(AfterglowGame.songs.get(0));
            }
        });
    }
}
