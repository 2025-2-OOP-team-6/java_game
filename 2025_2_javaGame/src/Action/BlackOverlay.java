package Action;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BlackOverlay extends JPanel {
	
	    private float opacity = 0.0f; // 0.0 (완전 투명) ~ 1.0 (완전 불투명)

	    public BlackOverlay() {
	        setOpaque(false); 
	    }
	    
	    

	    public void setOpacity(int opaInt) {
	    	opacity = opaInt/255f;
	        if (opacity < 0.0f) opacity = 0.0f;
	        if (opacity > 1.0f) opacity = 1.0f;
	        this.opacity = opacity;
	        // 변경 사항을 반영하기 위해 다시 그리도록 요청
	        repaint(); 
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g); // 이 줄을 제거하면 자식 컴포넌트도 투명하게 영향을 받을 수 있음

	        Graphics2D g2d = (Graphics2D) g.create();
	        
	        // 투명도 설정
	        // AlphaComposite.SRC_OVER는 소스(패널 배경)를 대상으로 그릴 때 투명도를 적용하는 방식
	        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
	        g2d.setComposite(alpha);

	        // 원하는 색상으로 패널 전체를 채우기 (예: 검은색)
	        g2d.setColor(Color.BLACK);
	        g2d.fillRect(0, 0, getWidth(), getHeight());

	        g2d.dispose();
	    }
	}