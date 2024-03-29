package unsw.dungeon;

import unsw.dungeon.Dungeon;

public class OrGoal extends CompositeGoal {
	/**
	 * Instantiate an Orgoal class with a dungeon
	 * @param dungeon  A dungeon for playing
	 */
	public OrGoal(Dungeon dungeon) {
        super(dungeon);
    }
	/**
     * override the isEnd in the CompositeGoal class
     * check if an or goal is finished or not
     * @return True if an orgoal is finished , false if not
     * @see {@link subGoal.isEnd()}
     */
    @Override
    public boolean isEnd() {
    	boolean flag = false;
        for (Goal subGoal : subGoals) {
            if (subGoal.isEnd()) {
                flag = true;
            }
        }
        return flag;
    }
    /**
     * return string "OR"
     */
    @Override
    public String toString() {
    	return "Or";
    }
}
