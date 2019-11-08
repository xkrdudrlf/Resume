package pickupItemTest;
import unsw.dungeon.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * User-Story-2 : As a player, I should be able to pick up the key so that I can use it.
 */
class PickupKeyTest {
	private Dungeon dungeon = new Dungeon(50,50);
	private Player player = new Player(dungeon,0,0); 
	private Goal goal = new BouldersGoal(dungeon);
	
	/**
	 * Acceptance Criteria 1
	 * A player can pick up a key automatically when the player moves into the square containing it.
	 * Acceptance Criteria 2
	 * When a key is picked up successfully, it should be automatically added to the player's item list.
	 */
	@Test
	void testPickupKey1() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Key key = new Key(1,1);
		dungeon.addEntity(key);
		dungeon.clock("Down");
		dungeon.clock("Right");
		
		Key pickupKey = player.getKey();
		assertEquals(pickupKey, key, "Player cannot pick up a key : " + pickupKey + "\n");
	}
	/**
	 * Acceptance Criteria 3
	 * When a key is picked up successfully, the key entity should be removed from the dungeon entity so that it cannot be picked up again.
	 */
	@Test
	void testPickupKey2() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Key key = new Key(1,1);
		dungeon.addEntity(key);
		dungeon.clock("Down");
		dungeon.clock("Right");
		
		Key pickupKey = player.getKey();
		assertEquals(pickupKey, key, "Player cannot pick up a key : " + pickupKey + "\n");
		
		Key nullKey = null;
		for(Entity e : dungeon.getEntity(1, 1)) {
			if (e instanceof Key) {
				nullKey = (Key)e;
			}
		}
		assertEquals(nullKey,null, "PickupKey is still in a dungeon : " + nullKey + "\n");
	}
	/**
	 * Acceptance Criteria 4
	 * A player can carry only 1 key at a time.
	 */
	@Test
	void testPickupKey3() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Key key1 = new Key(1,1);
		key1.pickedUpByPlayer(dungeon,player);
		Key pickup_key = player.getKey();
		assertEquals(pickup_key, key1, "Player does not have a key : " + pickup_key + "\n");
		
		Key key2 = new Key(1,2);
		dungeon.addEntity(key2);
		dungeon.clock("Down");
		dungeon.clock("Right");
		dungeon.clock("Right");
		
		Key key3 = null;
		for(Entity e : dungeon.getEntity(1, 2)) {
			if (e instanceof Key) {
				key3 = (Key)e;
			}
		}
		assertEquals(key3,key2, "Player picked up another key : " + key3 + "\n");
	}
}










