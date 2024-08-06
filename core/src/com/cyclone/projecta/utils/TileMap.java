package com.cyclone.projecta.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;

/**
 * A 3D array of tiles
 * <p>
 * List of methods:
 * </p>
 * <ul>
 *     <li>getTile: return the Tile at the coordinate</li>
 *     <li>get/set/removeFloor: manipulate the floor at the coordinate</li>
 *     <li>get/set/removeEntity: manipulate the entity at the coordinate</li>
 * </ul>
 */
public class TileMap {
    private final Array<Array<Array<Tile>>> tiles;

    public TileMap(int depth, int width, int height) {
        tiles = new Array<>();
        for (int z = 0; z < depth; z++) {
            Array<Array<Tile>> tileZ = new Array<>();
            for (int x = 0; x < width; x++) {
                Array<Tile> tileX = new Array<>();
                for (int y = 0; y < height; y++) {
                    Tile tileY = new Tile();
                    tileX.add(tileY);
                }
                tileZ.add(tileX);
            }
            tiles.add(tileZ);
        }
    }

    public Tile getTile(int z, int x, int y) {
        return tiles.get(z).get(x).get(y);
    }

    public Entity getFloor(int z, int x, int y) {
        return tiles.get(z).get(x).get(y).getFloor();
    }

    public void setFloor(int z, int x, int y, Entity entity) {
        tiles.get(z).get(x).get(y).setFloor(entity);
    }

    public void removeFloor(int z, int x, int y) {
        tiles.get(z).get(x).get(y).setFloor(null);
    }

    public Entity getEntity(int z, int x, int y) {
        return tiles.get(z).get(x).get(y).getEntity();
    }

    public void setEntity(int z, int x, int y, Entity entity) {
        tiles.get(z).get(x).get(y).setEntity(entity);
    }

    public void removeEntity(int z, int x, int y) {
        tiles.get(z).get(x).get(y).setEntity(null);
    }
}
