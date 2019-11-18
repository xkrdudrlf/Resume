package unsw.dungeon;
/**
 * boulder class: boulder type entity in the dungeon 
 * boulder can block the movement of other entities 
 * or be pushed by the player to achieve the FloorSwitch goal to 
 * complete the dungeon 
 * @author YUNXIANG ZHANG(z5083830)
 *
 */
public class Boulder extends Entity {
	/**
	 * inherit the constructor method in the super class Entity 
	 * gets the required wall with specified position 
	 * @param x
	 * @param y
	 */
	public Boulder(int x, int y) {
        super(x, y);
    }
	/**
     * check if there is empty square  behind the boulder or 
     * an unactivated FloorSwitch that makes the boulder
     * moveable , if so, move the boulder to another square
     * @param dungeon  A dungeon for playing
     * @param player   A player that plays in the dungeon 
     * @return  true or false to clarify if the boulder is able to be moved
     * by the player or not
     */
    public boolean ablePush(Dungeon dungeon, Player player) {

        int to_x = getX() - player.getX() + getX();
        int to_y = getY() - player.getY() + getY();
        for (Entity e : dungeon.getEntity(to_x, to_y)) {
            if (!(e instanceof FloorSwitch) || ((FloorSwitch) e).isActive(dungeon)) {
                return false;
            }
        }
        x().set(to_x);
        y().set(to_y);
        return true;
    }
    /**
     * invoke the ablepush method in the boulder class to check if the boulder
     * can be pushed or not
     * @param dungeon    A dungeon for playing
     * @param player     A player that plays in the dungeon
     * @return     True : if the boulder can be pushed, False if not
     */
    @Override
	public boolean blockPlayer(Dungeon dungeon,Player player) {
    	if (!this.ablePush(dungeon, player)) {
    		return true;
    	}
    	return false;
	}
    /**
     * @return  boulder always block the enemy, so it always return true
     */
    @Override
	public boolean blockEnemy() {
		return true;
	}
}
