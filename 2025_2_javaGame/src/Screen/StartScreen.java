package Screen;

import Util.Constant;
import Util.Screen;
import Data.UserData;
import Action.GButton;
import Data.DataManager;
import Data.CharactorData;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;

import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class StartScreen extends JPanel implements IScreen{

    //CONST
    private final String SUFFIX                  = ".png";
    private final String GOBACK_BTN              = "..//assets//buttons//gobackBtn.png";
    private final String CHARACTOR_IMAGE         = "..//assets//images//";
    private final String START_ADVENTURE_BTN     = "..//assets//buttons//startAdventureBtn.png";
    private final String CHANGE_CHARACTOR_BTN    = "..//assets//buttons//changeCharactorBtn.png";
    private final String CHOOSED_CHARACTOR_IMAGE = "..//assets//images//chooseCharactor.png";

    //VARIABLES
    private UserData usrMgr;
    private CharactorData charMgr;
    private ScreenManager screenMgr;

    private Color wallpaper;
    private Constant constant;


    @Override
    public void init(ScreenManager scManager) {
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.screenMgr = scManager;
        usrMgr = DataManager.getInstance().getUserMgr();
        charMgr = DataManager.getInstance().getCharactorMgr();

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);

        setBackground(wallpaper);

        setComponent();
    }

    private JPanel createHeaderPanel()
    {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));

        GButton gobackBtn = new GButton(GOBACK_BTN, ()->{
            screenMgr.show(Screen.HOME);
        });
        gobackBtn.setPreferredSize(new Dimension(150, 40));

        JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        westPanel.setOpaque(false);
        westPanel.add(gobackBtn);

        JLabel titleL = new JLabel("CHOOSE YOUR CHARACTER", JLabel.CENTER);
        titleL.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        titleL.setForeground(Color.WHITE);
        titleL.setOpaque(false);

        headerPanel.add(titleL, BorderLayout.CENTER);
        headerPanel.add(westPanel, BorderLayout.WEST);

        headerPanel.add(Box.createRigidArea(new Dimension(150, 40)), BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createRejectPanel()
    {
        JPanel rejectPanel = new JPanel(new BorderLayout());
        rejectPanel.setOpaque(false);
        rejectPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));

        JLabel noticeL = new JLabel(new ImageIcon(CHOOSED_CHARACTOR_IMAGE));
        noticeL.setHorizontalAlignment(JLabel.CENTER);

        GButton chooseCharactorBtn = new GButton(CHANGE_CHARACTOR_BTN, ()->{
            screenMgr.show(Screen.SELECT);
        });

        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnWrapper.setOpaque(false);
        btnWrapper.add(chooseCharactorBtn);

        rejectPanel.add(noticeL, BorderLayout.CENTER);
        rejectPanel.add(btnWrapper, BorderLayout.SOUTH);

        return rejectPanel;
    }

    private JPanel createCenterPanel()
    {
        final String[] choosedChr = usrMgr.getChoosedCharactor();

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        infoPanel.setOpaque(false);

        for(String charactor : choosedChr)
        {
            final String CHOOSED_IMAGE = CHARACTOR_IMAGE + charactor + SUFFIX;

            JPanel charPanel = new JPanel();
            charPanel.setLayout(new BoxLayout(charPanel, BoxLayout.Y_AXIS));
            charPanel.setBackground(new Color(40, 0, 80));
            charPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            Image original = new ImageIcon(CHOOSED_IMAGE).getImage();
            Image scaled = original.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel imageL = new JLabel(new ImageIcon(scaled));
            imageL.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel nameL = new JLabel(charactor);
            nameL.setFont(new Font("맑은 고딕", Font.BOLD, 18));
            nameL.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameL.setForeground(Color.WHITE);
            nameL.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

            Font traitFont = new Font("맑은 고딕", Font.PLAIN, 14);
            Color traitColor = new Color(200, 200, 200); // 연한 회색

            String trait1Data = String.valueOf(charMgr.getTrait(charactor, 1));
            JLabel trait1L = new JLabel("Trait1 : " + trait1Data);
            trait1L.setFont(traitFont);
            trait1L.setAlignmentX(Component.CENTER_ALIGNMENT);
            trait1L.setForeground(traitColor);

            String trait2Data = String.valueOf(charMgr.getTrait(charactor, 2));
            JLabel trait2L = new JLabel("Trait2 : " + trait2Data);
            trait2L.setFont(traitFont);
            trait2L.setAlignmentX(Component.CENTER_ALIGNMENT);
            trait2L.setForeground(traitColor);

            String trait3Data = String.valueOf(charMgr.getTrait(charactor, 3));
            JLabel trait3L = new JLabel("Trait3 : " + trait3Data);
            trait3L.setFont(traitFont);
            trait3L.setAlignmentX(Component.CENTER_ALIGNMENT);
            trait3L.setForeground(traitColor);

            charPanel.add(imageL);
            charPanel.add(nameL);
            charPanel.add(trait1L);
            charPanel.add(trait2L);
            charPanel.add(trait3L);
            charPanel.add(Box.createVerticalStrut(10));

            infoPanel.add(charPanel);
        }

        return infoPanel;
    }

    private JPanel createBottomButtonPanel()
    {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setOpaque(false);

        GButton adventureBtn = new GButton(START_ADVENTURE_BTN, ()->{
            screenMgr.show(Screen.LOGIN);
        });
        adventureBtn.setPreferredSize(new Dimension(200, 50));

        GButton chooseBtn = new GButton(CHANGE_CHARACTOR_BTN, ()->{
            screenMgr.show(Screen.SELECT);
        });
        chooseBtn.setPreferredSize(new Dimension(200, 50));

        bottomPanel.add(adventureBtn);
        bottomPanel.add(chooseBtn);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(bottomPanel, BorderLayout.CENTER);
        wrapper.add(Box.createVerticalStrut(20), BorderLayout.SOUTH);

        return wrapper;
    }

    private void setComponent()
    {
        if(usrMgr.getChoosedCharactor() != null)
        {
            add(createHeaderPanel(), BorderLayout.NORTH);
            add(createCenterPanel(), BorderLayout.CENTER);

            add(createBottomButtonPanel(), BorderLayout.SOUTH);
            return;
        }
        add(createRejectPanel(), BorderLayout.CENTER);
    }

    @Override
    public Screen getScreenType() {
        return Screen.START;
    }

    @Override
    public void onShow() {
        removeAll();
        setComponent();
        revalidate();
        repaint();

        System.out.println("Start: StartScreen is now Rendering");
    }
}