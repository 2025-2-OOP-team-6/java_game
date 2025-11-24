package Screen;

import Data.CharactorData;
import Data.DataManager;
import Action.GButton;
import Data.UserData;
import Data.ItemData;
import Util.Constant;
import Util.Screen;
import Logic.User;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class SelectScreen extends JPanel implements IScreen
{
    //CONST
    private final int MAX_CHOOSE_SIZE = 3;
    private final String ITEM_IMAGE   = "assets//images//itemImage.png";
    private final String GOBACK_BTN   = "assets//buttons//gobackBtn.png";
    private final String CHOOSE_BTN   = "assets//buttons//chooseBtn.png";
    private final String DELETE_BTN   = "assets//buttons//deleteBtn.png";

    private final String CHAR_PREFIX = "assets//images//";

    private final Color CHAR_BOX_BG_COLOR = new Color(40, 0, 70);
    private final Color SELECTION_BG_COLOR = new Color(40, 0, 70, 180);


    //VARIABLES
    private JLabel titleL;
    private Color wallpaper;
    private GButton gobackBtn;

    private ArrayList<String> choosedChractor;

    JPanel gridPanel;

    private User user;
    private ItemData itemMgr;
    private UserData userMgr;
    private Constant constant;
    private CharactorData charMgr;
    private ScreenManager scManager;

    @Override
    public void init(ScreenManager scManager)
    {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // 여백

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);

        setBackground(wallpaper);

        this.scManager = scManager;

        itemMgr = DataManager.getInstance().getItemMgr();
        userMgr = DataManager.getInstance().getUserMgr();
        user = DataManager.getInstance().getCurrentUser();
        charMgr = DataManager.getInstance().getCharactorMgr();

        choosedChractor = new ArrayList<>();

        setComponent();
    }

    private JPanel createHeaderPanel()
    {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(getWidth(), 50));

        titleL = new JLabel("CHOOSE YOUR CHARACTOR", JLabel.CENTER);
        titleL.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        titleL.setForeground(Color.WHITE);
        titleL.setOpaque(false);

        gobackBtn = new GButton(GOBACK_BTN, ()->{

            if(choosedChractor.size() < MAX_CHOOSE_SIZE)
            {
                JOptionPane.showMessageDialog(this, "You must choose " + MAX_CHOOSE_SIZE + " characters");
                return;
            }

            userMgr.updateChoosedCharactor(choosedChractor.toArray(new String[0]));
            scManager.show(Screen.HOME);
        });
        gobackBtn.setPreferredSize(new Dimension(150, 40));

        JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        eastPanel.setOpaque(false);
        eastPanel.add(gobackBtn);
        eastPanel.setPreferredSize(new Dimension(150, 40));


        header.add(titleL, BorderLayout.CENTER);
        header.add(eastPanel, BorderLayout.EAST);
        header.add(Box.createRigidArea(new Dimension(150, 40)), BorderLayout.WEST);

        return header;
    }

    private JScrollPane createCharactorPanel()
    {
        String[] charactors = charMgr.getjobList();
        gridPanel = new JPanel(new GridLayout(0, 3, 20, 20)); // 간격 20
        gridPanel.setOpaque(false);

        for(String charactor : charactors)
        {
            RoundPanel charPanel = new RoundPanel(20);
            charPanel.setBackground(CHAR_BOX_BG_COLOR);
            charPanel.setPreferredSize(new Dimension(250, 300));
            charPanel.setLayout(new BoxLayout(charPanel, BoxLayout.Y_AXIS));
            charPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            final String TARGET_IMAGE = CHAR_PREFIX + charactor + ".png";

            Image original = new ImageIcon(TARGET_IMAGE).getImage();
            Image scaledImage = original.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel imageL = new JLabel(new ImageIcon(scaledImage));
            imageL.setAlignmentX(Component.CENTER_ALIGNMENT);
            imageL.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

            JLabel nameL = new JLabel(charactor);
            nameL.setFont(new Font("맑은 고딕", Font.BOLD, 18));
            nameL.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameL.setForeground(Color.WHITE);
            nameL.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

            Font traitFont = new Font("맑은 고딕", Font.PLAIN, 13);
            Color traitColor = new Color(200, 200, 200);

            String trait1Data = String.valueOf(charMgr.getTrait(charactor, 1));
            JLabel trait1L = new JLabel("ATTACK : " + trait1Data);
            trait1L.setFont(traitFont);
            trait1L.setAlignmentX(Component.CENTER_ALIGNMENT);
            trait1L.setForeground(traitColor);

            String trait2Data = String.valueOf(charMgr.getTrait(charactor, 2));
            JLabel trait2L = new JLabel("DEFENSE : " + trait2Data);
            trait2L.setFont(traitFont);
            trait2L.setAlignmentX(Component.CENTER_ALIGNMENT);
            trait2L.setForeground(traitColor);

            String trait3Data = String.valueOf(charMgr.getTrait(charactor, 3));
            JLabel trait3L = new JLabel("ABILITY : " + trait3Data);
            trait3L.setFont(traitFont);
            trait3L.setAlignmentX(Component.CENTER_ALIGNMENT);
            trait3L.setForeground(traitColor);

            GButton chooseBtn = new GButton(CHOOSE_BTN, ()->{

                if(choosedChractor.size() >= MAX_CHOOSE_SIZE)
                {
                    JOptionPane.showMessageDialog(this, "You can only choose 3 characters");
                    return;
                }

                JOptionPane.showMessageDialog(this, charactor + " Character has Chosen");
                choosedChractor.add(charactor);
                refreshScreen();
            });
            chooseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            chooseBtn.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            charPanel.add(imageL);
            charPanel.add(nameL);
            charPanel.add(trait1L);
            charPanel.add(trait2L);
            charPanel.add(trait3L);
            charPanel.add(Box.createVerticalGlue());
            charPanel.add(chooseBtn);

            gridPanel.add(charPanel);
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(800, 500));

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    private JPanel createSelectionPanel()
    {
        RoundPanel selectionPanel = new RoundPanel(15);
        selectionPanel.setBackground(SELECTION_BG_COLOR);
        selectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        selectionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.CYAN, 2, true),
                "SELECTED CHARACTERS!!",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("맑은 고딕", Font.BOLD, 14),
                Color.CYAN
        ));

        for(String charName : choosedChractor)
        {
            JPanel charBox = createSelectCharBox(charName);
            selectionPanel.add(charBox);
        }

        selectionPanel.setPreferredSize(new Dimension(getWidth(), 120));

        return selectionPanel;
    }

    private JPanel createSelectCharBox(final String charName)
    {
        RoundPanel box = new RoundPanel(10);
        box.setBackground(CHAR_BOX_BG_COLOR);
        box.setLayout(new BorderLayout(5, 0));
        box.setPreferredSize(new Dimension(80, 100));
        box.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));

        final String TARGET_IMAGE = CHAR_PREFIX + charName +".png";
        Image original = new ImageIcon(TARGET_IMAGE).getImage();
        Image scaledImage = original.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel imageL = new JLabel(new ImageIcon(scaledImage));
        imageL.setHorizontalAlignment(JLabel.CENTER);
        imageL.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));


        JLabel nameL = new JLabel(charName.substring(0, Math.min(charName.length(), 4)), JLabel.CENTER);
        nameL.setFont(new Font("맑은 고딕", Font.BOLD, 10));
        nameL.setForeground(Color.YELLOW);
        nameL.setPreferredSize(new Dimension(80, 15));


        GButton deleteBtn = new GButton(DELETE_BTN, ()->{
            choosedChractor.remove(charName);
            JOptionPane.showMessageDialog(this, charName + " deleted");
            refreshScreen();
        });
        deleteBtn.setPreferredSize(new Dimension(70, 20));

        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(deleteBtn);


        box.add(imageL, BorderLayout.NORTH);
        box.add(nameL, BorderLayout.CENTER);
        box.add(buttonWrapper, BorderLayout.SOUTH);

        return box;
    }


    private void setComponent()
    {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createCharactorPanel(), BorderLayout.CENTER);
        add(createSelectionPanel(), BorderLayout.SOUTH);
    }

    @Override
    public Screen getScreenType() {
        return Screen.SELECT;
    }

    @Override
    public void onShow() {
        removeAll();
        setComponent();
        revalidate();
        repaint();

        System.out.println("Start: SelectScreen is now Rendering");
    }

    private void refreshScreen()
    {
        onShow();
    }
}