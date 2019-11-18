package goalTest;

import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class Goaltest {
	 /**
     * Acceptance Criteria 1
     * A player can complete a dungeon when he archives the exit goal once move to the square witch has an exit.
     */
    @Test
    void testExitGoal(){
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 1);
        Goal goal = new ExitGoal(dungeon);
        dungeon.setPlayer(player);
        dungeon.setGoal(goal);
        dungeon.addEntity(new Exit(1, 1));
        dungeon.clock("Right");
        assertTrue(dungeon.gameCheck());
    }


    /**
     * Acceptance Criteria 2
     * A player can complete a dungeon when all the squares which have floor covered with boulders.
     */
    @Test
    void testSwitchGoal(){
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal goal = new BouldersGoal(dungeon);
        dungeon.setGoal(goal);
        dungeon.setPlayer(player);
        dungeon.addEntity(new Boulder(0, 1));
        dungeon.addEntity(new FloorSwitch(0, 2));
        assertFalse(dungeon.gameCheck());
        dungeon.clock("Down");
        assertTrue(dungeon.gameCheck());
    }

    /**
     * Acceptance Criteria 3
     * A player can complete a dungeon when all the enemies have been cleared.
     */
    @Test
    void testEnemiesGoal(){
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal goal = new EnemiesGoal(dungeon);
        dungeon.setPlayer(player);
        dungeon.setGoal(goal);
        dungeon.addEntity(new Sword(0, 1));
        dungeon.addEntity(new Enemy(0, 4));
        assertFalse(dungeon.gameCheck());
        dungeon.clock("Down");
        assertFalse(dungeon.gameCheck());
        dungeon.clock("Down");
        dungeon.clock("Down");
        assertTrue(dungeon.gameCheck());
    }

    /**
     * Acceptance Criteria 4
     * A player can complete a dungeon when he collects all the treasures.
     */
    @Test
    void testTreasureGoal() {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal goal = new TreasureGoal(dungeon);
        dungeon.setPlayer(player);
        dungeon.setGoal(goal);
        dungeon.addEntity(new Treasure(0, 1));
        dungeon.addEntity(new Treasure(0, 4));
        assertFalse(dungeon.gameCheck());
        dungeon.clock("Down");
        assertFalse(dungeon.gameCheck());
        dungeon.clock("Down");
        dungeon.clock("Down");
        dungeon.clock("Down");
        assertTrue(dungeon.gameCheck());
    }

    /**
     * Acceptance Criteria 5
     * A player can't complete a dungeon if there are any other goals left not completed.
     */
    @Test
    void testAndExitNoFinish(){
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal goal = new AndGoal(dungeon);
        dungeon.setPlayer(player);
        dungeon.setGoal(goal);
        goal.addSubGoal(new TreasureGoal(dungeon));
        goal.addSubGoal(new ExitGoal(dungeon));
        dungeon.addEntity(new Treasure(0, 1));
        dungeon.addEntity(new Exit(1, 0));
        dungeon.clock("Right");
        assertFalse(dungeon.gameCheck());
        dungeon.clock("Down");
        dungeon.clock("Left");
        assertFalse(dungeon.gameCheck());
        dungeon.clock("Right");
        dungeon.clock("Up");
        assertTrue(dungeon.gameCheck());
    }

    /**
     * Acceptance Criteria 6
     * A player can complete a dungeon when a composite goals completed.
     */
    @Test
    void testAndExitFinish() {
        Dungeon dungeon = new Dungeon(10, 10);
        Player player = new Player(dungeon, 0, 0);
        Goal endGoal = new AndGoal(dungeon);
        Goal subGoal = new OrGoal(dungeon);
        dungeon.setPlayer(player);
        dungeon.setGoal(endGoal);
        endGoal.addSubGoal(subGoal);
        endGoal.addSubGoal(new ExitGoal(dungeon));
        subGoal.addSubGoal(new TreasureGoal(dungeon));
        subGoal.addSubGoal(new BouldersGoal(dungeon));
        dungeon.addEntity(new Exit(1, 0));
        dungeon.addEntity(new Treasure(1, 1));
        dungeon.addEntity(new FloorSwitch(2, 1));
        dungeon.clock("Right");
        assertFalse(dungeon.gameCheck());
        dungeon.clock("Down");
        assertFalse(dungeon.gameCheck());
        dungeon.clock("Up");
        assertTrue(dungeon.gameCheck());
    }
	

}
