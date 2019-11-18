package unsw.dungeon;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
/**
 * ThemeFactory is an interface which has basic images for various entities of dungeon
 * and methods to get Images/Labels to decorate the dungeon interface under the concept of each theme.
 */
public interface ThemeFactory {
	public Image playerImage = new Image("/human_new.png");
	public Image closedDoorImage = new Image("/closed_door.png");
	public Image openedDoorImage = new Image("/open_door.png");
	public Image treasureImage = new Image("gold_pile.png");
	public Image invincibilityPortionImage = new Image("brilliant_blue_new.png");
	public Image keyImage = new Image("key.png");
	public Image swordImage = new Image("greatsword_1_new.png");
	public Image enemyImage = new Image("deep_elf_master_archer.png");
	public Image boulderImage = new Image("boulder.png");
	public Image exitImage = new Image("exit.png");
	public Image floorSwitchImage = new Image("pressure_plate.png");
	public Image portalImage = new Image("portal.png");
	public Image enemyBImage = new Image("gnome.png");
	public Image enemyCImage = new Image("hound.png");
	
	public Image getGroundImage();
	public Image getWallImage();
	public Label getMissionLabel();
	public Label getANDlabel();
	public Label getORlabel();
	public Label getCompleteLabel();
	public Label getItemListLabel();
	public Label getTreasureScoreLabel();
	public Label getDurabilityScoreLabel();
	public Label getInvincibilityScoreLabel();
}
