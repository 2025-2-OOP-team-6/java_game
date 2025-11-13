package Screen;

import Data.DataManager;
import Action.GButton;
import Data.UserData;
import Util.Constant;
import Data.ItemData;
import Util.Screen;
import Logic.User;



import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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

import java.util.List;
import java.util.ArrayList;

public class MarketScreen extends JPanel implements IScreen
{
    //CONST
    private final String ITEM_IMAGE   = "..//assets//images//itemImage.png";
    private final String GOBACK_BTN   = "..//assets//buttons//gobackBtn.png";
    private final String PURCHASE_BTN = "..//assets//buttons//purchaseBtn.png";

    //VARIABLES
    private JLabel title;
    private JLabel coin;
    private Color wallpaper;
    private GButton gobackBtn;

    JPanel[] itemPenals;
    private ArrayList<String> purchasedList;

    private User user;
    private ItemData itemMgr;
    private UserData userMgr;
    private Constant constant;


    @Override
    public void init(ScreenManager scManager)
    {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
        setBackground(wallpaper);

        purchasedList = new ArrayList<>();
        itemMgr = DataManager.getInstance().getItemMgr();
        userMgr = DataManager.getInstance().getUserMgr();
        user = DataManager.getInstance().getCurrentUser();



        setComponent();
    }

    private JPanel createHeaderPanel()
    {
        JPanel header = new JPanel();

        title = new JLabel("MARKET PLACE");
        title.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        title.setOpaque(false);

        String coinData = String.valueOf(userMgr.getCoin(user.getId()));
        coin = new JLabel(coinData + " coin");
        coin.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        coin.setAlignmentX(Component.CENTER_ALIGNMENT);
        coin.setForeground(Color.WHITE);
        coin.setOpaque(false);

        header.add(title, BorderLayout.CENTER);
        header.add(coin, BorderLayout.EAST);

        return header;
    }


    private void createItemPanel()
    {
        String[] itemList = itemMgr.getItemNames();
        itemPenals = new JPanel[itemList.length];

        for(int i = 0; i < itemList.length; ++i)
        {
            JPanel itemPanel = new JPanel();

            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
            itemPanel.setOpaque(false);

            ImageIcon itemImage = new ImageIcon(ITEM_IMAGE);
            JLabel itemL = new JLabel(itemImage);

            JLabel nameL = new JLabel(itemList[i]);
            nameL.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
            nameL.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameL.setForeground(Color.WHITE);
            nameL.setOpaque(false);

            String priceData = String.valueOf(itemMgr.getPrice(itemList[i]));
            JLabel priceL = new JLabel("PRICE : priceData");
            priceL.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
            priceL.setAlignmentX(Component.CENTER_ALIGNMENT);
            priceL.setForeground(Color.YELLOW);
            priceL.setOpaque(false);

            String attackData = String.valueOf(+itemMgr.getAttack(itemList[i]));
            JLabel attackL = new JLabel("ATTACK : " + attackData);
            attackL.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
            attackL.setAlignmentX(Component.CENTER_ALIGNMENT);
            attackL.setForeground(Color.WHITE);
            attackL.setOpaque(false);


            final String itemName = itemList[i];
            GButton purchaseBtn = new GButton(PURCHASE_BTN, ()->{
                purchaseLogic(itemName);
            });


            itemPanel.add(itemL);
            itemPanel.add(Box.createHorizontalStrut(5));
            itemPanel.add(nameL);
            itemPanel.add(Box.createHorizontalStrut(5));
            itemPanel.add(priceL);
            itemPanel.add(Box.createHorizontalStrut(5));
            itemPanel.add(attackL);
            itemPanel.add(Box.createHorizontalStrut(5));
            itemPanel.add(purchaseBtn);
        }
    }

    private void purchaseLogic(final String itemName)
    {
        int coin = userMgr.getCoin(user.getId());
        int price= itemMgr.getPrice(itemName);

        if((coin - price) > 0)
        {
            userMgr.uqdateCoin(user.getId(), coin - price);
            purchasedList.add(itemName);

            JOptionPane.showMessageDialog(this, "Purchase success");
        }
        JOptionPane.showMessageDialog(this, "Not enough coin");
    }

    private void setComponent()
    {
        // 컴포넌트 순서대로 추가
        add(title);
        add(Box.createVerticalStrut(20)); // 간격
        add(label);
        add(setIdPanel());
        add(Box.createVerticalStrut(5));
        add(setPWPanle1());
        add(Box.createVerticalStrut(5));
        add(setPWPanel2());
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }

    @Override
    public Screen getScreenType() {
        return null;
    }

    @Override
    public void onShow() {

    }
}
