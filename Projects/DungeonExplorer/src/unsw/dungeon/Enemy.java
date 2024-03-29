package unsw.dungeon;
/**
 * Depending on a player's status(Invincible or not),
 * enemy can have 2 modes and in each mode, enemy moves differently.
 * Generally enemy chases after a player to kill the player.
 * Once the life score gets 0 with getting attacked by a player, the enemy is considered to be dead. 
 */
public class Enemy extends Entity implements Observer {
	Mode chaseMode;
	Mode runawayMode;
	
	private Mode mode;
	private int lifeScore = 1;
	private Dungeon dungeon;

	public Enemy(int x,int y) {
		super(x,y);
	}
	/**
	 * Set a dungeon and a player to 2 possible modes of an enemy. 
	 * @param dungeon
	 * @param player
	 */
	public void setEnvironment(Dungeon dungeon,Player player) {
		this.dungeon = dungeon;
		chaseMode = new ChaseMode(dungeon,player,this);
		runawayMode = new RunawayMode(dungeon,player,this);
		mode = chaseMode;
	}
	/**
	 * Enemy moves. Move can be different depending on the current
	 * enemy's mode.
	 */
	public void move() {
		mode.move();
	}
	/**
	 * @return enemy's LifeScore.
	 */
	public int getLifeScore() {
		return this.lifeScore;
	}
	/**
	 * Change enemy's mode depending on a player's status.
	 * If a player is in an invincibleMode, change mode to the runawayMode.
	 * If a player is not in an invincibleMode, change mode to the chaseMode.
	 * @param invincibleMode if a player is in an invincibleMode, true.
	 * Otherwise, false.
	 */
	public void setMode(boolean invincibleMode) {
		if (invincibleMode == true) {
			mode = runawayMode;
		}
		if (invincibleMode == false) {
			mode = chaseMode;
		}
	}
	/**
	 * Enemy got attacked by a player.
	 * Enemy loses its LifeScore and is considered to be dead. 
	 */
	public void attacked() {
		lifeScore--;
		if (lifeScore == 0) {
			this.visible().set(false);
			dungeon.enemyDied(this);
		}
	}
	/**
	 * As an observer of a player class, this class gets updated by
	 * changing the mode.
	 * When a player class' invincibleTimve value is greater than 0,
	 * it changes its mode to runawayMode using setMode(true).
	 * Otherwise, it changes its mode to chaseMode using setMode(false).
	 */
	@Override
	public void update(Subject obj) {
		if (obj instanceof Player) {
			if (((Player) obj).getInvincibleTime() > 0) {
				this.setMode(true);
			} else {
				this.setMode(false);
			}
		}
	}
	public Mode getRunawayMode() {
		return this.runawayMode;
	}
	public Mode getChaseMode() {
		return this.chaseMode;
	}
}
	