package com.model;

/**
 * This class is a subclass of Cat. It initializes the Cat
 * @author Alik Balika
 * @version 2020.12.19
 */
public class Cat2 extends Cat {

    /**
     * calls the super constructor and initializes the x and y coordinate
     * @param x the x - coordinate
     * @param y the y - coordinate
     */
    public Cat2(int x, int y) {
        super(x, y);

        initCat();
    }

    /**
     * Gets the path name from the project directory and sets the image of cat2
     */
    private void initCat() {
        setImage("src/resources/cat2.png");
    }
}
