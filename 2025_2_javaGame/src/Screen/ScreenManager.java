package Screen;

import Util.Screen;

import javax.swing.JPanel;

import java.awt.CardLayout;

import java.util.ArrayList;

public class ScreenManager
{
    private JPanel container;
    private CardLayout layout;
    private ArrayList<IScreen> screenList = new ArrayList<>();

    public ScreenManager()
    {
        layout = new CardLayout();
        container = new JPanel(layout);
    }

    public void initScreen(IScreenFactory fac)
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