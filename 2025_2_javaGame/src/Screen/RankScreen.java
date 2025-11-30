package Screen;

import Data.DataManager;
import Action.GButton;
import Data.UserData;
import Util.Constant;
import Util.Screen;

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
import java.util.Arrays;

public class RankScreen extends JPanel implements IScreen
{
    //CONST
    private final String GOBACK_BTN           = "assets//buttons//gobackBtn.png";
    private final String RANKING1_IMAGE      = "assets//images//ranking1.png";
    private final String RANKING2_IMAGE      = "assets//images//ranking2.png";
    private final String RANKING3_IMAGE      = "assets//images//ranking3.png";
    private final String RANKING_TITLE_IMAGE = "assets//images//rankingTitle.png";

    // 디자인 CONST
    private final Color GOLD_COLOR = new Color(255, 204, 0); // 1위 금색
    private final Color SILVER_COLOR = new Color(192, 192, 192); // 2위 은색
    private final Color BRONZE_COLOR = new Color(205, 127, 50); // 3위 동색
    private final Color TOP3_BG_COLOR = new Color(55, 55, 129, 255); // Top3 패널 배경색 (어두운 파란색 계열)
    private final Color LIST_BG_COLOR = new Color(67, 46, 129, 255); // 리스트 배경색 (더 어두운 파란색 계열)
    private final Color HEADER_TEXT_COLOR = new Color(255, 204, 0); // 헤더 텍스트 색상 (금색)


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
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

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
        gobackBtn.setPreferredSize(new Dimension(150, 50));

        JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        westPanel.setOpaque(false);
        westPanel.add(gobackBtn);
        westPanel.setPreferredSize(new Dimension(150, 50));

        header.add(titleImage, BorderLayout.CENTER);
        header.add(westPanel, BorderLayout.WEST);
        header.add(Box.createRigidArea(new Dimension(150, 50)), BorderLayout.EAST);

