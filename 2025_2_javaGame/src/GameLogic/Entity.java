package GameLogic;

public class Entity {
	String name;
	int hp;
	Dice dice;
	
	public Entity(String name, int hp, Dice dice) {
		this.name = name;
		this.hp = hp;
		
	}
	
	public void damage() {
		hp -= 1;
	}
	
	public int roll() {
		return dice.roll();
	}
}
