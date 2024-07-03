package com.cyclone.projecta.Tiles;

public enum Tile {
    FLOOR("."),
    WALL("#"),
    VOID("");

    private final String symbol;

    public String getSymbol() {
        return this.symbol;
    }

    Tile(String symbol) {
        this.symbol = symbol;
    }
}
