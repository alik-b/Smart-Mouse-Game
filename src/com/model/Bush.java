package com.model;

/**
 * This class is a subclass of Entity. It initializes the Bush.
 * @author Alik Balika
 * @version 2020.12.19
 */
public class Bush extends Entity {

    /**
     * calls the super constructor and initializes the x and y coordinate
     * @param x the x - coordinate
     * @param y the y - coordinate
     */
    public Bush(int x, int y) {
        super(x, y);

        initBush();
    }

    /**
     * Gets the path name from the project directory and sets the image of the mouse
     */
    private void initBush() {
        setImage("src/resources/bush.png");
    }

}
