package unsw.dungeon;

/**
 * As an observer interface, it has a method for update in case of getting notified.
 */
public interface Observer {
	/**
	 * when the observer get notified, this method will be invoked.
	 * @param obj
	 */
	public void update(Subject obj);
}