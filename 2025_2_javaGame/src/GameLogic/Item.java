package GameLogic;

import java.util.Scanner;


public class Item {
	public IGameRunnable<Entity> effect;
	public String description;
	public String name;
	
	public Item(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public void setEffect(IGameRunnable<Entity> effect) {
		this.effect = effect;
	}
	
	public void useEffect(Entity e) {
		effect.run(e);
	}
}
