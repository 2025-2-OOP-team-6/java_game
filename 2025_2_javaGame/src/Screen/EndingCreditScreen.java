
package Screen;

import Util.Constant;
import Util.Screen;
import Action.GButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndingCreditScreen extends JPanel implements IScreen {
    // CONST
    private final String SKIP_BTN = "assets//buttons//gobackBtn.png";
    private final int SCROLL_SPEED = 2;
    private final int CREDIT_FONT_SIZE = 28;
    private final int TITLE_FONT_SIZE = 40;

    // VARIABLES
    private ScreenManager screenMgr;
    private Timer scrollTimer;
    private Timer fadeTimer;
    private int scrollY;
    private float fadeAlpha = 1.0f;
    private boolean isFading = false;

    private GButton skipBtn;

    // 크레딧 데이터
    private String[] credits = {
            "",
            "",
            "2025-2 OOP Team-6",
            "",
            "Thank you for playing our game!",
            "",
            "Team Leader",
            "KIM JAE MIN",
            "",
            "Developers",
            "Byun Jae-woo",
            "WANG SEOK BIN",
            "Shin Dong-min",
            "NAM Seokhyun",
            "Joochan Kim",
            "",
            "=== END ==="
    };

    @Override
    public void init(ScreenManager scManager) {
        this.screenMgr = scManager;
        setLayout(null);
        setBackground(new Color(Constant.WALL_RED, Constant.WALL_GREEN, Constant.WALL_BLUE));

        // Skip 버튼
        skipBtn = new GButton(SKIP_BTN, () -> startFadeOut(Screen.LOGIN));
        skipBtn.setBounds(20, 20, 150, 50);
        add(skipBtn);
    }

    @Override
    public void onShow() {
        // 화면 크기 이후에 초기화
        SwingUtilities.invokeLater(() -> {
            scrollY = getHeight() + 200; // 화면 아래에서 시작
            startScroll();
        });
        System.out.println("EndingCreditScreen is now Rendering");
    }

    private void startScroll() {
        if (scrollTimer == null) {
            scrollTimer = new Timer(30, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scrollY -= SCROLL_SPEED;
                    repaint();

                    // 크레딧이 모두 지나갔을 때
                    if (scrollY < -(credits.length * 50)) {
                        startFadeOut(Screen.GAMEOVER);
                    }
                }
            });
            scrollTimer.start();
        }
    }

    private void stopScroll() {
        if (scrollTimer != null) {
            scrollTimer.stop();
            scrollTimer = null;
        }
    }

    private void startFadeOut(Screen nextScreen) {
        if (isFading) return;
        isFading = true;
        stopScroll();

        fadeTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fadeAlpha -= 0.05f;

                if (fadeAlpha <= 0) {
                    fadeAlpha = 0;
                    fadeTimer.stop();
                    screenMgr.show(nextScreen);
                }
                repaint();
            }
        });
        fadeTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int currentY = scrollY;

        for (String credit : credits) {
            if (currentY > -50 && currentY < getHeight() + 50) {
                if (credit.isEmpty()) {
                    currentY += 40;
                    continue;
                }

                if (credit.contains("===")) {
                    g2d.setFont(new Font("맑은 고딕", Font.BOLD, TITLE_FONT_SIZE));
                    g2d.setColor(new Color(1.0f, 0.0f, 0.0f, fadeAlpha)); // RED
                } else if (credit.equals("2025-2 OOP Team-6") || credit.equals("Team Leader") || credit.equals("Developers")) {
                    g2d.setFont(new Font("맑은 고딕", Font.BOLD, CREDIT_FONT_SIZE));
                    g2d.setColor(new Color(1.0f, 1.0f, 0.0f, fadeAlpha)); // YELLOW
                } else {
                    g2d.setFont(new Font("맑은 고딕", Font.PLAIN, CREDIT_FONT_SIZE));
                    g2d.setColor(new Color(1.0f, 1.0f, 1.0f, fadeAlpha)); // WHITE
                }

                int textWidth = g2d.getFontMetrics().stringWidth(credit);
                g2d.drawString(credit, centerX - textWidth / 2, currentY);
            }
            currentY += 50;
        }
    }

    @Override
    public Screen getScreenType() {
        return Screen.CREDIT;
    }
}
