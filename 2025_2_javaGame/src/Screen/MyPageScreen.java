package Screen;

import Data.DataManager;
import Data.LogData;
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
import javax.swing.Box;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Component;
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
    
    private CardPanel infoCard;
    private CardPanel profileCard;
    private CardPanel userCard;
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
        JLabel myPageTitle=new JLabel("마이페이지"); 
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

        // - user info -
        infoCard = new CardPanel(0);
        infoCard.setOpaque(true); 
        infoCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        infoCard.setBackground(new Color(15, 23, 42, 128));
        infoCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        // - set profile -
        String profile = userData.getProfileImage(user.getId());
        profileCard = new CardPanel();
        profileCard.addScaledImage(profile, 120, 120);
        profileCard.setAlignmentY(Component.CENTER_ALIGNMENT);

        infoCard.add(profileCard);
        infoCard.add(Box.createHorizontalStrut(20));

        // - userCard(set id with achievement, set rank, set coin) -
        final Font USER_INFO_FONT = new Font("맑은고딕", Font.BOLD, 20);
        userCard = new CardPanel();
        userCard.setAlignmentY(Component.LEFT_ALIGNMENT);
        
        userCard.addText("ID: " + user.getId(), USER_INFO_FONT, Color.WHITE); // set id
        userCard.add(Box.createVerticalStrut(10));
        userCard.addText("랭크: " + userData.getRank(user.getId()), USER_INFO_FONT, Color.WHITE); // set rank
        userCard.setAlignmentY(Component.CENTER_ALIGNMENT);

        // set coin
        int userCoin = userData.getCoin(user.getId());
        
        coinCard = new CardPanel(0);
        coinCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        coinCard.addScaledImage(COIN_IMG, 30, 30);
        coinCard.addText(String.valueOf(userCoin), USER_INFO_FONT, Color.YELLOW);

        userCard.add(Box.createVerticalStrut(10));
        userCard.add(coinCard);

        infoCard.add(userCard);
       
        // Battle Log Data
        int battle = logMgr.getBattleCnt(user.getId());   
        int win   = logMgr.getWinCnt(user.getId());      
        int lose  = logMgr.getLoseCnt(user.getId());      
        double rate  = logMgr.getWinRate(user.getId());   
        
        // - set Battle Log - 
        JPanel battleLog = new JPanel(new BorderLayout(10, 10));
        battleLog.setOpaque(true);
        battleLog.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.CYAN, 5),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        battleLog.setBackground(new Color(15, 23, 42, 128));
        
        logCard = new CardPanel();
        logCard.addText("전투 로그", new Font("맑은 고딕", Font.BOLD, 30), Color.WHITE);
        logCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        logCard.add(Box.createVerticalStrut(20));
        
        // - Battle Info Card -
        final Font LOG_FONT = new Font("맑은고딕", Font.BOLD, 20);
        final Font TITLE_FONT = new Font("맑은고딕", Font.BOLD, 20);

        JPanel battleInfoCard = new JPanel(new GridLayout(1, 4, 20, 0));
        
        battleInfoCard.setOpaque(false);    
        
        battleCntCard= new CardPanel();
        battleCntCard.addImage(BATTLE_CNT);
        battleCntCard.addText("총 전투", TITLE_FONT, Color.GRAY);
        battleCntCard.addText(String.valueOf(battle), LOG_FONT, Color.WHITE);
        battleCntCard.add(Box.createVerticalStrut(10));

        winCntCard= new CardPanel();
        winCntCard.addImage(WIN_CNT);
        winCntCard.addText("승리", TITLE_FONT, Color.GRAY);
        winCntCard.addText(String.valueOf(win), LOG_FONT, Color.WHITE);
        winCntCard.add(Box.createVerticalStrut(10));

        loseCntCard= new CardPanel();
        loseCntCard.addImage(LOSE_CNT);
        loseCntCard.addText("패배", TITLE_FONT, Color.GRAY);
        loseCntCard.addText(String.valueOf(lose), LOG_FONT, Color.WHITE);
        loseCntCard.add(Box.createVerticalStrut(10));

        winRateCard= new CardPanel();
        winRateCard.addImage(WIN_RATE);
        winRateCard.addText("전체 승률", TITLE_FONT, Color.GRAY);
        winRateCard.addText(String.valueOf(rate)+"%", LOG_FONT, Color.WHITE);
        winRateCard.add(Box.createVerticalStrut(10));

        // - add battleInfo Component -
        battleInfoCard.add(battleCntCard);
        battleInfoCard.add(winCntCard);
        battleInfoCard.add(loseCntCard);
        battleInfoCard.add(winRateCard);
        
        // - add battleLog Component -
        battleLog.add(logCard, BorderLayout.NORTH);
        battleLog.add(battleInfoCard, BorderLayout.CENTER);        
        
        // - add centerPanel Component - 
        centerPanel.add(infoCard, BorderLayout.NORTH);
        centerPanel.add(Box.createVerticalStrut(20)); 
        centerPanel.add(battleLog, BorderLayout.CENTER);

        return centerPanel;
    }

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
    @Override
    public void onResize(int width, int height) {

        int cardW = width / 12;
        int cardH = height / 12;

        for (CardPanel p : resizeTargets) {
            p.setPreferredSize(new Dimension(cardW, cardH));
            p.revalidate();
        }

        repaint();
    }
    */
}
