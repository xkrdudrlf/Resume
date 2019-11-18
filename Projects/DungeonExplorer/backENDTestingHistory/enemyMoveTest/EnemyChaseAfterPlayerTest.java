package enemyMoveTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
/**
 * User-Story-6 : As a player, I want enemies constantly move towards me so that a game can be more challenging to play with.
 * User-Story-7 : As a player, I want enemies should be able to kill me so that a game can be more challenging for me to play with.
 */
class EnemyChaseAfterPlayerTest {
	private Dungeon dungeon = new Dungeon(50,50);
	private Player player = new Player(dungeon,0,0);
	private Goal goal = new BouldersGoal(dungeon);
	/**
	 * Acceptance Criteria 6-1
	 * Enemies should always know where a player is and try to keep moving towards a player.
	 * Acceptance Criteria 6-2
	 * When enemies are chasing after a player, enemies firstly consider to move in north or south direction 
	 * and if there is no movement available towards north or south direction, then they secondly consider to move in west or east direction.
	 * Acceptance Criteria 7-1
	 * If an enemy stays in the same square with a player, then player should die.
	 */
	@Test
	void testEnemyMoveTowardsPlayer1() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Enemy enemy = new Enemy(3,3);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Left");
		if (enemy.getX() != 3 || enemy.getY() != 2) {
			fail("1: Enemy should move up first before Left/Right.\n");
		}
		dungeon.clock("Left");
		if (enemy.getX() != 3 || enemy.getY() != 1) {
			fail("2: Enemy should move up first before Left/Right.\n");
		}
		dungeon.clock("Left");
		if (enemy.getX() != 3 || enemy.getY() != 0) {
			fail("3: Enemy should move up first before Left/Right.\n");
		}
		dungeon.clock("Left");
		if (enemy.getX() != 2 || enemy.getY() != 0) {
			fail("4: Enemy should move left now as there is no move available upwards.\n");
		}
		dungeon.clock("Left");
		if (enemy.getX() != 1 || enemy.getY() != 0) {
			fail("5: Enemy should move left now as there is no move available upwards.\n");
		}
		dungeon.clock("Left");
		if (enemy.getX() != 0 || enemy.getY() != 0) {
			fail("6: Enemy should move left now as there is no move available upwards.\n");
		}
		assertEquals(false ,player.getAlive(), "Player should be dead by now");
	}
	void testEnemyMoveTowardsPlayer2() {
		Player player2 = new Player(dungeon,50,50);
		dungeon.setPlayer(player2);
		dungeon.setGoal(goal);
		
		Enemy enemy = new Enemy(0,0);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Down");
		if (enemy.getX() != 0 || enemy.getY() != 1) {
			fail("1: Enemy should move Down first before Left/Right.\n");
		}
	}
}
