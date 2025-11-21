package GameLogic;

import java.util.Random;

import Util.Debuff;

public class Dice implements Cloneable{
	String name;
	public DiceRange range;
	private Debuff debuff;
	private int value;
	
	Random rand = new Random();
	
	public Dice(String name, DiceRange range) {
		this.name = name;
		this.range = range;
	}
	
	@Override 
	public Dice clone() {
		try {
		return (Dice)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public int roll() {
		value = rand.nextInt(range.start, range.end+1);
		
		applyDebuff();
		return value;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setDebuff(Debuff debuff) {
		this.debuff = debuff;
	}
	
	private void applyDebuff() {
		/* 디버프 설정 */
	}

	public void setValue(int value) {
		this.value = value;
		
	}
}
