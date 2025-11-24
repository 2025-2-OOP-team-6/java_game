package Screen;

import Data.DataManager;
import Data.LogData;
import Action.GButton;
import Util.Constant;
import Data.UserData;
import Util.Screen;
import Logic.User;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.FlowLayout;

public class GameOverScreen extends JPanel implements IScreen
{
    //CONST
    private final String HOME_BTN = "assets//buttons//gobackBtn.png";
    private final String RETRY_BTN = "assets//buttons//startAdventureBtn.png";
    private final String CREDIT_BTN = "assets//buttons//endingCreditBtn.png";

    //VARIABLES
    private JLabel titleLabel;
    private JLabel timeLabel;
    private JLabel coinLabel;
    private JLabel characterLabel;
    private JLabel itemLabel;
    private JLabel resultLabel;

    private GButton homeBtn;
    private GButton retryBtn;
    private GButton creditBtn;

    private Color wallpaper;

    private User user;
    private UserData userData;
    private LogData logData;
    private ScreenManager screenMgr;

    // 게임 결과 데이터
    private int timeSpent = 0;
    private int coin = 0;

    @Override
    public void init(ScreenManager scManager)
    {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        wallpaper = new Color(Constant.WALL_RED, Constant.WALL_GREEN, Constant.WALL_BLUE);
        setBackground(wallpaper);

        user = DataManager.getInstance().getCurrentUser();
        userData = DataManager.getInstance().getUserMgr();
        logData = DataManager.getInstance().getLogMgr();
        screenMgr = scManager;

        setComponent();
    }

    private void setComponent()
    {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    // -- Set Header Panel --
    private JPanel createHeaderPanel()
    {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        titleLabel = new JLabel("게임 오버");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 60));
        titleLabel.setForeground(Color.RED);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(20));

        return headerPanel;
    }

    // -- Set Center Panel --
    private JPanel createCenterPanel()
    {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // 최신 로그 데이터 가져오기
        LogData.LogCon latestLog = logData.getLatestLog(user.getId());

        if (latestLog != null) {
            // 정보를 담을 RoundPanel 생성
            RoundPanel infoBox = new RoundPanel(20);
            infoBox.setBackground(new Color(67, 46, 129));
            infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
            infoBox.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            infoBox.setMaximumSize(new java.awt.Dimension(500, 400));
            infoBox.setAlignmentX(Component.CENTER_ALIGNMENT);

            // 게임 시간 (updateTime()으로 저장된 최신 시간)
            int gameTime = userData.getTime(user.getId());
            JPanel timePanel = createInfoRow("게임 시간", gameTime + "초", Color.WHITE);
            infoBox.add(timePanel);
            infoBox.add(Box.createVerticalStrut(15));

            // 캐릭터
            JPanel charPanel = createInfoRow("캐릭터", latestLog.getCharacter(), Color.CYAN);
            infoBox.add(charPanel);
            infoBox.add(Box.createVerticalStrut(15));

            // 아이템
            JPanel itemPanel = createInfoRow("아이템", latestLog.getItem(), Color.GREEN);
            infoBox.add(itemPanel);
            infoBox.add(Box.createVerticalStrut(15));

            // 게임 결과
            String resultText = latestLog.getResult().equals("WIN") ? "승리" : "패배";
            Color resultColor = latestLog.getResult().equals("WIN") ? new Color(0, 255, 0) : new Color(255, 100, 100);
            JPanel resultPanel = createInfoRow("결과", resultText, resultColor);
            infoBox.add(resultPanel);
            infoBox.add(Box.createVerticalStrut(15));

            // 랭크 포인트
            JPanel rankPanel = createInfoRow("랭크 포인트", latestLog.getRank(), Color.YELLOW);
            infoBox.add(rankPanel);

            centerPanel.add(Box.createVerticalGlue());
            centerPanel.add(infoBox);
            centerPanel.add(Box.createVerticalGlue());
        } else {
            // 로그가 없을 경우 기본값 표시
            JLabel noLogLabel = new JLabel("게임 데이터가 없습니다.");
            noLogLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            noLogLabel.setForeground(Color.WHITE);
            noLogLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(noLogLabel);
        }

        return centerPanel;
    }

    // 정보 행(label + value)을 생성하는 헬퍼 메서드
    private JPanel createInfoRow(String label, String value, Color valueColor)
    {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        rowPanel.setOpaque(false);
        rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelL = new JLabel(label);
        labelL.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        labelL.setForeground(Color.WHITE);
        labelL.setPreferredSize(new java.awt.Dimension(100, 30));

        JLabel valueL = new JLabel(value);
        valueL.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        valueL.setForeground(valueColor);

        rowPanel.add(labelL);
        rowPanel.add(valueL);

        return rowPanel;
    }

    // -- Set Bottom Panel --
    private JPanel createBottomPanel()
    {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        bottomPanel.setOpaque(false);

        retryBtn = new GButton(RETRY_BTN, () -> {
            resetGameData();
            screenMgr.show(Screen.START);
        });

        homeBtn = new GButton(HOME_BTN, () -> {
            // 보상을 사용자에게 지급
            int currentCoin = userData.getCoin(user.getId());
            userData.updateCoin(user.getId(), currentCoin);
            userData.storeUserData(user.getId());

            resetGameData();
            screenMgr.show(Screen.HOME);
        });

        creditBtn = new GButton(CREDIT_BTN, () -> {
            screenMgr.show(Screen.CREDIT);
        });

        bottomPanel.add(retryBtn);
        bottomPanel.add(creditBtn);
        bottomPanel.add(homeBtn);

        return bottomPanel;
    }

    /**
     * 게임 결과 데이터 설정
     * @param timeSpent 게임 시간 (초)
     * @param coin 남은 코인 
     */
    public void setGameResult(int timeSpent, int coin)
    {
        this.timeSpent = timeSpent;
        this.coin = coin;
    }

    /**
     * 게임 데이터 초기화
     */
    private void resetGameData()
    {
        this.timeSpent = 0;
        this.coin = 0;
    }

    @Override
    public Screen getScreenType() {
         return Screen.GAMEOVER; 
        }

    @Override
    public void onShow()
    {
        removeAll();
        setComponent();
        revalidate();
        repaint();
        System.out.print("Start: GameOverScreen is now Rendering\n");

        
    }
}
