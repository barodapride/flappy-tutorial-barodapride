package com.barodapride.flappy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Mike on 3/20/2015.
 */
public class GameplayScreen extends ScreenAdapter {

    public static final float PIPE_SPACING = 200f;
    public static final int PIPE_SETS = 3;


    protected OrthographicCamera camera;
    protected FlappyGame game;

    private Stage gameplayStage;
    private Stage uiStage;

    private Label scoreLabel;
    private Label tapToRetry;
    private int score;

    private Bird bird;
    private Array<PipePair> pipePairs;

    private Image background;

    private Ground ground;

    private boolean justTouched;

    private State state = State.PLAYING;

    private enum State {PLAYING, DYING, DEAD}

    ;

    public GameplayScreen(FlappyGame game) {
        this.game = game;

        camera = new OrthographicCamera(FlappyGame.WIDTH, FlappyGame.HEIGHT);
        gameplayStage = new Stage(new StretchViewport(FlappyGame.WIDTH, FlappyGame.HEIGHT, camera));
        uiStage = new Stage(new StretchViewport(FlappyGame.WIDTH, FlappyGame.HEIGHT));

        bird = new Bird();
        bird.setPosition(FlappyGame.WIDTH * .25f, FlappyGame.HEIGHT / 2, Align.center);

        scoreLabel = new Label("0", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        scoreLabel.setPosition(FlappyGame.WIDTH / 2, FlappyGame.HEIGHT * .9f, Align.center);
        uiStage.addActor(scoreLabel);

        tapToRetry = new Label("Tap To Retry!", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        tapToRetry.setPosition(FlappyGame.WIDTH / 2, FlappyGame.HEIGHT * .2f, Align.center);
        uiStage.addActor(tapToRetry);

        pipePairs = new Array<PipePair>();

        initFirstSetOfPipes();
        initSecondSetOfPipes();
        initThirdSetOfPipes();

        background = new Image(Assets.background);
        ground = new Ground();
        ground.setPosition(0, 0);


        // The order actors are added determines the order they are drawn so make sure the background is first
        gameplayStage.addActor(background);
        addPipes(gameplayStage);
        gameplayStage.addActor(ground);
        gameplayStage.addActor(bird);

        // Setup the input processor
        initInputProcessor();
    }

    private void initThirdSetOfPipes() {
        Pipe topPipe = new Pipe();
        Pipe bottomPipe = new Pipe();
        topPipe.getRegion().flip(false, true);
        PipePair pair = new PipePair(topPipe, bottomPipe);
        pair.initThird();

        // add the pair to the list
        pipePairs.add(pair);
    }

    private void initSecondSetOfPipes() {
        Pipe topPipe = new Pipe();
        Pipe bottomPipe = new Pipe();
        topPipe.getRegion().flip(false, true);
        PipePair pair = new PipePair(topPipe, bottomPipe);
        pair.initSecond();

        // add the pair to the list
        pipePairs.add(pair);
    }

    private void initFirstSetOfPipes() {
        Pipe topPipe = new Pipe();
        Pipe bottomPipe = new Pipe();
        topPipe.getRegion().flip(false, true);
        PipePair pair = new PipePair(topPipe, bottomPipe);
        pair.initFirst();

        // add the pair to the list
        pipePairs.add(pair);
    }

    @Override
    public void render(float delta) {
        switch (state) {
            case PLAYING:
                if (justTouched) {
                    bird.jump();
                    justTouched = false;
                }
                updatePipePairs();
                gameplayStage.act();
                uiStage.act();
                checkCollisions();
                if (bird.getState() == Bird.State.dying) {
                    stopTheWorld();
                    tapToRetry.addAction(Actions.moveBy(0, 200, .5f));
                    state = State.DYING;
                }
                gameplayStage.draw();
                uiStage.draw();
                break;

            case DEAD:
            case DYING:
                if (bird.getState() == Bird.State.dead) {
                    state = State.DEAD;
                }
                gameplayStage.act();
                gameplayStage.draw();
                uiStage.act();
                uiStage.draw();
                break;
        }
    }

    private void checkCollisions() {

        for (int i = 0; i < pipePairs.size; i++) {
            PipePair pair = pipePairs.get(i);
            if (pair.getBottomPipe().getBounds().overlaps(bird.getBounds())) {
                stopTheWorld();
            }
            if (pair.getTopPipe().getBounds().overlaps(bird.getBounds())) {
                stopTheWorld();
            }
            if (pair.getCoin().getBounds().overlaps(bird.getBounds())) {
                score++;
                updateScoreLabel();
                pair.moveCoinOffscreen();
            }
        }

    }

    private void updateScoreLabel() {
        scoreLabel.setText(String.valueOf(score));
        // TODO: center the label according to width
    }

    private void stopTheWorld() {
        bird.die();
        killPipePairs();
        stopTheGround();
        state = State.DYING;
    }

    private void stopTheGround() {
        ground.vel.x = 0;
    }

    private void killPipePairs() {
        for (PipePair pair : pipePairs) {
            pair.getBottomPipe().setState(Pipe.State.dead);
            pair.getTopPipe().setState(Pipe.State.dead);
            pair.getCoin().setVel(0, 0);
        }
    }


    private void updatePipePairs() {
        for (int i = 0; i < pipePairs.size; i++) {
            pipePairs.get(i).update();
        }
    }

    @Override
    public void resize(int width, int height) {

        camera.setToOrtho(false, width, height);
        Assets.batch.setProjectionMatrix(camera.combined);
        gameplayStage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);

    }

    @Override
    public void dispose() {
        gameplayStage.dispose();
        uiStage.dispose();
    }

    private void addPipes(Stage gameplayStage) {
        for (int i = 0; i < pipePairs.size; i++) {
            gameplayStage.addActor(pipePairs.get(i).getBottomPipe());
            gameplayStage.addActor(pipePairs.get(i).getTopPipe());
            gameplayStage.addActor(pipePairs.get(i).getCoin());
        }
    }

    /**
     * Tells libgdx to listen for inputs coming from the InputAdapter we give it
     */
    private void initInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            // We only care about the touch down event
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                if (state == State.DYING) {
                    return true;
                }

                if (state == State.DEAD) {
                    game.setScreen(new GameplayScreen(game));
                    return true;
                }

                justTouched = true;
                return true;
            }
        });
    }

    @Override
    public void show() {

        tapToRetry.addAction(Actions.moveBy(0, -200f));

    }
}
