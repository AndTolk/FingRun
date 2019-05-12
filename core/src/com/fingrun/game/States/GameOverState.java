package com.fingrun.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.fingrun.game.FingRun;

public class GameOverState extends State {

    private static final int BUTTON_POSITION = 250;
    private Texture youDead;
    private Texture deadFinger;
    private Texture background;
    private Texture tryAgainButtonOff;
    private Texture tryAgainButtonOn;
    private Texture curTryAgain;
    private Texture exitButtonOff;
    private Texture exitButtonOn;
    private Texture curExit;
    private Texture bestScore;
    private BitmapFont font;
    private Vector3 touchPos;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, FingRun.WIDTH, FingRun.HEIGHT);
        touchPos = new Vector3(0, 0, 0);
        youDead = new Texture("YouDead.png");
        deadFinger = new Texture("DeadFinger.png");
        background = new Texture("Background.png");
        tryAgainButtonOff = new Texture("TryAgainButtonOff.png");
        tryAgainButtonOn = new Texture("TryAgainButtonOn.png");
        curTryAgain = tryAgainButtonOff;
        exitButtonOff = new Texture("ExitButtonOff.png");
        exitButtonOn = new Texture("ExitButtonOn.png");
        curExit = exitButtonOff;
        bestScore = new Texture("BestScore.png");
        font = new BitmapFont(Gdx.files.internal("bestScoreText.fnt"), Gdx.files.internal("bestScoreText.png"), false);
    }

    private void exitButtonAnimation() {
        if (Gdx.input.isTouched()) {
            if (touchPos.x > camera.position.x - BUTTON_POSITION - exitButtonOff.getWidth() && touchPos.x < camera.position.x - BUTTON_POSITION &&
                    touchPos.y > camera.position.y - exitButtonOff.getHeight() && touchPos.y < camera.position.y)
                curExit = exitButtonOn;
            else
                curExit = exitButtonOff;
        }
        if (!Gdx.input.isTouched()) {
            if (curExit == exitButtonOn) {
                if (touchPos.x > camera.position.x - BUTTON_POSITION - exitButtonOff.getWidth() && touchPos.x < camera.position.x - BUTTON_POSITION &&
                        touchPos.y > camera.position.y - exitButtonOff.getHeight() && touchPos.y < camera.position.y) {
                    FingRun.statistics.saveScore(FingRun.statistics);
                    FingRun.music.dispose();
                    Gdx.app.exit();
                }
            }
        }
    }

    private void tryAgainButtonAnimation() {
        if (Gdx.input.isTouched()) {
            if (touchPos.x > camera.position.x + BUTTON_POSITION && touchPos.x < camera.position.x + BUTTON_POSITION + tryAgainButtonOff.getWidth() &&
                    touchPos.y > camera.position.y - tryAgainButtonOff.getHeight() && touchPos.y < camera.position.y)
                curTryAgain = tryAgainButtonOn;
            else
                curTryAgain = tryAgainButtonOff;
        }
        if (!Gdx.input.isTouched()) {
            if (curTryAgain == tryAgainButtonOn)
                if (touchPos.x > camera.position.x + BUTTON_POSITION && touchPos.x < camera.position.x + BUTTON_POSITION + tryAgainButtonOff.getWidth() &&
                        touchPos.y > camera.position.y - tryAgainButtonOff.getHeight() && touchPos.y < camera.position.y)
                    gsm.set(new MenuState(gsm));
        }
    }

    @Override
    protected void handleInput() {
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        touchPos = camera.unproject(touchPos);
        exitButtonAnimation();
        tryAgainButtonAnimation();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(youDead, FingRun.WIDTH / 2 - youDead.getWidth() / 2, FingRun.HEIGHT - youDead.getHeight() * 2);
        sb.draw(deadFinger, FingRun.WIDTH / 2 - deadFinger.getWidth() / 2, deadFinger.getHeight());
        sb.draw(curTryAgain, camera.position.x + BUTTON_POSITION, camera.position.y - curTryAgain.getHeight() + 7);
        sb.draw(curExit, camera.position.x - BUTTON_POSITION - curExit.getWidth(), camera.position.y - curExit.getHeight());
        sb.draw(bestScore, camera.position.x - bestScore.getWidth() + exitButtonOff.getWidth()/2 - 10, FingRun.HEIGHT / 2 + bestScore.getHeight() / 2);
        font.draw(sb, "" + FingRun.statistics.getBestScore(), camera.position.x + exitButtonOff.getWidth()/2 + 10, FingRun.HEIGHT / 2 + bestScore.getHeight() + 15);
        sb.end();
    }

    @Override
    public void dispose() {
        youDead.dispose();
        deadFinger.dispose();
        background.dispose();
        tryAgainButtonOff.dispose();
        tryAgainButtonOn.dispose();
        curTryAgain.dispose();
        exitButtonOff.dispose();
        exitButtonOn.dispose();
        curExit.dispose();
        bestScore.dispose();
        font.dispose();
    }
}
