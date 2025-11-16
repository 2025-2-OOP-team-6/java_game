package Screen;

import Data.DataManager;
import Action.GButton;
import Data.UserData;
import Util.Constant;
import Data.ItemData;
import Util.Screen;
import Logic.User;


import javax.swing.BorderFactory;
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

public class InventoryScreen  extends JPanel implements IScreen
{
    //CONST
    private final String ITEM_IMAGE   = "assets//images//itemImage.png";
    private final String GOBACK_BTN   = "assets//buttons//gobackBtn.png";

    //VARIABLES
    private JLabel titleL;
    private JLabel coinL;
    private Color wallpaper;
    private GButton gobackBtn;

    JPanel gridPanel;

    private User user;
    private ItemData itemMgr;
    private UserData userMgr;
    private Constant constant;
    private ScreenManager scManager;

    @Override
    public void init(ScreenManager scManager)
    {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
        setBackground(wallpaper);

        this.scManager = scManager;

        itemMgr = DataManager.getInstance().getItemMgr();
        userMgr = DataManager.getInstance().getUserMgr();
        user = DataManager.getInstance().getCurrentUser();

        setComponent();
    }

    private JPanel createHeaderPanel()
    {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setOpaque(false);

        titleL = new JLabel("MY INVENTORY");
        titleL.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        titleL.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleL.setForeground(Color.WHITE);
        titleL.setOpaque(false);

        String coinData = String.valueOf(userMgr.getCoin(user.getId()));
        coinL = new JLabel(coinData + " coin");
        coinL.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        coinL.setAlignmentX(Component.CENTER_ALIGNMENT);
        coinL.setForeground(Color.YELLOW);
        coinL.setOpaque(false);

        gobackBtn = new GButton(GOBACK_BTN, ()->{
            scManager.show(Screen.HOME);
        });

        header.add(titleL, BorderLayout.CENTER);
        header.add(coinL, BorderLayout.EAST);
        header.add(gobackBtn, BorderLayout.WEST);

        return header;
    }

    private JScrollPane createItemPanel()
    {
        String[] itemList = userMgr.getInventory(user.getId());
        gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false);

        for(String itemName : itemList)
        {
            JPanel itemPanel = new JPanel();
            itemPanel.setPreferredSize(new Dimension(250, 250));
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setOpaque(false);
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            itemPanel.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e)
                {
                    itemPanel.setBackground(new Color(50, 50, 50));
                    itemPanel.setOpaque(true);
                }
                public void mouseEvent(MouseEvent e)
                {
                    itemPanel.setOpaque(false);
                    itemPanel.repaint();
                }
            });

            Image original = new ImageIcon(ITEM_IMAGE).getImage();
            Image scaledImage = original.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel itemL = new JLabel(new ImageIcon(scaledImage));

            JLabel nameL = new JLabel(itemName);
            nameL.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            nameL.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameL.setForeground(Color.WHITE);

            String attackData = String.valueOf(itemMgr.getAttack(itemName));
            JLabel attackL = new JLabel("ATTACK : " + attackData);
            attackL.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
            attackL.setAlignmentX(Component.CENTER_ALIGNMENT);
            attackL.setForeground(Color.WHITE);
            attackL.setPreferredSize(new Dimension(280, 20));


            itemPanel.add(itemL);
            itemPanel.add(Box.createHorizontalStrut(5));
            itemPanel.add(nameL);
            itemPanel.add(attackL);
            itemPanel.add(Box.createHorizontalStrut(5));

            gridPanel.add(itemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(800, 500));

        return scrollPane;
    }


    private void setComponent()
    {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createItemPanel(), BorderLayout.CENTER);
    }

    @Override
    public Screen getScreenType() {
        return Screen.INVEN;
    }

    @Override
    public void onShow() {
        System.out.println("Start: InventoryScreen is now Rendering");
    }
}
