//package Screen;
//
//import Data.CharactorData;
//import Data.DataManager;
//import Action.GButton;
//import Data.UserData;
//import Util.Constant;
//import Data.ItemData;
//import Util.Screen;
//import Logic.User;
//
//
//import javax.swing.BorderFactory;
//import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
//import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.Box;
//
//
//import java.awt.Font;
//import java.awt.Color;
//import java.awt.Image;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.GridLayout;
//import java.awt.BorderLayout;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//
//public class SelectScreen extends JPanel implements IScreen
//{
//    //CONST
//    private final int MAX_CHOOSE_SIZE = 3;
//    private final String ITEM_IMAGE   = "assets//images//itemImage.png";
//    private final String GOBACK_BTN   = "assets//buttons//gobackBtn.png";
//    private final String CHOOSE_BTN   = "assets//buttons//chooseBtn.png";
//
//    //VARIABLES
//    private JLabel titleL;
//    private Color wallpaper;
//    private GButton gobackBtn;
//
//    private static int index;
//    private String[] choosedChractor;
//
//    JPanel gridPanel;
//
//    private User user;
//    private ItemData itemMgr;
//    private UserData userMgr;
//    private Constant constant;
//    private CharactorData charMgr;
//    private ScreenManager scManager;
//
//    @Override
//    public void init(ScreenManager scManager)
//    {
//        setLayout(new BorderLayout(20, 20));
//        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백
//
//        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
//        setBackground(wallpaper);
//
//        this.scManager = scManager;
//
//        itemMgr = DataManager.getInstance().getItemMgr();
//        userMgr = DataManager.getInstance().getUserMgr();
//        user = DataManager.getInstance().getCurrentUser();
//        charMgr = DataManager.getInstance().getCharactorMgr();
//
//        index = 0;
//        choosedChractor = new String[MAX_CHOOSE_SIZE];
//
//        setComponent();
//    }
//
//    private JPanel createHeaderPanel()
//    {
//        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        header.setOpaque(false);
//
//        titleL = new JLabel("CHOOSE YOUR CHARACTOR");
//        titleL.setFont(new Font("맑은 고딕", Font.BOLD, 15));
//        titleL.setAlignmentX(Component.CENTER_ALIGNMENT);
//        titleL.setForeground(Color.WHITE);
//        titleL.setOpaque(false);
//
//        gobackBtn = new GButton(GOBACK_BTN, ()->{
//            userMgr.updateChoosedCharactor(choosedChractor);
//            scManager.show(Screen.HOME);
//        });
//
//        header.add(titleL, BorderLayout.CENTER);
//        header.add(gobackBtn, BorderLayout.WEST);
//
//        return header;
//    }
//
//    private JScrollPane createItemPanel()
//    {
//        String[] charactors = charMgr.getjobList();
//        gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
//        gridPanel.setOpaque(false);
//
//        for(String itemName : charactors)
//        {
//            JPanel itemPanel = new JPanel();
//            itemPanel.setPreferredSize(new Dimension(250, 250));
//            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
//            itemPanel.setOpaque(false);
//            itemPanel.setBorder(BorderFactory.createCompoundBorder(
//                    BorderFactory.createLineBorder(Color.GRAY),
//                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
//            ));
//            itemPanel.addMouseListener(new MouseAdapter() {
//                public void mouseEntered(MouseEvent e)
//                {
//                    itemPanel.setBackground(new Color(50, 50, 50));
//                    itemPanel.setOpaque(true);
//                }
//                public void mouseEvent(MouseEvent e)
//                {
//                    itemPanel.setOpaque(false);
//                    itemPanel.repaint();
//                }
//            });
//
//            Image original = new ImageIcon(ITEM_IMAGE).getImage();
//            Image scaledImage = original.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//            JLabel itemL = new JLabel(new ImageIcon(scaledImage));
//
//            JLabel nameL = new JLabel(itemName);
//            nameL.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
//            nameL.setAlignmentX(Component.CENTER_ALIGNMENT);
//            nameL.setForeground(Color.WHITE);
//
//            String trait1Data = String.valueOf(charMgr.getTrait(itemName, 1));
//            JLabel trait1L = new JLabel("ATTACK : " + trait1Data);
//            trait1L.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
//            trait1L.setAlignmentX(Component.CENTER_ALIGNMENT);
//            trait1L.setForeground(Color.WHITE);
//            trait1L.setPreferredSize(new Dimension(280, 20));
//
//            String trait2Data = String.valueOf(charMgr.getTrait(itemName, 2));
//            JLabel trait2L = new JLabel("ATTACK : " + trait1Data);
//            trait2L.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
//            trait2L.setAlignmentX(Component.CENTER_ALIGNMENT);
//            trait2L.setForeground(Color.WHITE);
//            trait2L.setPreferredSize(new Dimension(280, 20));
//
//            String trait3Data = String.valueOf(charMgr.getTrait(itemName, 3));
//            JLabel trait3L = new JLabel("ATTACK : " + trait1Data);
//            trait3L.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
//            trait3L.setAlignmentX(Component.CENTER_ALIGNMENT);
//            trait3L.setForeground(Color.WHITE);
//            trait3L.setPreferredSize(new Dimension(280, 20));
//
//            GButton chooseBtn = new GButton(CHOOSE_BTN, ()->{
//                ++index;
//                JOptionPane.showMessageDialog(this, itemName + " Charactor has Chosen");
//                charactors[index % MAX_CHOOSE_SIZE] = itemName;
//            });
//
//            itemPanel.add(itemL);
//            itemPanel.add(Box.createHorizontalStrut(5));
//            itemPanel.add(nameL);
//            itemPanel.add(trait1L);
//            itemPanel.add(trait2L);
//            itemPanel.add(trait3L);
//            itemPanel.add(chooseBtn);
//            itemPanel.add(Box.createHorizontalStrut(5));
//
//
//            gridPanel.add(itemPanel);
//        }
//
//        JScrollPane scrollPane = new JScrollPane(gridPanel);
//        scrollPane.setOpaque(false);
//        scrollPane.getViewport().setOpaque(false);
//        scrollPane.setBorder(null);
//        scrollPane.setPreferredSize(new Dimension(800, 500));
//
//        return scrollPane;
//    }
//
//
//    private void setComponent()
//    {
//        add(createHeaderPanel(), BorderLayout.NORTH);
//        add(createItemPanel(), BorderLayout.CENTER);
//    }
//
//    @Override
//    public Screen getScreenType() {
//        return Screen.SELECT;
//    }
//
//    @Override
//    public void onShow() {
//        index = 0;
//        System.out.println("Start: SelectScreen is now Rendering");
//    }
//}
