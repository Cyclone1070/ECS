package com.cyclone.projecta.inputProcessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cyclone.projecta.App;

public class CameraInputProcessor implements InputProcessor {
    private App game;
    // For checking camera bound
    private OrthographicCamera camera;
    private float maxX;
    private float maxY;
    // For interpolation
    private boolean isMoving = false;
    private float targetX;
    private float targetY;
    private float startX;
    private float startY;
    private float elapsedTime;
    private final float moveDuration = 0.15f;
    // For key held down repeat
    private int currentKeyHeld = 0;
    private float timeHeld = 0f;

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

    public float getTimeHeld() {
        return timeHeld;
    }

    public void setTimeHeld(float timeHeld) {
        this.timeHeld = timeHeld;
    }

    public int getCurrentKeyHeld() {
        return currentKeyHeld;
    }
    // endregion
    // For key repeat when held down

    public CameraInputProcessor(OrthographicCamera camera, App game) {
        this.camera = camera;
        this.game = game;
        maxX = game.gridWidth * game.gridSize - camera.viewportWidth / 2;
        maxY = game.gridHeight * game.gridSize - camera.viewportHeight / 2;
        targetX = camera.position.x;
        targetY = camera.position.y;
    }

    @Override
    public boolean keyDown(int keycode) {
        processInput(keycode);
        // Repeat if key is held down
        currentKeyHeld = keycode;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (currentKeyHeld == keycode) {
            currentKeyHeld = 0;
        }
        return true;
    }

    // Call function to respond to input
    public void processInput(int keycode) {
        switch (keycode) {
            case Input.Keys.RIGHT:
            case Input.Keys.L:
                if (camera.position.x + game.gridSize * 5 <= maxX) {
                    cameraInterpolation(game.gridSize * 5, 0);
                } else {
                    cameraInterpolation(maxX - camera.position.x, 0);
                }
                break;
            case Input.Keys.LEFT:
            case Input.Keys.H:
                if (camera.position.x - game.gridSize * 5 >= camera.viewportWidth / 2) {
                    cameraInterpolation(-game.gridSize * 5, 0);
                } else {
                    cameraInterpolation((camera.viewportWidth / 2 - camera.position.x), 0);
                }
                break;
            case Input.Keys.DOWN:
            case Input.Keys.J:
                if (camera.position.y - game.gridSize * 5 >= camera.viewportHeight / 2) {
                    cameraInterpolation(0, -game.gridSize * 5);
                } else {
                    cameraInterpolation(0, (camera.viewportHeight / 2 - camera.position.y));
                }
                break;
            case Input.Keys.UP:
            case Input.Keys.K:
                if (camera.position.y + game.gridSize * 5 <= maxY) {
                    cameraInterpolation(0, game.gridSize * 5);
                } else {
                    cameraInterpolation(0, maxY - camera.position.y);
                }
                break;
            case Input.Keys.U:
                if (camera.position.x + game.gridSize * 5 <= maxX
                        && camera.position.y + game.gridSize * 5 <= maxY) {
                    cameraInterpolation(game.gridSize * 5, game.gridSize * 5);
                } else {
                    float distance = Math.min(maxX - camera.position.x, maxY - camera.position.y);
                    cameraInterpolation(distance, distance);
                }
                break;
            case Input.Keys.Y:
                if (camera.position.x - game.gridSize * 5 >= camera.viewportWidth / 2
                        && camera.position.y + game.gridSize * 5 <= maxY) {
                    cameraInterpolation(-game.gridSize * 5, game.gridSize * 5);
                } else {
                    float distance = Math.min(Math.abs(camera.viewportWidth / 2 - camera.position.x),
                            maxY - camera.position.y);
                    cameraInterpolation(-distance, distance);
                }
                break;
            case Input.Keys.B:
                if (camera.position.x - game.gridSize * 5 >= camera.viewportWidth / 2
                        && camera.position.y - game.gridSize * 5 >= camera.viewportHeight / 2) {
                    cameraInterpolation(-game.gridSize * 5, -game.gridSize * 5);
                } else {
                    float distance = Math.max(camera.viewportWidth / 2 - camera.position.x,
                            camera.viewportHeight / 2 - camera.position.y);
                    cameraInterpolation(distance, distance);
                }
                break;
            case Input.Keys.N:
                if (camera.position.x + game.gridSize * 5 <= maxX
                        && camera.position.y - game.gridSize * 5 >= camera.viewportHeight / 2) {
                    cameraInterpolation(game.gridSize * 5, -game.gridSize * 5);
                } else {
                    float distance = Math.min(maxX - camera.position.x,
                            Math.abs(camera.viewportHeight / 2 - camera.position.y));
                    cameraInterpolation(distance, -distance);
                }
                break;
        }
    }

    // To animate the camera
    private void cameraInterpolation(float distanceX, float distanceY) {
        if (isMoving) {
            return;
        }
        isMoving = true;
        targetX += distanceX;
        targetY += distanceY;
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
