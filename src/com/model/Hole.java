package com.model;

/**
 * This class is a subclass of Entity. It initializes the Hole.
 * @author Alik Balika
 * @version 2020.12.19
 */
public class Hole extends Entity {

    /**
     * calls the super constructor and initializes the x and y coordinate
     * @param x the x - coordinate
     * @param y the y - coordinate
     */
    public Hole(int x, int y) {
        super(x, y);

        initHole();
    }

    /**
     * Gets the path name from the project directory and sets the image of the mouse
     */
    private void initHole() {
        setImage("src/resources/hole.png");
    }

}
