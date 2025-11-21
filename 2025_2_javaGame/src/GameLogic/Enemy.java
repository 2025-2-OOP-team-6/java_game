package GameLogic;

import java.util.Random;

import Data.DataManager;
import Util.EventEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class Enemy extends Entity implements Cloneable{
	private HashMap<Item, Integer> dropTable = new HashMap<>();
	Random rand = new Random();
	private ArrayList<Item> items = new ArrayList<>();
	
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
		for(Item i:dropTable.keySet()) {
			items.add(i);
		}
	}
	
	public Item dropItem() {
		int sum = 0;
		for(Item i:items) {
			sum += dropTable.get(i);
		}
		
		int randNum = rand.nextInt(sum);
		int randSum = 0;
		
		for(int i=0; i<items.size(); i++) {
			if (randNum >= randSum && randNum < dropTable.get(items.get(i))+randSum) return items.get(i);
			randSum += dropTable.get(items.get(i));	
			
			
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
	public void die() {
		DataManager.getInstance().getEventListener().call(EventEnum.NEXT_MAP, this);
	}
}
