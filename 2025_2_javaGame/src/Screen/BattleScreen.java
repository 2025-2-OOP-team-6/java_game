package Screen;

import Logic.User;
import Util.EventEnum;
import Util.Position;
import Util.Screen;

import javax.swing.JPanel;

import Action.BlackOverlay;
import Action.GButton;
import Action.GPanel;
import Data.DataManager;
import Data.UserData;
import GameLogic.Enemy;
import GameLogic.Entity;
import GameLogic.Item;
import GameLogic.Player;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleScreen extends JPanel implements IScreen {
	private final DataManager dataMgr = DataManager.getInstance();

	private final GPanel background = new GPanel("assets//battle//background.png");
	private final GPanel bottomBar = new GPanel("assets//battle//bottomBar.png");
	private final GPanel defeat = new GPanel("assets//battle//defeat.png");
	private final GPanel enemyImg = new GPanel("assets//battle//enemy1.png");
	
	private final String blankImage = "assets//battle//blankHpBar.png";
	private final String hpImage = "assets//battle//hpBar.png";

	private final JLabel stageLabel = new JLabel();
	private final JLabel resultLabel = new JLabel();
	private final JLabel rangeLabel = new JLabel();
	private final JLabel resultLabel2 = new JLabel();
	private final JLabel rangeLabel2 = new JLabel();

	private String STAGE_TEXT;
	private String RESULT_TEXT;
	private String RANGE_TEXT;
	
	private int coin;
	
	private BlackOverlay blackPanel;
	

	private final GPanel playerImg = new GPanel("assets//battle//warrior.png");
	// private final GPanel wizard = new GPanel("assets//battle//wizard.png");

	private final Position PLAYER_POS = new Position(176, 228);
	private final Position ENEMY1_POS = new Position(772, 284);
	private final Position ENEMY2_POS = new Position(748, 184);
	private final Position ENEMY3_POS = new Position(708, 140);
	private final Position ITEMBTN_POS = new Position(45, 517);

	private Enemy enemy;
	private Player player;

	private HashMap<Entity, GPanel> entityMap;
	private HashMap<GButton, Item> itemMap;

	private ArrayList<GPanel> playerBarList;
	private ArrayList<GPanel> enemyBarList;
	private ArrayList<GButton> itemBarList;
	private GButton fightBtn;

	private int dtForMove = 0;
	private int dtForOpacity = 0;
	private int dtForMoveEnemy = 0;


	private ScreenManager scMgr;

	@Override
	public void init(final ScreenManager scManager) {
		scMgr = scManager;
		scMgr.getGameMgr().initGame();
		
		coin = dataMgr.getUserMgr().getCoin(dataMgr.getCurrentUser().getId());
		
		

		itemMap = new HashMap<>();
		itemBarList = new ArrayList<>();

		dtForOpacity = 0;

		stageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		resultLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		resultLabel.setForeground(Color.white);
		rangeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		resultLabel2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		resultLabel2.setForeground(Color.white);
		rangeLabel2.setFont(new Font("맑은 고딕", Font.BOLD, 20));

		player = dataMgr.getEventListener().getPlayer();
		dataMgr.getEventListener().getPlayer().getItem(dataMgr.getItemMgr().get("hpUp"));
		
		
		setLayout(null);
		
		setItemBar();
		setStage(dataMgr.getEventListener().getMapNum());
		
	}

	private void setStage(int id) {
		
		
		removeAll();
		revalidate();
		repaint();

		
		STAGE_TEXT = String.format("현재 스테이지: %d", dataMgr.getEventListener().getStage());
		stageLabel.setText(STAGE_TEXT);
		
		player.getDice().setValue(0);
		

		stageLabel.setBounds(512 - (stageLabel.getPreferredSize().width + 30) / 2, 10,
				stageLabel.getPreferredSize().width + 30, stageLabel.getPreferredSize().height);

		entityMap = new HashMap<>();
		playerBarList = new ArrayList<>();
		enemyBarList = new ArrayList<>();
		blackPanel = new BlackOverlay();
		blackPanel.setOpacity(dtForOpacity);

		blackPanel.setBounds(0, 0, 1024, 800);
		blackPanel.setOpaque(false);
		add(blackPanel);
		setDefaultObject();

		setBar(PLAYER_POS, player);
		
		switch (id) {
		case 1: {
			enemy = dataMgr.getEnemyMgr().get("enemy1").clone();
			enemyImg.setImage("assets//battle//enemy1.png");
			enemyImg.setBounds(ENEMY1_POS.x, ENEMY1_POS.y);
			entityMap.put(enemy, enemyImg);
			setBar(ENEMY1_POS, enemy);
			enemy.getDice().setValue(0);
			break;
		}
		case 2: {
			enemy = dataMgr.getEnemyMgr().get("enemy2").clone();
			enemyImg.setImage("assets//battle//enemy2.png");
			enemyImg.setBounds(ENEMY2_POS.x, ENEMY2_POS.y);
			entityMap.put(enemy, enemyImg);
			setBar(ENEMY2_POS, enemy);
			enemy.getDice().setValue(0);
			break;
		}
		case 3: {
			enemy = dataMgr.getEnemyMgr().get("enemy3").clone();
			enemyImg.setImage("assets//battle//enemy3.png");
			enemyImg.setBounds(ENEMY3_POS.x, ENEMY3_POS.y);
			entityMap.put(enemy, enemyImg);
			setBar(ENEMY3_POS, enemy);
			enemy.getDice().setValue(0);
			break;
		}
		default:
			
		}

		
		
		
		
		RANGE_TEXT = String.format("%d - %d", enemy.getDice().range.getRange()[0], enemy.getDice().range.getRange()[1]);

		fightBtn = new GButton("assets//battle//fightBtn.png", () -> {
			dataMgr.getEventListener().call(EventEnum.TURN_MOVE, enemy);
			refresh("fightBtn");
		});

		fightBtn.setBounds(536, 520);
		addObejct();

	}

	private void refresh(String b) {
		
		
		for (int i = 0; i < playerBarList.size() - 2; i++) {
			playerBarList.get(i).setImage(hpImage);
			
		}
		for (int i = 0; i < enemyBarList.size() - 2 - enemy.getHp(); i++) {
			enemyBarList.get(i).setImage(blankImage);
			
		}
		for (int i = 0; i < playerBarList.size() - 2 - player.getHp(); i++) {
			playerBarList.get(i).setImage(blankImage);
			
		}
		revalidate();
		repaint();
		
		
		RESULT_TEXT = String.format("%d", player.getDice().getValue());
		resultLabel.setText(RESULT_TEXT);
	
	
		RESULT_TEXT = String.format("%d", enemy.getDice().getValue());
		resultLabel2.setText(RESULT_TEXT);
	
	
	
		RANGE_TEXT = String.format("%d - %d", player.getDice().range.getRange()[0], player.getDice().range.getRange()[1]);
		rangeLabel.setText(RANGE_TEXT);
	
	
		RANGE_TEXT = String.format("%d - %d", enemy.getDice().range.getRange()[0], enemy.getDice().range.getRange()[1]);
		rangeLabel2.setText(RANGE_TEXT);
	
		setItems();
		
		
		

		Timer timerStart = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerImg.setBounds(PLAYER_POS.x + dtForMove, PLAYER_POS.y);
				blackPanel.setOpacity(dtForOpacity);
				dtForMove += 7;
				dtForOpacity -= 7;

				if (dtForMove >= 0) {
					((Timer) e.getSource()).stop();
					dtForMove = PLAYER_POS.x;
					setStage(dataMgr.getEventListener().getMapNum());

				}

			}
		});

		Timer timerClear = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerImg.setBounds(PLAYER_POS.x + dtForMove, PLAYER_POS.y);
				blackPanel.setOpacity(dtForOpacity);
				dtForMove += 7;

				if (dtForMove >= 1000) {
					((Timer) e.getSource()).stop();

					dataMgr.getEventListener().call(EventEnum.START, null);
					dtForOpacity = 255;
					dtForMove = -300;
					dataMgr.getEventListener().setIsStart(true);
					timerStart.setRepeats(true);
					timerStart.start();

				}
				if (dtForMove > 500)
					dtForOpacity += 5;
			}
		});
		
		Timer timerWin = new Timer(10, new ActionListener() {
			int dt = 7;
			@Override
			public void actionPerformed(ActionEvent e) {
				playerImg.setBounds(PLAYER_POS.x + dtForMove, PLAYER_POS.y);
				
				
				if (dtForMove > 50 && (dt > 0) ) {
					dt = -dt;
				}
				if (dtForMove <= 0 && (dt < 0)) {
					dtForMove = 0;
					fightBtn.setEnabled(true);
					((Timer) e.getSource()).stop();
				}
				dtForMove += dt;
				
				
			}
		});
		Timer timerLose = new Timer(10, new ActionListener() {
			int dt = -7;
			int x = entityMap.get(enemy).getX();
			int y = entityMap.get(enemy).getY();
			@Override
			public void actionPerformed(ActionEvent e) {
				
				enemyImg.setBounds(x + dtForMoveEnemy, y);
				
				
				if (dtForMoveEnemy < -50 && (dt < 0) ) {
					dt = -dt;
				}
				if (dtForMoveEnemy >= 0 && (dt > 0)) {
					dtForMoveEnemy = 0;
					fightBtn.setEnabled(true);
					((Timer) e.getSource()).stop();
				}
				dtForMoveEnemy += dt;
				
				
			}
		});
		
		
		
		if (!dataMgr.getEventListener().getClear() && b=="fightBtn") {
			if (dataMgr.getEventListener().getIsWin() > 0) {
				fightBtn.setEnabled(false);
				dtForMove = 0;
				dtForOpacity = 0;
				timerWin.start();
			}
			else if (dataMgr.getEventListener().getIsWin() < 0) {
				fightBtn.setEnabled(false);
				dtForMoveEnemy = 0;
				timerLose.start();
				
			}
			else if (dataMgr.getEventListener().getIsWin() == 0) {
				fightBtn.setEnabled(false);
				dtForMove = 0;
				dtForOpacity = 0;
				dtForMoveEnemy = 0;
				timerWin.start();
				timerLose.start();
				
			}
		}
		
		

		if (dataMgr.getEventListener().getClear()) {
			dtForMove = 0;
			dtForOpacity = 0;
			fightBtn.setEnabled(false);
			timerClear.setRepeats(true);
			timerClear.start();
		}

	}

	private void setBar(Position pos, Entity e) {
		GPanel g;
		int hp = e.getMaxHp();
		int width = entityMap.get(e).getWidth();
		int firstPosX = pos.x + width / 2 - hp * 40 / 2;

		for (int i = 0; i < hp; i++) {
			g = new GPanel("assets//battle//hpBar.png");
			g.setBounds(firstPosX + i * 40, pos.y - 47);
			if (e instanceof Player)
				playerBarList.add(g);
			else
				enemyBarList.add(g);
		}

		g = new GPanel("assets//battle//resultBar.png");

		g.setBounds(pos.x + width / 2 - 27, pos.y - 135);
		
		if (e instanceof Player) {
			playerBarList.add(g);
		}
		else {
			enemyBarList.add(g);
		}

		g = new GPanel("assets//battle//rangeBar.png");
		
		g.setBounds(pos.x + width / 2 + 45, pos.y - 120);
		if (e instanceof Player) {
			playerBarList.add(g);
		}
		else {
			enemyBarList.add(g);
		}
		

	}

	private void setItemBar() {
		GButton g;
		for (int i = 0; i < 6; i++) {
			int id = i;
			g = new GButton("assets//battle//itemBtn.png", () -> {
				clickItemButton(id);
				
				refresh("itemBtn");
			});
			
			g.setBounds(ITEMBTN_POS.x + (i % 3) * 145, ITEMBTN_POS.y + (int) (i / 3) * 120);
			
			itemBarList.add(g);
			
			itemMap.put(g, null);
		}
		setItems();
		
	}
	
	private void setItems() {
		int i = 0;
		
		
		for (GButton g:itemBarList) {
			
			if (player.getBag().size() > i) {
			
				itemMap.replace(g, player.getBag().get(i));
				g.setImageIcon(player.getBag().get(i).imagePath);
				
			}
			else {
				itemMap.replace(g, null);
				g.setImageIcon("assets//battle//itemBtn.png");
			}
		
			i++;
		}
		repaint();
	}

	private void clickItemButton(int id) {
		GButton g = itemBarList.get(id);
		
		Item i = itemMap.get(g);
		Random rand = new Random();
		if (i==null) return;
		
		if (i.name.matches("hpUp") ) {
			
			i.useEffect(player, null);
			
		}
		if (i.name.matches("rangeUp")) {
			i.useEffect(player, null);
		}
			
		if (i.name.matches("rangeDown")) {
			i.useEffect(enemy, null)	;
		}
			
		if (i.name.matches("diceChange")) {
			i.useEffect(player, "dice"+(rand.nextInt(dataMgr.getDiceMgr().getSize())-3));	
		}
		
		
		dataMgr.getEventListener().call(EventEnum.USE_ITEM, i);
		
		
	}

	
	private void setDefaultObject() {

		background.setBounds(0, 0);
		bottomBar.setBounds(0, 368);

		

		playerImg.setBounds(PLAYER_POS.x, PLAYER_POS.y);
		entityMap.put(player, playerImg);
		
		
	}

	private void addObejct() {

		for (GPanel g : playerBarList) {
			g.setLayout(null);
			if (g.getImagePath() == "assets//battle//resultBar.png") {
				RESULT_TEXT = String.format("%d", player.getDice().getValue());
				resultLabel.setText(RESULT_TEXT);
				resultLabel.setBounds(21, 20,
						resultLabel.getPreferredSize().width + 30, resultLabel.getPreferredSize().height);
				g.add(resultLabel);
				
			}
			if (g.getImagePath() == "assets//battle//rangeBar.png") {
				RANGE_TEXT = String.format("%d - %d", player.getDice().range.getRange()[0], player.getDice().range.getRange()[1]);
				rangeLabel.setText(RANGE_TEXT);
				rangeLabel.setBounds(35, 10,
						rangeLabel.getPreferredSize().width + 30, rangeLabel.getPreferredSize().height);
				g.add(rangeLabel);
				
			}
			add(g);
		}
		for (GPanel g : enemyBarList) {
			g.setLayout(null);
			if (g.getImagePath() == "assets//battle//resultBar.png") {
				RESULT_TEXT = String.format("%d", enemy.getDice().getValue());
				resultLabel2.setText(RESULT_TEXT);
				resultLabel2.setBounds(21, 20,
						resultLabel2.getPreferredSize().width + 30, resultLabel2.getPreferredSize().height);
				g.add(resultLabel2);
			}
			if (g.getImagePath() == "assets//battle//rangeBar.png") {
			
				RANGE_TEXT = String.format("%d - %d", enemy.getDice().range.getRange()[0], enemy.getDice().range.getRange()[1]);
				rangeLabel2.setText(RANGE_TEXT);
				rangeLabel2.setBounds(35, 10,
						rangeLabel2.getPreferredSize().width + 30, rangeLabel2.getPreferredSize().height);
				g.add(rangeLabel2);
				
				
			}
			add(g);
		}
		
		for (GButton g : itemBarList) {
			add(g);	
			
		}
		add(stageLabel);
		add(playerImg);
		add(enemyImg);
		add(fightBtn);
		add(bottomBar);
		add(background);
		
		for (int i = 0; i < enemyBarList.size() - 2 - enemy.getHp(); i++) {
			enemyBarList.get(i).setImage(blankImage);
			
		}
		for (int i = 0; i < playerBarList.size() - 2 - player.getHp(); i++) {
			playerBarList.get(i).setImage(blankImage);
			
		}
		
	}

	public void showDefeat() {
		UserData userMgr =dataMgr.getUserMgr();
        User user = DataManager.getInstance().getCurrentUser();

        userMgr.updateCoin(user.getId(),coin);
		scMgr.show(Screen.HOME);
		
	}

	@Override
	public Screen getScreenType() {
		return Screen.BATTLE;
	}

	@Override
	public void onShow() {
		
	}

	public void setupButtonHover(GButton button) {

		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {

				button.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {

				button.setBackground(Color.WHITE);
			}

		});
	}
}
