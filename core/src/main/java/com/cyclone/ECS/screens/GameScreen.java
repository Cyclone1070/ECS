package com.cyclone.ECS.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cyclone.ECS.ECS;
import com.cyclone.ECS.inputProcessors.CameraInputProcessor;
import com.cyclone.ECS.systems.RenderSystem;
import com.cyclone.ECS.utils.factories.MapFactory;

public class GameScreen implements Screen {
    private final ECS game;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final MapFactory mapFactory;
    private final CameraInputProcessor cameraInput;

    public GameScreen(ECS game) {
        this.game = game;
        camera.setToOrtho(false, game.viewportWidth, game.viewportHeight);
        mapFactory = new MapFactory(game);
        mapFactory.build(0, 0, 0, Gdx.files.local("maps/test.json"));
        cameraInput = new CameraInputProcessor(camera, game);
        Gdx.input.setInputProcessor(cameraInput);
        game.engine.addSystem(new RenderSystem(game, camera));
    }

    @Override
    public void render(float delta) {
        // For moving the cameras in respond to input, think loading delta into input
        // processor
        if (Gdx.input.getInputProcessor() == cameraInput) {
            if (cameraInput.getIsMoving()) {
                cameraInput.animateCamera(delta);
            }
            cameraInput.processKeyHeld(delta);
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
        game.engine.update(delta);
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