        return header;
    }

    private JPanel createTop3Panel()
    {
        int[] displayOrder = {1, 0, 2};

        JPanel top3Panel = new JPanel();
        top3Panel.setLayout(new BoxLayout(top3Panel, BoxLayout.X_AXIS));
        top3Panel.setOpaque(false);
        top3Panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ArrayList<String> top3Users = new ArrayList<>(Arrays.asList("", "", ""));
        for (int i = 0; i < Math.min(3, rankList.length); i++) {
            top3Users.set(i, rankList[i]);
        }

        for (int i = 0; i < 3; ++i) {
            String userid = top3Users.get(displayOrder[i]);
            int rank = displayOrder[i] + 1;

            RoundPanel rankPanel = new RoundPanel(20);
            rankPanel.setBackground(TOP3_BG_COLOR);
            rankPanel.setLayout(new BorderLayout());
            rankPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

            Dimension panelSize = (rank == 1)
                    ? new Dimension(220, 280) // 1위는 크게
                    : new Dimension(180, 240); // 2, 3위는 작게

            if (rank == 1) {
                rankPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                panelSize = new Dimension(220, 295);
            }

            rankPanel.setPreferredSize(panelSize);
            rankPanel.setMaximumSize(panelSize);
            rankPanel.setMinimumSize(panelSize);

            if (userid.isEmpty()) {
                top3Panel.add(Box.createHorizontalStrut(190));
                top3Panel.add(Box.createHorizontalStrut(20));
                continue;
            }

            String rankImage = switch (rank) {
                case 1 -> RANKING1_IMAGE;
                case 2 -> RANKING2_IMAGE;
                case 3 -> RANKING3_IMAGE;
                default -> "";
            };
            Color medalColor = switch (rank) {
                case 1 -> GOLD_COLOR;
                case 2 -> SILVER_COLOR;
                case 3 -> BRONZE_COLOR;
                default -> Color.WHITE;
            };

            Image original = new ImageIcon(rankImage).getImage();
            Image scaledImage = original.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // 이미지 크기 키움
            JLabel rankImgL = new JLabel(new ImageIcon(scaledImage));
            rankImgL.setHorizontalAlignment(JLabel.CENTER);


            JPanel topPart = new JPanel(new BorderLayout());
            topPart.setOpaque(false);
            topPart.add(rankImgL, BorderLayout.NORTH);


            JPanel centerPart = new JPanel();
            centerPart.setOpaque(false);
            centerPart.setLayout(new BoxLayout(centerPart, BoxLayout.Y_AXIS));
            centerPart.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

            JLabel nameL = new JLabel(userid);
            nameL.setFont(new Font("맑은 고딕", Font.BOLD, 22));
            nameL.setForeground(Color.WHITE);
            nameL.setAlignmentX(Component.CENTER_ALIGNMENT);


            centerPart.add(nameL);
            centerPart.add(Box.createVerticalStrut(5));

            JLabel scoreL = new JLabel(String.valueOf(userMgr.getRank(userid)) + "점");
            scoreL.setFont(new Font("맑은 고딕", Font.BOLD, 24));
            scoreL.setForeground(GOLD_COLOR);
            scoreL.setAlignmentX(Component.CENTER_ALIGNMENT);

            rankPanel.add(topPart, BorderLayout.NORTH);
            rankPanel.add(centerPart, BorderLayout.CENTER);
            rankPanel.add(scoreL, BorderLayout.SOUTH);

            top3Panel.add(rankPanel);
            top3Panel.add(Box.createHorizontalStrut(20)); // 패널 사이 간격
        }

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(top3Panel);

        return wrapper;
    }

    private JScrollPane createCenterPanel()
    {
        JPanel rankListContent = new JPanel();
        rankListContent.setLayout(new BoxLayout(rankListContent, BoxLayout.Y_AXIS));
        rankListContent.setOpaque(false);
        rankListContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        JPanel headerRow = new JPanel(new GridLayout(1, 4));
        headerRow.setOpaque(false);
        headerRow.setPreferredSize(new Dimension(800, 40));

        String[] headers = {"순위", "플레이어", "레벨", "점수"};
        for(String h : headers)
        {
            JLabel label = new JLabel(h, JLabel.CENTER);
            label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            label.setForeground(HEADER_TEXT_COLOR);
            headerRow.add(label);
        }
        rankListContent.add(headerRow);
        rankListContent.add(Box.createVerticalStrut(5));

        for(int i = 0; i < rankList.length; ++i)
        {
            final String userid = rankList[i];

            JPanel rowPanel = new JPanel(new GridLayout(1, 4));
            rowPanel.setPreferredSize(new Dimension(800, 40));
            rowPanel.setOpaque(false);

            JLabel rankL = new JLabel(String.valueOf(i + 1), JLabel.CENTER);
            JLabel idL = new JLabel(userid, JLabel.CENTER);

            JLabel scoreL = new JLabel(String.valueOf(userMgr.getRank(userid)), JLabel.CENTER); // Score

            Font font = new Font("맑은 고딕", Font.BOLD, 14);
            Color color = Color.WHITE;
            Color highlightColor = (i < 3) ? GOLD_COLOR : color;

            for (JLabel label : new JLabel[]{rankL, idL, scoreL}) {
                label.setFont(font);
                label.setForeground(highlightColor);
                rowPanel.add(label);
            }

            rankListContent.add(rowPanel);
        }

        RoundPanel totalRankPanel = new RoundPanel(20);
        totalRankPanel.setBackground(LIST_BG_COLOR);
        totalRankPanel.setLayout(new BorderLayout());
        totalRankPanel.add(rankListContent, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(totalRankPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

    private void setComponent()
    {
        add(createHeaderPanel(), BorderLayout.NORTH);

        JPanel rankWrapper = new JPanel();
        rankWrapper.setLayout(new BoxLayout(rankWrapper, BoxLayout.Y_AXIS));
        rankWrapper.setOpaque(false);

        rankWrapper.add(createTop3Panel());
        rankWrapper.add(Box.createVerticalStrut(20));
        rankWrapper.add(createCenterPanel());

        add(rankWrapper, BorderLayout.CENTER);
    }

    @Override
    public Screen getScreenType() {
        return Screen.RANK;
    }

    @Override
    public void onShow() {
        rankList = DataManager.getInstance().getTotalRanks();
        System.out.println("Start: RankScreen is now Rendering");
        removeAll();
        setComponent();
        revalidate();
        repaint();
    }
}