package com.cyclone.ECS.utils.factories;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.cyclone.ECS.ECS;
import com.cyclone.ECS.components.IDComponent;
import com.cyclone.ECS.components.SpriteComponent;

import java.util.UUID;

/**
 * Factory class for creating generic entities from JSON files.
 */

public class EntityFactory {
    protected final ECS game;
    protected Array<String> specialisedComponents = new Array<>();


    public EntityFactory(ECS game) {
        this.game = game;
    }

    public Entity build(FileHandle jsonPath) {
        Entity entity = game.engine.createEntity();
        Json json = new Json();
        Array<Component> componentArray = json.fromJson(null, jsonPath);

        for (Component currentComponent : componentArray) {
            if (currentComponent instanceof IDComponent) {
                ((IDComponent) currentComponent).id = UUID.randomUUID().toString();
                entity.add(currentComponent);
            } else {
                entity.add(currentComponent);
            }
        }
        return entity;
    }
}
