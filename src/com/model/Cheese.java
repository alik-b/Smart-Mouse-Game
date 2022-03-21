package com.model;

/**
 * This class is a subclass of Entity. It initializes the Cheese.
 * @author Alik Balika
 * @version 2020.12.19
 */
public class Cheese extends Entity {

    /**
     * calls the super constructor and initializes the x and y coordinate
     * @param x the x - coordinate
     * @param y the y - coordinate
     */
    public Cheese(int x, int y) {
        super(x, y);

        initCheese();
    }

    /**
     * Gets the path name from the project directory and sets the image of the mouse
     */
    private void initCheese() {
        setImage("src/resources/cheese.png");
    }

}
