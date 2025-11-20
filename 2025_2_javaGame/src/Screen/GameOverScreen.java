package Screen;

import Data.DataManager;
import Action.GButton;
import Util.Constant;
import Data.UserData;
import Util.Screen;
import Logic.User;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.FlowLayout;

public class GameOverScreen extends JPanel implements IScreen
{
    //CONST
    private final String HOME_BTN = "..//assets//buttons//gobackBtn.png";
    private final String RETRY_BTN = "..//assets//buttons//startAdventureBtn.png";
    private final String CREDIT_BTN = "..//assets//buttons//endingCreditBtn.png";

    //VARIABLES
    private JLabel titleLabel;
    private JLabel timeLabel;
    private JLabel coinLabel;

    private GButton homeBtn;
    private GButton retryBtn;
    private GButton creditBtn;

    private Color wallpaper;

    private User user;
    private UserData userData;
    private ScreenManager screenMgr;

    // 게임 결과 데이터
    private int timeSpent = 0;
    private int coin = 0;

    @Override
    public void init(ScreenManager scManager)
    {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        wallpaper = new Color(Constant.WALL_RED, Constant.WALL_GREEN, Constant.WALL_BLUE);
        setBackground(wallpaper);

        user = DataManager.getInstance().getCurrentUser();
        userData = DataManager.getInstance().getUserMgr();
        screenMgr = scManager;

        setComponent();
    }

    private void setComponent()
    {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    // -- Set Header Panel --
    private JPanel createHeaderPanel()
    {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        titleLabel = new JLabel("게임 오버");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 60));
        titleLabel.setForeground(Color.RED);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(20));

        return headerPanel;
    }

    // -- Set Center Panel --
    private JPanel createCenterPanel()
    {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // 게임 시간 표시 현재는 임시로 유저 데이터에서 불러오도록 설정
        timeLabel = new JLabel("게임 시간: " + userData.getTime(user.getId()) + "초");
        timeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(timeLabel);
        centerPanel.add(Box.createVerticalStrut(20));

        // 보상 표시
        coinLabel = new JLabel("현재 코인: " + userData.getCoin(user.getId()));
        coinLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        coinLabel.setForeground(Color.YELLOW);
        coinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(coinLabel);

        return centerPanel;
    }

    // -- Set Bottom Panel --
    private JPanel createBottomPanel()
    {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        bottomPanel.setOpaque(false);

        retryBtn = new GButton(RETRY_BTN, () -> {
            resetGameData();
            screenMgr.show(Screen.START);
        });

        homeBtn = new GButton(HOME_BTN, () -> {
            // 보상을 사용자에게 지급
            int currentCoin = userData.getCoin(user.getId());
            userData.updateCoin(user.getId(), currentCoin);
            userData.storeUserData(user.getId());

            resetGameData();
            screenMgr.show(Screen.HOME);
        });

        creditBtn = new GButton(CREDIT_BTN, () -> {
            screenMgr.show(Screen.CREDIT);
        });

        bottomPanel.add(retryBtn);
        bottomPanel.add(creditBtn);
        bottomPanel.add(homeBtn);

        return bottomPanel;
    }

    /**
     * 게임 결과 데이터 설정
     * @param timeSpent 게임 시간 (초)
     * @param coin 남은 코인 
     */
    public void setGameResult(int timeSpent, int coin)
    {
        this.timeSpent = timeSpent;
        this.coin = coin;
    }

    /**
     * 게임 데이터 초기화
     */
    private void resetGameData()
    {
        this.timeSpent = 0;
        this.coin = 0;
    }

    @Override
    public Screen getScreenType() {
         return Screen.GAMEOVER; 
        }

    @Override
    public void onShow()
    {
        removeAll();
        setComponent();
        revalidate();
        repaint();
        System.out.print("Start: GameOverScreen is now Rendering\n");

        
    }
}
