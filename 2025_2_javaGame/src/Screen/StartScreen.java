package Screen;

import Util.Constant;
import Util.Screen;
import Data.UserData;
import Action.GButton;
import Data.DataManager;
import Data.CharactorData;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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
        setLayout(new BorderLayout());

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

        JLabel titleL = new JLabel("CHOOSE YOUR CHARACTOR");
        titleL.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        titleL.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleL.setForeground(Color.WHITE);
        titleL.setOpaque(false);

        GButton gobackBtn = new GButton(GOBACK_BTN, ()->{
            screenMgr.show(Screen.HOME);
        });

        headerPanel.add(titleL, BorderLayout.CENTER);
        headerPanel.add(gobackBtn, BorderLayout.WEST);

        return headerPanel;
    }

    private JPanel createRejectPanel()
    {
        JPanel rejectPanel = new JPanel(new BorderLayout());
        rejectPanel.setOpaque(false);

        JLabel noticeL = new JLabel(new ImageIcon(CHOOSED_CHARACTOR_IMAGE));

        GButton chooseCharactorBtn = new GButton(CHANGE_CHARACTOR_BTN, ()->{
            screenMgr.show(Screen.SELECT);
        });

        rejectPanel.add(noticeL, BorderLayout.CENTER);
        rejectPanel.add(chooseCharactorBtn, BorderLayout.SOUTH);

        return rejectPanel;
    }

    private JScrollPane createCenterPanel()
    {
        final String[] choosedChr = usrMgr.getChoosedCharactor();

        JPanel infoPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        infoPanel.setOpaque(false);
        infoPanel.setBackground(new Color(75, 0, 130));

        for(String charactor : choosedChr)
        {
            final String CHOOSED_IMAGE = CHARACTOR_IMAGE + charactor + SUFFIX;

            JPanel charPanel = new JPanel();
            charPanel.setPreferredSize(new Dimension(220, 150));
            charPanel.setLayout(new BoxLayout(charPanel, BoxLayout.Y_AXIS));
            charPanel.setOpaque(false);
            charPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            Image original = new ImageIcon(CHOOSED_IMAGE).getImage();
            Image scaled = original.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel imageL = new JLabel(new ImageIcon(scaled));
            imageL.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel nameL = new JLabel(charactor);
            nameL.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            nameL.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameL.setForeground(Color.WHITE);

            String trait1Data = String.valueOf(charMgr.getTrait(charactor, 1));
            JLabel trait1L = new JLabel("Trait1 : " + trait1Data);
            trait1L.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
            trait1L.setAlignmentX(Component.CENTER_ALIGNMENT);
            trait1L.setForeground(Color.WHITE);
            trait1L.setPreferredSize(new Dimension(280, 20));

            String trait2Data = String.valueOf(charMgr.getTrait(charactor, 2));
            JLabel trait2L = new JLabel("Trait2 : " + trait2Data);
            trait2L.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
            trait2L.setAlignmentX(Component.CENTER_ALIGNMENT);
            trait2L.setForeground(Color.WHITE);
            trait2L.setPreferredSize(new Dimension(280, 20));

            String trait3Data = String.valueOf(charMgr.getTrait(charactor, 3));
            JLabel trait3L = new JLabel("Trait3 : " + trait3Data);
            trait3L.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
            trait3L.setAlignmentX(Component.CENTER_ALIGNMENT);
            trait3L.setForeground(Color.WHITE);
            trait3L.setPreferredSize(new Dimension(280, 20));

            charPanel.add(imageL);
            charPanel.add(nameL);
            charPanel.add(trait1L);
            charPanel.add(trait2L);
            charPanel.add(trait3L);

            infoPanel.add(charPanel);
        }

        JScrollPane centerScroll = new JScrollPane(infoPanel);
        centerScroll.setOpaque(false);
        centerScroll.getViewport().setOpaque(false);
        centerScroll.setBorder(null);
        centerScroll.setPreferredSize(new Dimension(800, 500));

        return centerScroll;
    }

    private void setComponent()
    {
        if(usrMgr.getChoosedCharactor() != null)
        {
            add(createHeaderPanel(), BorderLayout.NORTH);
            add(createCenterPanel(), BorderLayout.CENTER);

            JPanel btnPanel = new JPanel(new GridLayout(0, 3, 10, 10));


            GButton adventureBtn = new GButton(START_ADVENTURE_BTN, ()->{
                screenMgr.show(Screen.LOGIN);
            });

            GButton chooseBtn = new GButton(CHANGE_CHARACTOR_BTN, ()->{
                screenMgr.show(Screen.SELECT);
            });

            btnPanel.add(adventureBtn);
            btnPanel.add(chooseBtn);
            btnPanel.setOpaque(false);

            add(btnPanel, BorderLayout.SOUTH);
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
