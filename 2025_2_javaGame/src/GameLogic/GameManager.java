gi	package GameLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Data.DataManager;

public class GameManager {
	private Player player;
	private DataManager dataMgr = new DataManager();
	private int level = 1;
	
	public void initGame() {
		
		setEntity();
	}
	
	
	
	private void setEntity() {
		player = new Player("player", 5, dataMgr.getDiceMgr().get("first"));
		level = 1;
	}
	
	public void levelUp() {
		level++;
	}
	
	
	private void run() {
		
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
