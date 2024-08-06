package com.cyclone.projecta.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.cyclone.projecta.components.PositionComponent;
import com.cyclone.projecta.components.SpriteComponent;
/**
 * Usage:
 *     PositionComponent pos = Mappers.position.get(entity)
 */
public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
}
