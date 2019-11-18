package unsw.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Entity;

/**
 * Instantiate the Enemy goal class with dungeon 
 * @param dungeon  The dungeon for playing
 */

public class EnemiesGoal extends Goal {
    public EnemiesGoal(Dungeon dungeon) {
        super(dungeon);
    }
/**
 * override the method from the Goal abstract class
 * check if the enemy goal is completed or not.
 * If the goal has been achieved, change the status attribute value 
 * of the class to true unless it's already set to true.
 * @return True if the goal is completed , false if not
 * @see {@link dungeon.getEntities()}
 */
    @Override
    public boolean isEnd() {
        for (Entity e : dungeon.getEntities()) {
            if (e instanceof Enemy)
                return false;
        }
        if (this.getStatus() != true) {
        	this.setStatus(true);
        }
        return true;
    }
    /**
     * return a string "Enemy"
     */
    @Override
    public String toString() {
    	return "Enemy";
    }
}