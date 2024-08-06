package com.cyclone.projecta.components;

import com.badlogic.ashley.core.Component;

/**
 * <p>
 * attributes:
 * </p>
 * <ul>
 *     <li>x: x coordinate in grid</li>
 *     <li>y: y coordinate in grid</li>
 * </ul>
 * <p>
 *     Json usage: leave empty fields as position will be determined on spawn
 * </p>
 */
public class PositionComponent implements Component {
    public int z;
    public int x;
    public int y;

    public PositionComponent(int z, int x, int y) {
        this.z = z;
        this.x = x;
        this.y = y;
    }
}
