
package Screen;

import GameLogic.EventListener;
import GameLogic.Item;
import Data.AnalysisData;
import Data.DataManager;
import Action.GButton;
import Data.UserData;
import Util.Constant;
import Data.ItemData;
import Util.Screen;
import Logic.User;


import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.border.TitledBorder;


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

public class MarketScreen extends JPanel implements IScreen
{
    //CONST
    private final String ITEM_IMAGE   = "assets//images//itemImage.png";
    private final String GOBACK_BTN   = "assets//buttons//gobackBtn.png";
    private final String PURCHASE_BTN = "assets//buttons//purchaseBtn.png";

    //VARIABLES
    private JLabel titleL;
    private JLabel coinL;
    private Color wallpaper;
    private GButton gobackBtn;

    JPanel gridPanel;
    private ArrayList<Item> purchasedList;

    private User user;
    private ItemData itemMgr;
    private UserData userMgr;
    private Constant constant;
    private AnalysisData ansMgr;
    private ScreenManager scManager;
    private EventListener eventMgr;

    @Override
    public void init(ScreenManager scManager)
    {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
        setBackground(wallpaper);

        this.scManager = scManager;

        purchasedList = new ArrayList<>();
        itemMgr = DataManager.getInstance().getItemMgr();
        userMgr = DataManager.getInstance().getUserMgr();
        user = DataManager.getInstance().getCurrentUser();
        ansMgr = DataManager.getInstance().getAnsMgr();
        eventMgr = DataManager.getInstance().getEventListener();

        setComponent();
    }

    private JPanel createHeaderPanel()
    {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setOpaque(false);

        titleL = new JLabel("MARKET PLACE");
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
            gobackLogic();
            scManager.show(Screen.HOME);
        });

        header.add(titleL, BorderLayout.CENTER);
        header.add(coinL, BorderLayout.EAST);
        header.add(gobackBtn, BorderLayout.WEST);

        return header;
    }

    private void gobackLogic()
    {
        userMgr.addNewItem(user.getId(), purchasedList);
        JOptionPane.showMessageDialog(this, "Your Inventory is up to date");
    }


    private JScrollPane createItemPanel(ArrayList<Item> itemList)
    {
        gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false);

        for(Item item : itemList)
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

            Image original = new ImageIcon(item.imagePath).getImage();
            Image scaledImage = original.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel itemL = new JLabel(new ImageIcon(scaledImage));

            JLabel nameL = new JLabel(item.name);
            nameL.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            nameL.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameL.setForeground(Color.WHITE);

            String priceData = String.valueOf(itemMgr.getPrice(item.name));
            JLabel priceL = new JLabel("PRICE : " + priceData);
            priceL.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
            priceL.setAlignmentX(Component.CENTER_ALIGNMENT);
            priceL.setForeground(Color.YELLOW);
            priceL.setPreferredSize(new Dimension(280, 20));

            GButton purchaseBtn = new GButton(PURCHASE_BTN, ()->{
                purchaseLogic(item.name);
            });
            purchaseBtn.setPreferredSize(new Dimension(120, 30));
            purchaseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);


            itemPanel.add(itemL);
            itemPanel.add(Box.createHorizontalStrut(5));
            itemPanel.add(nameL);
            itemPanel.add(priceL);
            //itemPanel.add(attackL);
            itemPanel.add(Box.createHorizontalStrut(5));
            itemPanel.add(purchaseBtn);

            gridPanel.add(itemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(800, 500));

        return scrollPane;
    }

    private void purchaseLogic(final String itemName)
    {
        int coin = userMgr.getCoin(user.getId());
        int price= itemMgr.getPrice(itemName);

        if((coin - price) >= 0 && ((purchasedList.size()+userMgr.getInventory(user.getId()).size()) < 6))
        {
            eventMgr.getPlayer().getBag().add(itemMgr.getItemObj(itemName));
            userMgr.updateCoin(user.getId(), coin - price);
            purchasedList.add(itemMgr.get(itemName));
            String coinString = String.valueOf(userMgr.getCoin(user.getId()));
            coinL.setText(coinString);
            JOptionPane.showMessageDialog(this, "Purchase success");
            return;
        }
        JOptionPane.showMessageDialog(this, "Not enough coin or full inven");
    }

    private JPanel createSuggestedPanel()
    {
        JPanel suggestedPanel = new JPanel();

        suggestedPanel.setLayout(new BorderLayout(10, 10));
        suggestedPanel.setOpaque(false);
        suggestedPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.CYAN),
                "SUGGESTED ITEMS!!",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("맑은 고딕", Font.BOLD, 14),
                Color.CYAN
        ));

        final String[] itemList = ansMgr.getSuggestItems(user.getId());
        ArrayList<Item> suggestedItems = new ArrayList<>();
        
        suggestedItems.add(itemMgr.get(itemList[0]));
        suggestedItems.add(itemMgr.get(itemList[1]));
        suggestedItems.add(itemMgr.get(itemList[2]));

        JScrollPane itemDisplayPanel = createItemPanel(suggestedItems);

        JLabel messageL = new JLabel("사용자님은 이 아이템들을 자주 사용했습니다! 장착 또는 구매를 권장합니다.");
        messageL.setForeground(Color.LIGHT_GRAY);
        messageL.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        messageL.setHorizontalAlignment(JLabel.CENTER);

        suggestedPanel.add(messageL, BorderLayout.NORTH);
        suggestedPanel.add(itemDisplayPanel, BorderLayout.CENTER);

        return suggestedPanel;
    }


    private void setComponent()
    {
        final String[] itemList = ansMgr.getSuggestItems(user.getId()).clone();
        ArrayList<Item> items = new ArrayList<>();
        
        for (String s:itemList) {
        	items.add(itemMgr.get(s));
        }
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        mainPanel.add(createSuggestedPanel());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(createItemPanel(items));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public Screen getScreenType() {
        return Screen.MARKET;
    }

    @Override
    public void onShow() {
        System.out.println("Start: MarketScreen is now Rendering");
        removeAll();
        setComponent();
        revalidate();
        repaint();

    }
}
