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

    private JLabel idLabel;
    private JLabel rankLabel;
    private JPanel textPanel;
    private JLabel coinLabel;
    private JLabel profileLabel;
    
    private GButton gobackBtn;

    private Color wallpaper;

    private User user;
    private UserData userData;

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
        screenMgr = scManager;

        setComponent();
    }

    private void setComponent() {
        add(createHeaderPanel(screenMgr), BorderLayout.NORTH);
        add(createCenterPanel(screenMgr), BorderLayout.CENTER);
        add(createBottomPanel(screenMgr), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel(ScreenManager scManager) {

        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.setBackground(new Color(15, 23, 42, 128));
        headerPanel.setOpaque(true);

    
        JLabel myPageIcon = new JLabel(new ImageIcon(MYPAGE));
        headerPanel.add(myPageIcon, BorderLayout.WEST);

        return headerPanel;
    }

    private JPanel createCenterPanel(ScreenManager scManager) {

        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(new Color(15, 23, 42, 128));
        centerPanel.setOpaque(true);

        // - user info -
        JPanel infoCard = new JPanel();
        infoCard.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        infoCard.setBackground(new Color(32, 0, 64, 100));
        infoCard.setOpaque(true);

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

        // - set log -
        JPanel logArea = new JPanel(new BorderLayout());
        logArea.setOpaque(false);

        JLabel logLabel = new JLabel("전투 로그", SwingConstants.CENTER);
        logLabel.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        logLabel.setForeground(Color.WHITE);

        JPanel logContent = new JPanel();
        logContent.setOpaque(false);
        logContent.setBackground(new Color(32, 0, 64, 100));

        logArea.add(logLabel, BorderLayout.NORTH);
        logArea.add(logContent, BorderLayout.CENTER);

        centerPanel.add(logArea, BorderLayout.CENTER);

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
