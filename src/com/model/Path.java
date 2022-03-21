package com.model;

/**
 * This class is a subclass of Entity. It initializes the Path.
 * @author Alik Balika
 * @version 2020.12.19
 */
public class Path extends Entity {

    /**
     * calls the super constructor and initializes the x and y coordinate
     * @param x the x - coordinate
     * @param y the y - coordinate
     */
    public Path(int x, int y) {
        super(x, y);

        initPath();
    }

    /**
     * Gets the path name from the project directory and sets the image of the mouse
     */
    private void initPath() {
        setImage("src/resources/path.png");
    }

}
