package useItemTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

/**
 * User-Story-1 : As a player , I want to be able to move to a square which has a closed door 
 * if I have a key fit for the door so that i can open the door and move forward.
 */
class UseKeyTest {
	private Dungeon dungeon = new Dungeon(50,50);
	private Player player = new Player(dungeon,0,0);
	private Goal goal = new BouldersGoal(dungeon);
	/**
	 * Acceptance Criteria 1-1
	 * If a player carries a key whose id is same with the closed door, 
	 * then a player should be able to move to the square which has the closed door and the door should be opened. 
	 */
	@Test
	void testUseKey1() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Key key = new Key(1,1);
		key.setId(0);
		key.pickedUpByPlayer(dungeon,player);
		
		Door door = new Door(2,2);
		assertEquals(false, door.getStatus(), "Door is not closed\n");
		dungeon.addEntity(door);
		assertEquals(door.getId(), 0, "Door id is not 0\n");
		
		dungeon.clock("Down");
		dungeon.clock("Down");
		dungeon.clock("Right");
		dungeon.clock("Right");
		
		if (player.getX() != 2 || player.getY() != 2) {
			fail("Player cannot move to the square which has a closed door.\n");
		}
		assertEquals(true, door.getStatus(), "Door is not opened\n");
	}
	/**
	 * Acceptance Criteria 1-2
	 * If a player carries a key whose id is different with the closed door, 
	 * then a player should not be able to move to the square which has the closed door. 
	 */
	@Test
	void testUseKey2() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Key key = new Key(1,1);
		key.setId(1);
		key.pickedUpByPlayer(dungeon,player);
		
		Door door = new Door(2,2);
		assertEquals(false, door.getStatus(), "Door is not closed\n");
		dungeon.addEntity(door);
		assertEquals( 0, door.getId(), "Door id is not 0\n");
		
		dungeon.clock("Down");
		dungeon.clock("Down");
		dungeon.clock("Right");
		dungeon.clock("Right");
		
		if (player.getX() == 2 && player.getY() == 2) {
			fail("Player should not be able to move to the square which has a closed door.\n");
		}
		assertEquals( false, door.getStatus(), "Door should not be opened\n");
	}
	/**
	 * Acceptance Criteria 2
	 * Once a door is opened, it should remain open till the end of the game.
	 */
	@Test
	void testUseKey3() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Key key = new Key(1,1);
		key.setId(0);
		key.pickedUpByPlayer(dungeon,player);
		
		Door door = new Door(2,2);
		assertEquals( false, door.getStatus(),"Door is not closed\n");
		dungeon.addEntity(door);
		assertEquals( 0, door.getId(),"Door id is not 0\n");
		
		dungeon.clock("Down");
		dungeon.clock("Down");
		dungeon.clock("Right");
		dungeon.clock("Right");
		
		if (player.getX() != 2 || player.getY() != 2) {
			fail("Player cannot move to the square which has a closed door.\n");
		}
		assertEquals( true, door.getStatus(),"Door is not opened\n");
		
		dungeon.clock("Down");
		dungeon.clock("Down");
		dungeon.clock("Right");
		dungeon.clock("Right");
		
		assertEquals( true, door.getStatus(), "Door does not remain open\n");
	}
	/**
	 * Acceptance Criteria 3
	 * A key should be removed from a player's item list, once the key is used to open a door.
	 */
	@Test
	void testUseKey4() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Key key = new Key(1,1);
		key.setId(0);
		key.pickedUpByPlayer(dungeon,player);
		assertEquals( key, player.getKey(),"Key should be inside a player's item list.\n");
		
		Door door = new Door(2,2);
		assertEquals(false, door.getStatus(),"Door is not closed\n");
		dungeon.addEntity(door);
		assertEquals(0, door.getId(),"Door id is not 0\n");
		
		dungeon.clock("Down");
		dungeon.clock("Down");
		dungeon.clock("Right");
		dungeon.clock("Right");
		
		if (player.getX() != 2 || player.getY() != 2) {
			fail("Player cannot move to the square which has a closed door.\n");
		}
		assertEquals(true, door.getStatus(), "Door is not opened\n");
		assertEquals(null, player.getKey(), "Key must be removed from a player's item list after being used.\n");
	}

}
