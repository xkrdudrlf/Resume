package enemyMoveTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

/*
 * User-Story-8 : As a player, I want enemies run away from me 
 * when I am in an invincibility mode by consuming an invincibility portion 
 * so that i cannot kill enemies easily for making games more challenging.
 */
class EnemyRunawayMoveTest {
	private Dungeon dungeon = new Dungeon(50,50);
	private Player player = new Player(dungeon,0,0);
	private Goal goal = new BouldersGoal(dungeon);
	/**
	 * Acceptance Criteria 1
	 * As soon as a player becomes invincible by consuming an invincibility portion,
	 * enemies should try to run away from the player.
	 * Acceptance Criteria 2
	 * When enemies are trying to run away from a player, they consider to move in an opposite direction from a player. 
	 * For example, if a player is in south direction, enemies will try to move in north direction.
	 * Acceptance Criteria 3
	 * When enemies are trying to run away in an opposite direction from a player, they firstly consider to move in north or south direction 
	 * and if there is no square available to move forward in north or south direction, they secondly consider to move in west or east direction.
	 */
	@Test
	void testRunawayMove() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Enemy enemy = new Enemy(48,48);
		dungeon.addEntity(enemy);
		
		player.setInvincibleStatus(true);
		dungeon.clock("Left");
		if (enemy.getX() != 48 || enemy.getY() != 49) {
			fail("1: Enemy should move down first before Left/Right.\n");
		}
		dungeon.clock("Left");
		if (enemy.getX() != 49 || enemy.getY() != 49) {
			fail("2: Enemy should move right now as there is no move available downwards.\n");
		}
	}

}
