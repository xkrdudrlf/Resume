package unsw.dungeon;

import unsw.dungeon.Dungeon;

import java.util.ArrayList;
/**
 * compositegoal class
 * @author YUNXIANG ZHANG (z5083830)
 *
 */
public class CompositeGoal extends Goal {
	/**
	 * composite goal class has a list for 
	 * storing subgoals
	 */
	protected ArrayList<Goal> subGoals;
	/**
     * Instantiate  a composite goal with a dungeon
     * @param dungeon  a certain dungeon for playing 
     */
    public CompositeGoal(Dungeon dungeon) {
        super(dungeon);
        subGoals = new ArrayList<>();
    }
    /**
     * override the subgoal method in the abstract goal class
     * add subgoals
     * @param name of subgoal
     */
    @Override
    public void addSubGoal(Goal goal) {
        subGoals.add(goal);
    }
}