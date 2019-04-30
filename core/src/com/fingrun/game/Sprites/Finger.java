package com.fingrun.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Finger {

    private static final int GRAVITY = 17; //Constant responsible for gravity.Enter a positive number to change it.
    private float Movement = 500; // Responsible for initial velocity
    private static final int POSITION_ON_GROUND = 150;  //Constant responsible for the position of the finger on the ground
    private static final int JUMP_HEIGHT = 305; //Constant responsible for the height of the jump

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle boundFinger;
    private Animation fingerAnimation;
    private Texture texture;

    public Finger(int x, int y) {
        position = new Vector3(x, POSITION_ON_GROUND, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("finger.png");
        fingerAnimation = new Animation(new TextureRegion(texture), 4, 0.5f);
        boundFinger = new Rectangle(x, y, texture.getWidth() / 4, texture.getHeight() / 4);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getFinger() {
        return fingerAnimation.getFrame();
    }

    public void update(float dt) {
        fingerAnimation.update(dt);
        if (Movement < 1150)
            Movement += 0.2f;
        position.add(Movement * dt, 0, 0); //Finger acceleration
        if (position.y > POSITION_ON_GROUND) {   //If the finger is not on the ground, then gravity lowers the finger to the ground.
            velocity.add(0, -GRAVITY, 0);
            velocity.scl(dt);
            position.add(0, velocity.y, 0);
            velocity.scl(1 / dt);
        }
        boundFinger.setPosition(position.x, position.y);

    }

    public Rectangle getBoundFinger() {
        return boundFinger;
    }

    public void jump() {
        if (position.y < POSITION_ON_GROUND + 5) { //The finger jumps at a distance of no more than 5 pixels from the ground.
            velocity.y = JUMP_HEIGHT;
            position.add(0, velocity.y, 0);

        }
    }

    public void dispose() {
        texture.dispose();

    }
}