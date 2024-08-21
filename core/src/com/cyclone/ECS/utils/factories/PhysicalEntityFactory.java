package com.cyclone.ECS.utils.factories;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.cyclone.ECS.ECS;
import com.badlogic.gdx.files.FileHandle;
import com.cyclone.ECS.components.PositionComponent;
import com.cyclone.ECS.utils.Mappers;

/**
 * Factory for creating physical entities. Used to build components:
 * <ul>
 *     Position component
 * </ul>
 */
public class PhysicalEntityFactory extends EntityFactory {
    public PhysicalEntityFactory(ECS game) {
        super(game);
    }

    public Entity build(FileHandle jsonPath, int z, int x, int y) {
        Entity entity = super.build(jsonPath);
        PositionComponent positionComponent = Mappers.position.get(entity);
        if (positionComponent != null) {
            positionComponent.z = z;
            positionComponent.x = x;
            positionComponent.y = y;
        }
        return entity;
    }
}
