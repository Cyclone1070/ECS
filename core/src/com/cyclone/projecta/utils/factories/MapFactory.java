package com.cyclone.projecta.utils.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.cyclone.projecta.ECS;

/**
 *
 */

public class MapFactory {
    private final ECS game;

    public MapFactory(ECS game) {
        this.game = game;
    }

    public void build(int z, int minX, int minY, FileHandle mapPath) {
        PhysicalEntityFactory physicalEntityFactory = new PhysicalEntityFactory(game);
        Json json = new Json();
        JsonValue mapJson = json.fromJson(null, mapPath);
        JsonValue paletteJson = mapJson.get("palette");
        JsonValue floorArray = mapJson.get("floor");
        // Build entities from json arrays
        for (int x = 0; x < 12; x++) {
            String currentRow = floorArray.getString(x);
            for (int y = 0; y < 12; y++) {
                String currentTile = currentRow.substring(y, y + 1);
                if (!currentTile.equals(" ")) {
                    Entity floor = physicalEntityFactory.build(getComponentJsonPath(paletteJson.getString(currentTile)), z, x, y);
                    game.tileMap.setFloor(z, minX + x, minY + y, floor);
                    game.engine.addEntity(floor);
                }
            }
        }
        JsonValue entityArray = mapJson.get("entity");
        for (int x = 0; x < 12; x++) {
            String currentRow = entityArray.getString(x);
            for (int y = 0; y < 12; y++) {
                String currentTile = currentRow.substring(y, y + 1);
                if (!currentTile.equals(" ")) {
                    Entity entity = physicalEntityFactory.build(getComponentJsonPath(paletteJson.getString(currentTile)), z, x, y);
                    game.tileMap.setEntity(z, minX + x, minY + y, entity);
                    game.engine.addEntity(entity);
                }
            }
        }
    }

    private FileHandle getComponentJsonPath(String componentName) {
        return Gdx.files.local("data/entities/" + componentName + ".json");
    }
}
