package Logic;

import Render.IScreen;
import Render.IScreenFactory;
import Render.ScreenManager;
import Render.StartScreen;
import Render.RankingScreen;
import Util.Screen;

import javax.swing.JFrame;


public class Core
{
    // CONSTS
    private final int WINDOW_WIDTH  = 1024;
    private final int WINDOW_HEIGHT = 760;

    // VARIABLES
    private JFrame window;
    private ScreenManager screenManager;

    public boolean initCore()
    {
        // -- set swing window --
        window = new JFrame("OOP-Team-6 Game");
        screenManager = new ScreenManager();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        screenManager.initScreen(new StartScreenFactory());
        screenManager.initScreen(new RankingScreenFactory());
        return true;
    }

    public boolean run()
    {
        // -- get line --
        // -- get clue --
        // -- check clue, log, line status --
        Screen target = Screen.START;
        while(true)
        {
            //if(// -- click button -- )
            {
                switch(target)
                {
                    case Screen.START:
                        screenManager.show(Screen.START);
                        break;
                    case Screen.INTRO:
                        screenManager.show(Screen.INTRO);
                        break;
                    case Screen.PHASE_1:
                        screenManager.show(Screen.PHASE_1);
                        break;
                    case Screen.PHASE_2:
                        screenManager.show(Screen.PHASE_2);
                        break;
                    case Screen.PHASE_3:
                        screenManager.show(Screen.PHASE_3);
                        break;
                    case Screen.END:
                        screenManager.show(Screen.END);
                        break;    
                    case Screen.RANKING:
                        screenManager.show(Screen.RANKING);
                        break;
                    default:
                        assert true : "Error: Invailed type accession";
                }

                window.add(screenManager.getContainer());
                window.setVisible(true);
            }
            return true;
        }
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

class RankingScreenFactory implements IScreenFactory
{
    public IScreen create()
    {
        return new RankingScreen();
    }
}

