package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.Dungeon;
/**
 * define a unified goal abstract for both
 * individual goal and composite goals
 * @author YUNXIANG ZHANG(z5083830)
 */
abstract public class Goal implements Subject {
	/**
	 * The abstract class has a dungeon, a dungeonController
	 * and 2 boolean values for achievement status and goal achievement display status. 
	 */
    protected Dungeon dungeon;
    private boolean status = false;
    private List<Observer> observers;
    
    /**
     * instantiate the Goal with a dungeon
     * @param dungeon the dungeon that for playing 
     */
    public Goal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.observers = new ArrayList<Observer>();
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
    	this.status = status;
    	notifyObserver();
    }
    /**
     * As a Subject, this class has observers and
     * this method attach a given observer to the observer list 
     * of this class.
     */
    public void attach(Observer obj) {
    	if (!observers.contains(obj)) {
    		observers.add(obj);
    	}
    }
    /**
     * As a Subject, this class has observers and
     * this method detach a given observer from the observer list 
     * of this class.
     */
    public void detach(Observer obj) {
    	if (observers.contains(obj)) {
    		observers.remove(obj);
    	}
    }
    /**
     * As a Subject, this class has observers and
     * this method notifies its observers.
     */
    public void notifyObserver() {
    	for (Observer obj : observers) {
    		obj.update(this);
    	}
    }
}
