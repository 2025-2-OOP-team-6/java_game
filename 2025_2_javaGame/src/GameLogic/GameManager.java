package GameLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager {
	private ArrayList<Dice> diceList = new ArrayList<>();
	private ArrayList<Enemy> enemyList = new ArrayList<>();
	private ArrayList<Item> itemList = new ArrayList<>();
	private final String diceFilename = "dice.txt";
	private final String enemyFilename = "enemy.txt";
	private final String itemFilename = "item.txt";
	private Player player;
	
	public void initGame() {
		readAllDices();
		readAllEnemies();
		setEntity();
		run();
	}
	
	private void readAllDices() {
		Scanner fileIn = openFile(diceFilename);
		Dice d = null;
		while(fileIn.hasNext()) {
			d = new Dice(new DiceRange(fileIn.nextInt(), fileIn.nextInt()));
			diceList.add(d);
		}
		fileIn.close();
	}
	
	private void readAllEnemies() {
		Scanner fileIn = openFile(enemyFilename);
		Enemy e = null;
		while(fileIn.hasNext()) {
			e = new Enemy(fileIn.next(), fileIn.nextInt(), diceList.get(fileIn.nextInt()), null);
			enemyList.add(e);
		}
		fileIn.close();
	}
	
	private void readAllItems() {
		Scanner fileIn = openFile(itemFilename);
		Item i = null;
		while(fileIn.hasNext()) {
			i = new Item(fileIn.next(), fileIn.next());
			// effect 추가 필요
			itemList.add(i);
		}
		fileIn.close();
	}
	
	private void setEntity() {
		player = new Player("player", 5, diceList.get(0));
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
