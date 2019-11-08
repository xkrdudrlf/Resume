package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * Each entity has its own position(x,y) inside the dungeon.
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }
    /**
     * @return IntegerProperty x of an entity.
     */
    public IntegerProperty x() {
        return x;
    }
    /**
     * @return IntegerProperty y of an entity.
     */
    public IntegerProperty y() {
        return y;
    }
	/**
	 * @return actual int value of y position.
	 */
    public int getY() {
        return y().get();
    }
    /**
	 * @return actual int value of x position.
	 */
    public int getX() {
        return x().get();
    }
}
