package com.model;

import com.controller.Moveable;

/**
 * This class is a subclass of Entity. It initializes the Mouse and allows the mouse to move.
 * @author Alik Balika
 * @version 2020.12.19
 */
public class Mouse extends Entity implements Moveable {

    /**
     * calls the super constructor and initializes the x and y coordinate
     * @param x the x - coordinate
     * @param y the y - coordinate
     */
    public Mouse(int x, int y) {
        super(x, y);

        initMouse();
    }

    /**
     * Gets the path name from the project directory and sets the image of the mouse
     */
    private void initMouse() {

        setImage("src/resources/mouse.png");
    }

    /**
     * Moves the mouse and sets x and y accoring to the amount moved.
     * @param x the x amount being moved
     * @param y the y amount being moved
     */
    public void move(int x, int y) {
        int dx = getX() + x;
        int dy = getY() + y;

        setX(dx);
        setY(dy);
    }

}
