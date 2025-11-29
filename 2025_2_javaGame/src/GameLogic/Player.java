package GameLogic;

import java.util.ArrayList;

import Data.DataManager;
import Util.EventEnum;

public class Player extends Entity {
	private ArrayList<Item> bag;

	public Player(String name, int hp, Dice dice) {
		super(name,hp,dice);
		bag = new ArrayList<>(); 
	}

	public ArrayList<Item> getBag() {
		return bag;
	}
	
	public void getItem(Item item) {
		if(item != null)
			bag.add(item);
	}
	
	@Override
	public void die() {
		DataManager.getInstance().getEventListener().call(EventEnum.GAMEOVER, this);
	}

	public void setBag(ArrayList<Item> itemList) {
		bag = itemList;
		
	}
}
