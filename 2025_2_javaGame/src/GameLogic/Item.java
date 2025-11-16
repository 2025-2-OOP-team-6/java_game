package GameLogic;

public class Item {
	public IGameRunnable<Entity, String> effect;
	public String description;
	public String name;
	
	public Item(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public void setEffect(IGameRunnable<Entity, String> effect) {
		this.effect = effect;
	}
	
	public void useEffect(Entity e, String id) {
		effect.run(e, id);
	}
}
