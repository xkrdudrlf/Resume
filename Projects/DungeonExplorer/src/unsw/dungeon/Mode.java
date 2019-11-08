package unsw.dungeon;
/**
 * Mode defines enemy's movement.
 */
public class Mode {
	protected Enemy enemy;
	protected Player player;
	protected Dungeon dungeon;
	/**
	 * Need a dungeon to checkMoveable for an enemy,
	 * player to keep track of a player's position 
	 * and an enemy to move the enemy's position.
	 * @param dungeon
	 * @param player
	 * @param enemy
	 */
	public Mode(Dungeon dungeon,Player player,Enemy enemy) {
		this.enemy = enemy;
		this.player = player;
		this.dungeon = dungeon;
	}
	/**
	 * Move method Should not be directly used from this Mode.
	 */
	public void move() {
		System.out.println("Move method Should not be directly used from Mode\n");
	};
	/**
	 * Move up the enemy.
	 * @return if it cannot move up return false. Otherwise return true;
	 */
	public boolean moveUp() {
    	if (!dungeon.checkMoveable(enemy,enemy.getX(),enemy.getY() - 1)) 
    		return false;
        if (enemy.getY() > 0) {
        	enemy.y().set(enemy.getY() - 1);
            return true;
        }
        return false;
    }
	/**
	 * Move down the enemy.
	 * @return if it cannot move down return false. Otherwise return true;
	 */
    public boolean moveDown() {
    	if (!dungeon.checkMoveable(enemy,enemy.getX(),enemy.getY() + 1)) {
    		return false;
    	}
        if (enemy.getY() < dungeon.getHeight() - 1) {
        	enemy.y().set(enemy.getY() + 1);
        	return true;
        }
        return false;
    }
    /**
	 * Move left the enemy.
	 * @return if it cannot move left return false. Otherwise return true;
	 */
    public boolean moveLeft() {
    	if (!dungeon.checkMoveable(enemy,enemy.getX() - 1,enemy.getY())) 
    		return false;
    	if (enemy.getX() > 0) {
    		enemy.x().set(enemy.getX() - 1);
    		return true;
    	}
    	return false;
    }
    /**
	 * Move right the enemy.
	 * @return if it cannot move right return false. Otherwise return true;
	 */
    public boolean moveRight() {
    	if (!dungeon.checkMoveable(enemy,enemy.getX() + 1,enemy.getY())) 
    		return false;
    	if (enemy.getX() < dungeon.getWidth() - 1) {
    		enemy.x().set(enemy.getX() + 1);
    		return true;
		}
    	return false;
    }
}
