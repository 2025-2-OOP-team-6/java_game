package Screen;


import Data.DataManager;
import Action.GButton;
import Util.Constant;
import Data.UserData;
import Util.Screen;
import Logic.User;


import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Color;
import java.awt.Font;


public class HomeScreen extends JPanel implements IScreen
{
    //CONST
    private final String START_BTN  = "..//assets//buttons//startBtn.png";
    private final String LOGOUT_BTN = "..//assets//buttons//logoutBtn.png";
    private final String MARKET_BTN = "..//assets//buttons//marketBtn.png";
    private final String SELECT_BTN = "..//assets//buttons//selectBtn.png";


    //VARIABLES
    private JLabel coinLabel;
    private JLabel rankLabel;
    private JLabel timeLabel;
    private JLabel welcomeLabel;

    private GButton startBtn;
    private GButton logoutBtn;
    private GButton marketBtn;
    private GButton selectChrBtn;

    private Color wallpaper;

    private User user;
    private UserData userData;
    private Constant constant;




    @Override
    public void init(ScreenManager scManager)
    {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
        setBackground(wallpaper);

        user = DataManager.getInstance().getCurrentUser();
        userData = DataManager.getInstance().getUserMgr();

        add(createHeaderPanel(scManager), BorderLayout.NORTH);
        add(createCenterPanel(scManager), BorderLayout.CENTER);
        add(createBottomPanel(scManager), BorderLayout.SOUTH);
    }


    // -- Set Header Panel --

    private JPanel createHeaderPanel(ScreenManager scManager)
    {
        JPanel headerPanel =  new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel welcomeArea = new JPanel();
        welcomeArea.setLayout(new BoxLayout(welcomeArea, BoxLayout.X_AXIS));
        welcomeArea.setOpaque(false);

        // - Set welcome -
        welcomeLabel = new JLabel("환영합니다, " + "user.getId()" + "님!");
        welcomeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);

        // - Set coin -
        String userCoin = "String.valueOf(userData.getCoin(user.getId()))";
        coinLabel = new JLabel(userCoin + " 코인");
        coinLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        coinLabel.setForeground(Color.YELLOW);

        welcomeArea.add(welcomeLabel);
        welcomeArea.add(Box.createHorizontalStrut(10));
        welcomeArea.add(coinLabel);

        logoutBtn = new GButton(LOGOUT_BTN, ()->{
            logoutLogic();
            scManager.show(Screen.LOGIN);
        });

        headerPanel.add(welcomeArea, BorderLayout.WEST);
        headerPanel.add(logoutBtn, BorderLayout.EAST);

        return headerPanel;
    }

    private void logoutLogic()
    {
        DataManager.getInstance().getAccountMgr().storeAccountData();
        userData.storeUserData(user.getId());
    }


    // -- Set Center Panel --

    private JPanel createCenterPanel(ScreenManager scManager)
    {
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        centerPanel.setOpaque(false);

        startBtn = new GButton(START_BTN, ()->{
           scManager.show(Screen.START);
        });

        marketBtn = new GButton(MARKET_BTN, ()->{
           scManager.show(Screen.MARKET);
        });

        selectChrBtn = new GButton(SELECT_BTN, ()->{
           scManager.show(Screen.SELECT);
        });

        centerPanel.add(startBtn);
        centerPanel.add(marketBtn);
        centerPanel.add(selectChrBtn);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return centerPanel;
    }


    // -- Set Bottom Panel --

    private JPanel createBottomPanel(ScreenManager scManager)
    {
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        bottomPanel.setOpaque(false);

        // - Set rank -
        String rank = "String.valueOf(userData.getRank(user.getId()))";
        rankLabel = new JLabel(rank + " 랭크");
        rankLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        rankLabel.setForeground(Color.WHITE);
        rankLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // - Set rank -
        String time = "String.valueOf(userData.getTime(user.getId()))";
        timeLabel = new JLabel(time + " 초");
        timeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel.add(rankLabel);
        bottomPanel.add(timeLabel);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return bottomPanel;
    }


    @Override
    public Screen getScreenType() {return Screen.HOME;}

    @Override
    public void onShow()
    {
        user = DataManager.getInstance().getCurrentUser();

        if(user != null)
        {
            welcomeLabel.setText("환영합니다, " + user.getId() + "님!");

            String coin = String.valueOf(userData.getCoin(user.getId()));
            coinLabel.setText(coin + " 코인");

            String rank = String.valueOf(userData.getRank(user.getId()));
            rankLabel.setText(rank + "랭크");

            String time = String.valueOf(userData.getTime(user.getId()));
            timeLabel.setText(time + "초");
        }

        System.out.println("Start: HomeScreen is now Rendering");
    }
}
