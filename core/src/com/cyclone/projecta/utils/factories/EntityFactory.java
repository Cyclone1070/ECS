package com.cyclone.projecta.utils.factories;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.cyclone.projecta.ECS;
import com.cyclone.projecta.components.IDComponent;
import com.cyclone.projecta.components.PositionComponent;
import com.cyclone.projecta.components.SpriteComponent;

import java.util.UUID;

/**
 * Factory class for creating generic entities from JSON files.
 */

public class EntityFactory {
    protected final ECS game;
    protected Array<String> factorySpecificComponents = new Array<>();

    public EntityFactory(ECS game) {
        this.game = game;
    }

    public Entity build(FileHandle jsonPath) {
        Entity entity = game.engine.createEntity();
        Json json = new Json();
        JsonValue jsonText = json.fromJson(null, jsonPath);

        for (JsonValue componentJson : jsonText.get("components")) {
            Component component;
            if (factorySpecificComponents.contains(getComponentName(componentJson), false)) {
                continue;
            }
            switch (getComponentName(componentJson)) {
                case "IDComponent":
                    component = new IDComponent(UUID.randomUUID().toString());
                    break;
                case "SpriteComponent":
                    component = new SpriteComponent();
                    ((SpriteComponent) component).defaultSprite = game.atlas.findRegion(componentJson.getString("defaultSprite"));
                    if (componentJson.has("isSpriteFacingLeft")) {
                        ((SpriteComponent) component).isSpriteFacingLeft = componentJson.getBoolean("isSpriteFacingLeft");
                    }
                    if (componentJson.has("isDefaultSprite")) {
                        ((SpriteComponent) component).isDefaultSprite = componentJson.getBoolean("isDefaultSprite");
                    }
                    break;
                default:
                    component = json.fromJson(Component.class, componentJson.toString());
            }
            entity.add(component);
        }
        return entity;
    }

    protected String getComponentName(JsonValue componentJson) {
        String fullPath = componentJson.getString("class");
        return fullPath.substring(fullPath.lastIndexOf('.') + 1);
    }
}
