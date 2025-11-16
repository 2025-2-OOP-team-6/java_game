package Screen;

import Logic.BattleManager;
import Render.BattleView;
import Util.Screen;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;

public class BattleScreen extends JPanel implements IScreen
{
    private BattleManager battleManager;
    private BattleView battleView;

    @Override
    public void init(final ScreenManager scManager)
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setOpaque(false);

        battleManager = new BattleManager();

        battleView = new BattleView(
            battleManager,
            () -> scManager.show(Screen.HOME)
        );

        add(battleView, BorderLayout.CENTER);
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
}
