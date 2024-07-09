package com.cyclone.projecta.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.ScreenUtils;
import com.cyclone.projecta.App;
import com.cyclone.projecta.Tiles.TilesBuilder;
import com.cyclone.projecta.inputProcessors.CameraInputProcessor;
import com.cyclone.projecta.Tiles.Tile;

public class GameScreen implements Screen {
    private final App game;
    private OrthographicCamera camera = new OrthographicCamera();
    private Tile[][][] tiles;
    private CameraInputProcessor cameraInput;
    // region Sprites
    private Texture texture;
    // wallDR = wall with adjacent walls on the right & down
    private TextureRegion wallRD;
    private TextureRegion wallLD;
    private TextureRegion wallLU;
    private TextureRegion wallRU;
    private TextureRegion wallLR;
    private TextureRegion wallUD;
    private TextureRegion wallUDEnd;
    private TextureRegion wallLRU;
    private TextureRegion wallLRD;
    private TextureRegion wallRUD;
    private TextureRegion wallLUD;
    private TextureRegion wallLRUD;
    private TextureRegion bound;

    // endregion
    public GameScreen(App game) {
        this.game = game;

        camera.setToOrtho(false, game.viewportWidth, game.viewportHeight);

        this.tiles = TilesBuilder.build(game.gridWidth, game.gridHeight, 1);
        cameraInput = new CameraInputProcessor(camera, game);
        Gdx.input.setInputProcessor(cameraInput);

        texture = new Texture(Gdx.files.internal("graphic/32x32_Tergel.png"));
        wallRD = new TextureRegion(texture, 0, 0, game.gridSize, game.gridSize);
        wallLD = new TextureRegion(texture, game.gridSize, 0, game.gridSize, game.gridSize);
        wallRU = new TextureRegion(texture, game.gridSize * 2, 0, game.gridSize, game.gridSize);
        wallLU = new TextureRegion(texture, game.gridSize * 3, 0, game.gridSize, game.gridSize);
        wallLR = new TextureRegion(texture, game.gridSize * 4, 0, game.gridSize, game.gridSize);
        wallUD = new TextureRegion(texture, game.gridSize * 5, 0, game.gridSize, game.gridSize);
        wallUDEnd = new TextureRegion(texture, game.gridSize * 6, 0, game.gridSize, game.gridSize);
        wallLRU = new TextureRegion(texture, game.gridSize * 7, 0, game.gridSize, game.gridSize);
        wallLRD = new TextureRegion(texture, game.gridSize * 8, 0, game.gridSize, game.gridSize);
        wallRUD = new TextureRegion(texture, game.gridSize * 9, 0, game.gridSize, game.gridSize);
        wallLUD = new TextureRegion(texture, game.gridSize * 10, 0, game.gridSize, game.gridSize);
        wallLRUD = new TextureRegion(texture, game.gridSize * 11, 0, game.gridSize, game.gridSize);
        bound = new TextureRegion(texture, game.gridSize * 12, 0, game.gridSize, game.gridSize);
    }

