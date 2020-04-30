package com.itzjacki.afterglow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.itzjacki.afterglow.AfterglowGame;
import com.itzjacki.afterglow.controllers.EventManager;
import com.itzjacki.afterglow.controllers.NicknameChangeDialog;

public class OptionsScreen implements Screen {

    private Stage stage;
    private Table menuTable;
    private Skin skin;
    private SpriteBatch batch;
    private Camera menuCamera;
    private Viewport menuViewport;
    private Label nicknameLabel;

    public OptionsScreen(){
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("neon_skin/neon-ui.json")); //TODO: Change to single, shared skin - probably
        batch = new SpriteBatch(); //TODO: Change this to a single game-wide spritebatch
        menuCamera = new OrthographicCamera();
        menuViewport = new FitViewport(AfterglowGame.V_WIDTH, AfterglowGame.V_HEIGHT, menuCamera);

        Gdx.input.setInputProcessor(stage);

        menuTable = new Table(); // Table to hold menu items
        menuTable.setFillParent(true); // Sets the table to fill the entire window


        // All menu elements are created here
        final Label blankRow = new Label("", skin);

        final Label resolutionLabel = new Label("Resolution:", skin);
        final SelectBox<String> resolutionBox = new SelectBox<String>(skin);
        resolutionBox.setItems("1024x576", "1280x720", "1366x768", "1600x900", "1920x1080", "2560x1440");
        final CheckBox fullscreenCheckbox = new CheckBox("Fullscreen", skin);

        final Label audioOptionsLabel = new Label("Audio options", skin);
        final Label audioEffectLabel = new Label("Effect volume:", skin);
        final Slider audioEffectSlider = new Slider(0f, 1f, 0.01f, false, skin);
        final Label musicLabel = new Label("Music volume:", skin);
        final Slider musicSlider = new Slider(0f, 1f, 0.01f, false, skin);

        nicknameLabel = new Label(makeNicknameLabelText(), skin);
        final TextButton nicknameButton = new TextButton("Change nickname", skin, "default");

        final TextButton applyButton = new TextButton("Apply and save", skin, "default");
        final TextButton backButton = new TextButton("Back", skin, "default");


        // Adds all the elements in the right order to the table that holds them
        menuTable.add(resolutionLabel).row();
        menuTable.add(resolutionBox).row();
        menuTable.add(fullscreenCheckbox).row();
        menuTable.add(blankRow).row();
        menuTable.add(blankRow).row();

        menuTable.add(blankRow).row();
        menuTable.add(audioEffectLabel).row();
        menuTable.add(audioEffectSlider).row();
        menuTable.add(musicLabel).row();
        menuTable.add(musicSlider).row();
        menuTable.add(blankRow).row();
        menuTable.add(blankRow).row();

        menuTable.add(nicknameLabel).row();
        menuTable.add(nicknameButton).row();
        menuTable.add(blankRow).row();

        menuTable.add(blankRow).row();
        menuTable.add(applyButton).row();
        menuTable.add(backButton);

        // Adds table to window stage
        stage.addActor(menuTable);

        // Adds listeners to UI
        nicknameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog nickDialog = new NicknameChangeDialog(skin);
                nickDialog.show(stage);
                update();
            }
        });

        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("From variable: " + EventManager.getInstance().getNickname());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EventManager.getInstance().changeScreen("MainMenu");
            }
        });
    }

    // Generates text to display in nickname label with current nickname from player options
    private String makeNicknameLabelText(){
        return "Nickname: " + EventManager.getInstance().getNickname();
    }

    // Updates all UI elements to match preferences
    public void update(){
        nicknameLabel.setText(makeNicknameLabelText());
    }

    @Override
    public void show() {
        update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        stage.act();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
