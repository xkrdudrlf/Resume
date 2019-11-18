package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Sword can be picked up by a player and can be used by a player 5 times to kill the enemies.
 * Once it's used up, it disappears from a player's item list automatically.
 */
public class Sword extends Entity {
	private IntegerProperty durabilityScore;
	private BooleanProperty owned;
	
	/**
	 * this class gets created with a false for owned attribute, which means not owned by
	 * a player and the durability score 5.
	 * @param x
	 * @param y
	 */
	public Sword(int x,int y) {
		super(x, y);
		this.owned = new SimpleBooleanProperty(false);
		this.durabilityScore = new SimpleIntegerProperty(5);
	}
	/**
	 * @return durabilirytScore of a sword.
	 */
	public int getDurabilityScore() {
		return this.durabilityScore.get(); 
	}
	/**
	 * decrease the durabilityScore of this class by 1.
	 */
	public void decreaseDurabilityScore() {
		this.durabilityScore.set(this.getDurabilityScore() - 1);
	}
	/**
	 * @return durabilityScore(IntegerProperty) of this class.
	 */
	public IntegerProperty durabilityScore() {
		return this.durabilityScore;
	}
	/**
	 * @return owned(BooleanProperty) of this class.
	 */
	public BooleanProperty owned() {
		return this.owned;
	}
	/**
	 * set the status of owned attribute to the given status.
	 * @param status
	 */
	public void setOwned(boolean status) {
		this.owned.set(status);
	}
	/**
	 * Sword hits the enemy.
	 * Once it hits the enemy, it loses its 1 durabilityScore.
	 * When the durabilityScore becomes 0, it will be considered to be used up
	 * and get dumped(removed from a player's item list) by a player.
	 */
	public void hit(Player player) {
		this.decreaseDurabilityScore();
		if (this.getDurabilityScore() == 0) {
			player.removeFromItemList(this);
			this.setOwned(false);
		}
	}
	/**
	 * As an obtainable, sword can be picked up by a player.
	 * If a player already has a sword in an item list,
	 * a sword cannot be picked up since a player can carry only one sword at a time.
	 * Once a sword is picked up by a player, it will be removed from a dungeon/dungeon image.
	 */
	@Override
	public void pickedUpByPlayer(Dungeon dungeon,Player player) {
		if (player.getSword() == null) {
    		this.setOwned(true);
			this.visible().set(false);
			player.getItemList().add(this);
			dungeon.getEntities().remove(this);
		}
	}
}