    @Override
    public void render(float delta) {
        // For moving the cameras in respond to input
        if (cameraInput.getIsMoving()) {
            cameraInput.setElapsedTime(cameraInput.getElapsedTime() + delta);
            float progress = Math.min(1, cameraInput.getElapsedTime() / cameraInput.getMoveDuration());
            camera.position.set(
                    Interpolation.smooth.apply(cameraInput.getStartX(), cameraInput.getTargetX(), progress),
                    Interpolation.smooth.apply(cameraInput.getStartY(), cameraInput.getTargetY(), progress), 0);
            if (camera.position.x == cameraInput.getTargetX() && camera.position.y == cameraInput.getTargetY()) {
                cameraInput.setIsMoving(false);
            }
        }
        if (cameraInput.getCurrentKeyHeld() != 0) {
            cameraInput.setTimeHeld(cameraInput.getTimeHeld() + delta);
        } else {
            cameraInput.setTimeHeld(0);
        }
        if (cameraInput.getTimeHeld() > 0.5f && cameraInput.getIsMoving() == false) {
            cameraInput.processInput(cameraInput.getCurrentKeyHeld());
        }
        camera.update();
        // For rendering the tiles in view
        int minTileX = (int) ((camera.position.x - (camera.viewportWidth) / 2) / game.gridSize);
        int maxTileX = (int) ((camera.position.x + (camera.viewportWidth) / 2) / game.gridSize - 1);
        int minTileY = (int) ((camera.position.y - (camera.viewportHeight) / 2) / game.gridSize);
        int maxTileY = (int) ((camera.position.y + (camera.viewportHeight) / 2) / game.gridSize - 1);
        if (maxTileX < game.gridWidth - 1) {
            maxTileX++;
        }
        if (maxTileY < game.gridHeight - 1) {
            maxTileY++;
        }
        // render
        ScreenUtils.clear(0, 0, 0.2f, 1);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (int x = minTileX; x <= maxTileX; x++) {
            for (int y = minTileY; y <= maxTileY; y++) {
                game.batch.setColor(Color.WHITE);
                int z = 0;
                if (tiles[x][y][z] == Tile.WALL) {
                    if (tiles[x - 1][y][z] == Tile.WALL && tiles[x + 1][y][z] == Tile.WALL
                            && tiles[x][y + 1][z] == Tile.WALL && tiles[x][y - 1][z] == Tile.WALL) {
                        game.batch.draw(wallLRUD, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x - 1][y][z] == Tile.WALL && tiles[x + 1][y][z] == Tile.WALL
                            && tiles[x][y + 1][z] == Tile.WALL) {
                        game.batch.draw(wallLRU, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x - 1][y][z] == Tile.WALL && tiles[x + 1][y][z] == Tile.WALL
                            && tiles[x][y - 1][z] == Tile.WALL) {
                        game.batch.draw(wallLRD, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x - 1][y][z] == Tile.WALL && tiles[x][y + 1][z] == Tile.WALL
                            && tiles[x][y - 1][z] == Tile.WALL) {
                        game.batch.draw(wallLUD, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x + 1][y][z] == Tile.WALL && tiles[x][y + 1][z] == Tile.WALL
                            && tiles[x][y - 1][z] == Tile.WALL) {
                        game.batch.draw(wallRUD, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x - 1][y][z] == Tile.WALL && tiles[x + 1][y][z] == Tile.WALL) {
                        game.batch.draw(wallLR, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x][y + 1][z] == Tile.WALL && tiles[x][y - 1][z] == Tile.WALL) {
                        game.batch.draw(wallUD, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x + 1][y][z] == Tile.WALL && tiles[x][y + 1][z] == Tile.WALL) {
                        game.batch.draw(wallRU, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x + 1][y][z] == Tile.WALL && tiles[x][y - 1][z] == Tile.WALL) {
                        game.batch.draw(wallRD, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x - 1][y][z] == Tile.WALL && tiles[x][y + 1][z] == Tile.WALL) {
                        game.batch.draw(wallLU, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x - 1][y][z] == Tile.WALL && tiles[x][y - 1][z] == Tile.WALL) {
                        game.batch.draw(wallLD, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x][y - 1][z] == Tile.WALL) {
                        game.batch.draw(wallUD, x * game.gridSize, y * game.gridSize);
                    } else if (tiles[x][y + 1][z] == Tile.WALL) {
                        game.batch.draw(wallUDEnd, x * game.gridSize, y * game.gridSize);
                    } else {
                        game.batch.draw(wallLR, x * game.gridSize, y * game.gridSize);
                    }
                } else if (tiles[x][y][z] == Tile.BOUND) {
                    game.batch.setColor(Color.BLACK);
                    game.batch.draw(bound, x * game.gridSize, y * game.gridSize);
                }
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
