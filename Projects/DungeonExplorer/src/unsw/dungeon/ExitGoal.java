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
     * @return True if the exit goal is finished , false if not
     * @see {@link dungeon.getplayer(),@link dungeon.getEntities(),@link player.getX(),
     * player.getY()}
     */
    @Override
    public boolean isEnd() {
        Player player = dungeon.getPlayer();
        for (Entity e : dungeon.getEntities()) {
            if (e instanceof Exit && e.getX() == player.getX() && e.getY() == player.getY()) {
                return true;
            }
        }
        return false;
    }
}
