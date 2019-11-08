/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon class can contain a single player, entities except a player and goal.
 * Each entity occupies a square and More than one entity can occupy the same square.
 * Overall, This class can be used to 
 * 1. Get/Set the entities in a dungeon,
 * 2. Check positions of entities in a dungeon
 * 3. Do any other extra stuffs related to positions in a dungeon.
 * 4. Get/Set/Check goals to complete a dungeon.
 * @author Younggil Tak(z5192259)/ Zhang Jeff
 */
public class Dungeon {
	/**
	 * Has a size of dungeon(width,height),entities inside a dungeon, player,
	 * the number of keys/doors/portals inside a dungeon and goal.
	 */
    private int width, height;
    private List<Entity> entities;
    private Player player;
    private int n_keys = 0;
    private int n_doors = 0;
    private int n_portals = 0;
    private Goal goal = null;

    /**
     * Gets created with a size of a dungeon(width,height)
     */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
    }
    /**
     * @return width of a dungeon.
     */
    public int getWidth() {
        return width;
    }
    /**
     * @return height of a dungeon.
     */
    public int getHeight() {
        return height;
    }
    /**
     * @return a player set to this dungeon.
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * Set a given player to this dungeon.
     * When a player is added to the dungeon, it adds all enemies in the dungeon to the player
     * as observers and enemies also set their mode to chaseMode to chase after player.
     */
    public void setPlayer(Player player) {
        this.player = player;
        for (Entity e : entities) {
        	if (e instanceof Enemy) {
        		player.addEnemy((Enemy)e);
        		((Enemy)e).setEnvironment(this,player);
        	}
        }
    }
    /**
     * Examine if a player can move to position (x,y) or not.
     * @param x x position
     * @param y y position
     * @return if there is any blocking entity at position (x,y), return false. Otherwise return true.
     */
    public boolean checkMoveable(Player player,int x,int y) {
    	List<Entity> entities = getEntity(x,y);
    	for (Entity e : entities) {
    		if (e instanceof Barrier) {
    			if (((Barrier)e).blockPlayer(this,player)) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    /**
     * Examine if an enemy can move to position (x,y) or not.
     * @param x x position
     * @param y y position
     * @return if there is any blocking entity at position (x,y), return false. Otherwise return true.
     */
    public boolean checkMoveable(Enemy enemy,int x,int y) {
    	List<Entity> entities = getEntity(x,y);
    	for (Entity e : entities) {
    		if (e instanceof Barrier) {
    			if (((Barrier)e).blockEnemy()) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    /**
     * Add entity to a dungeon.
     */
    public void addEntity(Entity entity) {
    	entities.add(entity);
    }
    /**
     * Add door to a dungeon.
     */
    public void addEntity(Door door) {
    	door.setId(this.n_doors);
    	n_doors++;
    	entities.add(door);
    }
    /**
     * Add treasure to a dungeon.
     */
    public void addEntity(Treasure treasure) {
    	entities.add(treasure);
    }
    /**
     * Add key to a dungeon.
     */
    public void addEntity(Key key) {
    	key.setId(this.n_keys);
    	n_keys++;
    	entities.add(key);
    }
    /**
     * Add enemy to a dungeon.
     */
    public void addEntity(Enemy enemy) {
    	if (getPlayer() != null) {
    		getPlayer().addEnemy(enemy);
    		enemy.setEnvironment(this,player);
    	}
    	entities.add(enemy);
    }
    /**
     * Add portal to a dungeon.
     */
    public void addEntity(Portal portal) {
        entities.add(portal);
        portal.setId((n_portals)/ 2);
        n_portals++;
    }
    /**
     * @return all the entities except a player in a dungeon.
     */
    public List<Entity> getEntities() {
        return this.entities;
    }
    /**
     * Return entities at position(x,y).
     * @param x x position
     * @param y y position
     * @return if there are entities at position (x,y), return a list of them. Otherwise return empty list.
     */
    public List<Entity> getEntity(int x, int y) {
        List<Entity> result = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y)
                result.add(e);
        }
        return result;
    }
    /**
     * Remove a dead enemy(life score = 0) from a dungeon.
     * @param dead enemy
     */
    public void enemyDied(Enemy corpse) {
        entities.remove(corpse);
    }
    /**
	 * Print Game Over message when player died.
	 * (will be modified and do something more once the front-end part get implemented.
	 * like getting rid of an image of player/trigger the game over pop up message etc.)
     * @param dead player
     */
    public void playerDied(Player corpse) {
    	System.out.println("Game over\n");
    }
    /**
     * Set a goal of a dungeon.
     * @param goal
     */
    public void setGoal(Goal goal) {
        this.goal = goal;
    }
	/**
	 * Get a goal of a dungeon.
	 * @return goal
	 */
    public Goal getGoal() {
        return this.goal;
    }
    /**
     * A portal that teleports the player to the corresponding 
     * portal with the same ID
     * @param player  A player who plays the game
     * @param portal  A potral that teleports the player
     * @see {@link Dungeon.getEntities (),@link portal.getId(),@link entity.getX(),entity.getY()}
     */
    public void teleportPlayer(Player player,Portal portal) {
    	for (Entity entity : this.getEntities()) {
    		if (entity instanceof Portal && portal != entity && portal.getId() == ((Portal) entity).getId()) {
    			player.x().set(entity.getX());
    			player.y().set(entity.getY());
    		}
    	}
    }
    
    /**
     * check if the goals of game are completed or not
     * @return  if the goals are completed ,return true. if not ,return false
     */
    public boolean gameCheck() {
        if (this.getGoal().isEnd()) {
        	System.out.println("You complete the dungeon !!\n");
        	return true;
        }
        return false;
    }
    
    /**
     * check if the square where the player stay has a portal or not
     * @param player  The player who plays the game
     * @see {@link Dungeon.teleportPLayer(player,portal),@link Entity.getEntity(int
     * ,int}
     */
    public void portalCheck(Player player) {
        for (Entity e : getEntity(player.getX(),player.getY())) {
            if (e instanceof Portal) {
            	teleportPlayer(player,(Portal)e);
            }
        }
    }
    /**
     * @param player
     * @return enemies in the same position with a player.
     */
    public List<Enemy> enemyCheck(Player player) {
    	List<Enemy> enemies = new ArrayList<Enemy>();
    	for (Entity e : getEntity(player.getX(),player.getY())) {
    		if (e instanceof Enemy) {
    			enemies.add((Enemy)e);
    		}
    	}
    	return enemies;
    }
    /**
     * With a given direction, dungeon clock moves in 2 steps : tik() and tok().
     * If a player is already dead, nothing will happen.
     * @param direction a direction in which a player will move this time.
     */
    public void clock(String direction) {
    	if (player.getAlive() == false) return;
    	tik(direction);
    	tok();
    }
    /**
     * First step of 2 steps in 1 time frame of the dungeon.
     * In this time frame, a player moves in the given direction with invincibleTimeUpdate
     * and enemies also move after a player.
     * @param direction
     */
    public void tik(String direction) {
    	player.move(direction);
    	player.invincibileTimeUpdate();
    	enemyMove();
    }
    /**
     * Second step of 2 steps in 1 time frame of the dungeon.
     * In this time frame, a player check Enemy/Portal in the current square.
     * If there are any enemies in the same square, a player will kill them or die.
     * If there is a portal in the same square, after dealing with enemies,if any, a player can use the portal.
     * Also, player can pick up the item if there is any in the current square.
     * As a last step, dungeon check if the goal has been achieved or not.
     */
    public void tok() {
    	if (!player.attackOrDie(enemyCheck(player))) 
    		return;
    	portalCheck(player);
    	player.pickupItem();
    	gameCheck();
    }
    /**
     * All the enemies inside the dungeon moves.
     */
    public void enemyMove() {
    	for (Entity entity : entities) {
    		if (entity instanceof Enemy) {
    			((Enemy) entity).move();
    		}
    	}
    }
}
