package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Key can be picked up by a player.
 * If a player already has another key, it cannot be picked up.
 *
 */
public class Key extends Entity {
	private int id = -2;
	private BooleanProperty owned;
	
	public Key(int x,int y) {
		super(x,y);
		this.owned = new SimpleBooleanProperty(false);
	}
	/**
	 * @return id of a key.
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * @param id - id of a key
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * A Key is picked up by a player.
	 * Once it's picked up, it will be removed from a dungeon/dungeon image 
	 * and will be added to the player's item list.
	 * If a player already has another key, it cannot be picked up.
	 */
	@Override
	public void pickedUpByPlayer(Dungeon dungeon,Player player) {
		if (player.getKey() == null) {
			player.getItemList().add(this);
			this.visible().set(false);
			dungeon.getEntities().remove(this);
			this.owned.set(true);
		}
	}
	/**
	 * @return owned attribute(BooleanProperty) of the class.
	 */
	public BooleanProperty owned() {
		return this.owned;
	}
	/**
	 * set the value of owned attribute(BooleanProperty) of the class
	 * with the given boolean value.
	 * @param status
	 */
	public void setOwned(boolean status) {
		this.owned().set(status);
	}
}