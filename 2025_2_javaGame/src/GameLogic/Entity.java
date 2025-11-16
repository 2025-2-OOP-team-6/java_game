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
	
	public Dice getDice() {
		return dice;
	}
	
	public void damage() {
		hp -= 1;
	}
	
	public void heal() {
		hp += 1;
	}
	
	public void changeDice(Dice dice)
	{
		this.dice = dice;
	}
	
	abstract public void die(EventListener evManager);
}
