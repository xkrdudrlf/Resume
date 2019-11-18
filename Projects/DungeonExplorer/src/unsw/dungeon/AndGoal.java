package unsw.dungeon;

public class AndGoal extends CompositeGoal {
	/**
	 * Instantiate an Andgoal class from a dungeon
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
    	boolean flag = true;
        for (Goal subGoal : subGoals) {
            if (!subGoal.isEnd()) {
                flag = false;
            }
        }
        return flag;
    }
    /**
     * return a string "AND"
     */
    @Override
    public String toString() {
    	return "And";
    }
}
