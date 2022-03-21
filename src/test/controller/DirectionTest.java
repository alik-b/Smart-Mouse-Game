package test.controller;

import com.controller.Direction;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @org.junit.jupiter.api.Test
    void getX() {
        Direction direction = Direction.LEFT;
        assertEquals(-25, direction.getX());

        direction = Direction.RIGHT;
        assertEquals(25, direction.getX());

        direction = Direction.UP;
        assertEquals(0, direction.getX());

        direction = Direction.DOWN;
        assertEquals(0, direction.getX());

    }

    @org.junit.jupiter.api.Test
    void getY() {
        Direction direction = Direction.LEFT;
        assertEquals(0, direction.getY());

        direction = Direction.RIGHT;
        assertEquals(0, direction.getY());

        direction = Direction.UP;
        assertEquals(-25, direction.getY());

        direction = Direction.DOWN;
        assertEquals(25, direction.getY());
    }
}