/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A dungeon class can contain a single player, entities except a player and goal.
 * Each entity occupies a square and more than one entity can occupy the same square.
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
	 * the number of keys/doors/portals inside a dungeon,goal,timeline and treasureScore.
	 */
    private int width, height;
    private List<Entity> entities;
    private Player player;
    private int n_keys = 0;
    private int n_doors = 0;
    private int n_portals = 0;
    private Goal goal = null;
    private Timeline timeline;
    private IntegerProperty treasureScore;
    
    /**
     * Gets created with a size of a dungeon(width,height)
     */
    public Dungeon(int width, int height) {
    	this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.treasureScore = new SimpleIntegerProperty(0);
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
        		player.attach((Enemy)e);
        		((Enemy)e).setEnvironment(this,player);
        	}
        }
    }
    /**
     * @return a treasureScore attribute(IntegerProperty) of a Dungeon class
     */
    public IntegerProperty treasureScore() {
    	return this.treasureScore;
    }
    /**
     * @return a treasureScore of dungeon as an integer value.
     */
    public int getTreasureScore() {
    	return this.treasureScore.get();
    }
    /**
     * add 1 score to the current treasureScore
     */
    public void addTreasureScore() {
    	this.treasureScore.set(this.getTreasureScore() + 1);
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
			if (e.blockPlayer(this,player))
				return false;
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
    		if (e.blockEnemy())
    			return false;
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
    		getPlayer().attach(enemy);
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
	 * shows a Game Over pop-up message when player dies.
     * @param dead player
     */
    public void playerDied(Player corpse) {
    	this.removeEntity(corpse);
    	corpse.visible().set(false);
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("Game over");
    	alert.setHeaderText("Player is dead, mission failure.");
    	alert.show();
    	timeline.stop();
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
     * check if the goals of game are completed or not.
     * if all given goals are completed, it will show a player congrats pop-up message and
     * stop the timeline.
     * @return  if the goals are completed ,return true. if not ,return false
     */
    public boolean gameCheck() {
        if (this.getGoal().isEnd()) {
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Congratulation");
        	alert.setHeaderText("You complete the dungeon");
        	timeline.stop();
        	alert.show();
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
     * set a timeline to the dungeon.
     * @param timeline
     */
    public void setTimeline(Timeline timeline) {
    	this.timeline = timeline;
    }
    /**
     * remove the given entity from a dungeon.
     * @param e
     */
    public void removeEntity(Entity e) {
    	this.entities.remove(e);
    }
    /**
     * remove the dead enemy from a dungeon and player's observer list.
     * @param enemy
     */
    public void enemyDied(Enemy enemy) {
    	this.removeEntity(enemy);
    	this.getPlayer().detach(enemy);
    }
    /**
     * Dungeon's basic time unit.
     * when this method got invoked,
     * 1. player invincible time get updated
     * 2. enemies move
     * 3. checks any collisions between enemies and a player
     * 4. check if the goals have been achieved or not
     */
    public void tick() {
    	player.invincibileTimeUpdate();
    	this.enemyMove();
    	if (!player.attackOrDie(enemyCheck(player))) { 
    		return;
    	}
    	if (gameCheck()) {
    		player.setFinished(true);
    	}
    }
    /**
     * All the enemies inside the dungeon moves.
    */ 
    public void enemyMove() {
    	List<Enemy> enemies = new ArrayList<Enemy>();
    	for (Entity entity : this.getEntities()) {
    		if (entity instanceof Enemy) {
    			enemies.add((Enemy)entity);
    		}
    	}
    	for (Enemy enemy : enemies) {
    		enemy.move();
    	}
    }
}
