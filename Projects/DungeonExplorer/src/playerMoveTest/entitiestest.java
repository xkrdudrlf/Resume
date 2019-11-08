package playerMoveTest;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


public class entitiestest {
	
	
	
	/**
	 * Epic 1,User Story 1,ACceptance Criteria 1
	 */
	
	@Test
	void testPlayermoveable() {
		Dungeon dungeon = new Dungeon (10,10);
		Player player = new Player (dungeon,1,1);
		Goal goal = new ExitGoal(dungeon);
		dungeon.setGoal(goal);
		dungeon.setPlayer(player);
		dungeon.clock("Right");
		assertEquals(2,player.getX());
		dungeon.clock("Up");
		assertEquals(0,player.getY());
		dungeon.clock("Left");
		assertEquals(1,player.getX());
		dungeon.clock("Down");
		assertEquals(1,player.getY());
		
		
	}
		
	
    
    /**
     * Epic 1,User Story 2, Acceptance Criteria 2.1
     * A player should not be able to move to a square which has a wall.
     */
    @Test
    void testWall()  {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal goal = new ExitGoal(dungeon);
        dungeon.setGoal(goal);
        dungeon.setPlayer(player);
        dungeon.addEntity(new Wall(1, 0));
        dungeon.addEntity(new Wall(0, 1));
        dungeon.clock("Right");
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
        dungeon.clock("Down");
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    /**
     * Epic 1,User Story-3,Acceptance Criteria 3.1
     * A player should not be able to move to a square which has a closed door.
     */
    @Test
    void testClosedDoor()  {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
        Goal goal = new BouldersGoal(dungeon);
        dungeon.setGoal(goal);
        dungeon.addEntity(new Door(0, 1));
        dungeon.clock("Down");
        dungeon.clock("Down");
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    /**
     * Epic 1, User Story-4,Acceptance Criteria 3.1
     * A player should be able to move to a square which has a open door.
     */
    @Test
    void testOpenDoor()  {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
        Goal goal = new BouldersGoal(dungeon);
        dungeon.setGoal(goal);
        Door door = new Door(0, 1);
        door.setStatus(true);
        dungeon.addEntity(door);
        dungeon.clock("Down");
        dungeon.clock("Down");
        assertEquals(0, player.getX());
        assertEquals(2, player.getY());
    }

    /**
     * Epic 1,User Story-5,Acceptance Criteria 5.1
     * A player should be able to push the boulder 1 square away in the direction where a player move.
     */
    @Test
    void testPushBoulder()  {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal goal = new ExitGoal(dungeon);
        dungeon.setPlayer(player);
        dungeon.setGoal(goal);
        Boulder boulder = new Boulder(1, 0);
        dungeon.addEntity(boulder);
        FloorSwitch floorSwitch = new FloorSwitch(2, 0);
        dungeon.addEntity(floorSwitch);
        dungeon.addEntity(new Boulder(0, 1));
        dungeon.clock("Right");
        assertEquals(1, player.getX());
        assertEquals(2, boulder.getX());
        assertEquals(2, floorSwitch.getX());
        dungeon.clock("Right");
        assertEquals(2, player.getX());
        assertEquals(3, boulder.getX());
        assertEquals(2, floorSwitch.getX());
        dungeon.clock("Right");
        assertEquals(3, player.getX());
        assertEquals(4, boulder.getX());
        assertEquals(2, floorSwitch.getX());
    }

    /**
     * Epic 1,User Story-5,Acceptance Criteria 5.2
     * A player should not be able to push the boulder 1 square away if somethings behind the boulder.
     */
    @Test
    void testPushBoulderBlock() {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal goal = new ExitGoal(dungeon);
        dungeon.setPlayer(player);
        dungeon.setGoal(goal);
        Boulder boulder = new Boulder(1, 0);
        dungeon.addEntity(boulder);
        FloorSwitch floorSwitch = new FloorSwitch(2, 0);
        dungeon.addEntity(floorSwitch);
        dungeon.addEntity(new Boulder(3, 0));
        dungeon.clock("Right");
        dungeon.clock("Right");
        dungeon.clock("Right");
        dungeon.clock("Right");
        assertEquals(1, player.getX());
        assertEquals(2, boulder.getX());
    }

    /**
     * Epic 1,User-story6,Acceptance Criteria 6.1
     * A player should be able to move to  a square which has a floor switch.
     */
    @Test
    void testWalkSwitch() throws FileNotFoundException {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal goal = new ExitGoal(dungeon);
        dungeon.setPlayer(player);
        dungeon.setGoal(goal);
        FloorSwitch floorSwitch = new FloorSwitch(2, 0);
        dungeon.addEntity(floorSwitch);
        dungeon.clock("Right");
        assertEquals(1, player.getX());
        assertEquals(2, floorSwitch.getX());
        dungeon.clock("Right");
        assertEquals(2, player.getX());
        assertEquals(2, floorSwitch.getX());
        dungeon.clock("Right");
        assertEquals(3, player.getX());
        assertEquals(2, floorSwitch.getX());
    }
    
    
    /**
     * Epic 1,,User-story 7,Acceptance Criteria 7.1
     * A player should be able to move to a square which has a portal
     */
    @Test
    void testPortalMoveable() {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal goal = new ExitGoal(dungeon);
        dungeon.setPlayer(player);
        dungeon.setGoal(goal);
        Portal portal1 = new Portal(1, 0);
        portal1.setId(1);
        dungeon.clock("Right");
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
        dungeon.clock("Right");
        assertEquals(2, player.getX());
        assertEquals(0, player.getY());
    }

    /**
     * Epic 1,User-story8, Acceptance Criteria 8.1
     * A player should be able to relocate to a square which has a portal linked to the origin portal.
     */
    @Test
    void testPortalMove()  {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal goal = new ExitGoal(dungeon);
        dungeon.setPlayer(player);
        dungeon.setGoal(goal);
        Portal portal1 = new Portal(1, 0);
        portal1.setId(1);
        Portal portal2 = new Portal(10, 10);
        portal2.setId(1);
        Portal portal3 = new Portal(10, 10);
        portal3.setId(2);
        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);
        dungeon.addEntity(portal3);
        dungeon.clock("Right");
        assertEquals(10, player.getX());
        assertEquals(10, player.getY());
    }

}
