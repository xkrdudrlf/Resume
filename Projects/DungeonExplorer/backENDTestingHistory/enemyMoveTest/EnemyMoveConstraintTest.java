package enemyMoveTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

/**
 * User-Story-2 : As a player, I want enemies should not be able to move to a square which has a wall so that i can play a game with a valid maze.
 * User-Story-3 : As a player, I want enemies should not be able to move to a square which has a boulder so that they cannot totally block the way out to the exit.
 * User-Story-4 : As a player, I want enemies should not be able to move to a square which has a closed door so that they cannot approach me wherever i am.
 * User-Story-5 : As a player, I want enemies should be able to move to a square which has any entities except for closed door,boulder and wall entities so that they can chase after me without too many restrictions.
 */
class EnemyMoveConstraintTest {
	private Dungeon dungeon = new Dungeon(50,50);
	private Player player = new Player(dungeon,0,0); 
	private Goal goal = new BouldersGoal(dungeon);
	/**
	 * Acceptance Criteria 2-1
	 * An enemy cannot move to a square which has a wall.
	 */
	@Test
	void testWallConstraint() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Wall wall1 = new Wall(1,1);
		Wall wall2 = new Wall(1,2);
		Wall wall3 = new Wall(1,3);
		Wall wall4 = new Wall(2,1);
		Wall wall5 = new Wall(2,3);
		Wall wall6 = new Wall(3,1);
		Wall wall7 = new Wall(3,2);
		Wall wall8 = new Wall(3,3);
		Enemy enemy = new Enemy(2,2);
		
