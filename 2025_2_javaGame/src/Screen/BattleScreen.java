package Screen;

import Logic.BattleManager;
import Render.BattleView;
import Util.Screen;

import javax.swing.JPanel;

import Action.GButton;
import Action.GPanel;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BattleScreen extends JPanel implements IScreen
{

	private final GPanel background = new GPanel("assets//battle//background.png");
	private final GPanel bottomBar = new GPanel("assets//battle//bottomBar.png");
	private final GPanel defeat = new GPanel("assets//battle//defeat.png");
	private final GPanel enemy1 = new GPanel("assets//battle//enemy1.png");
	private final GPanel enemy2 = new GPanel("assets//battle//enemy2.png");
	private final GPanel enemy3 = new GPanel("assets//battle//enemy3.png");
	private final GButton fightBtn = new GButton("assets//battle//fightBtn.png", ()->{System.out.print("asd");});
	private final GPanel hpBar = new GPanel("assets//battle//hpBar.png");
	private final GButton itemBtn = new GButton("assets//battle//itemBtn.png", ()->{System.out.print("asd");});
	private final GPanel rangeBar = new GPanel("assets//battle//rangeBar.png");
	private final GPanel resultBar = new GPanel("assets//battle//resultBar.png");
	private final GPanel player = new GPanel("assets//battle//warrior.png");
	//private final GPanel wizard = new GPanel("assets//battle//wizard.png");


    
    
    @Override
    public void init(final ScreenManager scManager)
    {
        setLayout(null);
        
        background.setBounds(0,0);
        bottomBar.setBounds(0,368);
        defeat.setBounds(295,262);
        enemy1.setBounds(772,284);
        enemy2.setBounds(748,184);
        enemy3.setBounds(708,140);
        fightBtn.setBounds(536,520);
        hpBar.setBounds(184,156);
        itemBtn.setBounds(28,520);
        rangeBar.setBounds(252,88);
        resultBar.setBounds(176,72);
        player.setBounds(176,228);
        
        
//        wizard.setBounds(201,247);
//        add(wizard);
        
        add(player);
        add(enemy3);
        add(enemy2);
        add(enemy1);
        add(hpBar);
        add(rangeBar);
        add(resultBar);
        add(itemBtn);
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
        return Screen.LOGIN;
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
