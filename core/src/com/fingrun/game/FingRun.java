package com.fingrun.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fingrun.game.States.GameStateManager;
import com.fingrun.game.States.MenuState;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FingRun extends ApplicationAdapter {

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	public static final String TITLE = "Fingerun";

	private GameStateManager gsm;
	private SpriteBatch batch;

	public static Statistics statistics;
	public static GameMusicManager music;

	@Override
	public void create() {
		try {
			FileInputStream fileInputStream = new FileInputStream("score");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			statistics = (Statistics) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
		} catch (IOException | ClassNotFoundException e) {
			statistics = new Statistics();
		}
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music = new GameMusicManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push((new MenuState(gsm)));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}


}