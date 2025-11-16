package GameLogic;

import Util.EventEnum;

public class EventListener {
	private Player player;
	
	public EventListener(Player player) {
		this.player = player;
	}
	
	public void call(EventEnum e, Object object) {
		switch (e) {
		case TURN_MOVE: {
			turnMove(object);
			break;
		}
		case USE_ITEM: {
			useItem(object);
			break;
		}
		case NEXT_MAP: {
			nextMap(object);
			break;
		}
		case GAMEOVER: {
			gameOver();
			break;
		}
		default: throw new RuntimeException("해당하지 않는 이벤트가 입력되었습니다."); 
		 
		}
	}
	private void turnMove(Object o) {
		Enemy enemy = (Enemy)o;
		if (player.dice.roll() > enemy.dice.roll()) {
			enemy.damage();
		}
		else if (player.dice.roll() < enemy.dice.roll()) {
			player.damage();
		}
		else {
			
		}
		
	}
	private void useItem(Object o) {
		Item item = (Item)o;
		item.useEffect(player, null);
		player.bag.remove(item);
	}
	private void nextMap(Object o) {
		GameManager gameManager = (GameManager)o;
		gameManager.levelUp();
	}
	private void gameOver() {
		
	}


}
