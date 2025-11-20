package Screen;

import Util.Constant;
import Util.Screen;
import Action.GButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndingCreditScreen extends JPanel implements IScreen
{
    //CONST
    private final String SKIP_BTN = "..//assets//buttons//gobackBtn.png";
    private final int SCROLL_SPEED = 2;
    private final int CREDIT_FONT_SIZE = 28;
    private final int TITLE_FONT_SIZE = 40;
    private final int FADE_OUT_DURATION = 100; // 페이드 아웃 시간 (밀리초)

    //VARIABLES
    private ScreenManager screenMgr;
    private Timer scrollTimer;
    private Timer fadeTimer;
    private int scrollY;
    private Color wallpaper;
    private GButton skipBtn;
    private float fadeAlpha = 1.0f; // 투명도 (1.0 = 완전 불투명, 0.0 = 완전 투명)
    private boolean isFading = false;
    
    // 크레딧 데이터
    private String[] credits = {
        "",
        "",
        "",
        "2025-2 OOP Team-6",
        "",
        "Thank you for playing our game!",
        "",
        "",
        "Team Leader",
        "KIM JAE MIN",
        "",
        "Developers",
        "WANG SEOK BIN",
        "Byun Jae-woo",
        "Shin Dong-min",
        "NAM Seokhyun ",
        "Joochan Kim",
        "",
        "",
        "",
        "=== END ==="
    };

    @Override
    public void init(ScreenManager scManager)
    {
        setLayout(new BorderLayout());
        screenMgr = scManager;
        
        wallpaper = new Color(Constant.WALL_RED, Constant.WALL_GREEN, Constant.WALL_BLUE);
        setBackground(wallpaper);
        
        scrollY = getHeight();
        
        // Skip 버튼 추가
        skipBtn = new GButton(SKIP_BTN, () -> {
            startFadeOut(Screen.GAMEOVER);
        });
        add(skipBtn, BorderLayout.NORTH);
        
        // 스크롤 타이머 시작
        startScroll();
    }

    private void startFadeOut(Screen nextScreen)
    {
        isFading = true;
        stopScroll();
        
        fadeTimer = new Timer(20, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fadeAlpha -= 0.05f;
                
                if (fadeAlpha <= 0)
                {
                    fadeAlpha = 0;
                    if (fadeTimer != null)
                    {
                        fadeTimer.stop();
                        fadeTimer = null;
                    }
                    screenMgr.show(nextScreen);
                }
                
                repaint();
            }
        });
        fadeTimer.start();
    }

    private void startScroll()
    {
        if (scrollTimer == null)
        {
            scrollTimer = new Timer(30, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    scrollY -= SCROLL_SPEED;
                    repaint();
                    
                    // 크레딧이 모두 지나갔을 때
                    if (scrollY < -getHeight() * 2)
                    {
                        startFadeOut(Screen.GAMEOVER);
                    }
                }
            });
            scrollTimer.start();
        }
    }

    private void stopScroll()
    {
        if (scrollTimer != null)
        {
            scrollTimer.stop();
            scrollTimer = null;
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // 안티앨리어싱 설정
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        int centerX = getWidth() / 2;
        int currentY = scrollY;
        
        // 크레딧 그리기 (페이드 아웃 효과 적용)
        for (String credit : credits)
        {
            if (currentY > -50 && currentY < getHeight() + 50)
            {
                if (credit.isEmpty())
                {
                    // 빈 줄
                    currentY += 40;
                    continue;
                }
                
                // 제목인지 일반 텍스트인지 판단
                if (credit.contains("==="))
                {
                    g2d.setFont(new Font("맑은 고딕", Font.BOLD, TITLE_FONT_SIZE));
                    g2d.setColor(new Color(1.0f, 0.0f, 0.0f, fadeAlpha)); // RED with alpha
                }
                else if (credit.equals("2025-2 OOP Team-6") || credit.equals("Development Team") || 
                        credit.equals("Team Leader") || credit.equals("Developers") || 
                        credit.equals("Special Thanks"))
                {
                    g2d.setFont(new Font("맑은 고딕", Font.BOLD, CREDIT_FONT_SIZE));
                    g2d.setColor(new Color(1.0f, 1.0f, 0.0f, fadeAlpha)); // YELLOW with alpha
                }
                else
                {
                    g2d.setFont(new Font("맑은 고딕", Font.PLAIN, CREDIT_FONT_SIZE));
                    g2d.setColor(new Color(1.0f, 1.0f, 1.0f, fadeAlpha)); // WHITE with alpha
                }
                
                // 텍스트 중앙 정렬
                int textWidth = g2d.getFontMetrics().stringWidth(credit);
                g2d.drawString(credit, centerX - textWidth / 2, currentY);
            }
            
            currentY += 50;
        }
    }

    @Override
    public Screen getScreenType()
    {
        return Screen.CREDIT;
    }

    @Override
    public void onShow()
    {
        scrollY = getHeight();
        startScroll();
        System.out.println("EndingCreditScreen is now Rendering");
    }
}
