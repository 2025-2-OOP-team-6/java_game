package Screen;

import Logic.User;
import Util.Screen;
import Data.LogData;
import Data.UserData;
import Util.Constant;
import Action.GButton;
import Data.DataManager;
import Data.CharactorData;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;


public class statisticScreen extends JPanel implements IScreen
{
    //CONST
    private final String BAR_IMAGE       = "..//assets//images//barImage.png";
    private final String RANK_IMAGE      = "..//assets//images//rankLabel.png";
    private final String GOBACK_BTN      = "..//assets//buttons//gobackBtn.png";
    private final String ARROW_IMAGE     = "..//assets//images//arrowLabel.png";
    private final String STATISTIC_TITLE = "..//assets//images//statisticTitle.png";

    //VARIABLES
    private User user;
    private LogData logMgr;
    private UserData userMgr;
    private ScreenManager screenMgr;

    private Color wallpaper;
    private Constant constant;

    @Override
    public void init(ScreenManager screenMgr) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
        setBackground(wallpaper);

        this.screenMgr = screenMgr;
        logMgr = DataManager.getInstance().getLogMgr();
        userMgr = DataManager.getInstance().getUserMgr();
        user = DataManager.getInstance().getCurrentUser();


        setComponent();
    }

    private JPanel createHeaderPanel()
    {
        JPanel header = new JPanel(new GridLayout(0, 2, 10, 10));
        header.setOpaque(false);

        ImageIcon original = new ImageIcon(STATISTIC_TITLE);
        int width = original.getIconWidth() * 2;
        int height = original.getIconHeight() * 2;
        Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel titleL = new JLabel(new ImageIcon(scaled));

        GButton gobakBtn = new GButton(GOBACK_BTN, ()->{
            screenMgr.show(Screen.HOME);
        });

        header.add(gobakBtn);
        header.add(titleL);

        return header;
    }

    private JPanel createTimeGraph()
    {
        String[] timeList = logMgr.getTimeGraphData(user.getId());

        for(String time : timeList)
        {

        }


        JPanel graph = new JPanel(new GridLayout);


    }

    private JPanel createStatisticPanel()
    {
        JPanel statistic = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new BorderLayout());



        CardLayout statisticLayout = new CardLayout();





        return statistic;
    }

    private JScrollPane createLogPanel()
    {
        String[] logList = logMgr.getLogList();

    JPanel logListPanel = new JPanel(new BorderLayout());

        for(String log : logList)
        {
            JPanel logPanel = new JPanel(new BorderLayout());

            JLabel logL = new JLabel(log);
            logL.setOpaque(false);
            logL.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            logL.setAlignmentX(Component.CENTER_ALIGNMENT);
            logL.setForeground(Color.WHITE);

            logPanel.add(logL);
            logListPanel.add(logPanel);
        }

        JScrollPane scrollPane = new JScrollPane(logListPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(800, 500));

        return scrollPane;
    }

    private void setComponent()
    {

    }

    @Override
    public Screen getScreenType() {
        return null;
    }

    @Override
    public void onShow() {

    }
}
