package GameLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Data.DataManager;
import Screen.ScreenManager;
import Util.EventEnum;

public class GameManager {
	private final DataManager dataMgr = DataManager.getInstance();
	
	public void initGame() {
		setEntity();
		dataMgr.getEventListener().call(EventEnum.START, null);
	}
	
	private void setEntity() {
		Player player;
		if(dataMgr.getUserMgr().getStage(dataMgr.getCurrentUser().getId())>=100) {
			player = new Player("player", 5, dataMgr.getDiceMgr().get("dice3"));
		}
		else {
			 player= new Player("player", 5, dataMgr.getDiceMgr().get("dice1"));
		}
		
		if (player.dice == null) throw new RuntimeException("dice가 지정이 안됐습니다");
		
		
		
		ArrayList<Item> itemList = new ArrayList<>();
		itemList = dataMgr.getUserMgr().getInventory(dataMgr.getCurrentUser().getId());
		
		player.setBag(itemList);
		
		

		dataMgr.initEventListener(player);
		
		dataMgr.getEventListener().setStage(1);
		if(dataMgr.getUserMgr().getStage(dataMgr.getCurrentUser().getId())>=100)dataMgr.getEventListener().setStage(10); 
	}
	

	Scanner openFile(String filename) {
		Scanner filein = null;
		try {
			filein = new Scanner(new File(filename));
		} catch (Exception e) {
			System.out.printf("파일 오픈 실패: %s\n", filename);
			throw new RuntimeException(e);
		}
		return filein;
	}
}
