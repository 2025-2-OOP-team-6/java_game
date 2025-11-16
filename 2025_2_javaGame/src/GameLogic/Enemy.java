package GameLogic;

import java.util.Random;
import java.util.HashMap;

public class Enemy extends Entity implements Cloneable{
	private HashMap<Item, Integer> dropTable = new HashMap<>();
	Random rand = new Random();
	
	/**
	 * 
	 * @param name
	 * @param hp
	 * @param dice
	 * @param dropTable HashMap<Item item, Integer probablity 1~100>
	 */
	
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
	
	@Override 
	public Enemy clone() {
		try {
		return (Enemy)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	@Override
	public void die(EventListener evManager) {
		
	}
}
