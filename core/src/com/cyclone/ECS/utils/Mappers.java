package com.cyclone.ECS.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.cyclone.ECS.components.PositionComponent;
import com.cyclone.ECS.components.SpriteComponent;
/**
 * Usage:
 *     PositionComponent pos = Mappers.position.get(entity)
 */
public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
}
