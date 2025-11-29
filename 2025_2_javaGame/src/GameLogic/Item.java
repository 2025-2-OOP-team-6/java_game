package GameLogic;

public class Item {
	public IGameRunnable<Entity, String> effect;
	public String description;
	public String name;
	public String imagePath;
	public int price;
	
	public Item(String name, String description, int price, String imagePath) {
		this.name = name;
		this.description = description;
		this.price =price;
		this.imagePath = imagePath;
	}
	
	public void setEffect(IGameRunnable<Entity, String> effect) {
		this.effect = effect;
	}
	
	public void useEffect(Entity e, String id) {
		effect.run(e, id);
		
	}
}
