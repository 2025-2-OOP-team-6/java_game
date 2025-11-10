package GameLogic;

import java.util.Scanner;

public class Item {
	public IGameRunnable<Entity> effect;
	public String description;
	public String name;
	
	public void setItem(Scanner scan) {
		name = scan.next();
		description = scan.next();
	}
	
	public void setEffect(IGameRunnable<Entity> effect) {
		this.effect = effect;
	}
	
	public void useEffect(Entity e) {
		effect.run(e);
	}
}
