package unsw.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Treasure;

public class TreasureGoal extends Goal {
	/**
	 * Instantiate a treasureGoal class with a dungeon
	 * @param dungeon  A dungeon for playing
	 */
	public TreasureGoal(Dungeon dungeon) {
        super(dungeon);
    }
	/**
     * override the isEnd method inherited from Goal Class
     * to check if a Treasure Goal is finished or not
     * Once it's achieved, it changes the status attribute of this class
     * to true. 
     * @return True if Treasure goal is finished ,false if not
     * @see {@link dungeon.getEntities()}
     */
    @Override
    public boolean isEnd() {
        for (Entity e : dungeon.getEntities()) {
            if (e instanceof Treasure)
                return false;
        }
        if (this.getStatus() != true) {
        	this.setStatus(true);
        }
        return true;
    }
    /**
     * return a string "Treasure"
     */
    @Override
    public String toString() {
    	return "Treasure";
    }
}