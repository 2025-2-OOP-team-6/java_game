package Screen;

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

public class RankScreen extends JPanel implements IScreen
{
    //CONST
    private final String GOBACK_BTN           = "assets//buttons//gobackBtn.png";
    private final String RANKING1_IMAGE      = "assets//images//ranking1.png";
    private final String RANKING2_IMAGE      = "assets//images//ranking2.png";
    private final String RANKING3_IMAGE      = "assets//images//ranking3.png";
    private final String RANKING_TITLE_IMAGE = "assets//images//rankingTitle.png";

    //VARIABLES
    private Color wallpaper;
    private String[] rankList;

    private Constant constant;
    private ScreenManager scManager;
    private UserData userMgr;


    //FUNCTIONS

    @Override
    public void init(ScreenManager scManager)
    {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
        setBackground(wallpaper);

        this.scManager = scManager;
        userMgr = DataManager.getInstance().getUserMgr();
        rankList = Data.DataManager.getInstance().getTotalRanks();

        setComponent();
    }

    private JPanel createHeaderPanel()
    {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(800, 100));

        ImageIcon rankingTitle = new ImageIcon(RANKING_TITLE_IMAGE);
        int height = rankingTitle.getIconHeight() * 2;
        int width = rankingTitle.getIconWidth() * 2;
        Image resized = rankingTitle.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel titleImage = new JLabel(new ImageIcon(resized));
        titleImage.setHorizontalAlignment(JLabel.CENTER);

        GButton gobackBtn = new GButton(GOBACK_BTN, ()->{
            scManager.show(Screen.HOME);
        });
        gobackBtn.setPreferredSize(new Dimension(200, 50));


        header.add(titleImage, BorderLayout.CENTER);
        header.add(gobackBtn, BorderLayout.EAST);

        return header;
    }

    private JPanel createTop3Panel()
    {
        String[] top3User = {rankList[0], rankList[1], rankList[2]};

        JPanel top3Panel = new JPanel();
        top3Panel.setLayout(new BoxLayout(top3Panel, BoxLayout.X_AXIS));
        top3Panel.setOpaque(false);
        top3Panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (int i = 0; i < 3; ++i) {
            JPanel rankPanel = new JPanel();
            rankPanel.setPreferredSize(new Dimension(160, 180));
            rankPanel.setMaximumSize(new Dimension(160, 180));
            rankPanel.setLayout(new BoxLayout(rankPanel, BoxLayout.Y_AXIS));
            rankPanel.setOpaque(false);
            rankPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            String rankImage = switch (i) {
                case 0 -> RANKING1_IMAGE;
                case 1 -> RANKING2_IMAGE;
                case 2 -> RANKING3_IMAGE;
                default -> "";
            };

            Image original = new ImageIcon(rankImage).getImage();
            Image scaledImage = original.getScaledInstance(80, 60, Image.SCALE_SMOOTH);
            JLabel itemL = new JLabel(new ImageIcon(scaledImage));
            itemL.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel nameL = new JLabel(top3User[i]);
            JLabel timeL = new JLabel(String.valueOf(userMgr.getTime(top3User[i])));
            JLabel rankL = new JLabel(String.valueOf(userMgr.getRank(top3User[i])));

            for (JLabel label : new JLabel[]{itemL, nameL, timeL, rankL}) {
                label.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                label.setForeground(Color.WHITE);
                rankPanel.add(label);
            }

            top3Panel.add(rankPanel);
            top3Panel.add(Box.createHorizontalStrut(20));
        }

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(top3Panel);

        return wrapper;
    }

    private JScrollPane createCenterPanel()
    {
        JPanel totalRankPanel = new JPanel();
        totalRankPanel.setLayout(new GridLayout(0, 1, 0, 5));
        totalRankPanel.setOpaque(false);


        JPanel headerRow = new JPanel(new GridLayout(1, 4));
        headerRow.setOpaque(false);
        headerRow.setPreferredSize(new Dimension(800, 40));
        String[] headers = {"PLACE", "NAME", "TIME", "RANK"};
        for(String h : headers)
        {
            JLabel label = new JLabel(h, JLabel.CENTER);
            label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            label.setForeground(Color.YELLOW);
            headerRow.add(label);
        }
        totalRankPanel.add(headerRow);
        totalRankPanel.add(Box.createVerticalStrut(10));


        for(int i = 0; i < rankList.length; ++i)
        {
            final String userid = rankList[i];

            JPanel rowPanel = new JPanel(new GridLayout(1, 4));
            rowPanel.setPreferredSize(new Dimension(800, 40));
            rowPanel.setOpaque(false);

            JLabel rankL = new JLabel(String.valueOf(i + 1), JLabel.CENTER);
            JLabel idL = new JLabel(userid, JLabel.CENTER);
            JLabel timeL = new JLabel(String.valueOf(userMgr.getTime(userid)), JLabel.CENTER);
            JLabel scoreL = new JLabel(String.valueOf(userMgr.getRank(userid)), JLabel.CENTER);

            Font font = new Font("맑은 고딕", Font.BOLD, 14);
            Color color = Color.WHITE;

            for (JLabel label : new JLabel[]{rankL, idL, timeL, scoreL}) {
                label.setFont(font);
                label.setForeground(color);
                rowPanel.add(label);
            }

            totalRankPanel.add(rowPanel);
        }

        JScrollPane scrollPane = new JScrollPane(totalRankPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(800, 500));

        return scrollPane;
    }

    private void setComponent()
    {
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 랭킹 전체 묶음
        JPanel rankWrapper = new JPanel();
        rankWrapper.setLayout(new BoxLayout(rankWrapper, BoxLayout.Y_AXIS));
        rankWrapper.setOpaque(false);

        rankWrapper.add(createTop3Panel());
        rankWrapper.add(Box.createVerticalStrut(20));
        rankWrapper.add(createCenterPanel());

        JScrollPane scrollPane = new JScrollPane(rankWrapper);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(800, 600)); // 전체 화면 크기

        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public Screen getScreenType() {
        return Screen.RANK;
    }

    @Override
    public void onShow() {
        rankList = DataManager.getInstance().getTotalRanks();
        System.out.println("Start: MarketScreen is now Rendering");
    }
}
