package com.cyclone.ECS;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cyclone.ECS.screens.MainMenuScreen;
import com.cyclone.ECS.utils.TileMap;

import java.util.HashMap;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class ECS extends Game {
    public SpriteBatch batch;
    public HashMap<String, TextureAtlas.AtlasRegion> atlas;
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

    public void create() {
        System.out.println(Gdx.files.getLocalStoragePath());
        tileMap = new TileMap(gridDepth, gridWidth, gridHeight);
        batch = new SpriteBatch();
        engine = new PooledEngine(20, 50, 20, 50);
        assetManager = new AssetManager(new LocalFileHandleResolver());
        assetManager.load("atlas/default.atlas", TextureAtlas.class);
        assetManager.load("fonts/monogram-32.fnt", BitmapFont.class);
        assetManager.finishLoading();
        font = assetManager.get("fonts/monogram-32.fnt", BitmapFont.class);
        atlas = new HashMap<String, TextureAtlas.AtlasRegion>();
        TextureAtlas textureAtlas = assetManager.get("atlas/default.atlas", TextureAtlas.class);
        for (TextureAtlas.AtlasRegion region: textureAtlas.getRegions()) {
            atlas.put(region.name, region);
        }
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        assetManager.dispose();
    }
}
