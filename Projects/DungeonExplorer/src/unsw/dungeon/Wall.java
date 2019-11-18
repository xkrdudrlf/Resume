package unsw.dungeon;
/**
 * Wall Class: Wall entity in the dungeon 
 * wall can block the movement of other entities
 * @author YUNXIANG ZHANG (Z5083830)
 *
 */
public class Wall extends Entity {
	/**
	 * inherit the constructor method in the super class Entity
	 * creates the wall with the specified position
	 * @param x
	 * @param y
	 */
    public Wall(int x, int y) {
        super(x, y);
    }
    /**
     * return true if the wall blocks a player
     * @param dungeon, a certain dungeon 
     * @param, a player in the dungeon
     */
	@Override
	public boolean blockPlayer(Dungeon dungeon,Player player) {
		return true;
	}
	/**
	 * return true if the wall blocks an enemy 
	 */
	@Override
	public boolean blockEnemy() {
		return true;
	}
}
