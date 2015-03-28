package com.barodapride.flappy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Mike on 3/20/2015.
 */
public class GameplayScreen extends ScreenAdapter {

    protected OrthographicCamera camera;
    protected FlappyGame game;

    private Stage gameplayStage;

    private Bird bird;
    private Pipe pipe;
    private Image background;
    private Image ground;

    private boolean justTouched;

    public GameplayScreen(FlappyGame game) {
        this.game = game;
        
        camera = new OrthographicCamera(FlappyGame.WIDTH, FlappyGame.HEIGHT);
        gameplayStage = new Stage(new StretchViewport(FlappyGame.WIDTH, FlappyGame.HEIGHT, camera));

        bird = new Bird();
        bird.setPosition(FlappyGame.WIDTH*.25f, FlappyGame.HEIGHT/2, Align.center);

        pipe = new Pipe();
        pipe.setPosition(200, Utils.generateTopYPositionForABottomPipe(), Align.top);

        background = new Image(Assets.background);
        ground = new Image(Assets.ground);

        // The order actors are added determines the order they are drawn so make sure the background is first
        gameplayStage.addActor(background);
        gameplayStage.addActor(ground);
        gameplayStage.addActor(bird);
        gameplayStage.addActor(pipe);

        // Setup the input processor
        initInputProcessor();
    }


    @Override
    public void render(float delta) {

        if (justTouched){
            bird.jump();
            justTouched = false;
        }

        gameplayStage.act();
        gameplayStage.draw();
    }

    @Override
    public void resize(int width, int height) {

        camera.setToOrtho(false, width, height);
        Assets.batch.setProjectionMatrix(camera.combined);
        gameplayStage.getViewport().update(width, height, true);

    }

    @Override
    public void dispose() {
        gameplayStage.dispose();
    }

    /**
     * Tells libgdx to listen for inputs coming from the InputAdapter we give it
     */
    private void initInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            // We only care about the touch down event
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                justTouched = true;
                return false;
            }
        });
    }

}
