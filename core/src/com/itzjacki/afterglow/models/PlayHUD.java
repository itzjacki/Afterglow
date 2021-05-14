package com.itzjacki.afterglow.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itzjacki.afterglow.controllers.EventManager;

public class PlayHUD {
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;

    private final Label healthLabel;
    private final Label scoreLabel;
    private final Label comboLabel;

    public PlayHUD(Color textColor){
        skin = new Skin(Gdx.files.internal("flat_skin/skin/skin.json"));
        Label.LabelStyle hudLabelStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("fonts/AfterglowHUD/AfterglowHUD.fnt"), Gdx.files.internal("fonts/AfterglowHUD/AfterglowHUD.png"), false), textColor);

        batch = new SpriteBatch(); //TODO: Change this to a single game-wide sprite batch
        Camera hudCamera = new OrthographicCamera();
        Viewport hudViewport = new FitViewport(EventManager.getInstance().getScreenWidth(), EventManager.getInstance().getScreenHeight(), hudCamera);
        stage = new Stage(hudViewport);

        Table hudTable = new Table(); // Table to hold menu items
        hudTable.top();
        hudTable.setFillParent(true); // Sets the table to fill the entire window

        Label healthTextLabel = new Label("Health:", hudLabelStyle);
        healthLabel = new Label("100%", hudLabelStyle);
        Label scoreTextLabel = new Label("Score:", hudLabelStyle);
        scoreLabel = new Label("0", hudLabelStyle);
        Label comboTextLabel = new Label("Combo:", hudLabelStyle);
        comboLabel = new Label("0x", hudLabelStyle);

        healthTextLabel.setAlignment(Align.right);
        healthLabel.setAlignment(Align.right);

        final Label emptyLabel = new Label("", skin);

        hudTable.add(healthTextLabel).width(hudViewport.getWorldWidth() / 32f * 7f).padTop(10).padRight(10);
        hudTable.add(emptyLabel).width(hudViewport.getWorldWidth() / 16f * 9f);
        hudTable.add(scoreTextLabel).align(Align.right).width(hudViewport.getWorldWidth() / 32f * 7f).padTop(10).padLeft(10);
        hudTable.row();
        hudTable.add(healthLabel).width(hudViewport.getWorldWidth() / 32f * 7f).padRight(10);
        hudTable.add(emptyLabel).width(hudViewport.getWorldWidth() / 16f * 9f);
        hudTable.add(scoreLabel).width(hudViewport.getWorldWidth() / 32f * 7f).padLeft(10);
        hudTable.row();
        hudTable.add(emptyLabel).width(hudViewport.getWorldWidth() / 32f * 7f).padTop(10);
        hudTable.add(emptyLabel).width(hudViewport.getWorldWidth() / 16f * 9f);
        hudTable.add(comboTextLabel).width(hudViewport.getWorldWidth() / 32f * 7f).padTop(10).padLeft(10);
        hudTable.row();
        hudTable.add(emptyLabel).width(hudViewport.getWorldWidth() / 32f * 7f);
        hudTable.add(emptyLabel).width(hudViewport.getWorldWidth() / 16f * 9f);
        hudTable.add(comboLabel).width(hudViewport.getWorldWidth() / 32f * 7f).padLeft(10);

        stage.addActor(hudTable);
    }

    public void updateHealth(float health){
        healthLabel.setText(String.format("%.1f", health) + "%");
    }

    public void updateScore(int score){
        scoreLabel.setText(score);
    }

    public void updateCombo(int combo){
        comboLabel.setText("" + combo + "x");
    }

    public Stage getStage(){
        return stage;
    }
}
