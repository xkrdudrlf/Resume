package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.animation.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * A JavaFX controller for the dungeon.
 * This class is generally in charge of interface part of a dungeon game
 * like displaying the dungeon image and other interface parts of the game.
 * Also, it gives a player a choice to choose which dungeon to play with 
 * and starts the time line of a dungeon.
 */
public class DungeonController implements Observer {

    @FXML
    private GridPane squares;
    
    private List<ImageView> initialEntities;
    private ImageView exit;
    private ImageView floorSwitch;
    private Timeline timeline = new Timeline();   
    
    private Player player;
    private Dungeon dungeon;
    private DungeonControllerLoader dungeonControllerLoader;
    
    private int successCount = 1;
    private int leastHeight;
    private int time;
    
    private Label treasureScoreLabel;
    private Label durabilityScoreLabel;
    private Label invincibilityScoreLabel;
    
    /**
     * This class gets created with having a dungeonControllerLoader as a class attribute
     * @param dungeonControllerLoader
     */
    public DungeonController(DungeonControllerLoader dungeonControllerLoader) {
    	this.dungeonControllerLoader = dungeonControllerLoader;
    }
    /**
   	 * determines the procedures to go through at the start of the program.
   	 * 1. select game mode.
   	 * 2. load dungeon image.
   	 * 3. load goal interface image
   	 * 4. start the clock of a dungeon.
   	 */
       @FXML
       public void initialize() throws FileNotFoundException {
       	selectGameMode();
       	loadDungeonImage();
       	loadGoalInterface();
       	loadItemListInterface();
        startTheClock();
       }
    /**
     * Shows the game mode selection window before playing the game
     * and loads different dungeons according to a player's choice.
     * @throws FileNotFoundException
     */
    public void selectGameMode() throws FileNotFoundException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Game Menu");
    	alert.setHeaderText("Which Game Mode do you want to play ?");
    	alert.getButtonTypes().clear();
    	
    	ButtonType easy = new ButtonType("Easy");
    	ButtonType normal = new ButtonType("Normal");
    	ButtonType hard = new ButtonType("Hard");
    	alert.getButtonTypes().addAll(easy,normal,hard);
    	
    	Optional<ButtonType> option = alert.showAndWait();
    	
        if (option.get() == easy) {
    		dungeon = dungeonControllerLoader.LoadEasyMode();
    		time = 500;
        } else if (option.get() == normal) {
    		dungeon = dungeonControllerLoader.LoadNormalMode();
    		time = 500;
        } else if (option.get() == hard) {
    		dungeon = dungeonControllerLoader.LoadDifficultMode();
    		time = 500;
        }
        
