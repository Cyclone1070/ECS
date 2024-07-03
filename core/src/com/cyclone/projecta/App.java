package com.cyclone.projecta;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cyclone.projecta.screens.MainMenuScreen;

public class App extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public int gridWidth = 50;
	public int gridHeight = 50;
	public int gridDepth = 1;
	public int gridSize = 32;
	public int viewportWidth = 1280;
	public int viewportHeight = 704;

	public void create() {
		font = new BitmapFont(Gdx.files.internal("fonts/monogram-32.fnt"), Gdx.files.internal("fonts/monogram.png"),
				false);
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}