package Screen;

import Data.DataManager;
import Action.GButton;
import Util.Constant;
import Data.UserData;
import Util.Screen;
import Logic.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;

public class HomeScreen extends JPanel implements IScreen {
    // CONST
    private final String START_BTN = "..//assets//buttons//startBtn.png";
    private final String LOGOUT_BTN = "..//assets//buttons//logoutBtn.png";
    private final String MARKET_BTN = "..//assets//buttons//marketBtn.png";
    private final String SELECT_BTN = "..//assets//buttons//selectBtn.png";
    private final String INVEN_BTN = "..//assets//buttons//invenBtn.png";
    private final String RANK_BTN = "..//assets//buttons//rankBtn.png";
    private final String MYPAGE_BTN = "..//assets//buttons//myPageBtn.png";
    private final String STATISTIC_BTN = "..//assets//buttons//statisticBtn.png";

    // VARIABLES
    private JLabel coinLabel;
    private JLabel rankLabel;
    private JLabel timeLabel;
    private JLabel welcomeLabel;

    private GButton rankBtn;
    private GButton invenBtn;
    private GButton startBtn;
    private GButton logoutBtn;
    private GButton marketBtn;
    private GButton selectChrBtn;
    private GButton mypageBtn;
    private GButton statisticBtn;

    private Color wallpaper;

    private User user;
    private UserData userData;
    private Constant constant;

    private ScreenManager screenMgr;

    @Override
    public void init(ScreenManager scManager) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
        setBackground(wallpaper);

        user = DataManager.getInstance().getCurrentUser();
        userData = DataManager.getInstance().getUserMgr();
        screenMgr = scManager;

