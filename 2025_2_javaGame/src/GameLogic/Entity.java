package GameLogic;

public abstract class Entity {
	String name;
	int hp;
	int maxHp;
	Dice dice;
	
	public Entity(String name, int hp, Dice dice) {
		this.name = name;
		this.hp = hp;
		maxHp = hp;
		this.dice = dice;
	}
	
	public Dice getDice() {
		return dice;
	}
	
	public int getMaxHp() {
		return maxHp;
	}
	public int getHp() {
		return hp;
	}
	
	public void damage() {
		hp -= 1;
		if (hp <= 0) {
			hp = 0;
			die();
		}
		
	}
	
	public void heal() {
		
		hp += 1;
		if (hp > maxHp) {
			hp = maxHp;
		}
	}
	
	public void changeDice(Dice dice)
	{
		this.dice = dice;
	}
	
	abstract public void die();
}