		dungeon.addEntity(wall1);
		dungeon.addEntity(wall2);
		dungeon.addEntity(wall3);
		dungeon.addEntity(wall4);
		dungeon.addEntity(wall5);
		dungeon.addEntity(wall6);
		dungeon.addEntity(wall7);
		dungeon.addEntity(wall8);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() != 2 || enemy.getY() != 2) {
			fail("Enemy should not be able to move\n");
		}
	}
	/**
	 * Acceptance Criteria 3-1
	 * An enemy cannot move to a square which has a boulder since enemies cannot push boulders
	 */
	@Test
	void testBoulderConstraint() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Boulder boulder1 = new Boulder(1,1);
		Boulder boulder2 = new Boulder(1,2);
		Boulder boulder3 = new Boulder(1,3);
		Boulder boulder4 = new Boulder(2,1);
		Boulder boulder5 = new Boulder(2,3);
		Boulder boulder6 = new Boulder(3,1);
		Boulder boulder7 = new Boulder(3,2);
		Boulder boulder8 = new Boulder(3,3);
		Enemy enemy = new Enemy(2,2);
		
		dungeon.addEntity(boulder1);
		dungeon.addEntity(boulder2);
		dungeon.addEntity(boulder3);
		dungeon.addEntity(boulder4);
		dungeon.addEntity(boulder5);
		dungeon.addEntity(boulder6);
		dungeon.addEntity(boulder7);
		dungeon.addEntity(boulder8);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() != 2 || enemy.getY() != 2) {
			fail("Enemy should not be able to move\n");
		}
	}
	/**
	 * Acceptance Criteria 4-1
	 * An enemy cannot move to a square which has a closed door.
	 */
	@Test
	void testClosedDoorConstraint() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Door door1 = new Door(1,1);
		Door door2 = new Door(1,2);
		Door door3 = new Door(1,3);
		Door door4 = new Door(2,1);
		Door door5 = new Door(2,3);
		Door door6 = new Door(3,1);
		Door door7 = new Door(3,2);
		Door door8 = new Door(3,3);
		Enemy enemy = new Enemy(2,2);
		
		dungeon.addEntity(door1);
		dungeon.addEntity(door2);
		dungeon.addEntity(door3);
		dungeon.addEntity(door4);
		dungeon.addEntity(door5);
		dungeon.addEntity(door6);
		dungeon.addEntity(door7);
		dungeon.addEntity(door8);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() != 2 || enemy.getY() != 2) {
			fail("Enemy should not be able to move\n");
		}
	}
	/**
	 * Acceptance Criteria 5-1
	 * A enemy can move to a square which has a player.
	 */
	@Test
	void testEnemyMovePlayer() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Enemy enemy = new Enemy(1,1);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Left");
		dungeon.clock("Left");
		assertEquals(false ,player.getAlive(), "Player should be dead by now");
		if (enemy.getX() != 0 || enemy.getY() != 0) {
			fail("Enemy should be in the same square with a player which is (0,0)\n");
		}
	}
	/**
	 * Acceptance Criteria 5-2
	 * A enemy can move to a square which has an exit.
	 */
	@Test
	void testEnemyMoveExit() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Exit exit1 = new Exit(1,1);
		Exit exit2 = new Exit(1,2);
		Exit exit3 = new Exit(1,3);
		Exit exit4 = new Exit(2,1);
		Exit exit5 = new Exit(2,3);
		Exit exit6 = new Exit(3,1);
		Exit exit7 = new Exit(3,2);
		Exit exit8 = new Exit(3,3);
		Enemy enemy = new Enemy(2,2);
		
		dungeon.addEntity(exit1);
		dungeon.addEntity(exit2);
		dungeon.addEntity(exit3);
		dungeon.addEntity(exit4);
		dungeon.addEntity(exit5);
		dungeon.addEntity(exit6);
		dungeon.addEntity(exit7);
		dungeon.addEntity(exit8);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() == 2 && enemy.getY() == 2) {
			fail("Enemy should be able to move\n");
		}
	}
	/**
	 * Acceptance Criteria 5-3
	 * A enemy can move to a square which has a treasure.
	 */
	@Test
	void testEnemyMoveTreasure() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Treasure treasure1 = new Treasure(1,1);
		Treasure treasure2 = new Treasure(1,2);
		Treasure treasure3 = new Treasure(1,3);
		Treasure treasure4 = new Treasure(2,1);
		Treasure treasure5 = new Treasure(2,3);
		Treasure treasure6 = new Treasure(3,1);
		Treasure treasure7 = new Treasure(3,2);
		Treasure treasure8 = new Treasure(3,3);
		Enemy enemy = new Enemy(2,2);
		
		dungeon.addEntity(treasure1);
		dungeon.addEntity(treasure2);
		dungeon.addEntity(treasure3);
		dungeon.addEntity(treasure4);
		dungeon.addEntity(treasure5);
		dungeon.addEntity(treasure6);
		dungeon.addEntity(treasure7);
		dungeon.addEntity(treasure8);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() == 2 && enemy.getY() == 2) {
			fail("Enemy should be able to move\n");
		}
	}
	/**
	 * Acceptance Criteria 5-4
	 * A enemy can move to a square which has an open door.
	 */
	@Test
	void testEnemyMoveOpenDoor() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Door door1 = new Door(1,1);
		Door door2 = new Door(1,2);
		Door door3 = new Door(1,3);
		Door door4 = new Door(2,1);
		Door door5 = new Door(2,3);
		Door door6 = new Door(3,1);
		Door door7 = new Door(3,2);
		Door door8 = new Door(3,3);
		door1.setStatus(true);
		door2.setStatus(true);
		door3.setStatus(true);
		door4.setStatus(true);
		door5.setStatus(true);
		door6.setStatus(true);
		door7.setStatus(true);
		door8.setStatus(true);
		Enemy enemy = new Enemy(2,2);
		
		dungeon.addEntity(door1);
		dungeon.addEntity(door2);
		dungeon.addEntity(door3);
		dungeon.addEntity(door4);
		dungeon.addEntity(door5);
		dungeon.addEntity(door6);
		dungeon.addEntity(door7);
		dungeon.addEntity(door8);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() == 2 && enemy.getY() == 2) {
			fail("Enemy should be able to move\n");
		}
	}
	/**
	 * Acceptance Criteria 5-5
	 * A enemy can move to a square which has a key.
	 */
	@Test
	void testEnemyMoveKey() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Key key1 = new Key(1,1);
		Key key2 = new Key(1,2);
		Key key3 = new Key(1,3);
		Key key4 = new Key(2,1);
		Key key5 = new Key(2,3);
		Key key6 = new Key(3,1);
		Key key7 = new Key(3,2);
		Key key8 = new Key(3,3);
		Enemy enemy = new Enemy(2,2);
		
		dungeon.addEntity(key1);
		dungeon.addEntity(key2);
		dungeon.addEntity(key3);
		dungeon.addEntity(key4);
		dungeon.addEntity(key5);
		dungeon.addEntity(key6);
		dungeon.addEntity(key7);
		dungeon.addEntity(key8);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() == 2 && enemy.getY() == 2) {
			fail("Enemy should be able to move\n");
		}
	}
	/**
	 * Acceptance Criteria 5-6
	 * A enemy can move to a square which has a floorswitch.
	 */
	@Test
	void testEnemyMoveFloorSwitch() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		FloorSwitch floorswitch1 = new FloorSwitch(1,1);
		FloorSwitch floorswitch2 = new FloorSwitch(1,2);
		FloorSwitch floorswitch3 = new FloorSwitch(1,3);
		FloorSwitch floorswitch4 = new FloorSwitch(2,1);
		FloorSwitch floorswitch5 = new FloorSwitch(2,3);
		FloorSwitch floorswitch6 = new FloorSwitch(3,1);
		FloorSwitch floorswitch7 = new FloorSwitch(3,2);
		FloorSwitch floorswitch8 = new FloorSwitch(3,3);
		Enemy enemy = new Enemy(2,2);
		
		dungeon.addEntity(floorswitch1);
		dungeon.addEntity(floorswitch2);
		dungeon.addEntity(floorswitch3);
		dungeon.addEntity(floorswitch4);
		dungeon.addEntity(floorswitch5);
		dungeon.addEntity(floorswitch6);
		dungeon.addEntity(floorswitch7);
		dungeon.addEntity(floorswitch8);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() == 2 && enemy.getY() == 2) {
			fail("Enemy should be able to move\n");
		}
	}
	/**
	 * Acceptance Criteria 5-7
	 * A enemy can move to a square which has a portal.
	 */
	@Test
	void testEnemyMovePortal() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		Portal portal1 = new Portal(1,1);
		Portal portal2 = new Portal(1,2);
		Portal portal3 = new Portal(1,3);
		Portal portal4 = new Portal(2,1);
		Portal portal5 = new Portal(2,3);
		Portal portal6 = new Portal(3,1);
		Portal portal7 = new Portal(3,2);
		Portal portal8 = new Portal(3,3);
		Enemy enemy = new Enemy(2,2);
	
		dungeon.addEntity(portal1);
		dungeon.addEntity(portal2);
		dungeon.addEntity(portal3);
		dungeon.addEntity(portal4);
		dungeon.addEntity(portal5);
		dungeon.addEntity(portal6);
		dungeon.addEntity(portal7);
		dungeon.addEntity(portal8);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() == 2 && enemy.getY() == 2) {
			fail("Enemy should be able to move\n");
		}
	}
	/**
	 * Acceptance Criteria 5-8
	 * A enemy can move to a square which has a sword.
	 */
	@Test
	void testEnemyMoveSword() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		Sword sword1 = new Sword(1,1);
		Sword sword2 = new Sword(1,2);
		Sword sword3 = new Sword(1,3);
		Sword sword4 = new Sword(2,1);
		Sword sword5 = new Sword(2,3);
		Sword sword6 = new Sword(3,1);
		Sword sword7 = new Sword(3,2);
		Sword sword8 = new Sword(3,3);
		Enemy enemy = new Enemy(2,2);
		
		dungeon.addEntity(sword1);
		dungeon.addEntity(sword2);
		dungeon.addEntity(sword3);
		dungeon.addEntity(sword4);
		dungeon.addEntity(sword5);
		dungeon.addEntity(sword6);
		dungeon.addEntity(sword7);
		dungeon.addEntity(sword8);
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() == 2 && enemy.getY() == 2) {
			fail("Enemy should be able to move\n");
		}
	}
	/**
	 * Acceptance Criteria 5-9
	 * A enemy can move to a square which has a invincibilityPortion.
	 */
	@Test
	void testEnemyMoveInvincibilityPortion() {
		dungeon.setPlayer(player);
		dungeon.setGoal(goal);
		
		InvincibilityPortion invincibilityPortion1 = new InvincibilityPortion(1,1);
		InvincibilityPortion invincibilityPortion2 = new InvincibilityPortion(1,2);
		InvincibilityPortion invincibilityPortion3 = new InvincibilityPortion(1,3);
		InvincibilityPortion invincibilityPortion4 = new InvincibilityPortion(2,1);
		InvincibilityPortion invincibilityPortion5 = new InvincibilityPortion(2,3);
		InvincibilityPortion invincibilityPortion6 = new InvincibilityPortion(3,1);
		InvincibilityPortion invincibilityPortion7 = new InvincibilityPortion(3,2);
		InvincibilityPortion invincibilityPortion8 = new InvincibilityPortion(3,3);
		Enemy enemy = new Enemy(2,2);
		
		dungeon.addEntity(invincibilityPortion1);
		dungeon.addEntity(invincibilityPortion2);
		dungeon.addEntity(invincibilityPortion3);
		dungeon.addEntity(invincibilityPortion4);
		dungeon.addEntity(invincibilityPortion5);
		dungeon.addEntity(invincibilityPortion6);
		dungeon.addEntity(invincibilityPortion7);
		dungeon.addEntity(invincibilityPortion8);	
		dungeon.addEntity(enemy);
		
		dungeon.clock("Right");
		if (enemy.getX() == 2 && enemy.getY() == 2) {
			fail("Enemy should be able to move\n");
		}
	}
}