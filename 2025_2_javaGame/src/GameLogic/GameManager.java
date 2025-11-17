package GameLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Data.DataManager;
import Util.EventEnum;

public class GameManager {
	private final DataManager dataMgr = DataManager.getInstance();
	
	private int stage = 1;
	
	
	public void initGame() {
		setEntity();
		dataMgr.getEventListener().call(EventEnum.START, null);
	}
	
	
	
	private void setEntity() {
		
		Player player = new Player("player", 5, dataMgr.getDiceMgr().get("dice1"));
		if (player.dice == null) throw new RuntimeException("dice가 지정이 안됐습니다");
		dataMgr.initEventListener(player);
		stage = 1;
	}
	
	public void levelUp() {
		stage++;
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
