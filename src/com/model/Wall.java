package com.model;

/**
 * This class is a subclass of Entity. It initializes the Wall.
 * @author Alik Balika
 * @version 2020.12.19
 */
public class Wall extends Entity {

    /**
     * calls the super constructor and initializes the x and y coordinate
     * @param x the x - coordinate
     * @param y the y - coordinate
     */
    public Wall(int x, int y) {
        super(x, y);

        initWall();
    }

    /**
     * Gets the path name from the project directory and sets the image of the mouse
     */
    private void initWall() {
        setImage("src/resources/wall.png");
    }

}
