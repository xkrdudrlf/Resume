package useItemTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;
/**
 * User-Story-3：As a player , I want to use invincibility potion so that I can become invincible.
 */
class UsePortionTest {
	private Dungeon dungeon = new Dungeon(50,50);
	private Player player = new Player(dungeon,0,0); 
	private Goal goal = new BouldersGoal(dungeon);
	/**
	 * Acceptance Criteria 1
	 * The player consumes an invincibility portion automatically and becomes invincible 
	 * as soon as the player moves to the square which has an invincibility portion.
	 * Acceptance Criteria 2
	 * When a player becomes invincible by consuming a portion,
	 * the portion should be removed from a dungeon so that it cannot be consumed again.
	 */
	@Test
	void testUsePortion1() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		assertEquals(0, player.getInvincibleTimeCounter(),"Player should not be in invincible mode\n");
		
		InvincibilityPortion portion = new InvincibilityPortion(1,1);;
		dungeon.addEntity(portion);
		
		dungeon.clock("Down");
		dungeon.clock("Right");
		assertEquals(11, player.getInvincibleTimeCounter(), "Player should've consumed a portion\n");
		assertEquals(true, dungeon.getEntity(1,1).isEmpty(), "Portion should've been removed from a dungeon\n");
	}
	/**
	 * Acceptance Criteria 3
	 * When a player becomes invincible, colliding with enemies should result in their immediate destruction.
	 * The definition of colliding is that the player and the enemy stay in the same square.
	 */
	@Test
	void testUsePortion2() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		assertEquals(0, player.getInvincibleTimeCounter(), "Player should not be in invincible mode\n");
		
		InvincibilityPortion portion = new InvincibilityPortion(1,1);
		portion.pickedUpByPlayer(dungeon,player);
		assertEquals(11, player.getInvincibleTimeCounter(), "Player should've consumed a portion\n");
		
		Enemy enemy1 = new Enemy(1,1);
		dungeon.addEntity(enemy1);
		
		player.moveDown();
		player.moveRight();
		player.attackOrDie(dungeon.enemyCheck(player));
		assertEquals(true, dungeon.getEntity(1,1).isEmpty(), "Enemy should've been killed by a player\n");
	}
	/**
	 * Acceptance Criteria 4
	 * The effect of the portion only lasts for next 10 moves.
	 */
	@Test
	void testUsePortion3() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		assertEquals(0, player.getInvincibleTimeCounter(), "Player should not be in invincible mode\n");
		
		InvincibilityPortion portion = new InvincibilityPortion(1,1);
		portion.pickedUpByPlayer(dungeon,player);
		assertEquals(11, player.getInvincibleTimeCounter(), "Player should've consumed a portion\n");
		
		for (int i = 1; i < 11; i++ ) {
			dungeon.clock("Down");
			assertEquals(11 - i, player.getInvincibleTimeCounter(), "Player should be in invincible mode\n");
		}
		dungeon.clock("Down");
		assertEquals( 0, player.getInvincibleTimeCounter(), "Player should not be in invincible mode\n");
		dungeon.clock("Down");
		assertEquals( 0, player.getInvincibleTimeCounter(), "Player should not be in invincible mode\n");
	}
}
