package com.barodapride.flappy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameplayScreen extends ScreenAdapter {

    public static final float PIPE_SPACING = 200f;
    public static final int PIPE_SETS = 3;

    protected OrthographicCamera camera;
    protected FlappyGame game;

    private Stage gameplayStage;
    private Stage uiStage;

    private Label scoreLabel;
    private Label tapToRetry;
    private Label best;
    private Label tapToFlap;

    private Image whitePixel;
    private Image backgroundBuildings;

    private int score;

    private Bird bird;
    private Array<PipePair> pipePairs;

    private Ground ground;

    private boolean justTouched;

    private Color backgroundColor;

    private State screenState = State.PREGAME;
    private boolean allowRestart = false;

    private enum State {PREGAME, PLAYING, DYING, DEAD}

    ;

    public GameplayScreen(FlappyGame game) {
        this.game = game;

        camera = new OrthographicCamera(FlappyGame.WIDTH, FlappyGame.HEIGHT);
        gameplayStage = new Stage(new StretchViewport(FlappyGame.WIDTH, FlappyGame.HEIGHT, camera));
        uiStage = new Stage(new StretchViewport(FlappyGame.WIDTH, FlappyGame.HEIGHT));

        bird = new Bird();
        bird.setPosition(FlappyGame.WIDTH * .25f, FlappyGame.HEIGHT / 2, Align.center);
        bird.addAction(Utils.getFloatyAction());
        bird.setState(Bird.State.PREGAME);

        whitePixel = new Image(Assets.whitePixel);

        scoreLabel = new Label("0", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        scoreLabel.setPosition(FlappyGame.WIDTH / 2, FlappyGame.HEIGHT * .9f, Align.center);
        uiStage.addActor(scoreLabel);

        tapToRetry = new Label("Tap To Retry!", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        tapToRetry.setPosition(FlappyGame.WIDTH / 2, 0, Align.top);
        uiStage.addActor(tapToRetry);

        best = new Label("Best: ", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        best.setPosition(FlappyGame.WIDTH / 2, 0, Align.top);
        uiStage.addActor(best);

        tapToFlap = new Label("Tap To Flap!", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        tapToFlap.setPosition(FlappyGame.WIDTH / 2, FlappyGame.HEIGHT, Align.bottom);
        uiStage.addActor(tapToFlap);

        initBackgroundBuildings();

        pipePairs = new Array<PipePair>();

        ground = new Ground();
        ground.setPosition(0, 0);

        backgroundColor = Utils.getRandomBackgroundColor();

        // The order actors are added determines the order they are drawn so make sure the background is first
        gameplayStage.addActor(ground);
        gameplayStage.addActor(backgroundBuildings);
        gameplayStage.addActor(bird);

        // Setup the input processor
        initInputProcessor();
    }

    private void initBackgroundBuildings() {
        backgroundBuildings = new Image(Assets.backgroundBuildings);
        backgroundBuildings.setWidth(FlappyGame.WIDTH);
        backgroundBuildings.setHeight(backgroundBuildings.getHeight()*2f);
        backgroundBuildings.setY(Ground.HEIGHT);
    }

    @Override
    public void show() {
        tapToFlap.addAction(Actions.moveToAligned(FlappyGame.CENTER_X, FlappyGame.CENTER_Y + 100f, Align.center, .75f, Interpolation.sine));
        Assets.playWooshSound();
    }

    @Override
    public void render(float delta) {

        Gdx.graphics.getGL20().glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, 1f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        switch (screenState) {
            case PREGAME:
                updateAndDrawStages();
                break;
            case PLAYING:
                renderPlaying();
                break;
            case DYING:
            case DEAD:
                renderDeadOrDying();
                break;
        }
    }

    private void renderDeadOrDying() {
        if (bird.getState() == Bird.State.DEAD) {
            screenState = State.DEAD;
        }
        updateAndDrawStages();
    }

    private void renderPlaying() {
        if (justTouched) {
            bird.jump();
            justTouched = false;
        }
        updatePipePairs();
        gameplayStage.act();
        uiStage.act();
        checkCollisions();
        if (bird.getState() == Bird.State.DYING) {
            stopTheWorld();


            RunnableAction playWooshAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    Assets.playWooshSound();

                }
            });

            SequenceAction actions = Actions.sequence(Actions.delay(1f), playWooshAction, Actions.moveToAligned(FlappyGame.CENTER_X, FlappyGame.CENTER_Y, Align.bottom,
                    .75f, Interpolation.sine), Actions.run(new Runnable() {
                @Override
                public void run() {
                    // Allow the player to restart the game once the tap to retry finishes coming up
                    allowRestart = true;
                }
            }));
            tapToRetry.addAction(actions);

            best.setText("Best: " + SavedDataManager.getInstance().getHighScore());
            best.setWidth(best.getTextBounds().width);
            best.setPosition(FlappyGame.CENTER_X, 0, Align.top);
            best.addAction(Actions.delay(1f, Actions.moveToAligned(FlappyGame.CENTER_X, FlappyGame.CENTER_Y, Align.top,
                    .75f, Interpolation.sine)));

            screenState = State.DYING;
        }
        gameplayStage.draw();
        uiStage.draw();
    }

    private void updateAndDrawStages() {
        gameplayStage.act();
        gameplayStage.draw();
        uiStage.act();
        uiStage.draw();
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


    private void checkCollisions() {

        for (int i = 0; i < pipePairs.size; i++) {
            PipePair pair = pipePairs.get(i);
            if (pair.getBottomPipe().getBounds().overlaps(bird.getBounds()) || pair.getTopPipe().getBounds().overlaps(bird.getBounds())) {
                stopTheWorld();
                SavedDataManager.getInstance().setHighScore(score);
                showWhiteScreen();
            } else if (bird.isBelowGround()) {
                bird.setY(FlappyGame.GROUND_LEVEL);
                bird.clearActions();
                bird.setToDying();
                showWhiteScreen();
            } else if (bird.isAboveCeiling()) {
                bird.setY(FlappyGame.HEIGHT - bird.getHeight());
                bird.setToDying();
                showWhiteScreen();
            } else if (pair.getRuby().getBounds().overlaps(bird.getBounds())) {
                score++;
                updateScoreLabel();
                pair.moveCoinOffscreen();
                Assets.playBingSound();
            }
        }

    }

    private void showWhiteScreen() {
        whitePixel.setWidth(FlappyGame.WIDTH);
        whitePixel.setHeight(FlappyGame.HEIGHT);

        gameplayStage.addActor(whitePixel);

        whitePixel.addAction(Actions.fadeOut(.5f));
    }

    private void updateScoreLabel() {
        scoreLabel.setText(String.valueOf(score));
        scoreLabel.setWidth(scoreLabel.getTextBounds().width);
        scoreLabel.setPosition(FlappyGame.WIDTH / 2, FlappyGame.HEIGHT * .9f, Align.center);
    }

    private void stopTheWorld() {
        bird.setToDying();
        killPipePairs();
        stopTheGround();
        screenState = State.DYING;

    }

    private void stopTheGround() {
        ground.vel.x = 0;
    }

    private void killPipePairs() {
        for (PipePair pair : pipePairs) {
            pair.getBottomPipe().setState(Pipe.State.dead);
            pair.getTopPipe().setState(Pipe.State.dead);
            pair.getRuby().setVel(0, 0);
        }
    }


    private void updatePipePairs() {
        for (int i = 0; i < pipePairs.size; i++) {
            pipePairs.get(i).update();
        }
    }


    private void addPipes(Stage gameplayStage) {
        for (int i = 0; i < pipePairs.size; i++) {
            gameplayStage.addActor(pipePairs.get(i).getBottomPipe());
            gameplayStage.addActor(pipePairs.get(i).getTopPipe());
            gameplayStage.addActor(pipePairs.get(i).getRuby());
        }
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

    /**
     * Tells libgdx to listen for inputs coming from the InputAdapter we give it
     */
    private void initInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            // We only care about the touch down event
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                switch (screenState) {

                    case DYING:
                        justTouched = true;
                        break;

                    case DEAD:
                        if (allowRestart) {
                            game.setScreen(new GameplayScreen(game));
                        }
                        justTouched = true;
                        break;

                    case PLAYING:
                        justTouched = true;
                        break;

                    case PREGAME:
                        justTouched = true;
                        screenState = State.PLAYING;
                        bird.setState(Bird.State.ALIVE);
                        bird.clearActions();
                        tapToFlap.addAction(Actions.moveToAligned(FlappyGame.CENTER_X, FlappyGame.HEIGHT, Align.bottom, .75f, Interpolation.sine));
                        initFirstSetOfPipes();
                        initSecondSetOfPipes();
                        initThirdSetOfPipes();
                        addPipes(gameplayStage);
                        gameplayStage.addActor(ground);
                        gameplayStage.addActor(bird);
                        break;

                }
                return true;
            }
        });
    }


}
