package unsw.dungeon;
/**
 * Obtainable can be picked up by a player.
 */
public interface Obtainable {
	public void pickedUpByPlayer(Dungeon dungeon,Player player);
}
