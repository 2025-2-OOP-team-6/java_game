package Screen;

import Data.DataManager;
import Data.LogData;
import Data.AchievementManager;
import Action.GButton;
import Util.Constant;
import Data.UserData;
import Util.Screen;
import Logic.User;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.Box;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;

public class MyPageScreen extends JPanel implements IScreen {
    private final String MYPAGE_IMG = "assets//images//mypageImage.png";
    private final String COIN_IMG = "assets//images//coin.png";
    private final String GOBACK_BTN = "assets//buttons//gobackBtn.png";

    private final String BATTLE_CNT = "assets//images//battleCnt.png";
    private final String WIN_CNT = "assets//images//winCnt.png";
    private final String LOSE_CNT = "assets//images//loseCnt.png";
    private final String WIN_RATE = "assets//images//winRate.png";

    private CardPanel profileCard;
    private CardPanel userCard;
    private CardPanel idCard;
    private CardPanel achCard;
    private CardPanel rankCard;
    private CardPanel coinCard;
    private CardPanel logCard;
    private CardPanel battleCntCard;
    private CardPanel winCntCard;
    private CardPanel loseCntCard;
    private CardPanel winRateCard;

    private GButton gobackBtn;

    private Color wallpaper;

    private User user;
    private UserData userData;
    private LogData logMgr;
    private AchievementManager achMgr;

    private Constant constant;
    private ScreenManager screenMgr;

    @Override
    public void init(ScreenManager scManager) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
        setBackground(wallpaper);

        user = DataManager.getInstance().getCurrentUser();
        userData = DataManager.getInstance().getUserMgr();
        logMgr = DataManager.getInstance().getLogMgr();
        achMgr = new AchievementManager(logMgr);
        screenMgr = scManager;

