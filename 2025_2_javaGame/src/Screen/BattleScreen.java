package Screen;

import Logic.BattleManager;
import Render.BattleView;
import Util.EventEnum;
import Util.Position;
import Util.Screen;

import javax.swing.JPanel;

import Action.GButton;
import Action.GPanel;
import Data.DataManager;
import GameLogic.Enemy;
import GameLogic.Entity;
import GameLogic.Item;
import GameLogic.Player;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class BattleScreen extends JPanel implements IScreen
{
	private final DataManager dataMgr = DataManager.getInstance();

	private final GPanel background = new GPanel("assets//battle//background.png");
	private final GPanel bottomBar = new GPanel("assets//battle//bottomBar.png");
	private final GPanel defeat = new GPanel("assets//battle//defeat.png");
	private final GPanel enemy1 = new GPanel("assets//battle//enemy1.png");
	private final GPanel enemy2 = new GPanel("assets//battle//enemy2.png");
	private final GPanel enemy3 = new GPanel("assets//battle//enemy3.png");
	private final GButton fightBtn = new GButton("assets//battle//fightBtn.png", null);
	
	
	private final GPanel playerImg = new GPanel("assets//battle//warrior.png");
	//private final GPanel wizard = new GPanel("assets//battle//wizard.png");

	private final Position PLAYER_POS = new Position(176,228);
	private final Position ENEMY1_POS = new Position(772,284);
	private final Position ENEMY2_POS = new Position(748,184);
	private final Position ENEMY3_POS = new Position(708,140);
	private final Position ITEMBTN_POS = new Position(45,517);
	
    
    private Enemy enemy;
    private Player player;
    
    private HashMap<Entity, GPanel> entityMap;
    private HashMap<GButton, Item> itemMap;
    
    private ArrayList<GPanel> playerBarList;
    private ArrayList<GPanel> enemyBarList;
    private ArrayList<GButton> itemBarList;
    
    @Override
    public void init(final ScreenManager scManager)
    {
    	scManager.getGameMgr().initGame();
    	entityMap = new HashMap<>();
    	itemMap = new HashMap<>();
    	playerBarList = new ArrayList<>();
    	enemyBarList = new ArrayList<>();
    	itemBarList = new ArrayList<>();
    	
    	player = dataMgr.getEventListener().getPlayer();
    	
        setLayout(null);
        
        
        setStage(dataMgr.getEventListener().getMapNum());    
    }
    
    private void setStage(int id) {
    	
    	setDefaultObject();
    	System.out.print(dataMgr.getEventListener().getMapNum());
    	switch(id) {
    		case 1: {
    			enemy = dataMgr.getEnemyMgr().get("enemy1").clone();
    	        enemy1.setBounds(ENEMY1_POS.x,ENEMY1_POS.y);
    	        entityMap.put(enemy, enemy1);
    	        setBar(ENEMY1_POS,enemy);
    			add(enemy1);
    			break;
    		}
    		case 2: {
    			enemy = dataMgr.getEnemyMgr().get("enemy2").clone();
    	        enemy2.setBounds(ENEMY2_POS.x,ENEMY2_POS.y);
    	        entityMap.put(enemy, enemy2);
    	        setBar(ENEMY2_POS,enemy);
    			add(enemy2);
    			break;
    		}
    		case 3: {
    			enemy = dataMgr.getEnemyMgr().get("enemy3").clone();
    	        enemy3.setBounds(ENEMY3_POS.x,ENEMY3_POS.y);
    	        entityMap.put(enemy, enemy3);
    	        setBar(ENEMY3_POS,enemy);
    			add(enemy3);
    			break;
    		}
    		default: 
    	}
    	
    	fightBtn.setListener(()->{dataMgr.getEventListener().call(EventEnum.TURN_MOVE, enemy);});
    	addObejct();
    }
    
    private void setBar(Position pos, Entity e) {
    	GPanel g;
    	int hp = e.getHp();
    	int width = entityMap.get(e).getWidth();
    	int firstPosX = pos.x+width/2-hp*40/2;
    	
    	for (int i=0; i<hp; i++) {
    		g = new GPanel("assets//battle//hpBar.png");
    		g.setBounds(firstPosX + i*40,pos.y-47);
    		if (e instanceof Player)
    			playerBarList.add(g);
    		else
    			enemyBarList.add(g);
    	}
    	
    	g = new GPanel("assets//battle//resultBar.png");
    	
    	g.setBounds(pos.x + width/2 - 27, pos.y-135);
    	if (e instanceof Player)
			playerBarList.add(g);
		else
			enemyBarList.add(g);
    	
    	g = new GPanel("assets//battle//rangeBar.png");
    	g.setBounds(pos.x + width/2 + 45, pos.y-120);
    	if (e instanceof Player)
			playerBarList.add(g);
		else
			enemyBarList.add(g);
    }
    
    private void setItemBar() {
    	GButton g;
    	
    	for(int i=0; i<6; i++) {
    		int id = i;
    		g = new GButton("assets//battle//itemBtn.png", () -> clickItemButton(id));
    		g.setBounds(ITEMBTN_POS.x+(i%3)*145,ITEMBTN_POS.y+(int)(i/3)*120);
    		System.out.print(g.getY()+"\n");
    		itemBarList.add(g);
    		itemMap.put(g, null);
    	}
    }
    
    private void clickItemButton(int id) {
    	itemMap.get(itemBarList.get(id)).useEffect(player, null);
	}

	private void setDefaultObject() {
		
        background.setBounds(0,0);
        bottomBar.setBounds(0,368);
        fightBtn.setBounds(536,520);
        

        setItemBar();
        
        playerImg.setBounds(PLAYER_POS.x, PLAYER_POS.y);
        entityMap.put(player, playerImg);
        setBar(PLAYER_POS, player);
    }
    
    private void addObejct() {
    	for(GPanel g:playerBarList) {
    		add(g);
    	}
    	for(GPanel g:enemyBarList) {
    		add(g);
    	}
    	for(GButton g:itemBarList) {
    		add(g);
    	}
    	add(playerImg);
        add(fightBtn);
        add(bottomBar);
        add(background);
    }
    
    
    
    
    public void showDefeat() {
    	add(battleView, defeat);
    }

    @Override
    public Screen getScreenType()
    {
        return Screen.BATTLE;
    }

    @Override
    public void onShow()
    {
        System.out.println("Start: BattleScreen is now Rendering");
    }
    public void setupButtonHover(GButton button) {
        
        button.addMouseListener(new MouseAdapter() {
            
            
            @Override
            public void mouseEntered(MouseEvent e) {

                button.setBackground(Color.LIGHT_GRAY);
                System.out.println("커서가 버튼 위에 있습니다.");
            }


            @Override
            public void mouseExited(MouseEvent e) {
                
                button.setBackground(Color.WHITE);
                System.out.println("커서가 버튼을 벗어났습니다.");
            }
            
        });
    }
}
