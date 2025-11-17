package GameLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Data.DataManager;
import Util.EventEnum;

public class GameManager {
	private Player player;
	private final DataManager dataMgr = DataManager.getInstance();
	private EventListener eventListener;
	private int stage = 1;
	
	
	public void initGame() {
		setEntity();
		eventListener = new EventListener(player);
		eventListener.call(EventEnum.START, null);
	}
	
	
	
	private void setEntity() {
		
		player = new Player("player", 5, dataMgr.getDiceMgr().get("first"));
		dataMgr.setPlayer(player);
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
