package GameLogic;

import java.util.Random;

import Util.Debuff;

public class Dice {
	String name;
	public DiceRange range;
	private Debuff debuff;
	private int value;
	
	Random rand = new Random();
	
	public Dice(DiceRange range) {
		this.range = range;
	}
	
	public int roll() {
		value = rand.nextInt(range.start, range.end+1);
		applyDebuff();
		return value;
	}
	
	public void setDebuff(Debuff debuff) {
		this.debuff = debuff;
	}
	
	private void applyDebuff() {
		/* 디버프 설정 */
	}
}
