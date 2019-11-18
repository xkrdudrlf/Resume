package pickupItemTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;
/**
 * User-Story-1 : As a player, I should be able to pick up the treasure so that I can achieve the treasure collection goal to complete the dungeon.
 */
class PickupTreasureTest {
	private Dungeon dungeon = new Dungeon(50,50); 
	private Player player = new Player(dungeon,0,0); 
	private Goal goal = new BouldersGoal(dungeon);
	
	/**
	 * Acceptance Criteria 1
	 * When a player moves into the same square which holds the treasure, the player should be able to pick up the treasure automatically.
	 * Acceptance Criteria 2
	 * When a player picks up the treasure, the treasure should be added to the player's item list.
	 */
	@Test
	void testPickupTreasure1() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Treasure treasure = new Treasure(1,1);
		dungeon.addEntity(treasure);
		
		dungeon.clock("Down");
		dungeon.clock("Right");
		
		boolean find = false;
		for (Obtainable e: player.getItemList()) {
			if (e instanceof Treasure) {
				if (e == treasure)
					find = true;
			}
		}
		assertEquals(true, find, "Player cannot pick up a treasure.\n");
	}
	/**
	 * Acceptance Criteria 3
	 * When a treasure is picked up successfully, the treasure entity should be removed from the dungeon so that it cannot be picked up again.
	 */
	@Test
	void testPickupTreasure2() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Treasure treasure = new Treasure(1,1);
		dungeon.addEntity(treasure);
		
		dungeon.clock("Down");
		dungeon.clock("Right");
		
		Treasure nullTreasure = null;
		for(Entity e : dungeon.getEntity(1, 1)) {
			if (e instanceof Treasure) {
				nullTreasure = (Treasure)e;
			}
		}
		assertEquals(nullTreasure, null, "PickupTreasure is still in a dungeon : " + nullTreasure + "\n");
	}
}







