package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A player can move around the dungeon, pick up obtainable
 * and kill the enemies using obtainables or get killed by
 * the enemies.
 */
public class Player extends Entity {
	/**
	 * Player has a dungeon where the player is,
	 * itemLIst where a player put picked up obtainables,
	 * enemies as observers for cheking the invincible mode,
	 * invincibleTimeCounter to determine the invincible mode and
	 * its lasting time(if this is greater than 0, then player is consider to be
	 * in an invincible mode), and alive status which indicates player's
	 * life status.
	 */
	 private Dungeon dungeon;
	 private List<Obtainable> itemList;
	 private List<Enemy> enemies;
	 private int invincibleTimeCounter = 0;
	 private boolean alive;

    /**
     * Create a player positioned in square (x,y) inside a given dungeon.
     * When a player gets created, its alive value is true which means a 
     * player is alive, not dead.
     * @param x - x position inside a dungeon.
     * @param y - y position inside a dungeon.
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.itemList = new ArrayList<Obtainable>();
        this.enemies = new ArrayList<Enemy>();
        this.alive = true;
    }
    /**
     * Add enemy as an observer to the player.
     * @param enemy
     */
    public void addEnemy(Enemy enemy) {
    	this.enemies.add(enemy);
    }
    /**
     * Player moves in the given direction.
     * @param direction
     */
    public void move(String direction) {
    	if (direction.equals("Up")) moveUp();
        else if (direction.equals("Down")) moveDown();
        else if (direction.equals("Left")) moveLeft();
        else if (direction.equals("Right")) moveRight();
        else System.out.println("Invalid direction.\n");
    }
    /**
     * A player moves up.
     */
    public void moveUp() {
    	if (!dungeon.checkMoveable(this,getX(),getY() - 1)) return;
        if (getY() > 0)
            y().set(getY() - 1);
    }
    /**
     * A player moves down.
     */
    public void moveDown() {
    	if (!dungeon.checkMoveable(this,getX(),getY() + 1)) return;
        if (getY() < dungeon.getHeight() - 1)
            y().set(getY() + 1);
    }
    /**
     * A player moves left.
     */
    public void moveLeft() {
    	if (!dungeon.checkMoveable(this,getX() - 1,getY())) return;
    	if (getX() > 0)
            x().set(getX() - 1);
    }
    /**
     * A player moves right.
     */
    public void moveRight() {
    	if (!dungeon.checkMoveable(this,getX() + 1,getY())) return;
    	if (getX() < dungeon.getWidth() - 1)
            x().set(getX() + 1);
    }
    /**
     * If a player is in an invincible mode(invincibleTimeCounter > 0),
     * decrease an invincibleTimeCounter by 1.
     * If an invincibleTimeCounter is 0, set a player's invincibleStatus to false
     * so that enemies(observers of a player) can be notified.
     */
    public void invincibileTimeUpdate() {
    	if (invincibleTimeCounter > 0) {
    		invincibleTimeCounter--;
    	} else if (invincibleTimeCounter == 0) {
    		setInvincibleStatus(false);
    	} else {
    		System.out.println("InvincibleTimeCounter never should be negative.\n");
    	}
    }
    /**
     * If there is no enemy in the same square or
     * a player is in an invincible mode or has a sword, a player survives.
     * Otherwise, a player gets killed by enemies in the same square.
     * @param enemies in a same position with a player.
     * @return true if a player survives from enemies. Otherwise, false(dead).
     */
    public boolean attackOrDie(List<Enemy> enemies) {
    	if (enemies.isEmpty()) return true;
    	
    	if (invincibleTimeCounter > 0) {
    		for (Enemy enemy : enemies) {	
        		enemy.attacked();
        	}
    		return true;
    	}
    	
		for (Enemy enemy : enemies) {
			if (getSword() == null) {
				System.out.println("You are dead.\n");
				this.setAlive(false);
				return false;
			} else {
				getSword().hit();
				enemy.attacked();
			}
		}
    	return true;
    }
    /**
     * A player picks up the items in a current square.
     */
    public void pickupItem() {
    	List<Entity> items = dungeon.getEntity(getX(),getY());
    	for(Entity e : items) {
    		if (e instanceof Obtainable) {
    			((Obtainable)e).pickedUpByPlayer(dungeon,this);
    		}
    	}
    }
    /**
     * @return a key in a player's item list. return null if there is none.
     */
    public Key getKey() {
    	for (Obtainable item : getItemList()) {
    		if (item instanceof Key)
    			return (Key)item;
    	}
    	return null;
    }
    /**
     * @return a sword in a player's item list. return null if there is none.
     */
    public Sword getSword() {
    	for (Obtainable item : getItemList()) {
    		if (item instanceof Sword)
    			return (Sword)item;
    	}
    	return null;
    }
    /**
     * @return a list of items in a player's item list.
     * if there is nothing, return empty list.
     */
    public List<Obtainable> getItemList() {
    	return this.itemList;
    }
    /**
     * If a status is true, player becomes invincible and
     * player will be invincible for next 10 moves.
     * If a status is false, player becomes non-invincible.
     * Whenever a player's status changes, enemies(observers) get notified for the change.
     * @param status * true - invinciblemode, * false - non-invinciblemode
     */
    public void setInvincibleStatus(boolean status) {
    	if (status == true) {
    		invincibleTimeCounter = 11;
    		for (Enemy enemy : enemies) {
    			enemy.setMode(status);
    		}
    	} else {
    		assert(invincibleTimeCounter == 0);
    		for (Enemy enemy : enemies) {
    			enemy.setMode(status);
    		}
    	}
    }
    /**
     * A player opens a given door.
     * @param door
     * @return true if a player can open the door. Otherwise, false.
     */
    public boolean open(Door door) {
        Key key = getKey();
        if (key == null) return false;
        if (key.getId() == door.getId()) {
            door.setStatus(true);
            getItemList().remove(key);
            return true;
        }
        return false;
    }
    /**
     * Dump the given item by removing it from the player's item list.
     * @param wornOutSword
     */
    public void dumpItem(Sword wornOutSword) {
        Sword sword = getSword();
        getItemList().remove(sword);
        System.out.println("Dump the worn out Sword.\n");
    }
    /**
     * @return a player's invincibleTimeCounter.
     */
    public int getInvincibleTimeCounter() {
    	return this.invincibleTimeCounter;
    }
    /**
     * @param status * true - alive, * false - dead.
     */
    public void setAlive(boolean status) {
    	this.alive = status;
    	if (status == false) {
    		dungeon.playerDied(this);
    	}
    }
    /**
     * @return player's alive status.
     */
    public boolean getAlive() {
    	return this.alive;
    }
}
