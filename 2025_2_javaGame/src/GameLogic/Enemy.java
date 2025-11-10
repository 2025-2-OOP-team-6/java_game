package GameLogic;

import java.util.Random;
import java.util.HashMap;

public class Enemy extends Entity{
	private HashMap<Item, Integer> dropTable = new HashMap<>();
	
	Random rand = new Random();
	
	public Enemy(String name, int hp, Dice dice, HashMap<Item, Integer> dropTable) {
		super(name,hp,dice);
		this.dropTable = dropTable;
	}
	
	public Item dropItem() {
		int sum = 0;
		for(int i:dropTable.values()) {
			sum += i;
		}
		
		int randNum = rand.nextInt(sum);
		int randSum = 0;
		
		for(Item i:dropTable.keySet()) {
			if (randNum >= randSum && randNum < dropTable.get(i)) return i;
			randSum += dropTable.get(i);	
		}
		
		return null;
	}
}
