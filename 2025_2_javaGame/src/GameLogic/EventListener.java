package GameLogic;

import java.util.Random;

import Util.EventEnum;

public class EventListener {
	private Player player;
	private final Random rand = new Random();
	
	private int mapNum = 0;
	private boolean isClear = false;
	private boolean isStart = false;
	private int stage = 0; 
	private int isWin = 0;
	
	
	public EventListener(Player player) {
		this.player = player;
	}
	
	public void call(EventEnum e, Object object) {
		switch (e) {
		case START: {
			start();
			break;
		}
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
		int playerResult = player.dice.roll();
		int enemyResult = enemy.dice.roll();
		isWin = 	playerResult-enemyResult ;
				
		System.out.print("hp : "+enemy.getHp());
		if (playerResult > enemyResult) {
			enemy.damage();
			
		}
		else if (playerResult < enemyResult) {
			player.damage();
		}
		System.out.print("player : "+playerResult+", enemy : "+enemyResult+"\n");	
	}
	
	private void start() {
		if (stage < 10) {
			mapNum = rand.nextInt(2)+1;
		}	
		else if (stage >= 10) {
			mapNum = rand.nextInt(2)+2;
		}
		isClear = false;
	}
	
	private void useItem(Object o) {
		Item item = (Item)o;
		player.getBag().remove(item);
		
	}
	
	private void nextMap(Object o) {
		Enemy enemy = (Enemy)o;
		Item i = enemy.dropItem();
		
		player.getItem(i);
		
		
		isClear = true;
		stage++;
		
		
	}
	
	private void gameOver() {
		
	}

	public void setStage(int stage) {
		this.stage = stage;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getMapNum() {
		return mapNum;
	}
	public int getStage() {
		return stage;
	}

	public boolean getClear() {
		return isClear;
	}
	
	public Player getPlayer() {
		return player;
	}

	public boolean getIsStart() {
		return isStart;
	}
	public void setIsStart(boolean isStart) {
		this.isStart = isStart;
	}
	public int getIsWin() {
		return isWin;
	}

}
