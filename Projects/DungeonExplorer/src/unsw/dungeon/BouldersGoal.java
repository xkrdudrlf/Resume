package unsw.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.FloorSwitch;

public class BouldersGoal extends Goal {
	/**
	 * Instantiate  a BouldersGoal class with a dungeon for playing
	 * @param dungeon A dungeon for playing 
	 */
    public BouldersGoal(Dungeon dungeon) {
        super(dungeon);
    }
    /**
     * override the isEnd method in Goal class,
     * check if a bouldergoal is finished or not
     * If a bouldergoal has been achieved, it changes the goal achievement status
     * to true, otherwise leave it false , which is a default value.
     * If one of boulders has been moved and now the goal is not achieved, it changes the
     * goal achievement status to false from true. 
     * @return  True if the boulder goal is finished, false if not
     * @see {@link dungeon.getEntities(),@FloorSwitch.isActive(dungeon)}
     */
    @Override
    public boolean isEnd() {
        boolean flag = true;
        for (Entity e : dungeon.getEntities()) {
            if (e instanceof FloorSwitch && !((FloorSwitch) e).isActive(dungeon)) {
                flag = false;
            }
        }
        if (flag == true && this.getStatus() != true) {
    		this.setStatus(true);
        } 
        if (flag == false && this.getStatus() != false) {
    		this.setStatus(false);
        }
        return flag;
    }
    /**
     * return a string "Switch"
     */
    @Override
    public String toString() {
    	return "Switch";
    }
}
