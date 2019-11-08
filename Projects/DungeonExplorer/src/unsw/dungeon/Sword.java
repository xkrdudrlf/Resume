package unsw.dungeon;
/**
 * Sword can be picked up by a player and can be used by a player 5 times to kill the enemies.
 * Once it's used up, it disappears from a player's item list automatically.
 */
public class Sword extends Entity implements Obtainable {
	private int durabilityScore = 5;
	private Player owner = null;
	
	public Sword(int x,int y) {
		super(x, y);
	}
	/**
	 * Set the given player as the owner so that a player(observer) can keep track of
	 * sword's durability score.
	 */
	public void setOwner(Player player) {
		owner = player;
	}
	/**
	 * Sword hits the enemy.
	 * Once it hits the enemy, it loses its 1 durabilityScore.
	 * When the durabilityScore becomes 0, it will be considered to be used up
	 * and get dumped(removed from a player's item list) by a player.
	 */
	public void hit() {
		durabilityScore--;
		if (durabilityScore == 0) {
			owner.dumpItem(this);
			owner = null;
		}
	}
	/**
	 * @return a sword's durabilityScore.
	 */
	public int getDurabilityScore() {
		return this.durabilityScore;
	}
	/**
	 * As an obtainable, sword can be picked up by a player.
	 * If a player already has a sword in an item list,
	 * a sword cannot be picked up since a player can carry only one sword at a time.
	 * Once a sword is picked up by a player, it will be removed from a dungeon.
	 */
	@Override
	public void pickedUpByPlayer(Dungeon dungeon,Player player) {
		if (player.getSword() == null) {
    		this.setOwner(player);
			player.getItemList().add(this);
			dungeon.getEntities().remove(this);
		}
	}
}