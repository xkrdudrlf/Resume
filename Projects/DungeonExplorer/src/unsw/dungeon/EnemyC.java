package unsw.dungeon;

/**
 * 	The new enemy Type with slower speed and different setMode method
 *  with two local field : the moveFlag and mode
 */
public class EnemyC extends Enemy {
	private int moveFlag = 0;
	private Mode mode;

	/**
	 * Constructor class for Enemyc
	 * @param x the x position 
	 * @param y the y position
	 */
	public EnemyC(int x, int y) {
		super(x, y);
	}
	/**
	 * override the move method , makes the enemy move slowest
	 */
	
	@Override
	public void move() {
		if(moveFlag % 10 == 0) {
		    mode.move();
		}
		moveFlag++;
	}
	/**
	 * override the setMode method,
	 * the enemy will always in runawaymode 
	 * whenever the player is invincible or not
	 */
	@Override
	public void setMode(boolean invincibleMode) {
		if (invincibleMode == true) {
			mode = this.getRunawayMode();
		}
		if (invincibleMode == false) {
			mode = this.getRunawayMode();
		}
	}
}
