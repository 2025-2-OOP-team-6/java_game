package GameLogic;

public class Player extends Entity {

	public Player(String name, int hp, Dice dice) {
		super(name,hp,dice);
	}

	public void changeDice(Dice dice)
	{
		this.dice = dice;
	}
	
	@Override
	public void die(EventManager evManager) {
		
	}
}
