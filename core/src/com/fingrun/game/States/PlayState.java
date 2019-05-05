package com.fingrun.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.fingrun.game.FingRun;
import com.fingrun.game.Sprites.Obstacle;
import com.fingrun.game.Sprites.Finger;

public class PlayState extends State {

    private static final int BARRIER_SPACING = 1500; //Constant responsible for the distance between the obstacles

    private Texture background;
    private Texture ground;
    private Texture scorePicture;
    private Finger finger;
    private Obstacle obstacle0;
    private Obstacle obstacle1;
    private Vector2 groundPos0, groundPos1;
    private double curScore;
    private BitmapFont font;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        curScore = 0;
        scorePicture = new Texture("ScorePicture.png");
        font = new BitmapFont(Gdx.files.internal("kek.fnt"),Gdx.files.internal("kek.png"),false);
        camera.setToOrtho(false, FingRun.WIDTH, FingRun.HEIGHT);
        finger = new Finger(10, 30);
        background = new Texture("Background.png");
        ground = new Texture("Ground.png");
        groundPos0 = new Vector2(camera.position.x - camera.viewportWidth / 2, 0);
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2 + background.getWidth(), 0);
        obstacle0 = new Obstacle(BARRIER_SPACING + Obstacle.BARRIER_WIDTH);
        obstacle1 = new Obstacle(2 * (BARRIER_SPACING + Obstacle.BARRIER_WIDTH));

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()) {
            finger.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        finger.update(dt);
        updateGround();
        curScore += dt * 5;
        camera.position.x = finger.getPosition().x + 900;
        if (camera.position.x - camera.viewportWidth / 2 > obstacle0.getPosBarrier().x + obstacle0.getBarrier().getWidth()) {
            obstacle0.reposition(obstacle0.getPosBarrier().x + (Obstacle.BARRIER_WIDTH + BARRIER_SPACING) * 2);
        }
        if (camera.position.x - camera.viewportWidth / 2 > obstacle1.getPosBarrier().x + obstacle1.getBarrier().getWidth()) {
            obstacle1.reposition(obstacle1.getPosBarrier().x + (Obstacle.BARRIER_WIDTH + BARRIER_SPACING) * 2);
        }
        if (obstacle1.collides(finger.getBoundFinger())) {
            FingRun.statistics.updateBestScore((int) curScore);
            gsm.set(new GameOverState(gsm));
        }
        if (obstacle0.collides(finger.getBoundFinger())) {
            FingRun.statistics.updateBestScore((int) curScore);
            gsm.set(new GameOverState(gsm));
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        font.draw(sb, "" + (int) curScore, camera.position.x - (camera.viewportWidth / 2) + scorePicture.getWidth() + 20, FingRun.HEIGHT - scorePicture.getHeight()/2 + 5);
        sb.draw(ground, groundPos0.x, groundPos0.y);
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(scorePicture, camera.position.x - (camera.viewportWidth / 2) + 10, FingRun.HEIGHT - scorePicture.getHeight() - 5);
        sb.draw(finger.getFinger(), finger.getPosition().x, finger.getPosition().y);
        sb.draw(obstacle0.getBarrier(), obstacle0.getPosBarrier().x, obstacle0.getPosBarrier().y);
        sb.draw(obstacle1.getBarrier(), obstacle1.getPosBarrier().x, obstacle1.getPosBarrier().y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        ground.dispose();
        scorePicture.dispose();
        finger.dispose();
        obstacle0.dispose();
        obstacle1.dispose();
        font.dispose();
    }

    private void updateGround() {
        if (camera.position.x - camera.viewportWidth / 2 > groundPos0.x + ground.getWidth())
            groundPos0.add(ground.getWidth() * 2, 0);
        if (camera.position.x - camera.viewportWidth / 2 > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
    }
}