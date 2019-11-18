package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * A DungeonLoader creates necessary ImageViews from images
 * it gets from ThemeFactory Class and connects them via listeners to the model, 
 * and creates a controller.
 * Other than that, it also offers methods to load different dungeons with
 * different themes according to the difficulty of the game,  
 */
public class DungeonControllerLoader extends DungeonLoader {
	
    private List<ImageView> entities;
    public ThemeFactory dungeonTheme = new BasicTheme(); 
    //Images
    protected Image playerImage;
    protected Image wallImage;
    protected Image openedDoorImage;
    protected Image closedDoorImage;
    protected Image treasureImage;
    protected Image invincibilityPortionImage;
    protected Image keyImage;
    protected Image swordImage;
    protected Image enemyImage;
    protected Image boulderImage;
    protected Image exitImage;
    protected Image floorSwitchImage;
    protected Image portalImage;
    protected Image groundImage;
    protected Image enemyBImage;
    protected Image enemyCImage;
    //Labels
    protected Label MissionLabel;
    protected Label ANDLabel;
    protected Label ORLabel;
    protected Label CompleteLabel;
    protected Label ItemListLabel;
    protected Label TreasureScoreLabel;
    protected Label DurabilityScoreLabel;
    protected Label InvincibilityScoreLabel;
    
    public DungeonControllerLoader() {
        entities = new ArrayList<>();
    }
    // Added 2 functions..
    public Label getANDLabel() {
    	return dungeonTheme.getANDlabel();
    }
    public Label getORLabel() {
    	return dungeonTheme.getORlabel();
    }
    /**
     * It loads themes(images and labels) for the dungeon from a dungeonTheme attribute. 
     */
    public void loadTheme() {
    	playerImage = ThemeFactory.playerImage;
        closedDoorImage = ThemeFactory.closedDoorImage;
        openedDoorImage = ThemeFactory.openedDoorImage;
        treasureImage = ThemeFactory.treasureImage;
        invincibilityPortionImage = ThemeFactory.invincibilityPortionImage;
        keyImage = ThemeFactory.keyImage;
        swordImage = ThemeFactory.swordImage;
        enemyImage = ThemeFactory.enemyImage;
        boulderImage = ThemeFactory.boulderImage;
        exitImage = ThemeFactory.exitImage;
        floorSwitchImage = ThemeFactory.floorSwitchImage;
        portalImage = ThemeFactory.portalImage;
        enemyBImage = ThemeFactory.enemyBImage;
        enemyCImage = ThemeFactory.enemyCImage;
        groundImage = dungeonTheme.getGroundImage();
        wallImage = dungeonTheme.getWallImage();
        
        MissionLabel = dungeonTheme.getMissionLabel();
        //ANDLabel = dungeonTheme.getANDlabel();
        //ORLabel = dungeonTheme.getORlabel();
        CompleteLabel = dungeonTheme.getCompleteLabel();
        ItemListLabel = dungeonTheme.getItemListLabel();
        TreasureScoreLabel = dungeonTheme.getTreasureScoreLabel();
        DurabilityScoreLabel = dungeonTheme.getDurabilityScoreLabel();
        InvincibilityScoreLabel = dungeonTheme.getInvincibilityScoreLabel();
    }
    /**
     * Load an easy level dungeon with BasicTheme.
     * @return a dungeon
     * @throws FileNotFoundException
     */
    public Dungeon LoadEasyMode() throws FileNotFoundException {
    	this.dungeonTheme = new BasicTheme();
    	this.loadTheme();
    	return this.loadDungeonFromFile("EasyMode.json");
    }
    /**
     * Load an normal level dungeon with OceanTheme.
     * @return a dungeon
     * @throws FileNotFoundException
     */
    public Dungeon LoadNormalMode() throws FileNotFoundException {
    	this.dungeonTheme = new OceanTheme();
    	this.loadTheme();
    	return this.loadDungeonFromFile("NormalMode.json");
    }
    /**
     * Load an difficult level dungeon with ForestTheme.
     * @return a dungeon
     * @throws FileNotFoundException
     */
    public Dungeon LoadDifficultMode() throws FileNotFoundException {
    	this.dungeonTheme = new ForestTheme();
    	this.loadTheme();
    	return this.loadDungeonFromFile("Demo.json");
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }
    
    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(closedDoorImage);
        trackOpenStatus(door,view);
        addEntity(door, view);
    }
    
    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }
    
    @Override
    public void onLoad(InvincibilityPortion invincibilityPortion) {
        ImageView view = new ImageView(invincibilityPortionImage);
        addEntity(invincibilityPortion, view);
    }
    
    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }
    
    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }
    
    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }
    
    @Override
    public void onLoad(EnemyB enemy) {
        ImageView view = new ImageView(enemyBImage);
        addEntity(enemy, view);
    }
    
    @Override
    public void onLoad(EnemyC enemy) {
        ImageView view = new ImageView(enemyCImage);
        addEntity(enemy, view);
    }
    
    
    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }
    
    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }
    
    @Override
    public void onLoad(FloorSwitch floorswitch) {
        ImageView view = new ImageView(floorSwitchImage);
        addEntity(floorswitch, view);
    }
    
    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }
    
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
        entity.visible().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
            		Boolean oldValue, Boolean newValue) {
                node.setVisible(newValue);
            }
        });
    }
    /**
     * bind a status value of a door to an image of a door.
     * Hence, when a door's status value has changed to true,
     * this method will bind a door with an open door image from
     * a closed door image.
     * @param door
     * @param imageview
     */
    private void trackOpenStatus(Door door, ImageView imageview) {
    	door.status().addListener(new ChangeListener<Boolean>() {
    		@Override
    		public void changed(ObservableValue<? extends Boolean> observable,
    				Boolean oldValue, Boolean newValue) {
    			if (newValue == true) 
    				imageview.setImage(openedDoorImage);
    		}
    	});
    }
    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(this);
    }
    /**
     * @return a list of available image views
     */
    public List<ImageView> getEntities() {
    	return this.entities;
    }
}
