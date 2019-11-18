package unsw.dungeon;

public interface Subject {
	public void attach(Observer obj);
	public void detach(Observer obj);
	public void notifyObserver();
}