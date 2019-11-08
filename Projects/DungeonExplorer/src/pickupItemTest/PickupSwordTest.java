package pickupItemTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

/**
 * User-Story-3:As a player, I should be able to pick up the sword so that I can use it.
 */
class PickupSwordTest {
	private Dungeon dungeon = new Dungeon(50,50);
	private Player player = new Player(dungeon,0,0); 
	private Goal goal = new BouldersGoal(dungeon);
	
	/**
	 * Acceptance Criteria 1
	 * A player should be able to pick up a sword automatically when the player moves into the square that contains the sword.
	 * Acceptance Criteria 2
	 * When a sword is picked up successfully, the sword should be automatically added to the player's item list.
	 */
	@Test
	void testPickupSword1() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Sword sword = new Sword(1,1);
		dungeon.addEntity(sword);
		dungeon.clock("Down");
		dungeon.clock("Right");
		
		Sword pickupSword = player.getSword();
		assertEquals(pickupSword, sword, "Player cannot pick up a sword : " + pickupSword + "\n");
	}
	
	/**
	 * Acceptance Criteria 3
	 * When a sword is picked up successfully, the sword entity should be removed from the dungeon so that it cannot be picked up again.
	 */
	@Test
	void testPickupSword2() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Sword sword = new Sword(1,1);
		dungeon.addEntity(sword);
		dungeon.clock("Down");
		dungeon.clock("Right");
		
		Sword pickupSword = player.getSword();
		assertEquals(pickupSword, sword, "Player cannot pick up a sword : " + pickupSword + "\n");
		
		Sword nullSword = null;
		for(Entity e : dungeon.getEntity(1, 1)) {
			if (e instanceof Sword) {
				nullSword = (Sword)e;
			}
		}
		assertEquals(nullSword, null, "PickupSword is still in a dungeon : " + nullSword + "\n");
	}
	
	/**
	 * Acceptance Criteria 4
	 * When a sword is picked up, the sword should have 5 durability score.
	 */
	@Test
	void testPickupSword3() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Sword sword = new Sword(1,1);
		dungeon.addEntity(sword);
		dungeon.clock("Down");
		dungeon.clock("Right");
		
		Sword pickupSword = player.getSword();
		assertEquals(pickupSword, sword, "Player cannot pick up a sword : " + pickupSword + "\n");
		assertEquals(pickupSword.getDurabilityScore(), 5, "Durability Score is not 5 : " + pickupSword.getDurabilityScore() +"\n");
	}
	/**
	 * Acceptance Criteria 5
	 * A player can carry only 1 sword at a time.
	 */
	@Test
	void testPickupSword4() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Sword sword1 = new Sword(1,1);
		sword1.pickedUpByPlayer(dungeon,player);
		Sword pickupSword = player.getSword();
		assertEquals(pickupSword, sword1, "Player does not have a sword : " + pickupSword + "\n");
		
		Sword sword2 = new Sword(1,2);
		dungeon.addEntity(sword2);
		dungeon.clock("Down");
		dungeon.clock("Right");
		dungeon.clock("Right");
		
		Sword sword3 = null;
		for(Entity e : dungeon.getEntity(1, 2)) {
			if (e instanceof Sword) {
				sword3 = (Sword)e;
			}
		}
		assertEquals(sword3, sword2, "Player picked up another sword : " + sword3 + "\n");
	}
}