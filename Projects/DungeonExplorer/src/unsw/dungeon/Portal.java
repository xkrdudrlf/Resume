package unsw.dungeon;
/**
 * Teleport a player to the other linked portal.
 */
public class Portal extends Entity {
    /**
     * Each portal is linked with the other portal who shares a same id.
     * @param x
     * @param y
     */
	public Portal(int x, int y) {
        super(x, y);
    }
    private int id = -2;
    /**
     * @return id of the portal.
     */
    public int getId() {
        return this.id;
    }
    /**
     * set an id of the portal.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
}
