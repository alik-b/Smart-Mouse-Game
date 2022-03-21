package com.controller;

/**
 * enum that holds the direction that the entity will go in
 * @author Alik Balika
 * @version 2020.12.19
 */
public enum Direction {

    LEFT(-25, 0), RIGHT(25, 0), UP(0, -25), DOWN(0, 25);

    private int x;
    private int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Creates a random int between 0-3 and returns a random direction object
     * using a switch statement
     *
     * @return a random direction object
     */
    public static Direction getRandDirection() {
        int rand = (int) (Math.random() * 4);
        return switch (rand) {
            case 0 -> Direction.LEFT;
            case 1 -> Direction.RIGHT;
            case 2 -> Direction.UP;
            case 3 -> Direction.DOWN;
            default -> throw new IllegalStateException("Unexpected value: " + rand);
        };
    }

}
