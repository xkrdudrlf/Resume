package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.dungeon.Enum.Direction;

/**
 * A player can move around the dungeon, pick up items
 * and kill the enemies using items or get killed by
 * the enemies.
 */
public class Player extends Entity implements Subject{
	/**
	 * Player has a dungeon where the player is,
	 * itemLIst where a player put picked up items,
	 * enemies as observers for checking the invincible mode,
	 * invincibleTimeCounter to determine the invincible mode and
	 * its lasting time(if this is greater than 0, then player is consider to be
	 * in an invincible mode), and alive status which indicates player's
	 * life status and the finished status which indicates whether game has
	 * finished or not.
	 */
	 private Dungeon dungeon;
	 private List<Entity> itemList;
	 private List<Observer> observers;
	 private IntegerProperty invincibleTime;
	 private boolean alive;
	 private boolean finished;
	
	 /**
     * Create a player positioned in square (x,y) inside a given dungeon.
     * When a player gets created, its alive value is true which means a 
     * player is alive, not dead. And invincibleTime is set to 0 which means
     * its not in an invincible mode. Also, finished value is false which means
     * game has not finished yet.
     * @param x - x position inside a dungeon.
     * @param y - y position inside a dungeon.
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.itemList = new ArrayList<Entity>();
        this.observers = new ArrayList<Observer>();
        this.alive = true;
        this.invincibleTime = new SimpleIntegerProperty(0);
        this.finished = false;
    }
    /**
     * As a Subject, this class has observers and
     * this method attach a given observer to the observer list 
     * of this class.
     */
    @Override
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
	@Override
	public void detach(Observer obj) {
		if (observers.contains(obj)) {
			observers.remove(obj);
		}
	}
	/**
     * As a Subject, this class has observers and
     * this method notifies its observers.
     */
	@Override
	public void notifyObserver() {
		for (Observer obj : observers) {
			obj.update(this);
		}
	}
	/**
	 * @return invincibleTime attribute(IntegerProperty) of this class.
	 */
    public IntegerProperty invincibleTime() {
    	return this.invincibleTime;
    }
    /**
     * @return invincibleTime integer value of this class.
     */
    public int getInvincibleTime() {
    	return this.invincibleTime.get();
    }
    /**
     * set the invincibleTime with the given integer.
     * @param time
     */
    public void setInvincibleTime(int time) {
    	this.invincibleTime.set(time);
    }
    /**
     * decrease the invincibleTime value by 1.
     */
    public void decreaseInvincibleTime() {
    	this.invincibleTime.set(this.getInvincibleTime() - 1);
    }
    /**
     * Player moves in the given direction.
     * If a player is dead or game has been finished, the player cannot move.
     * When a player moves, these things happens in order: 
     * 1. If enemies and player are in a same square, fight or die.
     * 2. Check the portal in the current position and use if there is one.
     * 3. Pick up items if there are any in the current position.
     * 4. Check if a goal condition has been met or not.
     */
    public void move(Enum.Direction direction) {
        if (alive == false || finished == true) return;
    	if (direction.equals(Direction.NORTH)) moveUp();
        else if (direction.equals(Direction.SOUTH)) moveDown();
        else if (direction.equals(Direction.WEST)) moveLeft();
        else if (direction.equals(Direction.EAST)) moveRight();
    	
    	if (!attackOrDie(dungeon.enemyCheck(this))) 
    		return;
        dungeon.portalCheck(this);
        pickupItem();
        if (dungeon.gameCheck()) 
        	this.setFinished(true);
    }
    public void setFinished(boolean status) {
    	this.finished = status;
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
     * so that enemies(observers of a player) can be notified for the change.
     */
    public void invincibileTimeUpdate() {
    	if (this.getInvincibleTime() > 0) {
    		this.decreaseInvincibleTime();
    	} else if (this.getInvincibleTime() == 0) {
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
    	
    	if (this.getInvincibleTime() > 0) {
    		for (Enemy enemy : enemies) {	
        		enemy.attacked();
        	}
    		return true;
    	}
    	
		for (Enemy enemy : enemies) {
			if (getSword() == null) {
				this.setAlive(false);
				return false;
			} else {
				getSword().hit(this);
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
    		e.pickedUpByPlayer(dungeon,this);
    	}
    }
    /**
     * @return a key in a player's item list. return null if there is none.
     */
    public Key getKey() {
    	for (Entity item : getItemList()) {
    		if (item instanceof Key)
    			return (Key)item;
    	}
    	return null;
    }
    /**
     * @return a sword in a player's item list. return null if there is none.
     */
    public Sword getSword() {
    	for (Entity item : getItemList()) {
    		if (item instanceof Sword)
    			return (Sword)item;
    	}
    	return null;
    }
    /**
     * @return a list of items in a player's item list.
     * if there is nothing, return empty list.
     */
    public List<Entity> getItemList() {
    	return this.itemList;
    }
    /**
     * If a status is true, player becomes invincible and
     * player will be invincible for next 10 ticks of dungeon.
     * If a status is false, player becomes non-invincible.
     * Whenever a player's status changes, enemies(observers) get notified for the change.
     * @param status * true -> invinciblemode, * false -> non-invinciblemode
     */
    public void setInvincibleStatus(boolean status) {
    	if (status == true) {
    		this.setInvincibleTime(11);
    	}
		this.notifyObserver();
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
            key.setOwned(false);
            this.removeFromItemList(key);
            return true;
        }
        return false;
    }
    /**
     * @param status * true -> alive, * false -> dead.
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
    /**
     * remove the given entity from the player's itemList.
     * @param obs
     */
    public void removeFromItemList(Entity obs) {
    	this.getItemList().remove(obs);
    }
}