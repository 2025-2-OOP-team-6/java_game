package Screen;

import Data.DataManager;
import Data.LogData;
import Action.GButton;
import Util.Constant;
import Data.UserData;
import Util.Screen;
import Logic.User;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;

public class MyPageScreen extends JPanel implements IScreen {
    private final String MYPAGE = "assets//images//mypage.png";
    private final String GOBACK_BTN = "assets//buttons//gobackBtn.png";

    private final String BATTLE_CNT = "assets//images//battleCnt.png";
    private final String WIN_CNT = "assets//images//winCnt.png";
    private final String LOSE_CNT = "assets//images//loseCnt.png";
    private final String WIN_RATE = "assets//images//winRate.png";

    private JLabel idLabel;
    private JLabel rankLabel;
    private JPanel textPanel;
    private JLabel coinLabel;
    private JLabel profileLabel;
    
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

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        headerPanel.setBackground(new Color(15, 23, 42, 128));
        headerPanel.setOpaque(true);

    
        JLabel myPageIcon = new JLabel(new ImageIcon(MYPAGE));
        headerPanel.add(myPageIcon);

        return headerPanel;
    }

    private JPanel createCenterPanel(ScreenManager scManager) {

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        centerPanel.setOpaque(false);

        // - user info -
        JPanel infoCard = new JPanel();
        infoCard.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoCard.setOpaque(true); 
        infoCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        infoCard.setBackground(new Color(15, 23, 42, 128));
        infoCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        // - set profile -
        String profile = userData.getProfileImage(user.getId());
        Image img = new ImageIcon(profile).getImage();
        Image scaled = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        profileLabel = new JLabel(new ImageIcon(scaled));

        infoCard.add(profileLabel);
        infoCard.add(Box.createHorizontalStrut(20));

        // - textpanel -
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        // - set id -
        idLabel = new JLabel("ID: " + user.getId());
        idLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        idLabel.setForeground(Color.WHITE);

        // - set rank - 
        rankLabel = new JLabel("랭크: " + userData.getRank(user.getId()));
        rankLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        rankLabel.setForeground(Color.WHITE);

        // - set coin -
        coinLabel = new JLabel("코인: " + userData.getCoin(user.getId()));
        coinLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        coinLabel.setForeground(Color.YELLOW);

        textPanel.add(idLabel);
        textPanel.add(rankLabel);
        textPanel.add(coinLabel);

        infoCard.add(textPanel);

        centerPanel.add(infoCard, BorderLayout.NORTH);
        centerPanel.add(Box.createVerticalStrut(20)); 

        // - set Battel Log -
        int battle = logMgr.getBattleCnt(user.getId());   // - set total battle count -
        int win   = logMgr.getWinCnt(user.getId());       // - set win count -
        int lose  = logMgr.getLoseCnt(user.getId());      // - set lose count -
        double rate  = logMgr.getWinRate(user.getId());        // - set win rate -
        
        JPanel battleLog = new JPanel(new BorderLayout(10, 10));
        battleLog.setOpaque(true);
        battleLog.setBackground(new Color(15, 23, 42, 128));
        battleLog.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel logTitle = new JLabel("전투 로그", SwingConstants.CENTER);
        logTitle.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        logTitle.setForeground(Color.WHITE);
        



        JPanel battleInfoCard = new JPanel(new GridLayout(1, 4, 20, 0));
        battleInfoCard.setOpaque(false);

        // - set battle Cnt -
        JPanel battleCntCard= new JPanel();
        battleCntCard.setLayout(new BoxLayout(battleCntCard, BoxLayout.Y_AXIS));
        battleCntCard.setOpaque(false);
       
        JLabel battleCntImg = new JLabel(new ImageIcon(BATTLE_CNT));
        battleCntImg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel battleCnt= new JLabel(String.valueOf(battle), SwingConstants.CENTER);
        battleCnt.setForeground(Color.WHITE);
        battleCnt.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        battleCnt.setAlignmentX(Component.CENTER_ALIGNMENT);

        battleCntCard.add(battleCntImg);
        battleCntCard.add(Box.createVerticalStrut(10));
        battleCntCard.add(battleCnt);

        // - set win Cnt -
        JPanel winCntCard= new JPanel();
        winCntCard.setLayout(new BoxLayout(winCntCard, BoxLayout.Y_AXIS));
        winCntCard.setOpaque(false);
        
        JLabel winCntImg = new JLabel(new ImageIcon(WIN_CNT));
        winCntImg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel winCnt= new JLabel(String.valueOf(win), SwingConstants.CENTER);
        winCnt.setForeground(Color.WHITE);
        winCnt.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        winCnt.setAlignmentX(Component.CENTER_ALIGNMENT);

        winCntCard.add(winCntImg);
        winCntCard.add(Box.createVerticalStrut(10));
        winCntCard.add(winCnt);

        // - set lose Cnt -
        JPanel loseCntCard= new JPanel();
        loseCntCard.setLayout(new BoxLayout(loseCntCard, BoxLayout.Y_AXIS));
        loseCntCard.setOpaque(false);
        
        JLabel loseCntImg = new JLabel(new ImageIcon(LOSE_CNT));
        loseCntImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel loseCnt= new JLabel(String.valueOf(lose), SwingConstants.CENTER);
        loseCnt.setForeground(Color.WHITE);
        loseCnt.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        loseCnt.setAlignmentX(Component.CENTER_ALIGNMENT);

        loseCntCard.add(loseCntImg);
        loseCntCard.add(Box.createVerticalStrut(10));
        loseCntCard.add(loseCnt);

        // - set win Rate -
        JPanel winRateCard= new JPanel();
        winRateCard.setLayout(new BoxLayout(winRateCard, BoxLayout.Y_AXIS));
        winRateCard.setOpaque(false);
        
        JLabel winRateImg = new JLabel(new ImageIcon(WIN_RATE));
        winRateImg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel winRate= new JLabel(String.valueOf(rate), SwingConstants.CENTER);
        winRate.setForeground(Color.WHITE);
        winRate.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        winRate.setAlignmentX(Component.CENTER_ALIGNMENT);

        winRateCard.add(winRateImg);
        winRateCard.add(Box.createVerticalStrut(10));
        winRateCard.add(winRate);

        // -set Component -
        battleInfoCard.add(battleCntCard);
        battleInfoCard.add(winCntCard);
        battleInfoCard.add(loseCntCard);
        battleInfoCard.add(winRateCard);

        







        battleLog.add(logTitle, BorderLayout.NORTH);
        battleLog.add(battleInfoCard, BorderLayout.CENTER);

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
}
