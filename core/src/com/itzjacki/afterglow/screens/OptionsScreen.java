package com.itzjacki.afterglow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
    private final Label nicknameLabel;
    private final SelectBox<String> resolutionBox;

    public OptionsScreen(){
        skin = new Skin(Gdx.files.internal("neon_skin/neon-ui.json"));
        batch = new SpriteBatch(); //TODO: Change this to a single game-wide sprite batch
        menuCamera = new OrthographicCamera();
        menuViewport = new FitViewport(EventManager.getInstance().getScreenWidth(), EventManager.getInstance().getScreenHeight(), menuCamera);
        stage = new Stage(menuViewport);

        Gdx.input.setInputProcessor(stage);

        menuTable = new Table(); // Table to hold menu items
        menuTable.setFillParent(true); // Sets the table to fill the entire window


        // All menu elements are created here
        final Label blankRow = new Label("", skin);

        final Label resolutionLabel = new Label("Resolution:", skin);
        resolutionBox = new SelectBox<String>(skin);
        resolutionBox.setItems(AfterglowGame.resolutions);
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

        resolutionBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                EventManager.getInstance().selectResolution(resolutionBox.getSelected());
            }
        });

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
                EventManager.getInstance().saveAndApplyPreferences();
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

    // Updates all UI elements to match preferences after pulling data from preferences
    public void update(){
        resolutionBox.setSelected("" + EventManager.getInstance().getScreenWidth() + "x" + EventManager.getInstance().getScreenHeight());
        System.out.println("Set selected resolution to " + EventManager.getInstance().getScreenWidth() + "x" + EventManager.getInstance().getScreenHeight());
        nicknameLabel.setText(makeNicknameLabelText());
    }

    @Override
    public void show() {
        EventManager.getInstance().loadPreferencesFromFile();
        update();
        Gdx.input.setInputProcessor(stage);
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
