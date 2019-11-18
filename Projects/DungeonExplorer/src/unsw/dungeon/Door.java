package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Door can be open or closed.
 * If it's closed it will block enemy/player's movement.
 * When it's closed, it can only be opened by a key whose id is same with the door.
 */
public class Door extends Entity {
	private int id = -1;
	private BooleanProperty status; // true: open, false: closed.
	
	public Door(int x,int y) {
		super(x,y);
		this.status = new SimpleBooleanProperty(false);
	}
	/**
	 * @return id of a door.
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * set id of a door.
	 * @param id 
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return if a door is closed return false. Otherwise, return true.
	 */
	public boolean getStatus() {
		return this.status.get();
	}
	/**
	 * Set door's closed/open status.
	 * false for closed, true for open.
	 * @param status
	 */
	public void setStatus(boolean status) {
		this.status.set(status);
	}
	/**
	 * If a door is open, return true.
	 * If a door is closed and a player can open it, return true. Otherwise return false.
	 * @return If a door blocks a player's movement return true, otherwise return false.
	 */
	@Override
	public boolean blockPlayer(Dungeon dungeon,Player player) {
		if (this.getStatus() == false) {
			return !player.open(this);
		}
		return false;
	}
	/**
	 * If a door is open, return true. Otherwise return false.
	 * @return If a door blocks an enemy's movement return true, otherwise return false.
	 */
	@Override
	public boolean blockEnemy() {
		if (this.getStatus() == false) {
			return true;
		}
		return false;
	}
	/**
	 * @return a status attribute(BooleanProperty) of a Door entity.
	 */
	public BooleanProperty status() {
		return this.status;
	}
}