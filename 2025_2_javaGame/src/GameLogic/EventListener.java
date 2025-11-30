package GameLogic;

import java.util.Random;

import Data.LogData;
import Logic.User;
import Util.EventEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventListener {
	private Player player;
	private final Random rand = new Random();
	
	private int mapNum = 0;
	private boolean isClear = false;
	private boolean isStart = false;
	public boolean isOver = false;
	private int stage = 0; 
	private int isWin = 0;
	public boolean isBoss = false;
	private String log = "";

    private boolean pedingGameOver = false;
	private LocalDateTime now = LocalDateTime.now();
	
	private LogData logMgr;
	private User currentUser;
	
	public EventListener(Player player, LogData logMgr, User currentUser) {
		this.player = player;
		this.logMgr = logMgr;
		this.currentUser = currentUser;
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
			gameOver(object);
			break;
		}
		default: throw new RuntimeException("해당하지 않는 이벤트가 입력되었습니다."); 
		 
		}
	}
	private void turnMove(Object o) {
		Enemy enemy = (Enemy)o;
		int playerResult = player.dice.roll();
		int enemyResult = enemy.dice.roll();
		
				
		System.out.print("hp : "+enemy.getHp());
		if (playerResult > enemyResult) {
			enemy.damage();
			
		}
		else if (playerResult < enemyResult) {
			player.damage();
		}
		isWin = 	playerResult-enemyResult ;
			
	}
	
	private void start() {
		
		if (stage < 10) {
			mapNum = rand.nextInt(2)+1;
		}
		else if (stage == 10) {
			mapNum = 3;
			isBoss=true;
		}
		
		else if (stage > 10) {
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
		
		if (player.getBag().size()<6)
			player.getItem(i);
		log = String.format("%s,%s,%s", player.dice.name,enemy.name,"WIN");
		logMgr.insertLog(currentUser.getId(), log);
		
		isClear = true;
		stage++;
		
		
	}
	
	private void gameOver(Object o) {
		Enemy enemy = (Enemy)o;
		log = String.format("%s,%s,%s", player.dice.name,"","LOSE");
		logMgr.insertLog(currentUser.getId(), log);
		isOver = true;
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
	public boolean getIsOver() {
		return isStart;
	}
	public void setIsStart(boolean isStart) {
		this.isStart = isStart;
	}
	public int getIsWin() {
		return isWin;
	}
	public void setIsOver(boolean b) {
		isOver = b;
		System.out.print("사"+isOver+"\n");
	}
	
}
