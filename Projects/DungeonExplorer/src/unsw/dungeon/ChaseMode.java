package unsw.dungeon;
/**
 * In this mode, Enemy chases after a player.
 */
public class ChaseMode extends Mode {
	/**
	 * @param dungeon
	 * @param player
	 * @param enemy
	 */
	public ChaseMode(Dungeon dungeon,Player player,Enemy enemy) {
		super(dungeon,player,enemy);
	}
	/**
	 * When an enemy chases after a player, it considers directions to move with the following precedence:
	 *  1. north or south
	 *  2. east or west
	 */
	@Override
	public void move() {
		if (enemy.getY() > player.getY()) {
			if (moveUp()) return;
		}
		if (enemy.getY() < player.getY()) {
			if (moveDown()) return;
		}
		if (enemy.getX() < player.getX()) {
			if (moveRight()) return;
		}
		if (enemy.getX() > player.getX()) {
			if (moveLeft()) return;
		}
	}
}
