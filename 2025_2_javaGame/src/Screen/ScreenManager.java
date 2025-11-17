package Screen;

import Util.Screen;

import javax.swing.JPanel;

import GameLogic.GameManager;

import java.awt.CardLayout;

import java.util.ArrayList;

public class ScreenManager
{
    private JPanel container;
    private CardLayout layout;
    private ArrayList<IScreen> screenList = new ArrayList<>();
    private GameManager gameMgr;

    public ScreenManager()
    {
        layout = new CardLayout();
        container = new JPanel(layout);
    }

    public void initPriorityScreen()
    {
        initScreen(new LoginScreenFactory());
        initScreen(new SignUpScreenFacotry());
    }

    public void setGameMgr(GameManager gameMgr) {
    	this.gameMgr = gameMgr;
    }
    public GameManager getGameMgr() {
    	return gameMgr;
    }
    
    public void initAllScreens()
    {
        initScreen(new StartScreenFactory());
        initScreen(new HomeScreenFactory());
//        initScreen(new MarketScreenFactory());
//        initScreen(new InventoryScreenFactory());
//        initScreen(new SelectScreenFactory());
        initScreen(new RankScreenFactory());
        initScreen(new BattleScreenFactory());
    }

    private void initScreen(IScreenFactory fac)
    {
        IScreen screen = fac.create();
        screen.init(this);
        screenList.add(screen);
        container.add((JPanel)screen, screen.getScreenType().name());
    }

    public void show(final Screen type)
    {
        for(IScreen target : screenList)
        {
            if(target.getScreenType() == type)
            {
                target.onShow();
                layout.show(container, type.name());
                return;
            }
        }
        throw new RuntimeException("Error: Screen not found" + type);
    }

    public JPanel getContainer()
    {
        return container;
    }
}

class StartScreenFactory implements IScreenFactory
{
    public IScreen create()
    {
        return new StartScreen();
    }
}
class SignUpScreenFacotry implements IScreenFactory
{
    public IScreen create() {return new SignUpScreen();}
}
class HomeScreenFactory implements IScreenFactory
{
    public IScreen create() {return new HomeScreen();}
}
class LoginScreenFactory implements IScreenFactory
{
    public IScreen create()  {return new LoginScreen();}
}
//class MarketScreenFactory implements IScreenFactory
//{
//    public IScreen create() { return new MarketScreen();}
//}
//class InventoryScreenFactory implements IScreenFactory
//{
//    public IScreen create() {return new InventoryScreen();}
//}
//class SelectScreenFactory implements IScreenFactory
//{
//    public IScreen create() {return new SelectScreen();}
//}
class RankScreenFactory implements IScreenFactory
{
    public IScreen create() {return new RankScreen();}
}
class BattleScreenFactory implements IScreenFactory
{
    @Override
    public IScreen create()
    {
        return new BattleScreen();
    }
}

