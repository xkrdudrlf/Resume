package unsw.dungeon;

import unsw.dungeon.Enum.Direction;

/**
 * In this mode, Enemy run away from a player.
 */
public class RunawayMode extends Mode {
	
	Direction NorS = null;
	Direction WorE = null;
	// false for TOP/NORTH, true for WEST/EAST
	boolean directionFlag = false;
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
	 * If there is no way to move because of walls, enemy breaks the wall.
	 */
	@Override
	public void move() {
		if (enemy.getY() > player.getY()) {
			if (moveDown()) return;
			NorS = Direction.SOUTH;
		}
		if (enemy.getY() < player.getY()) {
			if (moveUp()) return;
			NorS = Direction.NORTH;
		}
		if (enemy.getX() < player.getX()) {
			if (moveLeft()) return;
			WorE = Direction.WEST;
		} 
		if (enemy.getX() > player.getX()) {
			if (moveRight()) return;
			WorE = Direction.EAST;
		}
		breakWall();
	}
	/**
	 * Enemy breaks the wall with the Following Order.
	 * When directionFlag is false, it breaks the wall on the south or north side.
	 * When directionFlag is true, it breaks the wall on the west or east side.
	 * In this way, enemy can break walls in turn between north/south and west/east.
	 */
	private void breakWall() {
		if (directionFlag == false && NorS == Direction.SOUTH) {
			directionFlag = true;
			if (enemy.getY() + 1 == dungeon.getHeight()) {
				return;
			}
			for (Entity e : this.dungeon.getEntity(enemy.getX(),enemy.getY() + 1)) {
				if (e instanceof Wall) {
					e.visible().set(false);
					this.dungeon.removeEntity(e);
					return;
				}
			}
		} else if (directionFlag == false && NorS == Direction.NORTH) {
			directionFlag = true;
			if (enemy.getY() - 1 == 0) {
				return;
			}
			
			for (Entity e : this.dungeon.getEntity(enemy.getX(),enemy.getY() - 1)) {
				if (e instanceof Wall) {
					e.visible().set(false);
					this.dungeon.removeEntity(e);
					return;
				}
			}
		} else if (directionFlag == true && WorE == Direction.WEST) {
			directionFlag = false;
			if (enemy.getX() - 1 == 0) {
				return;
			}
			for (Entity e : this.dungeon.getEntity(enemy.getX() - 1,enemy.getY())) {
				if (e instanceof Wall) {
					e.visible().set(false);
					this.dungeon.removeEntity(e);
					return;
				}
			}
		} else if (directionFlag == true && WorE == Direction.EAST) {
			directionFlag = false;
			if (enemy.getX() + 1 == dungeon.getWidth()) {
				return;
			}
			for (Entity e : this.dungeon.getEntity(enemy.getX() + 1,enemy.getY())) {
				if (e instanceof Wall && (enemy.getX() + 1) < dungeon.getWidth()) {
					e.visible().set(false);
					this.dungeon.removeEntity(e);
					return;
				}
			}
		}
	}
}
