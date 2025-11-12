package Logic;

import Screen.*;
import Util.Constant;

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


    public void initCore()
    {
        // -- set swing window --
        window = new JFrame("OOP-Team-6 Game");



        screenManager = new ScreenManager();
        actionManager = new ActionManager();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(constant.WINDOW_WIDTH, constant.WINDOW_HEIGHT);

        screenManager.initScreen(new LoginScreenFactory());
        screenManager.initScreen(new SignUpScreenFacotry());
        screenManager.initScreen(new StartScreenFactory());
        screenManager.initScreen(new HomeScreenFactory());

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
    public IScreen create()
    {
        return new LoginScreen();
    }
}

