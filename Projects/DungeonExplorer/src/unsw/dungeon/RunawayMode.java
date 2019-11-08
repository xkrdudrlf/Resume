package unsw.dungeon;
/**
 * In this mode, Enemy run away from a player.
 */
public class RunawayMode extends Mode {
	/**
	 * @param dungeon
	 * @param player
	 * @param enemy
	 */
	public RunawayMode(Dungeon dungeon,Player player,Enemy enemy) {
		super(dungeon,player,enemy);
	}
	/**
	 * When an enemy run away from a player, it considers directions to move with the following precedence:
	 *  1. north or south
	 *  2. east or west
	 */
	@Override
	public void move() {
		if (enemy.getY() > player.getY()) {
			if (moveDown()) return;
		}
		if (enemy.getY() < player.getY()) {
			if (moveUp()) return;
		}
		if (enemy.getX() < player.getX()) {
			if (moveLeft()) return;
		}
		if (enemy.getX() > player.getX()) {
			if (moveRight()) return;
		}
	}
}
