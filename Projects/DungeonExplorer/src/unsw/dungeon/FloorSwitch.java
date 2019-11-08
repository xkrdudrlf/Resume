package unsw.dungeon;

public class FloorSwitch extends Entity {
	/**
	 * Instantiate a FloorSwitch class with x and y
	 * @param x  the x position of the floorswitch
	 * @param y  the y position of the floorswitch 
	 */
    public FloorSwitch(int x, int y) {
        super(x, y);
    }
    /**
     * Check if the floorswitch is been triggered or not
     * Check if there is a boulder on the floorswitch
     * @param dungeon  The dungeon map for playing
     * @return    True if the floorswitch is triggered , false is not triggered.
     * @see (@link dungeon.getEntity(int , int)
     */
    public boolean isActive(Dungeon dungeon) {
        for (Entity e : dungeon.getEntity(getX(), getY())) {
            if (e instanceof Boulder)
                return true;
        }
        return false;
    }
}