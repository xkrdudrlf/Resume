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
        return flag;
    }
}