        setComponent();
    }

    private void setComponent() {
        add(createHeaderPanel(screenMgr), BorderLayout.NORTH);
        add(createCenterPanel(screenMgr), BorderLayout.CENTER);
        add(createBottomPanel(screenMgr), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel(ScreenManager scManager) {

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        headerPanel.setBackground(new Color(15, 23, 42, 128));
        headerPanel.setOpaque(true);

        JLabel myPageLabel = new JLabel(new ImageIcon(MYPAGE_IMG));
        JLabel myPageTitle = new JLabel("마이페이지");
        myPageTitle.setFont(new Font("맑은고딕", Font.PLAIN, 15));
        myPageTitle.setForeground(Color.WHITE);

        headerPanel.add(myPageLabel);
        headerPanel.add(myPageTitle);

        return headerPanel;
    }

    private JPanel createCenterPanel(ScreenManager scManager) {

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        centerPanel.setOpaque(false);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        infoPanel.setOpaque(true);
        infoPanel.setBackground(new Color(15, 23, 42, 128));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20,10, 20, 20));

        // - set profile -
        String profile = userData.getProfileImage(user.getId());
        profileCard = new CardPanel();
        profileCard.addScaledImage(profile, 120, 120);
        profileCard.setAlignmentY(Component.CENTER_ALIGNMENT);


        // - add profile wrapper -
        JPanel profileWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        profileWrapper.setOpaque(false);
        profileWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        profileWrapper.add(profileCard);
        infoPanel.add(profileWrapper, BorderLayout.WEST);

        // - userCard(set id with achievement, set rank, set coin) -
        final Font USER_INFO_FONT = new Font("맑은고딕", Font.BOLD, 20);

        String achievements = achMgr.getAchievements(user.getId());
        
        userCard = new CardPanel();
        userCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        userCard.setLayout(new BoxLayout(userCard, BoxLayout.Y_AXIS));

        // - id Card -
        idCard = new CardPanel(0); 
        idCard.setOpaque(false);
        idCard.addLeftText("ID: " + user.getId(), USER_INFO_FONT, Color.WHITE);
        idCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        // - achievement Card -
        if (achievements != null) {
            achCard = new CardPanel(0);
            achCard.setOpaque(false);
            achCard.addLeftText(achievements, new Font("맑은고딕", Font.PLAIN, 15), Color.GRAY);
            achCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        }  

        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setOpaque(false);
        rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        rowPanel.add(idCard);
        
        if (achCard != null) {
            rowPanel.add(Box.createHorizontalStrut(10));
            rowPanel.add(achCard);
        }

        userCard.add(rowPanel);
       
        // - rank Card -
        userCard.add(Box.createVerticalStrut(10));
        rankCard = new CardPanel(0);
        rankCard.setOpaque(false);
        rankCard.addLeftText("랭크: " + userData.getRank(user.getId()), USER_INFO_FONT, Color.WHITE);
        rankCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        userCard.add(rankCard);

        // - coin Card -
        int userCoin = userData.getCoin(user.getId());
        coinCard = new CardPanel(0);
        coinCard.setOpaque(false);
        coinCard.addScaledImage(COIN_IMG, 30, 30);
        coinCard.addLeftText(String.valueOf(userCoin), USER_INFO_FONT, Color.YELLOW);
        coinCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        userCard.add(Box.createVerticalStrut(10));
        userCard.add(coinCard);

        infoPanel.add(userCard);

        // Battle Log Data
        int battle = logMgr.getBattleCnt(user.getId());
        int win = logMgr.getWinCnt(user.getId());
        int lose = logMgr.getLoseCnt(user.getId());
        double rate = logMgr.getWinRate(user.getId());

        // - set Battle Log -
        JPanel battleLog = new JPanel();
        battleLog.setLayout(new BoxLayout(battleLog, BoxLayout.Y_AXIS));
        battleLog.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.CYAN, 5),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        battleLog.setBackground(new Color(15, 23, 42, 128));

        logCard = new CardPanel();
        logCard.addCenteredText("전투 로그", new Font("맑은 고딕", Font.BOLD, 30), Color.WHITE);
        logCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        // - Battle Info Card -
        final Font LOG_FONT = new Font("맑은고딕", Font.BOLD, 20);
        final Font TITLE_FONT = new Font("맑은고딕", Font.BOLD, 20);

        JPanel battleInfoCard = new JPanel(new GridLayout(1, 4, 20, 0));
        battleInfoCard.setOpaque(false);
        battleInfoCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        battleCntCard = new CardPanel();
        battleCntCard.addCenteredImage(BATTLE_CNT);
        battleCntCard.add(Box.createVerticalStrut(5));
        battleCntCard.addCenteredText("총 전투", TITLE_FONT, Color.GRAY);
        battleCntCard.addCenteredText(String.valueOf(battle), LOG_FONT, Color.WHITE);

        winCntCard = new CardPanel();
        winCntCard.addCenteredImage(WIN_CNT);
        winCntCard.add(Box.createVerticalStrut(5));
        winCntCard.addCenteredText("승리", TITLE_FONT, Color.GRAY);
        winCntCard.addCenteredText(String.valueOf(win), LOG_FONT, Color.WHITE);

        loseCntCard = new CardPanel();
        loseCntCard.addCenteredImage(LOSE_CNT);
        loseCntCard.add(Box.createVerticalStrut(5));
        loseCntCard.addCenteredText("패배", TITLE_FONT, Color.GRAY);
        loseCntCard.addCenteredText(String.valueOf(lose), LOG_FONT, Color.WHITE);

        winRateCard = new CardPanel();
        winRateCard.addCenteredImage(WIN_RATE);
        winRateCard.add(Box.createVerticalStrut(5));
        winRateCard.addCenteredText("전체 승률", TITLE_FONT, Color.GRAY);
        winRateCard.addCenteredText(String.valueOf(rate) + "%", LOG_FONT, Color.WHITE);

        // - add battleInfo Component -
        battleInfoCard.add(battleCntCard);
        battleInfoCard.add(winCntCard);
        battleInfoCard.add(loseCntCard);
        battleInfoCard.add(winRateCard);
        battleInfoCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        // - add battleLog Component -
        battleLog.add(logCard);
        battleLog.add(Box.createVerticalStrut(20));
        battleLog.add(battleInfoCard);

        centerPanel.add(infoPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(battleLog);

        return centerPanel;
    }
    /*
     * private JPanel createUserInfoPanel() {
     * 
     * JPanel infoPanel = new JPanel(new BorderLayout());
     * infoPanel.setOpaque(true);
     * infoPanel.setBackground(new Color(15, 23, 42, 128));
     * infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
     * 
     * // - set profile -
     * String profile = userData.getProfileImage(user.getId());
     * profileCard = new CardPanel();
     * profileCard.addScaledImage(profile, 120, 120);
     * profileCard.setAlignmentY(Component.CENTER_ALIGNMENT);
     * 
     * JPanel profileWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
     * profileWrapper.setOpaque(false);
     * profileWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); //
     * 오른쪽 여백
     * profileWrapper.add(profileCard);
     * 
     * infoPanel.add(profileWrapper, BorderLayout.WEST);
     * 
     * // - userCard(set id with achievement, set rank, set coin) -
     * final Font USER_INFO_FONT = new Font("맑은고딕", Font.BOLD, 20);
     * userCard = new CardPanel();
     * userCard.setAlignmentY(Component.LEFT_ALIGNMENT);
     * 
     * userCard.addText("ID: " + user.getId(), USER_INFO_FONT, Color.WHITE); // set
     * id
     * userCard.add(Box.createVerticalStrut(10));
     * userCard.addText("랭크: " + userData.getRank(user.getId()), USER_INFO_FONT,
     * Color.WHITE); // set rank
     * 
     * // set coin
     * int userCoin = userData.getCoin(user.getId());
     * 
     * coinCard = new CardPanel(0);
     * coinCard.setAlignmentX(Component.LEFT_ALIGNMENT);
     * coinCard.addScaledImage(COIN_IMG, 30, 30);
     * coinCard.addText(String.valueOf(userCoin), USER_INFO_FONT, Color.YELLOW);
     * 
     * userCard.add(Box.createVerticalStrut(10));
     * userCard.add(coinCard);
     * userCard.setAlignmentY(Component.CENTER_ALIGNMENT);
     * userCard.setMaximumSize(new Dimension(400, 140));
     * 
     * infoPanel.add(userCard, BorderLayout.CENTER);
     * 
     * return infoPanel;
     * }
     * 
     * private JPanel createBattleLogPanel() {
     * 
     * // Battle Log Data
     * int battle = logMgr.getBattleCnt(user.getId());
     * int win = logMgr.getWinCnt(user.getId());
     * int lose = logMgr.getLoseCnt(user.getId());
     * double rate = logMgr.getWinRate(user.getId());
     * 
     * // - set Battle Log -
     * JPanel battleLog = new JPanel();
     * battleLog.setLayout(new BoxLayout(battleLog, BoxLayout.Y_AXIS));
     * battleLog.setBorder(
     * BorderFactory.createCompoundBorder(
     * BorderFactory.createLineBorder(Color.CYAN, 5),
     * BorderFactory.createEmptyBorder(5, 5, 5, 5)));
     * battleLog.setBackground(new Color(15, 23, 42, 128));
     * 
     * logCard = new CardPanel();
     * logCard.addCenteredText("전투 로그", new Font("맑은 고딕", Font.BOLD, 30),
     * Color.WHITE);
     * logCard.setAlignmentX(Component.CENTER_ALIGNMENT);
     * 
     * // - Battle Info Card -
     * final Font LOG_FONT = new Font("맑은고딕", Font.BOLD, 20);
     * final Font TITLE_FONT = new Font("맑은고딕", Font.BOLD, 20);
     * 
     * JPanel battleInfoCard = new JPanel(new GridLayout(1, 4, 20, 0));
     * battleInfoCard.setOpaque(false);
     * battleInfoCard.setAlignmentX(Component.CENTER_ALIGNMENT);
     * 
     * battleCntCard= new CardPanel();
     * battleCntCard.addCenteredImage(BATTLE_CNT);
     * battleCntCard.addCenteredText("총 전투", TITLE_FONT, Color.GRAY);
     * battleCntCard.addCenteredText(String.valueOf(battle), LOG_FONT, Color.WHITE);
     * battleCntCard.add(Box.createVerticalStrut(10));
     * 
     * winCntCard= new CardPanel();
     * winCntCard.addCenteredImage(WIN_CNT);
     * winCntCard.addCenteredText("승리", TITLE_FONT, Color.GRAY);
     * winCntCard.addCenteredText(String.valueOf(win), LOG_FONT, Color.WHITE);
     * winCntCard.add(Box.createVerticalStrut(10));
     * 
     * loseCntCard= new CardPanel();
     * loseCntCard.addCenteredImage(LOSE_CNT);
     * loseCntCard.addCenteredText("패배", TITLE_FONT, Color.GRAY);
     * loseCntCard.addCenteredText(String.valueOf(lose), LOG_FONT, Color.WHITE);
     * loseCntCard.add(Box.createVerticalStrut(10));
     * 
     * winRateCard= new CardPanel();
     * winRateCard.addCenteredImage(WIN_RATE);
     * winRateCard.addCenteredText("전체 승률", TITLE_FONT, Color.GRAY);
     * winRateCard.addCenteredText(String.valueOf(rate)+"%", LOG_FONT, Color.WHITE);
     * winRateCard.add(Box.createVerticalStrut(10));
     * 
     * // - add battleInfo Component -
     * battleInfoCard.add(battleCntCard);
     * battleInfoCard.add(winCntCard);
     * battleInfoCard.add(loseCntCard);
     * battleInfoCard.add(winRateCard);
     * battleInfoCard.setAlignmentX(Component.CENTER_ALIGNMENT);
     * 
     * // - add battleLog Component -
     * battleLog.add(logCard);
     * battleLog.add(Box.createVerticalStrut(20));
     * battleLog.add(battleInfoCard);
     * 
     * return battleLog;
     * }
     */

    private JPanel createBottomPanel(ScreenManager scManager) {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        gobackBtn = new GButton(GOBACK_BTN, () -> {
            scManager.show(Screen.HOME);
        });

        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(gobackBtn);
        return bottomPanel;
    }

    @Override
    public Screen getScreenType() {
        return Screen.MYPAGE;
    }

    @Override
    public void onShow() {
        System.out.println("Start: MyPageScreen is now Rendering");
    }

    /*
     * @Override
     * public void onResize(int width, int height) {
     * 
     * int cardW = width / 12;
     * int cardH = height / 12;
     * 
     * for (CardPanel p : resizeTargets) {
     * p.setPreferredSize(new Dimension(cardW, cardH));
     * p.revalidate();
     * }
     * 
     * repaint();
     * }
     */
}
