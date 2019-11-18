package unsw.dungeon;

public class ExitGoal extends Goal {
	/**
	 * Instantiate a exit goal with a dungeon
	 * @param dungeon A dungeon for playing
	 */
	public ExitGoal(Dungeon dungeon) {
        super(dungeon);
    }
	/**
     * override the isEnd method in abstract goal class
     * check if exit goal is finished or not 
     * If a goal's status attribute value is not true when the goal has been achieved,
     * set the status value to true.
     * If a goal's status attribute value is not false when the goal has not been achieved,
     * set the status value to false.
     * @return True if the exit goal is finished , false if not
     * @see {@link dungeon.getplayer(),@link dungeon.getEntities(),@link player.getX(),
     * player.getY()}
     */
    @Override
    public boolean isEnd() {
        Player player = dungeon.getPlayer();
        for (Entity e : dungeon.getEntities()) {
            if (e instanceof Exit && e.getX() == player.getX() && e.getY() == player.getY()) {
            	if (this.getStatus() != true) {
            		this.setStatus(true);
            	}
            	return true;
            }
            if (this.getStatus() != false) {
            	this.setStatus(false);
            }
        }
        return false;
    }
    /**
     * retrun a string "Exit"
     */
    @Override
    public String toString() {
    	return "Exit";
    }
}