    	this.player = dungeon.getPlayer();
    	this.initialEntities = dungeonControllerLoader.getEntities();
	}
	/**
	 * Load the image of a dungeon including all the images of entities inside.
	 * put the ground images first so that it can be overlapped with other entities afterwards.
	 */
    public void loadDungeonImage() {
    	leastHeight = dungeon.getHeight();
    	if (dungeon.getHeight() < 10) {
    		leastHeight = 11;
    	}
    	Image ground = this.dungeonControllerLoader.groundImage;
    	for (int x = 0; x < dungeon.getWidth(); x++) {
        	for (int y = 0; y < leastHeight; y++) {
        		squares.add(new ImageView(ground), x, y);
            }
        }
        for (ImageView entity : initialEntities) {
        	squares.getChildren().add(entity);
        }
    }
    /**
     * Load the image of a Goal interface.
     */
    public void loadGoalInterface() {
    	constructGoalFrame();
    	fillGoalFrame();
    	dungeon.getGoal().attach(this);
    }
    /**
     * Construct a frame of goal interface.
     */
    public void constructGoalFrame() {
    	Image ground = this.dungeonControllerLoader.groundImage;
    	for (int x = dungeon.getWidth(); x < dungeon.getWidth() + 10; x++) {
        	for (int y = 0; y < 7; y++) {
        		squares.add(new ImageView(ground), x, y);
            }
        }
    	Image wall = this.dungeonControllerLoader.wallImage;
    	for (int x = dungeon.getWidth(); x < dungeon.getWidth() + 10; x++) {
    		for (int y = 0; y < 7; y++) {
    			if (x == dungeon.getWidth() || x == dungeon.getWidth() + 9)
    				squares.add(new ImageView(wall), x, y);
    			else if (y == 0 || y == 3 || y == 6)
    				squares.add(new ImageView(wall), x, y);
    		}
    	}
    }
    /**
     * Fill the contents inside a goal interface frame.
     */
    public void fillGoalFrame() {
    	setMissionDisplay();
    	setCompleteDisplay();
    }
    /**
     * set up a mission display.
     */
    public void setMissionDisplay() {
    	setMissionLabel();
    	setMissionGoalDisplay();
    }
    /**
     * set up a mission label.
     */
    public void setMissionLabel() {
    	Label mission = this.dungeonControllerLoader.MissionLabel;
    	squares.add(mission, dungeon.getWidth() + 1, 1, 8, 1);
    }
    /**
     * set up a mission goal display.
     */
    public void setMissionGoalDisplay() {
    	Goal goal = dungeon.getGoal();
        List<String> goalLogic = getGoalLogic(goal);
        int n_gl = 1;
        for (String g : goalLogic) {
        	if (g.equals("And")) {
        		Label and = this.dungeonControllerLoader.getANDLabel();
                squares.add(and, dungeon.getWidth() + n_gl, 2, 7, 1);
                n_gl++;
        	} else if (g.equals("Or")) {
        		Label or = this.dungeonControllerLoader.getORLabel();
        		squares.add(or, dungeon.getWidth() + n_gl, 2, 7, 1);
                n_gl++;
        	} else if (g.equals("Enemy")) {
        		// Start from here
        		Image enemy = this.dungeonControllerLoader.enemyImage;
            	squares.add(new ImageView(enemy),dungeon.getWidth() + n_gl, 2);
            	n_gl++;
        	} else if (g.equals("Treasure")) {
        		Image treasure = this.dungeonControllerLoader.treasureImage;
            	squares.add(new ImageView(treasure),dungeon.getWidth() + n_gl, 2);
            	n_gl++;
        	} else if (g.equals("Exit")) {
        		Image exit = this.dungeonControllerLoader.exitImage;
            	squares.add(new ImageView(exit),dungeon.getWidth() + n_gl, 2);
            	n_gl++;
        	} else if (g.equals("Switch")) {
        		Image floorSwitch = this.dungeonControllerLoader.floorSwitchImage;
            	squares.add(new ImageView(floorSwitch),dungeon.getWidth() + n_gl, 2);
            	n_gl++;
        	}
        }
    }
    /**
     * @return a list of string which contains a goal logic.
     */
    public List<String> getGoalLogic(Goal goal) { 
    	List<String> goalLogic = new ArrayList<String>();
        if (goal instanceof CompositeGoal) {
	        goalLogic.add(goal.toString());
	        
	        List<Goal> subgoals = ((CompositeGoal) goal).subGoals;
	        for (Goal subgoal : subgoals) {
	        	goalLogic.addAll(getGoalLogic(subgoal));
	        }
        } else {
        	goalLogic.add(goal.toString());
        }
        return goalLogic;
    }
    /**
     * set up a complete goal display.
     */
    public void setCompleteDisplay() {
    	Label complete = this.dungeonControllerLoader.CompleteLabel;
        squares.add(complete, dungeon.getWidth() + 1, 4, 8, 1);
        
    }
    /**
     * set up a item list display.
     */
    public void loadItemListInterface() {
    	constructItemListFrame();
    	fillItemListFrame();
    }
    /**
     * Construct a frame of ItemList interface.
     */
    public void constructItemListFrame() {
    	Image ground = this.dungeonControllerLoader.groundImage;
    	for (int x = dungeon.getWidth(); x < dungeon.getWidth() + 10; x++) {
        	for (int y = 7; y < leastHeight; y++) {
        		squares.add(new ImageView(ground), x, y);
            }
        }
    	Image wall = this.dungeonControllerLoader.wallImage;
    	for (int x = dungeon.getWidth(); x < dungeon.getWidth() + 10; x++) {
    		for (int y = 7; y < leastHeight; y++) {
    			if (x == dungeon.getWidth() || x == dungeon.getWidth() + 9)
    				squares.add(new ImageView(wall), x, y);
    			else if (y == 10)
    				squares.add(new ImageView(wall), x, y);
    		}
    	}
    }
    /**
     * Fill the contents inside a goal interface frame.
     */
    public void fillItemListFrame() {
    	setItemListLabel();
        setTreasureScoreDisplay();
        setDurabilityScoreDisplay();
        setInvincibilityTimeDisplay();
        setKeyObtainDisplay();
    }
    /**
     * set up a item list label.
     */
    public void setItemListLabel() {
        Label mission = this.dungeonControllerLoader.ItemListLabel;
        squares.add(mission, dungeon.getWidth() + 1, 7, 8, 1);
    }
    /**
     * set up a treasureScore display
     */
    public void setTreasureScoreDisplay() {
    	ImageView treasure = new ImageView(this.dungeonControllerLoader.treasureImage);
		squares.add(treasure,dungeon.getWidth() + 2, 8);
		treasure.setVisible(false);
		
    	treasureScoreLabel = this.dungeonControllerLoader.TreasureScoreLabel;
    	trackTreasureScore(dungeon,treasureScoreLabel,treasure);
    	squares.add(treasureScoreLabel, dungeon.getWidth() + 3, 8, 2, 1);
    }
    /**
     * bind a treasureScore of a dungeon with a scoreLabel and an image of a treasure
     * Hence, when the treasureScore changes, it get reflected on the scoreLabel.
     * More specifically, when the treasureScore gets incremented for the first time,
     * An image of a treasure will appear with the scoreLabel on the screen.
     * After that, whenever the score gets changed, it will be reflected on the scoreLabel.
     * @param dungeon
     * @param scoreLabel
     * @param treasure
     */
    private void trackTreasureScore(Dungeon dungeon, Label scoreLabel, ImageView treasure) {
    	dungeon.treasureScore().addListener(new ChangeListener<Number>() {
    		@Override
    		public void changed(ObservableValue<? extends Number> observable,
    				Number oldValue, Number newValue) {
    			treasure.setVisible(true);
    			scoreLabel.setText(": " + newValue);
    		}
    	});
    }
    /**
     * set up a durabilityScore display
     */
    public void setDurabilityScoreDisplay() {
    	durabilityScoreLabel = this.dungeonControllerLoader.DurabilityScoreLabel;
        squares.add(durabilityScoreLabel, dungeon.getWidth() + 3, 9, 1, 1);
        durabilityScoreLabel.setVisible(false);
        
    	for (Entity entity : dungeon.getEntities()) {
			if (entity instanceof Sword) {
				ImageView sword = new ImageView(this.dungeonControllerLoader.swordImage);
				trackSwordOwnedStatus((Sword)entity, sword, durabilityScoreLabel);
				trackDurabilityScore((Sword)entity,durabilityScoreLabel);
			}
		}
    }
    /**
     * bind an owned() attribute of a sword with a scoreLabel and an image of sword.
     * Hence, when the owned() value changes, it get reflected on the scoreLabel, which means
     * when the owned() is true, it shows the durability score of the sword on the label 
     * and the image of sword on the screen. Otherwise, nothing will be shown on the screen.
     * @param sword
     * @param imageView
     * @param scoreLabel
     */
    private void trackSwordOwnedStatus(Sword sword, ImageView imageView, Label scoreLabel) {
    	sword.owned().addListener(new ChangeListener<Boolean>() {
    		@Override
    		public void changed(ObservableValue<? extends Boolean> observable,
    				Boolean oldValue, Boolean newValue) {
    			if (newValue == true) {
    				scoreLabel.setText(": " + sword.getDurabilityScore());
    				imageView.setVisible(true);
    				scoreLabel.setVisible(true);
    				squares.add(imageView, dungeon.getWidth() + 2, 9);
    			} else {
    				scoreLabel.setVisible(false);
    				squares.getChildren().remove(imageView);
    			}
    		}
    	});
    }
    /**
     * bind a durability score of a sword with a scoreLabel.
     * While trackSwordOwnedStatus mainly bind an owned() attribute of sword with
     * an image of a sword on the screen, this methods more closely bind a durability score of 
     * a sword with a scoreLabel than the trackSwordOwnedStaus method.
     * In other words, whenever a durability score changes, the value of durability score
     * gets shown on the scoreLabel as long as the score is greater than zero. 
     * @param sword
     * @param scoreLabel
     */
    private void trackDurabilityScore(Sword sword, Label scoreLabel) {
    	sword.durabilityScore().addListener(new ChangeListener<Number>() {
    		@Override
    		public void changed(ObservableValue<? extends Number> observable,
    				Number oldValue, Number newValue) {
    			if ((int)newValue > 0) {
    				scoreLabel.setText(": " + sword.getDurabilityScore());
    			}
    		}
    	});
    }
    /**
     * set up an invincibilityTime display
     */
    public void setInvincibilityTimeDisplay() {
    	ImageView portion = new ImageView(dungeonControllerLoader.invincibilityPortionImage);
    	
		squares.add(portion,dungeon.getWidth() + 5, 8);
    	portion.setVisible(false);
		
    	invincibilityScoreLabel = this.dungeonControllerLoader.InvincibilityScoreLabel;
    	invincibilityScoreLabel.setVisible(false);
		
    	trackInvincibilityScore(player,invincibilityScoreLabel,portion);
    	squares.add(invincibilityScoreLabel, dungeon.getWidth() + 6, 8, 2, 1);
    }
    /**
     * bind an invincibilityScore with a scoreLabel and an image of an invincibility portion.
     * When an invincibilityScore changes to non-zero value, the score gets reflected on
     * the scoreLabel and an image of an invincibility portion and the label will appear
     * on the screen.
     * In case of the score having changed to 0, the scoreLabel and the image will disappear
     * from the screen.
     * @param player
     * @param scoreLabel
     * @param portion
     */
    private void trackInvincibilityScore(Player player, Label scoreLabel,ImageView portion) {
    	player.invincibleTime().addListener(new ChangeListener<Number>() {
    		@Override
    		public void changed(ObservableValue<? extends Number> observable,
    				Number oldValue, Number newValue) {
    			if ((int)newValue == 0) {
    				portion.setVisible(false);
    				scoreLabel.setVisible(false);
    			} else {
    				portion.setVisible(true);
    				scoreLabel.setVisible(true);
    				scoreLabel.setText(": " + newValue);
    			}
    		}
    	});
    }
    /**
     * set up the keyObtain display.
     */
    public void setKeyObtainDisplay() {
    	for (Entity entity : dungeon.getEntities()) {
			if (entity instanceof Key) {
				ImageView key = new ImageView(this.dungeonControllerLoader.keyImage);
				trackKeyOwnedStatus((Key)entity, key);
			}
		}
    }
    /**
     * bind a owned value of a key with an image of a key.
     * When the owned value has changed to true, an image of key will
     * appear on the screen.
     * When the owned value has changed to false, the image of key will
     * disappear from the screen.
     * @param key
     * @param imageView
     */
    private void trackKeyOwnedStatus(Key key, ImageView imageView) {
    	key.owned().addListener(new ChangeListener<Boolean>() {
    		@Override
    		public void changed(ObservableValue<? extends Boolean> observable,
    				Boolean oldValue, Boolean newValue) {
    			if (newValue == true) {
    				squares.add(imageView, dungeon.getWidth() + 5, 9);
    			} else {
    				squares.getChildren().remove(imageView);
    			}
    		}
    	});
    }
    
    /**
     * start the clock of the dungeon. 
     * When this function executes, the time of a dungeon start ticking.
     */
    public void startTheClock() {
        dungeon.setTimeline(timeline);
    	Duration duration = Duration.millis(time);
        EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent t) {
    			dungeon.tick();
    		}
    	};
    	KeyValue keyValue1 = new KeyValue(new SimpleIntegerProperty(0), 1);
    	KeyValue keyValue2 = new KeyValue(new SimpleIntegerProperty(0), 1);
    	KeyFrame keyFrame = new KeyFrame(duration, onFinished, keyValue1, keyValue2);
    	
    	timeline.setCycleCount(Timeline.INDEFINITE);
    	timeline.getKeyFrames().add(keyFrame);
    	timeline.play();
    }
	 /**
     * When a player achieves the treasure goal, shows the corresponding icon on the goal display
     * so that a player can recognize they've achieved the goal.
     */
    public void showTreasureSuccess() {
    	Image treasure = this.dungeonControllerLoader.treasureImage;
    	squares.add(new ImageView(treasure),dungeon.getWidth() + successCount, 5);
    	successCount++;
    }
    /**
     * When a player achieves the exit goal, shows the corresponding icon on the goal display
     * so that a player can recognize they've achieved the goal.
     */
    public void showExitSuccess() {
    	Image exit = this.dungeonControllerLoader.exitImage;
    	this.exit = new ImageView(exit);
    	squares.add(this.exit,dungeon.getWidth() + successCount, 5);
    }
    /**
     * When a player step out of the exit. It removes the exit success icon from the goal display.
     */
    public void hideExitSuccess() {
    	squares.getChildren().remove(exit);
    }
    /**
     * When a player achieves the enemy goal, shows the corresponding icon on the goal display
     * so that a player can recognize they've achieved the goal.
     */
    public void showEnemySuccess() {
    	Image enemy = this.dungeonControllerLoader.enemyImage;
    	squares.add(new ImageView(enemy),dungeon.getWidth() + successCount, 5);
    	successCount++;
    }
    /**
     * When a player achieves the switch goal, shows the corresponding icon on the goal display
     * so that a player can recognize they've achieved the goal.
     */
    public void showSwitchSuccess() {
    	Image floorSwitch = this.dungeonControllerLoader.floorSwitchImage;
    	this.floorSwitch = new ImageView(floorSwitch);
    	squares.add(this.floorSwitch,dungeon.getWidth() + successCount, 5);
    	successCount++;
    }
    /**
     * When one of the switch floors gets unactivated and the goal is not achieved anymore,
     * it will remove the floorSwitch achievement icon from the Complete display section.
     */
    public void hideSwitchSuccess() {
    	squares.getChildren().remove(floorSwitch);
    }
    /**
     * As an observer of the goal class, this class gets updated by hiding or showing
     * the goal achievement icon from the Complete display section whenever a value of status
     * from a goal class changes to true or false. 
     */
    @Override
	public void update(Subject obj) {
		if (obj instanceof TreasureGoal) {
			if (((Goal)obj).getStatus() == true) {
				showTreasureSuccess();
			}
		} else if (obj instanceof ExitGoal) {
			if (((Goal)obj).getStatus() == true) {
				showExitSuccess();
			} else {
				hideExitSuccess();
			}
		} else if (obj instanceof EnemiesGoal) {
			if (((Goal)obj).getStatus() == true) {
				showEnemySuccess();
			}
		} else if (obj instanceof BouldersGoal) {
			if (((Goal)obj).getStatus() == true) {
				showSwitchSuccess();
			} else {
				hideSwitchSuccess();
			}
		}
	}
    
    private boolean pressFlag = false;
    @FXML
    public void handleKeyPress(KeyEvent event) throws FileNotFoundException {
        switch (event.getCode()) {
        case UP:
        	player.move(Enum.Direction.NORTH);
        	break;
        case DOWN:
        	player.move(Enum.Direction.SOUTH);
        	break;
        case LEFT:
    		player.move(Enum.Direction.WEST);
        	break;
        case RIGHT:
        	player.move(Enum.Direction.EAST);
        	break;
        case CONTROL:
        	if (pressFlag == false) {
        		pressFlag = true;
        		timeline.stop();
        	} else {
        		pressFlag = false;
        		timeline.play();
        	}
        default:
            break;
        }
    }
}