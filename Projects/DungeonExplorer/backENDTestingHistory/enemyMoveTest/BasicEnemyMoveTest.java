package enemyMoveTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

/**
 * User-Story-1 : As a player, I want that enemies are able to move so that they can chase after me.
 */
class BasicEnemyMoveTest {
	private Dungeon dungeon = new Dungeon(50,50);
	private Player player = new Player(dungeon,0,0); 
	private Goal goal = new BouldersGoal(dungeon);
	/**
	 * Acceptance Criteria 1
	 * When a player moves , enemies also move in 4 directions(north,south,east,west) like a player.
	 */
	@Test
	void testBasicEnemyMoveTest() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Enemy enemy1 = new Enemy(7,7);
		Enemy enemy2 = new Enemy(5,3);
		dungeon.addEntity(enemy1);
		dungeon.addEntity(enemy2);
		
		dungeon.clock("Down");
		if (enemy1.getX() == 7 && enemy1.getY() == 7) {
			fail("Enemy1 should've moved\n");
		}
		if (enemy2.getX() == 5 && enemy2.getY() == 3) {
			fail("Enemy2 should've moved\n");
		}
	}

}
