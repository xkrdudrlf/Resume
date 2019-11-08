package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 * <p>
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     *
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        loadGoal(dungeon, json.getJSONObject("goal-condition"));
        return dungeon;
    }
    /**
     * load the goal by the jason object to the dungeon 
     * to set individual goal and composite goals 
     * @param dungeon A dungeon for playing 
     * @param json  jsonobject
     */
    private void loadGoal(Dungeon dungeon, JSONObject json) {
        Goal goal = null;
        String type = json.getString("goal");
        if (type.equals("exit") || type.equals("enemies") || type.equals("boulders") || type.equals("treasure")) {
            goal = loadIndividualGoal(json, dungeon);
        } else {
            goal = loadCompositeGoal(json, dungeon);
        }
        dungeon.setGoal(goal);
    }
    /**
     * load the specified individual goal
     * @param json json object for loading 
     * @param dungeon dungeon for matched jasonobject
     * @return the specified type individual goal
     */
    private Goal loadIndividualGoal(JSONObject json, Dungeon dungeon) {
        Goal goal = null;
        switch (json.getString("goal")) {
            case "exit":
                goal = new ExitGoal(dungeon);
                break;
            case "enemies":
                goal = new EnemiesGoal(dungeon);
                break;
            case "boulders":
                goal = new BouldersGoal(dungeon);
                break;
            case "treasure":
                goal = new TreasureGoal(dungeon);
                break;
        }
        return goal;
    }
    /**
     * load the compositegoal to the dungeon
     * check the And Or type of the goals
     * @param json json object
     * @param dungeon  dungeon with matched jasonobject 
     * @return the CompositeGoal
     */
    private Goal loadCompositeGoal(JSONObject json, Dungeon dungeon) {
        Goal goal = null;
        if (json.getString("goal").equals("AND")) {
            goal = new AndGoal(dungeon);

        }
        if (json.getString("goal").equals("OR")) {
            goal = new OrGoal(dungeon);
        }
        JSONArray jsonEntities = json.getJSONArray("subgoals");
        for (int i = 0; i < jsonEntities.length(); i++) {
            JSONObject object = jsonEntities.getJSONObject(i);
            if (object.getString("goal").equals("AND") || object.getString("goal").equals("OR")) {
                goal.addSubGoal(loadCompositeGoal(object, dungeon));
            } else {
                goal.addSubGoal(loadIndividualGoal(object, dungeon));
            }
        }
        return goal;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        switch (type) {
            case "player":
                addPlayer(dungeon, x, y);
                break;
            case "wall":
                addWall(dungeon, x, y);
                break;
            case "door":
                addDoor(dungeon, x, y);
                break;
            case "treasure":
                addTreasure(dungeon, x, y);
                break;
            case "invincibility":
                addInvincibilityPortion(dungeon, x, y);
                break;
            case "key":
                addKey(dungeon, x, y);
                break;
            case "sword":
                addSword(dungeon, x, y);
                break;
            case "enemy":
                addEnemy(dungeon, x, y);
                break;
            case "exit":
                addExit(dungeon, x, y);
                break;
            case "boulder":
                addBoulder(dungeon, x, y);
                break;
            case "floor_switch":
                addFloorSwitch(dungeon, x, y);
                break;
            case "portal":
                addPortal(dungeon, x, y);
                break;
        }
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    public void addPlayer(Dungeon dungeon, int x, int y) {
        Player player = new Player(dungeon, x, y);
        dungeon.setPlayer(player);
        onLoad(player);
    }

    public void addWall(Dungeon dungeon, int x, int y) {
        Wall wall = new Wall(x, y);
        onLoad(wall);
        dungeon.addEntity(wall);
    }

    public void addDoor(Dungeon dungeon, int x, int y) {
        Door door = new Door(x, y);
        onLoad(door);
        dungeon.addEntity(door);
    }

    public void addTreasure(Dungeon dungeon, int x, int y) {
        Treasure treasure = new Treasure(x, y);
        onLoad(treasure);
        dungeon.addEntity(treasure);
    }

    public void addInvincibilityPortion(Dungeon dungeon, int x, int y) {
        InvincibilityPortion invincibilityPortion = new InvincibilityPortion(x, y);
        onLoad(invincibilityPortion);
        dungeon.addEntity(invincibilityPortion);
    }

    public void addKey(Dungeon dungeon, int x, int y) {
        Key key = new Key(x, y);
        onLoad(key);
        dungeon.addEntity(key);
    }

    public void addSword(Dungeon dungeon, int x, int y) {
        Sword sword = new Sword(x, y);
        onLoad(sword);
        dungeon.addEntity(sword);
    }

    public void addEnemy(Dungeon dungeon, int x, int y) {
        Enemy enemy = new Enemy(x, y);
        onLoad(enemy);
        dungeon.addEntity(enemy);
    }

    public void addBoulder(Dungeon dungeon, int x, int y) {
        Boulder boulder = new Boulder(x, y);
        onLoad(boulder);
        dungeon.addEntity(boulder);
    }

    public void addExit(Dungeon dungeon, int x, int y) {
        Exit exit = new Exit(x, y);
        onLoad(exit);
        dungeon.addEntity(exit);
    }

    public void addFloorSwitch(Dungeon dungeon, int x, int y) {
        FloorSwitch floorswitch = new FloorSwitch(x, y);
        onLoad(floorswitch);
        dungeon.addEntity(floorswitch);
    }

    public void addPortal(Dungeon dungeon, int x, int y) {
        Portal portal = new Portal(x, y);
        onLoad(portal);
        dungeon.addEntity(portal);
    }
    
    public abstract void onLoad(Door door);
    public abstract void onLoad(Treasure treasure);
    public abstract void onLoad(InvincibilityPortion invincibilityPortion);
    public abstract void onLoad(Key key);
    public abstract void onLoad(Sword sword);
    public abstract void onLoad(Enemy enemy);
    public abstract void onLoad(Boulder boulder);
    public abstract void onLoad(Exit exit);
    public abstract void onLoad(FloorSwitch floorswitch);
    public abstract void onLoad(Portal portal);
}
