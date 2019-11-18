package unsw.dungeon;
/**
 * new type of enemy with slower speed and different behaviour for setMode method
 * 
 *
 */

/**
 * The class EnemyB with two fields
 * moveFlag and mode
 */
public class EnemyB extends Enemy  {
	private int moveFlag = 0;
    private Mode mode;
    
    /**
     * The consturctor class for EnemyB
     * @param x  the x position
     * @param y  the y position
     */
   
	public EnemyB(int x, int y) {
		super(x, y);
	}
	
	/**
	 * override the move method to make the enemy move slower
	 */
	@Override
	public void move() {
		if(moveFlag % 5 == 0) {
		    mode.move();
		}
		moveFlag++;
		}
	
	/**
	 * override the setMode method,the enemyB will always
	 * in getChaseMode whenever the player in invincible or not
	 */
	
	@Override
	public void setMode(boolean invincibleMode) {
		if (invincibleMode == true) {
			mode = this.getChaseMode();
		}
		if (invincibleMode == false) {
			mode = this.getChaseMode();
		}
	}

}
