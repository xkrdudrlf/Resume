package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
/**
 * An entity in the dungeon.
 * Each entity has its own position(x,y) inside the dungeon.
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private BooleanProperty visible;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.visible = new SimpleBooleanProperty(true);
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
    /**
	 * Block a player's movement.
	 * @param dungeon
	 * @param player
	 * @return true if it blocks a player, false if it does not block.
	 */
	public boolean blockPlayer(Dungeon dungeon,Player player) {
		return false;
	}
	/**
	 * Block an enemy's movement.
	 * @return true if it blocks an enemy, false if it does not block.
	 */
	public boolean blockEnemy() {
		return false;
	}
	/**
	 * @return visible attribute(BooleanProperty) of an Entity class.
	 */
	public BooleanProperty visible() {
    	return visible;
    }
	/**
	 * this method doesn't do anything under the general entity class.
	 * Hence, this method will be overlapped by subclasses when its needed to be.
	 * @param dungeon
	 * @param player
	 */
	public void pickedUpByPlayer(Dungeon dungeon,Player player) {
		return;
	}
}
