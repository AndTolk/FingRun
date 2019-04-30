package com.fingrun.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.fingrun.game.FingRun;

public class MenuState extends State {

    private Texture background;
    private Texture playButtonOff;
    private Texture playButtonOn;
    private Texture curPlay;
    private Texture achievmentsButtonOff;
    private Texture achievmentsButtonOn;
    private Texture curAchievments;
    private Texture exitButtonOn;
    private Texture exitButtonOff;
    private Texture curExit;
    private Texture soundButtonOn;
    private Texture soundButtonOff;
    private Texture curSoundButton;
    private Texture bestScore;
    private BitmapFont font;
    private Vector3 touchPos;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        bestScore = new Texture("BestScore.png");
        font = new BitmapFont(Gdx.files.internal("bestScoreText.fnt"), Gdx.files.internal("bestScoreText.png"), false);
        touchPos = new Vector3(0, 0, 0);
        camera.setToOrtho(false, FingRun.WIDTH, FingRun.HEIGHT);
        background = new Texture("Background.png");
        playButtonOff = new Texture("PlayButtonOff.png");
        playButtonOn = new Texture("PlayButtonOn.png");
        achievmentsButtonOff = new Texture("AchievementsButtonOff.png");
        achievmentsButtonOn = new Texture("AchievementsButtonOn.png");
        exitButtonOff = new Texture("ExitButtonOff.png");
        exitButtonOn = new Texture("ExitButtonOn.png");
        soundButtonOn = new Texture("SoundButtonOn.png");
        soundButtonOff = new Texture("SoundButtonOff.png");
        if (FingRun.music.isPlayingMusic())
            curSoundButton = soundButtonOn;
        else
            curSoundButton = soundButtonOff;
        curPlay = playButtonOff;
        curAchievments = achievmentsButtonOff;
        curExit = exitButtonOff;
    }


    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos = camera.unproject(touchPos);
            if (touchPos.x > camera.position.x - curPlay.getWidth() / 2 && touchPos.x < camera.position.x + curPlay.getWidth() / 2) {
                if (touchPos.y > camera.position.y + curPlay.getHeight() / 2 && touchPos.y < camera.position.y + 3 * curPlay.getHeight() / 2)
                    curPlay = playButtonOn;
                else
                    curPlay = playButtonOff;
            } else
                curPlay = playButtonOff;
            if (touchPos.x > camera.position.x - curAchievments.getWidth() / 2 && touchPos.x < camera.position.x + curAchievments.getWidth() / 2) {
                if (touchPos.y > camera.position.y - curAchievments.getHeight() / 2 - 25 && touchPos.y < camera.position.y + curAchievments.getHeight() / 2 - 25)
                    curAchievments = achievmentsButtonOn;
                else
                    curAchievments = achievmentsButtonOff;
            } else
                curAchievments = achievmentsButtonOff;
            if (touchPos.x > camera.position.x - curExit.getWidth() / 2 && touchPos.x < camera.position.x + curExit.getWidth() / 2) {
                if (touchPos.y > camera.position.y - curExit.getHeight() * 2 && touchPos.y < camera.position.y - curExit.getHeight())
                    curExit = exitButtonOn;
                else
                    curExit = exitButtonOff;
            } else
                curExit = exitButtonOff;
        }
        if (!Gdx.input.isTouched()) {
            if (curPlay == playButtonOn) {
                if (touchPos.x > camera.position.x - curPlay.getWidth() / 2 && touchPos.x < camera.position.x + curPlay.getWidth() / 2) {
                    if (touchPos.y > camera.position.y + curPlay.getHeight() / 2 && touchPos.y < camera.position.y + 3 * curPlay.getHeight() / 2)
                        gsm.set(new PlayState(gsm));
                }
            }
            if (curAchievments == achievmentsButtonOn) {
                if (touchPos.x > camera.position.x - curAchievments.getWidth() / 2 && touchPos.x < camera.position.x + curAchievments.getWidth() / 2) {
                    if (touchPos.y > camera.position.y - curAchievments.getHeight() / 2 - 25 && touchPos.y < camera.position.y + curAchievments.getHeight() / 2 - 25)
                        gsm.set(new MenuState(gsm));
                }
            }
            if (curExit == exitButtonOn) {
                if (touchPos.x > camera.position.x - curExit.getWidth() / 2 && touchPos.x < camera.position.x + curExit.getWidth() / 2) {
                    if (touchPos.y > camera.position.y - curExit.getHeight() * 2 && touchPos.y < camera.position.y - curExit.getHeight()) {
                        FingRun.statistics.saveScore(FingRun.statistics);
                        Gdx.app.exit();
                    }
                }
            }
        }
        if (Gdx.input.justTouched()) {
            if (touchPos.x > 5 && touchPos.x < 5 + curSoundButton.getWidth()) {
                if (touchPos.y > FingRun.HEIGHT - curSoundButton.getHeight() - 5 && touchPos.y < FingRun.HEIGHT - 5) {
                    if (curSoundButton == soundButtonOff)
                        curSoundButton = soundButtonOn;
                    else
                        curSoundButton = soundButtonOff;
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (curSoundButton == soundButtonOn)
            FingRun.music.playMusic(true);
        else
            FingRun.music.playMusic(false);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(curPlay, camera.position.x - curPlay.getWidth() / 2, camera.position.y + curPlay.getHeight() / 2);
        sb.draw(curAchievments, camera.position.x - curAchievments.getWidth() / 2, camera.position.y - curAchievments.getHeight() / 2 - 25);
        sb.draw(curExit, camera.position.x - curExit.getWidth() / 2, camera.position.y - curExit.getHeight() * 2);
        sb.draw(curSoundButton, 5, FingRun.HEIGHT - curSoundButton.getHeight() - 5);
        sb.draw(bestScore, camera.position.x - bestScore.getWidth(), 3 * camera.position.y / 2);
        font.draw(sb, "" + FingRun.statistics.getBestScore(), camera.position.x + 10, 3 * camera.position.y / 2 + 50);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButtonOff.dispose();
        playButtonOn.dispose();
        curPlay.dispose();
        achievmentsButtonOff.dispose();
        achievmentsButtonOn.dispose();
        curAchievments.dispose();
        exitButtonOff.dispose();
        exitButtonOn.dispose();
        curExit.dispose();
    }
}