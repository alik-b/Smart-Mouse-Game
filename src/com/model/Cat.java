package com.model;

import com.controller.Moveable;

/**
 * This class is a subclass of Entity. It initializes the Cat and contains several methods to
 * check if the cat is moving. it is also an abstract class.
 * @author Alik Balika
 * @version 2020.12.19
 */
public abstract class Cat extends Entity implements Moveable {

    /**
     * The current x and y amount that the cat is moving.
     */
    private int currentX;
    private int currentY;

    /**
     * calls the super constructor and initializes the x and y coordinate
     * @param x the x - coordinate
     * @param y the y - coordinate
     */
    public Cat(int x, int y) {
        super(x, y);
    }

    /**
     * Moves the cat and sets x and y according to the amount moved.
     * @param x the x amount being moved
     * @param y the y amount being moved
     */
    public void move(int x, int y) {

        int dx = getX() + x;
        int dy = getY() + y;

        setX(dx);
        setY(dy);
    }

    /**
     * This method checks if the cat is moving right.
     * @return whether or not the cat is moving right
     */
    public boolean isMovingRight() {
        return currentX == SPACE && currentY == 0;
    }

    /**
     * This method checks if the cat is moving left.
     * @return whether or not the cat is moving left
     */
    public boolean isMovingLeft() {
        return currentX == -SPACE && currentY == 0;
    }

    /**
     * This method checks if the cat is moving up.
     * @return whether or not the cat is moving up
     */
    public boolean isMovingUp() {
        return currentX == 0 && currentY == -SPACE;
    }

    /**
     * This method checks if the cat is moving down.
     * @return whether or not the cat is moving down
     */
    public boolean isMovingDown() {
        return currentX == 0 && currentY == SPACE;
    }

    /**
     * This method checks if the cat is stopped
     * @return whether or not the cat is stopped
     */
    public boolean isCatStopped() {
        return currentX == 0 && currentY == 0;
    }

    /**
     * getters and setters:
     */
    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
}
