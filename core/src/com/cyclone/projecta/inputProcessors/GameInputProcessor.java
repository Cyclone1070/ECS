package com.cyclone.projecta.inputProcessors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Timer;
import com.cyclone.projecta.App;

public class GameInputProcessor implements InputProcessor {
    App game;
    // For checking camera bound
    OrthographicCamera camera;
    int maxX;
    int maxY;
    // For interpolation
    private boolean isMoving;
    private float targetX;
    private float targetY;
    private float startX;
    private float startY;
    private float elapsedTime;
    private final float moveDuration = 0.1f;
    Timer.Task interpolationTask;
    // For key repeat when held down
    private Timer timer;
    private Timer.Task keyRepeatTask;

    public GameInputProcessor(OrthographicCamera camera, App game) {
        this.camera = camera;
        this.game = game;
        maxX = game.gridWidth * game.gridSize - (int) camera.viewportWidth / 2;
        maxY = game.gridHeight * game.gridSize - (int) camera.viewportHeight / 2;
        timer = new Timer();
        targetX = camera.position.x;
        targetY = camera.position.y;
        isMoving = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        processInput(keycode);
        // Repeat if key is held down
        timer.scheduleTask(keyRepeatTask = new Timer.Task() {
            @Override
            public void run() {
                processInput(keycode);
            }
        }, 0.5f, 0.1f);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keyRepeatTask != null) {
            keyRepeatTask.cancel();
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    // Call function to respond to input
    private void processInput(int keycode) {
        switch (keycode) {
            case Input.Keys.RIGHT:
            case Input.Keys.L:
                if (camera.position.x + game.gridSize <= maxX) {
                    cameraInterpolation(game.gridSize, 0);
                }
                break;
            case Input.Keys.LEFT:
            case Input.Keys.H:
                if (camera.position.x - game.gridSize >= camera.viewportWidth / 2) {
                    cameraInterpolation(-game.gridSize, 0);
                }
                break;
            case Input.Keys.DOWN:
            case Input.Keys.J:
                if (camera.position.y - game.gridSize >= camera.viewportHeight / 2) {
                    cameraInterpolation(0, -game.gridSize);
                }
                break;
            case Input.Keys.UP:
            case Input.Keys.K:

                if (camera.position.y + game.gridSize <= maxY) {
                    cameraInterpolation(0, game.gridSize);
                }
                break;
            case Input.Keys.U:
                if (camera.position.x + game.gridSize <= maxX && camera.position.y + game.gridSize <= maxY) {
                    cameraInterpolation(game.gridSize, game.gridSize);
                }
                break;
            case Input.Keys.Y:
                if (camera.position.x - game.gridSize >= camera.viewportWidth / 2
                        && camera.position.y + game.gridSize <= maxY) {
                    cameraInterpolation(-game.gridSize, game.gridSize);
                }
                break;
            case Input.Keys.B:
                if (camera.position.x - game.gridSize >= camera.viewportWidth / 2
                        && camera.position.y - game.gridSize >= camera.viewportHeight / 2) {
                    cameraInterpolation(-game.gridSize, -game.gridSize);
                }
                break;
            case Input.Keys.N:
                if (camera.position.x + game.gridSize <= maxX
                        && camera.position.y - game.gridSize >= camera.viewportHeight / 2) {
                    cameraInterpolation(game.gridSize, -game.gridSize);
                }
                break;
        }
    }

    private void cameraInterpolation(int directionX, int directionY) {
        if (isMoving) {
            return;
        }
        isMoving = true;
        targetX += directionX;
        targetY += directionY;
        startX = camera.position.x;
        startY = camera.position.y;
        elapsedTime = 0;

        timer.scheduleTask(interpolationTask = new Timer.Task() {
            @Override
            public void run() {
                elapsedTime += 0.01f;
                float progress = Math.min(1, elapsedTime / moveDuration);
                camera.position.set(Interpolation.linear.apply(startX, targetX, progress),
                        Interpolation.linear.apply(startY, targetY, progress), 0);
                if (camera.position.x == targetX && camera.position.y == targetY) {
                    interpolationTask.cancel();
                    isMoving = false;
                }
            }

        }, 0, 0.01f);
    }
}
