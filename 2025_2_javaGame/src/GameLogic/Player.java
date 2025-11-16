package GameLogic;

import java.util.ArrayList;

public class Player extends Entity {
	ArrayList<Item> bag;

	public Player(String name, int hp, Dice dice) {
		super(name,hp,dice);
	}

	
	
	public void getItem(Item item) {
		bag.add(item);
	}
	
	@Override
	public void die(EventListener evManager) {
		
	}
}
