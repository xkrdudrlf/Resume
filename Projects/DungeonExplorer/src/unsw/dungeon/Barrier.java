package unsw.dungeon;
/**
 * Barrier blocks a player/an enemy 's movement. 
 */
public interface Barrier {
	/**
	 * Block a player's movement.
	 * @param dungeon
	 * @param player
	 * @return true if it blocks a player, false if it does not block.
	 */
	public boolean blockPlayer(Dungeon dungeon,Player player);
	/**
	 * Block an enemy's movement.
	 * @return true if it blocks an enemy, false if it does not block.
	 */
	public boolean blockEnemy();
}
