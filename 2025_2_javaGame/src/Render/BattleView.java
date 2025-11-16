package Render;

import Action.GButton;
import Logic.BattleManager;
import Util.Constant;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

/*
  인게임 전투 화면을 실제로 그리는 View 패널.
 
   상단 중앙: 현재 층수 표시
   가운데: 좌측 플레이어 / 우측 적(각각: 위에서부터 주사위 범위 → 주사위 값 육각형 → 체력바 → 유닛 이미지)
   하단: 좌측 2x3 아이템 슬롯, 우측 결투 버튼 + 뒤로가기 버튼
 */
public class BattleView extends JPanel
{
    // 이미지 경로
	private static final String PLAYER_IMAGE_PATH   = "assets/battle/player.png";
	private static final String ENEMY_IMAGE_PATH    = "assets/battle/enemy.png";
	private static final String DICE_BADGE_PATH     = "assets/battle/diceBadge.png";
	private static final String HP_SLOT_PLAYER_PATH = "assets/battle/hpPlayer.png";
	private static final String HP_SLOT_ENEMY_PATH  = "assets/battle/hpEnemy.png";
	private static final String ITEM_SLOT_PATH      = "assets/battle/itemSlot.png";
	private static final String DUEL_BUTTON_PATH    = "assets/battle/duelButton.png";
	private static final String BACK_BUTTON_PATH    = "assets/buttons/gobackBtn.png";

    private final BattleManager battleManager;
    private final Runnable onBackToHome;

    // 상단
    private JLabel stageLabel;

    // 플레이어 / 적 HP / 주사위
    private HpBar playerHpBar;
    private HpBar enemyHpBar;

    private DiceBadge playerDiceBadge;
    private DiceBadge enemyDiceBadge;

    public BattleView(final BattleManager battleManager, final Runnable onBackToHome)
    {
        this.battleManager = battleManager;
        this.onBackToHome = onBackToHome;

        ToolTipManager.sharedInstance().setInitialDelay(100);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        Color wallpaper = new Color(
            Constant.WALL_RED,
            Constant.WALL_GREEN,
            Constant.WALL_BLUE
        );
        setBackground(wallpaper);

        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    // 상단: 스테이지 표시
    private JPanel createTopPanel()
    {
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BorderLayout());

        stageLabel = new JLabel(battleManager.getStage() + " STAGE", JLabel.CENTER);
        stageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        stageLabel.setForeground(Color.WHITE);

        topPanel.add(stageLabel, BorderLayout.CENTER);
        return topPanel;
    }

    // 중앙: 플레이어 / 적
    private JPanel createCenterPanel()
    {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        centerPanel.setOpaque(false);

        centerPanel.add(createUnitPanel(true));   // 플레이어
        centerPanel.add(createUnitPanel(false));  // 적

        return centerPanel;
    }

    private JPanel createUnitPanel(final boolean isPlayer)
    {
        JPanel unitPanel = new JPanel();
        unitPanel.setOpaque(false);
        unitPanel.setLayout(new BoxLayout(unitPanel, BoxLayout.Y_AXIS));

        //주사위값 범위
        int min = isPlayer ? battleManager.getPlayerDiceMin() : battleManager.getEnemyDiceMin();
        int max = isPlayer ? battleManager.getPlayerDiceMax() : battleManager.getEnemyDiceMax();

        JLabel rangeLabel = new JLabel("DICE: " + min + " ~ " + max);
        rangeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        rangeLabel.setForeground(Color.WHITE);
        rangeLabel.setAlignmentX(CENTER_ALIGNMENT);

        //주사위 값을 감싸는 육각형
        DiceBadge diceBadge = new DiceBadge(DICE_BADGE_PATH);
        diceBadge.setAlignmentX(CENTER_ALIGNMENT);

        if (isPlayer)
        {
            playerDiceBadge = diceBadge;
        }
        else
        {
            enemyDiceBadge = diceBadge;
        }

        //체력 바
        int maxHp = isPlayer ? battleManager.getPlayerMaxHp() : battleManager.getEnemyMaxHp();
        String hpImagePath = isPlayer ? HP_SLOT_PLAYER_PATH : HP_SLOT_ENEMY_PATH;

        HpBar hpBar = new HpBar(hpImagePath, maxHp);
        hpBar.setAlignmentX(CENTER_ALIGNMENT);

        if (isPlayer)
        {
            playerHpBar = hpBar;
        }
        else
        {
            enemyHpBar = hpBar;
        }

        //유닛 이미지
        String imgPath = isPlayer ? PLAYER_IMAGE_PATH : ENEMY_IMAGE_PATH;
        JLabel unitImage = createScaledImageLabel(imgPath, 200, 200);
        unitImage.setAlignmentX(CENTER_ALIGNMENT);

        //주사위 범위 → 주사위 값 → HP → 유닛
        unitPanel.add(rangeLabel);
        unitPanel.add(Box.createVerticalStrut(5));
        unitPanel.add(diceBadge);
        unitPanel.add(Box.createVerticalStrut(10));
        unitPanel.add(hpBar);
        unitPanel.add(Box.createVerticalStrut(15));
        unitPanel.add(unitImage);

        return unitPanel;
    }

