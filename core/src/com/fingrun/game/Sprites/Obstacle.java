package com.fingrun.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Obstacle {

    public static final int BARRIER_WIDTH = 62;
    private static final int POSITION_ON_GROUND = 150;  //Constant responsible for the position of the obstacle on the ground

    private Texture barrierOrangeHammer;
    private Texture barrierBlueHammer;
    private Texture barrierRedHammer;
    private Texture barrierNightstand;
    private Texture barrierPinRed;
    private Texture barrierPinYellow;
    private Texture barrierPinBlue;
    private Texture barrierPinPurple;
    private Texture curBarrier;
    private Vector2 posBarrier;
    private Rectangle boundBarrier;
    private Random rand;

    public Texture getBarrier() {
        return curBarrier;
    }

    public Vector2 getPosBarrier() {
        return posBarrier;
    }

    public Obstacle(float x) {
        barrierPinRed = new Texture("RedPin.png");
        barrierPinYellow = new Texture("YellowPin.png");
        barrierPinBlue = new Texture("BluePin.png");
        barrierPinPurple = new Texture("PurplePin.png");
        barrierNightstand = new Texture("NightStand.png");
        barrierOrangeHammer = new Texture("OrangeHammer.png");
        barrierBlueHammer = new Texture("BlueHammer.png");
        barrierRedHammer = new Texture("RedHammer.png");
        rand = new Random();
        posBarrier = new Vector2(x, POSITION_ON_GROUND);
        randomBarrier();
    }

    public void randomBarrier() {
        int choice = rand.nextInt(3);
        if (choice == 0) {
            boundBarrier = new Rectangle(posBarrier.x, posBarrier.y, barrierPinRed.getWidth(), barrierPinRed.getHeight());
            choice = rand.nextInt(4);
            if (choice == 0) {
                curBarrier = barrierPinRed;
                return;
            }
            if (choice == 1) {
                curBarrier = barrierPinBlue;
                return;
            }
            if (choice == 2) {
                curBarrier = barrierPinYellow;
                return;
            }
            curBarrier = barrierPinPurple;
            return;
        }
        if (choice == 1) {
            boundBarrier = new Rectangle(posBarrier.x, posBarrier.y, barrierOrangeHammer.getWidth(), barrierOrangeHammer.getHeight());
            choice = rand.nextInt(3);
            if (choice == 0) {
                curBarrier = barrierOrangeHammer;
                return;
            }
            if (choice == 1) {
                curBarrier = barrierBlueHammer;
                return;
            }
            curBarrier = barrierRedHammer;
            return;
        }
        boundBarrier = new Rectangle(posBarrier.x, posBarrier.y, barrierNightstand.getWidth(), barrierNightstand.getHeight());
        curBarrier = barrierNightstand;
    }

    public void reposition(float x) {
        randomBarrier();
        posBarrier.set(x, POSITION_ON_GROUND);
        boundBarrier.setPosition(posBarrier.x, POSITION_ON_GROUND);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(boundBarrier);
    }

    public void dispose() {
        barrierOrangeHammer.dispose();
        barrierBlueHammer.dispose();
        barrierRedHammer.dispose();
        barrierNightstand.dispose();
        barrierPinRed.dispose();
        barrierPinYellow.dispose();
        barrierPinBlue.dispose();
        barrierPinPurple.dispose();
        curBarrier.dispose();
    }
}
