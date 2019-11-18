package unsw.dungeon;
/**
 * InvincibilityPortion can be picked up by player.
 * Once it's picked up, it will be consumed automatically and set
 * a player in the invincible mode.
 */
public class InvincibilityPortion extends Entity {
	public InvincibilityPortion(int x, int y) {
        super(x, y);
    }
	/**
	 * A portion is picked up by a player.
	 * As soon as it's picked up, it will be consumed automatically and 
	 * set a player in an invincible mode. Also, once it's picked up,
	 * the portion will be removed from a dungeon and the image of
	 * a portion will disappear.
	 */
	@Override
	public void pickedUpByPlayer(Dungeon dungeon,Player player) {
		player.setInvincibleStatus(true);
		this.visible().set(false);
		dungeon.getEntities().remove(this);
	}
}