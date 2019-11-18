package useItemTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

/**
 * User-Story-2 : As a player, I want to use a sword so that I can kill the enemies.
 */
class UseSwordTest {
	private Dungeon dungeon = new Dungeon(50,50);
	private Player player = new Player(dungeon,0,0); 
	private Goal goal = new BouldersGoal(dungeon);
	/**
	 * Acceptance Criteria 1
	 * If a player stays in the same square with an enemy and carries a sword,
	 * the enemy should be considered to be hit to death by a sword by disappearing from the dungeon and sword durability point should decrease by 1.
	 * Acceptance Criteria 2
  	 * If a sword's durability point become 0 by used 5 times, the sword should be considered to be worn out and disappear from the player's item list.
 	 */
	@Test
	void testUseSword1() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Sword sword = new Sword(7,7);
		assertEquals(sword.getDurabilityScore(), 5, "Durability score should be 5\n");
		sword.pickedUpByPlayer(dungeon,player);
		
		Enemy enemy1 = new Enemy(1,1);
		Enemy enemy2 = new Enemy(2,2);
		Enemy enemy3 = new Enemy(3,3);
		Enemy enemy4 = new Enemy(4,4);
		Enemy enemy5 = new Enemy(5,5);
		dungeon.addEntity(enemy1);
		dungeon.addEntity(enemy2);
		dungeon.addEntity(enemy3);
		dungeon.addEntity(enemy4);
		dungeon.addEntity(enemy5);
		
		player.moveDown();
		player.moveRight();
		if (!dungeon.enemyCheck(player).isEmpty()) {
            player.attackOrDie(dungeon.enemyCheck(player));
        }
		assertEquals(4, player.getSword().getDurabilityScore(), "Durability score should be 4\n");
		assertEquals(dungeon.getEntity(1,1).isEmpty(), true, "Enemy1 should be dead\n");
		
		player.moveDown();
		player.moveRight();
		if (!dungeon.enemyCheck(player).isEmpty()) {
            player.attackOrDie(dungeon.enemyCheck(player));
        }
		assertEquals(3, player.getSword().getDurabilityScore(), "Durability score should be 3\n");
		assertEquals(dungeon.getEntity(1,2).isEmpty(), true, "Enemy2 should be dead\n");
		
		player.moveDown();
		player.moveRight();
		if (!dungeon.enemyCheck(player).isEmpty()) {
            player.attackOrDie(dungeon.enemyCheck(player));
        }
		assertEquals(2, player.getSword().getDurabilityScore(), "Durability score should be 2\n");
		assertEquals(dungeon.getEntity(1,3).isEmpty(), true, "Enemy3 should be dead\n");
		
		player.moveDown();
		player.moveRight();
		if (!dungeon.enemyCheck(player).isEmpty()) {
            player.attackOrDie(dungeon.enemyCheck(player));
        }
		assertEquals(1, player.getSword().getDurabilityScore(), "Durability score should be 1\n");
		assertEquals(dungeon.getEntity(1,4).isEmpty(), true, "Enemy4 should be dead\n");
		
		player.moveDown();
		player.moveRight();
		if (!dungeon.enemyCheck(player).isEmpty()) {
            player.attackOrDie(dungeon.enemyCheck(player));
        }
		assertEquals(null, player.getSword(), "Sword should be worn out and disappear from the item list\n");
		assertEquals(dungeon.getEntity(1,5).isEmpty(), true, "Enemy5 should be dead\n");
	}
	/**
	 * Acceptance Criteria 3
	 * A player should not need to use a sword to kill an enemy if a player is in an invincible mode.
	 */
	@Test
	void testUseSword2() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Sword sword = new Sword(7,7);
		assertEquals(sword.getDurabilityScore(), 5, "Durability score should be 5\n");
		sword.pickedUpByPlayer(dungeon,player);
		player.setInvincibleStatus(true);
		
		Enemy enemy1 = new Enemy(1,1);
		dungeon.addEntity(enemy1);
		player.moveDown();
		player.moveRight();
		if (!dungeon.enemyCheck(player).isEmpty()) {
            player.attackOrDie(dungeon.enemyCheck(player));
        }
		assertEquals(5, player.getSword().getDurabilityScore(), "Durability score should be 5\n");
		assertEquals(dungeon.getEntity(1,1).isEmpty(), true, "Enemy1 should be dead\n");
	}
}
