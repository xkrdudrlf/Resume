package unsw.dungeon;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 * ForestTheme Class has its own images for ground/wall entities
 * and colors/fonts/style to decorate the dungeon interface under the concept of Forest.
 */
public class ForestTheme implements ThemeFactory {
	private String missionColor = "#FFC300";
	private String completeColor = "#FF5733";
	private String itemListColor = "#1477E0";
	private String stdStyle = "-fx-font-weight: bold;";
	private Font majorFont = new Font("Arial",20);
	private Font minorFont = new Font("Arial",11);
	
	/**
	 * returns a ground image
	 */
	@Override
	public Image getGroundImage() {
		return new Image("/forest/grass.png");
	}
	/**
	 * returns a wall image
	 */
	@Override
	public Image getWallImage() {
		return new Image("/forest/tree.png");
	}
	/**
	 * returns a label for mission display
	 */
	@Override
	public Label getMissionLabel() {
		Label mission = new Label("  	Missions ");
        mission.setFont(majorFont);
        mission.setTextFill(Color.web(missionColor));
        mission.setStyle(stdStyle);
		return mission;
	}
	/**
	 * returns a label for string "AND"
	 */
	@Override
	public Label getANDlabel() {
		Label and = new Label("AND");
        and.setFont(minorFont);
        and.setTextFill(Color.web(missionColor));
        and.setStyle(stdStyle);
		return and;
	}
	/**
	 * returns a label for string "OR"
	 */
	@Override
	public Label getORlabel() {
		Label or = new Label("OR");
		or.setFont(minorFont);
		or.setTextFill(Color.web(missionColor));
		or.setStyle(stdStyle);
		return or;
	}
	/**
	 * returns a label for complete display
	 */
	@Override
	public Label getCompleteLabel() {
		Label complete = new Label("  	Complete");
        complete.setFont(majorFont);
        complete.setTextFill(Color.web(completeColor));
        complete.setStyle(stdStyle);
		return complete;
	}
	/**
	 * returns a label for item list display
	 */
	@Override
	public Label getItemListLabel() {
		Label mission = new Label("  	Item List ");
        mission.setFont(majorFont);
        mission.setTextFill(Color.web(itemListColor));
        mission.setStyle(stdStyle);
		return mission;
	}
	/**
	 * returns a label for treasureScore display
	 */
	@Override
	public Label getTreasureScoreLabel() {
		Label scoreLabel = new Label();
    	scoreLabel.setFont(majorFont);
    	scoreLabel.setTextFill(Color.web(itemListColor));
    	scoreLabel.setStyle(stdStyle);
		return scoreLabel;
	}
	/**
	 * returns a label for durabilityScore display
	 */
	@Override
	public Label getDurabilityScoreLabel() {
		Label durabilityScore = new Label();
        durabilityScore.setFont(majorFont);
        durabilityScore.setTextFill(Color.web(itemListColor));
        durabilityScore.setStyle(stdStyle);
		return durabilityScore;
	}
	/**
	 * returns a label for invincibilityScore display
	 */
	@Override
	public Label getInvincibilityScoreLabel() {
		Label InvincibilityScore = new Label();
    	InvincibilityScore.setFont(majorFont);
    	InvincibilityScore.setTextFill(Color.web(itemListColor));
    	InvincibilityScore.setStyle(stdStyle);
		return InvincibilityScore;
	}
}
