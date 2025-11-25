package Logic;

import Data.DataManager;
import GameLogic.GameManager;
import Screen.ScreenManager;
import Util.Constant;
import Util.Screen;

import Action.ActionManager;

import javax.swing.JFrame;
import java.util.IntSummaryStatistics;


public class Core
{
    // CONSTANT
    private Constant constant;

    // VARIABLES
    private JFrame window;
    private ScreenManager screenManager;
    private ActionManager actionManager;
    private GameManager gameManager;

    public void initCore()
    {
        // -- set swing window --
        window = new JFrame("OOP-Team-6 Game");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(constant.WINDOW_WIDTH, constant.WINDOW_HEIGHT);
        window.setLocationRelativeTo(null);
        
        
        DataManager.getInstance();
        
        gameManager = new GameManager();
      screenManager = new ScreenManager();

        screenManager.setGameMgr(gameManager);
        screenManager.initPriorityScreen();
        screenManager.show(Screen.LOGIN);

        window.add(screenManager.getContainer());
        window.setVisible(true);
    }

    public void run()
    {
    }

    public void end()
    {
        // -- ending credit --
    }
}