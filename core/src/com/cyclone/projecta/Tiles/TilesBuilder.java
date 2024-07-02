package com.cyclone.projecta.Tiles;

public class TilesBuilder {

    public static Tile[][][] build(int width, int height, int depth) {
        Tile[][][] tiles = new Tile[width][height][depth];
        randomiseTiles(width, height, depth, tiles);
        smoothTiles(5, width, height, depth, tiles);
        return tiles;
    }

    private static void randomiseTiles(int width, int height, int depth, Tile[][][] tiles) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    tiles[x][y][z] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
                }
            }
        }
    }

    private static void smoothTiles(int times, int width, int height, int depth, Tile[][][] tiles) {
        Tile[][][] newTiles = new Tile[width][height][depth];
        for (int time = 0; time < times; time++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    for (int z = 0; z < depth; z++) {
                        int floors = 0;
                        int walls = 0;
                        for (int surroundingX = -1; surroundingX < 2; surroundingX++) {
                            for (int surroundingY = -1; surroundingY < 2; surroundingY++) {
                                if (x + surroundingX < 0 || x + surroundingX >= width || y + surroundingY < 0
                                        || y + surroundingY >= height) {
                                    continue;
                                }

                                if (tiles[x + surroundingX][y + surroundingY][z] == Tile.FLOOR) {
                                    floors++;
                                } else {
                                    walls++;
                                }
                            }
                            newTiles[x][y][z] = floors >= walls ? Tile.FLOOR : Tile.WALL;
                        }
                    }
                }
            }
        }
        tiles = newTiles;
    }
}