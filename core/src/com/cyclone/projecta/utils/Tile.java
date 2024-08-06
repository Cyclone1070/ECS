package com.cyclone.projecta.utils;

import com.badlogic.ashley.core.Entity;

/**
 *  A single tile, consists of a floor and an entity
 */
public class Tile {
    private Entity floor;
    private Entity entity;

    public Entity getFloor() {
        return floor;
    }

    public void setFloor(Entity floor) {
        this.floor = floor;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
