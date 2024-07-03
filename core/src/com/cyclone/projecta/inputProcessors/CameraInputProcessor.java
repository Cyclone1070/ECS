package com.cyclone.projecta.inputProcessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Timer;
import com.cyclone.projecta.App;

public class CameraInputProcessor implements InputProcessor {
    private App game;
    // For checking camera bound
    private OrthographicCamera camera;
    private int maxX;
    private int maxY;
    // For interpolation
    private boolean isMoving;
    private float targetX;
    private float targetY;
    private float startX;
    private float startY;
    private float elapsedTime;
    private final float moveDuration = 0.1f;

    // region Getters and setters
    public float getTargetX() {
        return targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getMoveDuration() {
        return moveDuration;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    // endregion
    // For key repeat when held down
    private Timer timer;
    private Timer.Task keyRepeatTask;

    public CameraInputProcessor(OrthographicCamera camera, App game) {
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
        if (keyRepeatTask != null) {
            keyRepeatTask.cancel();
        }
        timer.scheduleTask(keyRepeatTask = new Timer.Task() {
            @Override
            public void run() {
                processInput(keycode);
            }
        }, 0.4f, 0.1f);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keyRepeatTask != null) {
            keyRepeatTask.cancel();
        }
        return true;
    }

    // Call function to respond to input
    private void processInput(int keycode) {
        switch (keycode) {
            case Input.Keys.RIGHT:
            case Input.Keys.L:
                // shift held down for faster movement
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                    if (camera.position.x + game.gridSize * 5 <= maxX) {
                        cameraInterpolation(game.gridSize * 5, 0);
                    } else {
                        cameraInterpolation(maxX - (int) camera.position.x, 0);
                    }
                } else if (camera.position.x + game.gridSize <= maxX) {
                    cameraInterpolation(game.gridSize, 0);
                }
                break;
            case Input.Keys.LEFT:
            case Input.Keys.H:
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                    if (camera.position.x - game.gridSize * 5 >= camera.viewportWidth / 2) {
                        cameraInterpolation(-game.gridSize * 5, 0);
                    } else {
                        cameraInterpolation((int) (camera.viewportWidth / 2 - camera.position.x), 0);
                    }
                } else if (camera.position.x - game.gridSize >= camera.viewportWidth / 2) {
                    cameraInterpolation(-game.gridSize, 0);
                }
                break;
            case Input.Keys.DOWN:
            case Input.Keys.J:
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                    if (camera.position.y - game.gridSize * 5 >= camera.viewportHeight / 2) {
                        cameraInterpolation(0, -game.gridSize * 5);
                    } else {
                        cameraInterpolation(0, (int) (camera.viewportHeight / 2 - camera.position.y));
                    }
                } else if (camera.position.y - game.gridSize >= camera.viewportHeight / 2) {
                    cameraInterpolation(0, -game.gridSize);
                }
                break;
            case Input.Keys.UP:
            case Input.Keys.K:
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                    if (camera.position.y + game.gridSize * 5 <= maxY) {
                        cameraInterpolation(0, game.gridSize * 5);
                    } else {
                        cameraInterpolation(0, maxY - (int) camera.position.y);
                    }
                } else if (camera.position.y + game.gridSize <= maxY) {
                    cameraInterpolation(0, game.gridSize);
                }
                break;
            case Input.Keys.U:
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                    if (camera.position.x + game.gridSize * 5 <= maxX
                            && camera.position.y + game.gridSize * 5 <= maxY) {
                        cameraInterpolation(game.gridSize * 5, game.gridSize * 5);
                    } else {
                        int distance = Math.min(maxX - (int) camera.position.x, maxY - (int) camera.position.y);
                        cameraInterpolation(distance, distance);
                    }
                } else if (camera.position.x + game.gridSize <= maxX && camera.position.y + game.gridSize <= maxY) {
                    cameraInterpolation(game.gridSize, game.gridSize);
                }
                break;
            case Input.Keys.Y:
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                    if (camera.position.x - game.gridSize * 5 >= camera.viewportWidth / 2
                            && camera.position.y + game.gridSize * 5 <= maxY) {
                        cameraInterpolation(-game.gridSize * 5, game.gridSize * 5);
                    } else {
                        int distance = Math.min(Math.abs((int) (camera.viewportWidth / 2 - camera.position.x)),
                                maxY - (int) camera.position.y);
                        cameraInterpolation(-distance, distance);
                    }
                } else if (camera.position.x - game.gridSize >= camera.viewportWidth / 2
                        && camera.position.y + game.gridSize <= maxY) {
                    cameraInterpolation(-game.gridSize, game.gridSize);
                }
                break;
            case Input.Keys.B:
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                    if (camera.position.x - game.gridSize * 5 >= camera.viewportWidth / 2
                            && camera.position.y - game.gridSize * 5 >= camera.viewportHeight / 2) {
                        cameraInterpolation(-game.gridSize * 5, -game.gridSize * 5);
                    } else {
                        int distance = Math.max((int) (camera.viewportWidth / 2 - camera.position.x),
                                (int) (camera.viewportHeight / 2 - camera.position.y));
                        cameraInterpolation(distance, distance);
                    }
                } else if (camera.position.x - game.gridSize >= camera.viewportWidth / 2
                        && camera.position.y - game.gridSize >= camera.viewportHeight / 2) {
                    cameraInterpolation(-game.gridSize, -game.gridSize);
                }
                break;
            case Input.Keys.N:
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                    if (camera.position.x + game.gridSize * 5 <= maxX
                            && camera.position.y - game.gridSize * 5 >= camera.viewportHeight / 2) {
                        cameraInterpolation(game.gridSize * 5, -game.gridSize * 5);
                    } else {
                        int distance = Math.min(maxX - (int) camera.position.x,
                                Math.abs((int) (camera.viewportHeight / 2 - camera.position.y)));
                        cameraInterpolation(distance, -distance);
                    }
                } else if (camera.position.x + game.gridSize <= maxX
                        && camera.position.y - game.gridSize >= camera.viewportHeight / 2) {
                    cameraInterpolation(game.gridSize, -game.gridSize);
                }
                break;
        }
    }

    // To animate the camera
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
}
