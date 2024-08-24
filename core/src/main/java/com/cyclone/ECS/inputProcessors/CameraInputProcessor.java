package com.cyclone.ECS.inputProcessors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.cyclone.ECS.ECS;

/**
 * <p>
 * Public methods:
 * </p>
 * <ul>
 * <li>getIsMoving(): return true if the camera wants to move (when key is
 * pressed
 * or in the middle of animation)</li>
 *
 * <li>animateCamera(float delta): trigger camera animation process, set
 * isMoving to
 * false when animation finishes</li>
 *
 * <li>processKeyHeld(float delta): check for key held time and trigger auto
 * repeat
 * input</li>
 */
public class CameraInputProcessor implements InputProcessor {
    private final ECS game;
    // For checking camera bound
    private final OrthographicCamera camera;
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

    public CameraInputProcessor(OrthographicCamera camera, ECS game) {
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

    /**
     * Check for key held time and trigger auto-repeat input
     */
    public void processKeyHeld(float delta) {
        if (currentKeyHeld != 0) {
            timeHeld += delta;
        } else {
            timeHeld = 0;
        }
        if (timeHeld > 0.5f && !isMoving) {
            processInput(currentKeyHeld);
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

    /**
     * Trigger camera animation process, set
     * isMoving to
     * false when animation finishes
     */
    public void animateCamera(float delta) {
        elapsedTime += delta;
        float progress = Math.min(1, elapsedTime / moveDuration);
        camera.position.set(
                Interpolation.linear.apply(startX, targetX, progress),
                Interpolation.linear.apply(startY, targetY, progress), 0);
        if (camera.position.x == targetX && camera.position.y == targetY) {
            isMoving = false;
        }
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

    // Getters and setters

    /**
     * Return true if the camera wants to move (when key is
     * pressed
     * or in the middle of animation)
     */
    public boolean getIsMoving() {
        return isMoving;
    }
}
