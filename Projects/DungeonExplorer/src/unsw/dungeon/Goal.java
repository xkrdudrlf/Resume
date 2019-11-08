package unsw.dungeon;

import unsw.dungeon.Dungeon;
/**
 * define a unified goal abstract for both
 * individual goal and composite goals
 * @author YUNXIANG ZHANG(z5083830)
 */
abstract public class Goal {
	/**
	 * The abstract class has a dungeon and a boolean status 
	 */
    protected Dungeon dungeon;
    private boolean status = false;
    /**
     * instantiate the Goal with a dungeon
     * @param dungeon the dungeon that for playing 
     */
    public Goal(Dungeon dungeon) {
        this.dungeon = dungeon;
    }
    /**
     * add Subgoals 
     * @param goal  name of subgoal
     */
    public void addSubGoal(Goal goal) {
    }
    /**
     * test if goal is finished or not
     * @return initialized state is false
     */
    public boolean isEnd() {
        return false;
    }
    /**
     * get the status of goals
     * @return  true if the goal is finished , false if not
     */
    public boolean getStatus() {
    	return this.status;
    }
    /**
     * set the true or false to clarify a goal 
     * is finished or not
     * @param status  true or false for goals
     */
    public void setStatus(boolean status) {
    	this.status = true;
    }
}