    // 하단: 아이템 + 버튼
    private JPanel createBottomPanel()
    {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(0, 220));

        bottomPanel.add(createItemArea(), BorderLayout.WEST);
        bottomPanel.add(createRightControlArea(), BorderLayout.EAST);

        return bottomPanel;
    }

    private JPanel createItemArea()
    {
        JPanel itemPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        itemPanel.setOpaque(false);
        itemPanel.setPreferredSize(new Dimension(400, 180));

        for (int i = 0; i < 6; ++i)
        {
            JPanel slot = createItemSlotPanel(i);
            itemPanel.add(slot);
        }

        return itemPanel;
    }

    private JPanel createItemSlotPanel(final int index)
    {
        JPanel slotPanel = new JPanel();
        slotPanel.setOpaque(false);
        slotPanel.setLayout(new BoxLayout(slotPanel, BoxLayout.Y_AXIS));

        JLabel iconLabel = createScaledImageLabel(ITEM_SLOT_PATH, 64, 64);
        iconLabel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("ITEM " + (index + 1));
        nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);

        String tooltipText = "임시 아이템 설명입니다. (나중에 효과 연결)";
        iconLabel.setToolTipText(tooltipText);
        nameLabel.setToolTipText(tooltipText);

        iconLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseClicked(final java.awt.event.MouseEvent e)
            {
                useItem(index);
            }
        });

        slotPanel.add(iconLabel);
        slotPanel.add(Box.createVerticalStrut(3));
        slotPanel.add(nameLabel);

        return slotPanel;
    }

    private JPanel createRightControlArea()
    {
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(220, 180));

        GButton duelButton = new GButton(DUEL_BUTTON_PATH, this::startDuel);
        duelButton.setAlignmentX(CENTER_ALIGNMENT);

        GButton backButton = new GButton(BACK_BUTTON_PATH, onBackToHome);
        backButton.setAlignmentX(CENTER_ALIGNMENT);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(duelButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(backButton);
        rightPanel.add(Box.createVerticalGlue());

        return rightPanel;
    }

    private void startDuel()
    {
        BattleManager.DuelResult result = battleManager.duel();

        switch (result)
        {
            case PLAYER_WIN:
                enemyHpBar.damageOneSlot();
                break;
            case ENEMY_WIN:
                playerHpBar.damageOneSlot();
                break;
            case DRAW:
            default:
                break;
        }

        repaint();
    }

    private void useItem(final int index)
    {
        System.out.println("아이템 사용: 슬롯 " + index + " (TODO: 실제 효과 적용)");
    }

    private JLabel createScaledImageLabel(final String path, final int width, final int height)
    {
        ImageIcon scaledIcon = ImageLoader.loadScaledImageIcon(path, width, height);
        return new JLabel(scaledIcon);
    }

    private static class HpBar extends JPanel
    {
        private final ImageIcon slotImage;
        private final int maxHp;
        private int currentHp;

        public HpBar(final String slotImagePath, final int maxHp)
        {
            this.slotImage = ImageLoader.loadImageIcon(slotImagePath);
            this.maxHp = maxHp;
            this.currentHp = maxHp;

            setOpaque(false);

            int width = maxHp * (slotImage.getIconWidth() + 4);
            int height = slotImage.getIconHeight() + 4;
            setPreferredSize(new Dimension(width, height));
        }

        public void damageOneSlot()
        {
            if (currentHp <= 0)
            {
                return;
            }

            currentHp -= 1;
            repaint();
        }

        @Override
        protected void paintComponent(final java.awt.Graphics g)
        {
            super.paintComponent(g);

            for (int i = 0; i < maxHp; ++i)
            {
                int x = i * (slotImage.getIconWidth() + 4);
                int y = 0;

                if (i < currentHp)
                {
                    g.drawImage(slotImage.getImage(), x, y, this);
                }
                else
                {
                    g.setColor(new Color(50, 50, 50, 150));
                    g.fillRect(x, y, slotImage.getIconWidth(), slotImage.getIconHeight());
                }
            }
        }
    }

    private static class DiceBadge extends JPanel
    {
        private final ImageIcon badgeImage;
        private final JLabel valueLabel;

        public DiceBadge(final String imagePath)
        {
            this.badgeImage = ImageLoader.loadImageIcon(imagePath);

            setLayout(new BorderLayout());
            setOpaque(false);

            valueLabel = new JLabel("", JLabel.CENTER);
            valueLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
            valueLabel.setForeground(Color.WHITE);

            setPreferredSize(
                new Dimension(badgeImage.getIconWidth(), badgeImage.getIconHeight())
            );

            add(valueLabel, BorderLayout.CENTER);
        }

        public void setValue(final int value)
        {
            valueLabel.setText(String.valueOf(value));
            repaint();
        }

        @Override
        protected void paintComponent(final java.awt.Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(badgeImage.getImage(), 0, 0, this);
        }
    }
}
