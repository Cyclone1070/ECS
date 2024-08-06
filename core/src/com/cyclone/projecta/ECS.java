package com.cyclone.projecta;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cyclone.projecta.screens.MainMenuScreen;
import com.cyclone.projecta.utils.TileMap;

public class ECS extends Game {
	public SpriteBatch batch;
	public TextureAtlas atlas;
	public BitmapFont font;
	public PooledEngine engine;
	public AssetManager assetManager;
	public TileMap tileMap;
	public int gridDepth = 1;
	public int gridWidth = 50;
	public int gridHeight = 50;
	public int gridSize = 32;
	public int viewportWidth = 1280;
	public int viewportHeight = 704;
	private boolean isLoaded = false;

	public void create() {
		tileMap = new TileMap(gridDepth, gridWidth, gridHeight);
		batch = new SpriteBatch();
		engine = new PooledEngine(20, 50, 20, 50);
		assetManager = new AssetManager();
		assetManager.load("data/atlas/default.atlas", TextureAtlas.class);
		assetManager.load("data/fonts/monogram-32.fnt", BitmapFont.class);
	}

	public void render() {
		super.render(); // important!
		if (assetManager.update() && !isLoaded) {
			isLoaded = true;
			atlas = assetManager.get("data/atlas/default.atlas", TextureAtlas.class);
			font = assetManager.get("data/fonts/monogram-32.fnt", BitmapFont.class);
			this.setScreen(new MainMenuScreen(this));
		};
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		assetManager.dispose();
	}
}