        setComponent();
    }

    private void setComponent() {
        add(createHeaderPanel(screenMgr), BorderLayout.NORTH);
        add(createCenterPanel(screenMgr), BorderLayout.CENTER);
        add(createBottomPanel(screenMgr), BorderLayout.SOUTH);
    }

    // -- Set Header Panel --

    private JPanel createHeaderPanel(ScreenManager scManager) {
        Color darkWall = new Color(64, 64, 115);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel welcomeArea = new JPanel();
        welcomeArea.setLayout(new BoxLayout(welcomeArea, BoxLayout.X_AXIS));
        welcomeArea.setOpaque(false);

        // - Set welcome -
        welcomeLabel = new JLabel("환영합니다, " + user.getId() + "님!");
        welcomeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);

        RoundPanel welcomBox = new RoundPanel(25);
        welcomBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        welcomBox.setBackground(darkWall);
        welcomBox.setBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10));
        welcomBox.add(welcomeLabel);

        // - Set coin -

        String userCoin = String.valueOf(userData.getCoin(user.getId()));
        coinLabel = new JLabel(userCoin + " 코인");
        coinLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        coinLabel.setForeground(Color.YELLOW);

        RoundPanel coinBox = new RoundPanel(25);
        coinBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        coinBox.setBackground(darkWall);
        coinBox.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.YELLOW, 1),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        coinBox.add(coinLabel);

        welcomeArea.add(welcomBox, BorderLayout.EAST);
        welcomeArea.add(coinBox, BorderLayout.WEST);

        welcomeArea.add(welcomBox, BorderLayout.EAST);
        welcomeArea.add(coinBox, BorderLayout.WEST);

        logoutBtn = new GButton(LOGOUT_BTN, () -> {
            logoutLogic();
            scManager.show(Screen.LOGIN);
        });

        headerPanel.add(welcomeArea, BorderLayout.WEST);
        headerPanel.add(logoutBtn, BorderLayout.EAST);

        return headerPanel;
    }

    private void logoutLogic()
    {
        DataManager.getInstance().getUserMgr().storeUserData();
    }

    // -- Set Center Panel --

    private JPanel createCenterPanel(ScreenManager scManager) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        // 패널 위쪽에 여백을 넣어서 헤더와 떨어트림
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.setOpaque(false);

        final int BTN_WIDTH = 150;
        final int BTN_HEIGHT = 150;

        Image original = new ImageIcon(STATISTIC_BTN).getImage();
        Image scaled = original.getScaledInstance(BTN_WIDTH, BTN_HEIGHT, Image.SCALE_SMOOTH);
        statisticBtn = new GButton(new ImageIcon(scaled), () -> {
            scManager.show(Screen.STATISTIC);
        });

        original = new ImageIcon(START_BTN).getImage();
        scaled = original.getScaledInstance(BTN_WIDTH, BTN_HEIGHT, Image.SCALE_SMOOTH);
        startBtn = new GButton(new ImageIcon(scaled), () -> {
        	
        	scManager.getGameMgr().initGame();
            scManager.show(Screen.BATTLE);

        });

        original = new ImageIcon(MARKET_BTN).getImage();
        scaled = original.getScaledInstance(BTN_WIDTH, BTN_HEIGHT, Image.SCALE_SMOOTH);
        marketBtn = new GButton(new ImageIcon(scaled), () -> {
            scManager.show(Screen.MARKET);
        });

        original = new ImageIcon(SELECT_BTN).getImage();
        scaled = original.getScaledInstance(BTN_WIDTH, BTN_HEIGHT, Image.SCALE_SMOOTH);
        selectChrBtn = new GButton(new ImageIcon(scaled), () -> {
            scManager.show(Screen.SELECT);
        });

        original = new ImageIcon(INVEN_BTN).getImage();
        scaled = original.getScaledInstance(BTN_WIDTH, BTN_HEIGHT, Image.SCALE_SMOOTH);
        invenBtn = new GButton(new ImageIcon(scaled), () -> {
            scManager.show(Screen.INVEN);
        });

        original = new ImageIcon(RANK_BTN).getImage();
        scaled = original.getScaledInstance(BTN_WIDTH, BTN_HEIGHT, Image.SCALE_SMOOTH);
        rankBtn = new GButton(new ImageIcon(scaled), () -> {
            scManager.show(Screen.RANK);
        });

        original = new ImageIcon(MYPAGE_BTN).getImage();
        scaled = original.getScaledInstance(BTN_WIDTH, BTN_HEIGHT, Image.SCALE_SMOOTH);
        mypageBtn = new GButton(new ImageIcon(scaled), () -> {
            scManager.show(Screen.MYPAGE);
        });

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        row1.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        row1.setOpaque(false);
        row1.add(invenBtn);
        row1.add(startBtn);
        row1.add(selectChrBtn);

        centerPanel.add(statisticBtn);
        centerPanel.add(rankBtn);
        //centerPanel.add(invenBtn);
        centerPanel.add(startBtn);

        centerPanel.add(marketBtn);
        //centerPanel.add(selectChrBtn);
        centerPanel.add(mypageBtn);
        centerPanel.add(statisticBtn);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        return centerPanel;
    }

    // -- Set Bottom Panel --

    private JPanel createBottomPanel(ScreenManager scManager) {
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        bottomPanel.setOpaque(false);

        Color darkWall = new Color(64, 64, 115);

        // - Set rank -
        String rank = String.valueOf(userData.getRank(user.getId()));
        rankLabel = new JLabel(rank + " 랭크");
        rankLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        rankLabel.setForeground(Color.WHITE);
        rankLabel.setHorizontalAlignment(SwingConstants.CENTER);

        RoundPanel rankBox = new RoundPanel(25);
        rankBox.setLayout(new BorderLayout());
        rankBox.setBackground(darkWall);
        rankBox.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(),
                        "BEST RANK!!",
                        TitledBorder.CENTER,
                        TitledBorder.TOP,
                        new Font("맑은 고딕", Font.BOLD, 12),
                        Color.LIGHT_GRAY));
        rankBox.add(rankLabel);

        rankBox.setLayout(new BorderLayout());
        rankBox.setBackground(darkWall);
        rankBox.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(),
                        "BEST RANK!!",
                        TitledBorder.CENTER,
                        TitledBorder.TOP,
                        new Font("맑은 고딕", Font.BOLD, 12),
                        Color.LIGHT_GRAY));
        rankBox.add(rankLabel);


//        RoundPanel timeBox = new RoundPanel(25);
//        timeBox.setLayout(new BorderLayout());
//        timeBox.setBackground(darkWall);
//        timeBox.setBorder(
//                BorderFactory.createTitledBorder(
//                        BorderFactory.createEmptyBorder(),
//                        "TIME!!",
//                        TitledBorder.CENTER,
//                        TitledBorder.TOP,
//                        new Font("맑은 고딕", Font.BOLD, 12),
//                        Color.LIGHT_GRAY));
//
//        timeBox.add(timeLabel);

        bottomPanel.add(rankBox);
        //bottomPanel.add(timeBox);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return bottomPanel;
    }

    @Override
    public Screen getScreenType() {
        return Screen.HOME;
    }

    @Override
    public void onShow() {
        UserData userMgr = DataManager.getInstance().getUserMgr();
        User user = DataManager.getInstance().getCurrentUser();

        String coinString = String.valueOf(userMgr.getCoin(user.getId()));
        String rankString = String.valueOf(userMgr.getRank(user.getId()));
        coinLabel.setText(coinString + " coin");
        rankLabel.setText(rankString + " rank");

        removeAll();
        setComponent();
        revalidate();
        repaint();

        System.out.println("Start: HomeScreen is now Rendering");
    }
}
