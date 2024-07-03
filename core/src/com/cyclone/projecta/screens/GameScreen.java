package com.cyclone.projecta.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.ScreenUtils;
import com.cyclone.projecta.App;
import com.cyclone.projecta.Tiles.TilesBuilder;
import com.cyclone.projecta.inputProcessors.CameraInputProcessor;
import com.cyclone.projecta.Tiles.Tile;

public class GameScreen implements Screen {
    private final App game;
    private OrthographicCamera camera;
    private Tile[][][] tiles;
    private CameraInputProcessor cameraInput;

    public GameScreen(App game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.viewportWidth, game.viewportHeight);

        this.tiles = TilesBuilder.build(game.gridWidth, game.gridHeight, 1);
        cameraInput = new CameraInputProcessor(camera, game);
        Gdx.input.setInputProcessor(cameraInput);
    }

    @Override
    public void render(float delta) {
        // For moving the cameras in respond to input
        if (cameraInput.getIsMoving()) {
            cameraInput.setElapsedTime(cameraInput.getElapsedTime() + delta);
            float progress = Math.min(1, cameraInput.getElapsedTime() / cameraInput.getMoveDuration());
            camera.position.set(
                    Interpolation.linear.apply(cameraInput.getStartX(), cameraInput.getTargetX(), progress),
                    Interpolation.linear.apply(cameraInput.getStartY(), cameraInput.getTargetY(), progress), 0);
            if (camera.position.x == cameraInput.getTargetX() && camera.position.y == cameraInput.getTargetY()) {
                cameraInput.setIsMoving(false);
            }
        }
        // For rendering the tiles in view
        int minTileX = (int) ((camera.position.x - (camera.viewportWidth) / 2) / game.gridSize);
        int maxTileX = (int) ((camera.position.x + (camera.viewportWidth) / 2) / game.gridSize - 1);
        int minTileY = (int) ((camera.position.y - (camera.viewportHeight) / 2) / game.gridSize);
        int maxTileY = (int) ((camera.position.y + (camera.viewportHeight) / 2) / game.gridSize - 1);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (int x = minTileX; x <= maxTileX; x++) {
            for (int y = minTileY; y <= maxTileY; y++) {
                game.font.draw(game.batch, tiles[x][y][0].getSymbol(), x * game.gridSize,
                        y * game.gridSize + game.font.getLineHeight());
            }
        }
        game.batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
