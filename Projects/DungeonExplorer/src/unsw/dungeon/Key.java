package unsw.dungeon;
/**
 * Key can be picked up by a player.
 * If a player already has another key, it cannot be picked up.
 *
 */
public class Key extends Entity implements Obtainable {
	private int id = -2;
	
	public Key(int x,int y) {
		super(x,y);
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
	 * Once it's picked up, it will be removed from a dungeon and 
	 * will be added to the player's item list.
	 * If a player already has another key, it cannot be picked up.
	 */
	@Override
	public void pickedUpByPlayer(Dungeon dungeon,Player player) {
		if (player.getKey() == null) {
			player.getItemList().add(this);
			dungeon.getEntities().remove(this);
		}
	}
}