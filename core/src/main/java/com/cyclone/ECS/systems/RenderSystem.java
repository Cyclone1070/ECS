package com.cyclone.ECS.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.cyclone.ECS.ECS;
import com.cyclone.ECS.utils.Mappers;

public class RenderSystem extends EntitySystem {
    private final ECS game;
    private final OrthographicCamera camera;

    public RenderSystem(ECS game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
    }

    @Override
    public void update(float delta) {
        if (delta > 0) {
            ScreenUtils.clear(0, 0, 0.2f, 1);
            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            for (int z = 0; z < game.gridDepth; z++) {
                for (int x = 0; x < game.gridWidth; x++) {
                    for (int y = 0; y < game.gridHeight; y++) {
                        drawEntity(z, x, y, game.tileMap.getFloor(z, x, y));
                        drawEntity(z, x, y, game.tileMap.getEntity(z, x, y));
                    }
                }
            }
            game.batch.end();
        }
    }

    private void drawEntity(int z, int x, int y, Entity entity) {
        if (entity != null) {
            if (Mappers.sprite.get(entity).currentSprite.equals("wall")) {
                if (isSpriteWall(z, x, y - 1)) {
                    game.batch.draw(game.atlas.get("wallUD"), x * game.gridSize, y * game.gridSize);
                } else {
                    game.batch.draw(game.atlas.get("wallLR"), x * game.gridSize, y * game.gridSize);
                }
            } else {
                game.batch.draw(game.atlas.get(Mappers.sprite.get(entity).currentSprite), x * game.gridSize, y * game.gridSize);
            }
        }
    }
    private boolean isSpriteWall(int z, int x, int y) {
        try {
            return Mappers.sprite.get(game.tileMap.getEntity(z, x, y)).currentSprite.equals("wall") ||
                    Mappers.sprite.get(game.tileMap.getEntity(z, x, y)).currentSprite.equals("door");
        } catch (Exception e) {
            return false;
        }
    }
}
