package com.model;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a superclass for most of the objects in the game. It holds the coordinates, the image, and
 * contains collision methods that each entity can use. It is an abstract class.
 * @author Alik Balika
 * @version 2020.12.19
 */
public abstract class Entity {

    /**
     * The amount that entities will move by
     */
    protected final int SPACE = 25;

    /**
     * The x and y coordinates of the entity
     */
    private int x;
    private int y;

    /**
     * The image of the entity
     */
    protected Image image;

    /**
     * initializes x and y in this class
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Initializes the image of the class
     * @param imageName the file name of the image
     */
    protected void setImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    /**
     * This method checks if there is a collision happening on the left side with the
     * entity being passed in.
     * @param entity the entity that is being checked
     * @return whether or not there is a collision
     */
    public boolean isLeftCollision(Entity entity) {
        return getX() - SPACE == entity.getX() && getY() == entity.getY();
    }

    /**
     * This method checks if there is a collision happening on the right side with the
     * entity being passed in.
     * @param entity the entity that is being checked
     * @return whether or not there is a collision
     */
    public boolean isRightCollision(Entity entity) {
        return getX() + SPACE == entity.getX() && getY() == entity.getY();
    }

    /**
     * This method checks if there is a collision happening on the top side with the
     * entity being passed in.
     * @param entity the entity that is being checked
     * @return whether or not there is a collision
     */
    public boolean isTopCollision(Entity entity) {
        return getY() - SPACE == entity.getY() && getX() == entity.getX();
    }

    /**
     * This method checks if there is a collision happening on the bottom side with the
     * entity being passed in.
     * @param entity the entity that is being checked
     * @return whether or not there is a collision
     */
    public boolean isBottomCollision(Entity entity) {
        return getY() + SPACE == entity.getY() && getX() == entity.getX();
    }


    /**
     * getters and setters:
     */

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
