package test.model;

import com.model.Cat1;
import com.model.Entity;
import com.model.Mouse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void getX() {
        Entity entity = new Mouse(50,50);
        assertEquals(50, entity.getX());
    }

    @Test
    void getY() {
        Entity entity = new Mouse(50,50);
        assertEquals(50, entity.getY());
    }

    @Test
    void isLeftCollision() {
        Entity entity = new Mouse(50,50);
        assertTrue(entity.isLeftCollision(new Cat1(25, 50)));
        assertFalse(entity.isLeftCollision(new Cat1(75, 50)));
    }

    @Test
    void isRightCollision() {
        Entity entity = new Mouse(50,50);
        assertTrue(entity.isRightCollision(new Cat1(75, 50)));
        assertFalse(entity.isRightCollision(new Cat1(125, 50)));
    }

    @Test
    void isTopCollision() {
        Entity entity = new Mouse(50,50);
        assertTrue(entity.isTopCollision(new Cat1(50, 25)));
        assertFalse(entity.isTopCollision(new Cat1(50, 75)));
    }

    @Test
    void isBottomCollision() {
        Entity entity = new Mouse(50,50);
        assertTrue(entity.isBottomCollision(new Cat1(50, 75)));
        assertFalse(entity.isBottomCollision(new Cat1(50, 25)));
    }
}