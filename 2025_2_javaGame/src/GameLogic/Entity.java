package GameLogic;

public abstract class Entity {
	String name;
	int hp;
	Dice dice;
	
	public Entity(String name, int hp, Dice dice) {
		this.name = name;
		this.hp = hp;
		this.dice = dice;
	}
	
	public void damage() {
		hp -= 1;
	}
	
	abstract public void die(EventListener evManager);
}
