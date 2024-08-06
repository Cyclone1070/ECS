package com.cyclone.projecta.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * <p>
 * attributes:
 * </p>
 * <ul>
 *      <li>spriteFacingLeft: whether sprite should face left (default) or right (flipped)</li>
 *      <li>isDefaultSprite: whether current sprite should be default, default to true</li>
 *      <li>defaultSprite: default sprite</li>
 * </ul>
 */
public class SpriteComponent implements Component {
    public boolean isSpriteFacingLeft = true;
    public boolean isDefaultSprite = true;
    public TextureAtlas.AtlasRegion defaultSprite;
}
