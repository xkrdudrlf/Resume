package unsw.dungeon;

public class AndGoal extends CompositeGoal {
	/**
	 * Instantiate a Andgoal class from a dungeon
	 * @param dungeon A dungeon for playing
	 */
	public AndGoal(Dungeon dungeon) {
        super(dungeon);
    }
	/**
     * override the isEnd method inherited from CompositeGoal class
     * check if a Andgoal is completed or not
     * @return True if an Andgoal is finished ,False if not
     * @see {@link subGoal.isEnd()}
     */
    @Override
    public boolean isEnd() {
        for (Goal subGoal : subGoals) {
            if (!subGoal.isEnd()) {
                return false;
            }
        }
        return true;
    }
}
