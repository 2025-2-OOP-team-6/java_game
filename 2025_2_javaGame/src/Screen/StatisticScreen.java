package Screen;

import Data.*;
import Logic.User;
import Util.Screen;
import Util.Constant;
import Action.GButton;
import Screen.StatisticsPanel.GraphType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class StatisticScreen extends JPanel implements IScreen
{
    //CONST
    private final String GOBACK_BTN      = "assets//buttons//gobackBtn.png";
    private final String STATISTIC_TITLE = "assets//images//statisticTitle.png";
    private final String USER_DATA_NULL  = "assets//images//userDataNull.png";
    private final String ITEM_USAGE_BTN  = "assets//buttons//itemUsageBtn.png";
    private final String CHAR_USAGE_BTN  = "assets//buttons//charUsageBtn.png";
    private final String RESULT_BTN      = "assets//buttons//resultBtn.png";

    private final String CHAR_USAGE = "CHAR_USAGE";
    private final String ITEM_USAGE = "ITEM_USAGE";
    private final String WIN_LOSE = "WIN_LOSE";

    private Constant constant;

    private final Color WALLPAPER_COLOR = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
    private final Color PANEL_BG_COLOR  = new Color(35, 39, 45);
    private final Color LOG_BG_COLOR    = new Color(50, 54, 62);

    //VARIABLES
    private User user;
    private LogData logMgr;
    private UserData userMgr;
    private ItemData itemMgr;
    private AnalysisData ansMgr;
    private ScreenManager screenMgr;

    private Color wallpaper;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    @Override
    public void init(ScreenManager screenMgr)
    {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        
        wallpaper = WALLPAPER_COLOR;
        setBackground(wallpaper);

        this.screenMgr = screenMgr;
        userMgr = DataManager.getInstance().getUserMgr();
        user = DataManager.getInstance().getCurrentUser();
        ansMgr = DataManager.getInstance().getAnsMgr();
        itemMgr = DataManager.getInstance().getItemMgr();
        

        if(userMgr.getCoin(user.getId()) == 0)
        {
            JLabel noUser = new JLabel(USER_DATA_NULL);
            add(noUser);
            return;
        }

        ansMgr.anlysisPlayData(user.getId());
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        logMgr = DataManager.getInstance().getLogMgr();

        setComponent();
    }

    private JPanel createHeaderPanel()
    {
        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);

        GButton gobakBtn = new GButton(GOBACK_BTN, ()->{
            screenMgr.show(Screen.HOME);
        });

        ImageIcon original = new ImageIcon(STATISTIC_TITLE);
        int width = original.getIconWidth() * 2;
        int height = original.getIconHeight() * 2;
        Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel titleL = new JLabel(new ImageIcon(scaled));
        titleL.setHorizontalAlignment(JLabel.CENTER);

        JPanel eastFiller = new JPanel();
        eastFiller.setOpaque(false);
        eastFiller.setPreferredSize(gobakBtn.getPreferredSize());

        header.add(gobakBtn, BorderLayout.WEST);
        header.add(titleL, BorderLayout.CENTER);
        header.add(eastFiller, BorderLayout.EAST);

        return header;
    }

    private JPanel createStatisticPanel()
    {
        RoundPanel panel = new RoundPanel(25);
        panel.setLayout(new BorderLayout());

        panel.setBackground(PANEL_BG_COLOR);


        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setOpaque(false);

        GButton itemGraph = new GButton(ITEM_USAGE_BTN, ()-> {
            cardLayout.show(cardPanel, ITEM_USAGE);
        });

        GButton charGraph = new GButton(CHAR_USAGE_BTN, ()->{
            cardLayout.show(cardPanel, CHAR_USAGE);
        });

        GButton winLoseGraph = new GButton(RESULT_BTN, ()-> {
            cardLayout.show(cardPanel, WIN_LOSE);
        });

        buttonPanel.add(itemGraph);
        buttonPanel.add(charGraph);
        buttonPanel.add(winLoseGraph);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setOpaque(false);
        northPanel.add(buttonPanel, BorderLayout.CENTER);
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(cardPanel, BorderLayout.CENTER);

        return panel;
    }

    private JScrollPane createWinLoseGraph()
    {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        List<String> dateList = Arrays.stream(logMgr.getDateArray(user.getId())).toList();

        List<Integer> winGraph = ansMgr.getWinGraph();
        List<Integer> loseGraph = ansMgr.getLoseGraph();
        List<Integer> playTimeGraph = ansMgr.getPlayTimeGraph();

        contentPanel.add(createGraphWrapper(
                "날짜별 승리 횟수 추이 (꺾은선)", winGraph, dateList, new Color(132, 108, 181), GraphType.POINT));
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createGraphWrapper(
                "날짜별 패배 횟수 추이 (꺾은선)", loseGraph, dateList, new Color(220, 50, 50), GraphType.POINT));
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createGraphWrapper(
                "날짜별 플래이 횟수 추이 (꺾은선)", playTimeGraph, dateList, new Color(50, 150, 220), GraphType.POINT));
        contentPanel.add(Box.createVerticalStrut(20));

        final int GRAPH_HEIGHT = 300;
        final int STRUT_HEIGHT = 20;
        final int NUM_GRAPHS = 3;

        int totalHeight = (NUM_GRAPHS * GRAPH_HEIGHT) + ((NUM_GRAPHS - 1) * STRUT_HEIGHT);
        contentPanel.setPreferredSize(new Dimension(800, totalHeight));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        return scrollPane;
    }

    private RoundPanel createItemUsageGraph()
    {
        List<String> itemNames = Arrays.asList(itemMgr.getItemNames());
        List<Integer> itemUsage = new ArrayList<>();
        final int[] usageArr = logMgr.getItemGraphData(user.getId());

        for(int i : usageArr)
        {
            itemUsage.add(i);
        }

        
        RoundPanel item = createGraphWrapper(
                "사용한 아이템 빈도수 (막대)", itemUsage, itemNames, new Color(250, 224, 130), GraphType.BAR);

        return item;
    }

