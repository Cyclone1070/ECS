package com.cyclone.projecta.utils.factories;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.cyclone.projecta.ECS;
import com.badlogic.gdx.files.FileHandle;
import com.cyclone.projecta.components.PositionComponent;

/**
 * Factory for creating physical entities. Used to build components:
 * <ul>
 *     Position component
 * </ul>
 */
public class PhysicalEntityFactory extends EntityFactory {
    public PhysicalEntityFactory(ECS game) {
        super(game);
        factorySpecificComponents.add("PositionComponent");
    }

    public Entity build(FileHandle jsonPath, int z, int x, int y) {
        Entity entity = super.build(jsonPath);
        Json json = new Json();
        JsonValue jsonText = json.fromJson(null, jsonPath);

        for (JsonValue componentJson : jsonText.get("components")) {
            Component component = null;
            switch (getComponentName(componentJson)) {
                case "PositionComponent":
                    component = new PositionComponent(z, x, y);
                    break;
            }
            if (component != null) {
                entity.add(component);
            }
        }
        return entity;
    }
}
