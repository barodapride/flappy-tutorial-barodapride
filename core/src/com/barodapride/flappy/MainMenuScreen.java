package com.barodapride.flappy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenuScreen extends ScreenAdapter {

    private FlappyGame game;
    private Stage stage;

    private Label titleLabel;
    private Button playButton;
    private Image backgroundBuildings;
    private Ground ground;

    public MainMenuScreen(FlappyGame game) {

        this.game = game;
        stage = new Stage(new StretchViewport(FlappyGame.WIDTH, FlappyGame.HEIGHT));

        initPlayButton();

        ground = new Ground();

        titleLabel = new Label("Bird vs. Pipes", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        titleLabel.setPosition(FlappyGame.CENTER_X, FlappyGame.HEIGHT *.75f, Align.center);

        initBackgroundBuildings();

        stage.addActor(ground);
        stage.addActor(backgroundBuildings);
        stage.addActor(titleLabel);
        stage.addActor(playButton);

        Gdx.input.setInputProcessor(stage);
    }

    private void initBackgroundBuildings() {
        backgroundBuildings = new Image(Assets.backgroundBuildings);
        backgroundBuildings.setWidth(FlappyGame.WIDTH);
        backgroundBuildings.setHeight(backgroundBuildings.getHeight() * 2f);
        backgroundBuildings.setY(Ground.HEIGHT);
    }

    private void initPlayButton() {
        playButton = new Button(new TextureRegionDrawable(Assets.playUp), new TextureRegionDrawable(Assets.playDown));
        playButton.setWidth(96);
        playButton.setHeight(48);
        playButton.setPosition(FlappyGame.WIDTH/2, FlappyGame.HEIGHT*.2f, Align.center);
        playButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameplayScreen(game));

            }
        });
    }

    @Override
    public void render(float delta) {

        Gdx.graphics.getGL20().glClearColor(Utils.MAIN_MENU_BACKGROUND_COLOR.r, Utils.MAIN_MENU_BACKGROUND_COLOR.g, Utils.MAIN_MENU_BACKGROUND_COLOR.b, 1f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

}