//    private RoundPanel createCharUsageGraph()
//    {
//        List<String> charNames = Arrays.asList(charMgr.getjobList());
//        List<Integer> charUsage = new ArrayList<>();
//        final int[] usageArr = logMgr.getCharGraphData(user.getId());
//
//        for(int i : usageArr)
//        {
//            charUsage.add(i);
//        }
//
//        RoundPanel character = createGraphWrapper(
//                "사용한 캐릭터 빈도수 (막대)", charUsage, charNames, new Color(184, 255, 126), GraphType.BAR);
//
//        return character;
//    }

    private RoundPanel createGraphWrapper(String title, List<Integer> data, List<String> labels, Color graphAccentColor, GraphType type)
    {
        RoundPanel wrapper = new RoundPanel(25);

        wrapper.setLayout(new BorderLayout());
        wrapper.setBackground(PANEL_BG_COLOR);

        final int FIXED_HEIGHT = 300;
        Dimension fixedSize = new Dimension(800, FIXED_HEIGHT);

        wrapper.setPreferredSize(fixedSize);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, FIXED_HEIGHT));
        wrapper.setMinimumSize(fixedSize);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        titlePanel.add(titleLabel, BorderLayout.CENTER);


        wrapper.add(titlePanel, BorderLayout.NORTH);

        StatisticsPanel graphPanel = new StatisticsPanel(data, labels, graphAccentColor, type);

        JPanel graphWrapper = new JPanel(new BorderLayout());
        graphWrapper.setOpaque(false);
        graphWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        graphWrapper.add(graphPanel, BorderLayout.CENTER);

        wrapper.add(graphWrapper, BorderLayout.CENTER);

        return wrapper;
    }

    private JScrollPane createLogPanel()
    {
        String[] logsArray = logMgr.getDateArray(user.getId());
        List<String> logEntries = new ArrayList<>();
        if(logsArray != null) {
            for(String date : logsArray) {
                logEntries.add(date + " - (로그 상세 정보는 LogData에서 가져와야 함)");
            }
        }

        JPanel logListPanel = new JPanel();
        logListPanel.setLayout(new BoxLayout(logListPanel, BoxLayout.Y_AXIS));
        logListPanel.setBackground(LOG_BG_COLOR);

        if (logEntries.isEmpty()) {
            JLabel emptyLabel = new JLabel("No play logs found.");
            emptyLabel.setForeground(Color.LIGHT_GRAY);
            emptyLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            logListPanel.add(Box.createVerticalGlue());
            logListPanel.add(emptyLabel);
            logListPanel.add(Box.createVerticalGlue());
        } else {
            for(String log : logEntries)
            {
                JPanel logPanel = new JPanel(new BorderLayout());
                logPanel.setBackground(LOG_BG_COLOR);
                logPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                JLabel logL = new JLabel(log);
                logL.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
                logL.setForeground(Color.WHITE);
                logL.setAlignmentX(Component.LEFT_ALIGNMENT);

                logPanel.add(logL, BorderLayout.WEST);
                logListPanel.add(logPanel);
                logListPanel.add(Box.createVerticalStrut(2));
            }
        }


        JScrollPane scrollPane = new JScrollPane(logListPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 110), 1, true));
        scrollPane.setPreferredSize(new Dimension(300, 500));

        return scrollPane;
    }


    private void setComponent()
    {
        cardPanel.add(createWinLoseGraph(), WIN_LOSE);
        //cardPanel.add(createCharUsageGraph(), CHAR_USAGE);
        cardPanel.add(createItemUsageGraph(), ITEM_USAGE);


        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createStatisticPanel(), BorderLayout.CENTER);

        cardLayout.show(cardPanel, WIN_LOSE);
    }

    @Override
    public Screen getScreenType() {
        return Screen.STATISTIC;
    }

    @Override
    public void onShow() {
    	removeAll();
        setComponent();
        revalidate();
        repaint();

    }
}