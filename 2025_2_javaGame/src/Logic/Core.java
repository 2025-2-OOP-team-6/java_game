package Logic;

import Render.IScreen;
import Render.IScreenFactory;
import Render.ScreenManager;
import Render.StartScreen;
import Util.Screen;

import javax.swing.JFrame;

import Action.ActionManager;
import LoginScreen.HomeScreen;


public class Core
{
    // CONSTS
    private final int WINDOW_WIDTH  = 1000;
    private final int WINDOW_HEIGHT = 1000;

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
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        screenManager.initScreen(new HomeScreenFactory());
        screenManager.initScreen(new StartScreenFactory());
       

        window.add(screenManager.getContainer());
        window.setVisible(true);
    }

    public void run()
    {
        // -- get line --
        // -- get clue --
        // -- check clue, log, line status --
//        while(true)
//        {
//            //if(// -- click button -- )
//            {
//                switch(target)
//                {
//                    case Screen.START:
//                        screenManager.show(Screen.START);
//                        break;
//                    case Screen.INTRO:
//                        screenManager.show(Screen.INTRO);
//                        break;
//                    case Screen.PHASE_1:
//                        screenManager.show(Screen.PHASE_1);
//                        break;
//                    case Screen.PHASE_2:
//                        screenManager.show(Screen.PHASE_2);
//                        break;
//                    case Screen.PHASE_3:
//                        screenManager.show(Screen.PHASE_3);
//                        break;
//                    case Screen.END:
//                        screenManager.show(Screen.END);
//                        break;
//                    default:
//                        assert true : "Error: Invailed type accession";
//                }
//
//                window.add(screenManager.getContainer());
//                window.setVisible(true);
//            }
//        }

       
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
class HomeScreenFactory implements IScreenFactory
{
    public IScreen create()
    {
        return new HomeScreen();
    }
}

