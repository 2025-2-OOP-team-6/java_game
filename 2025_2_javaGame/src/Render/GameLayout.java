package Render;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;

import Action.GButton;
import Util.Position;

public class GameLayout implements LayoutManager2 {


    private HashMap<Component, Position> componentPositions = new HashMap<>();

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof Position) {
            componentPositions.put(comp, (Position) constraints);
        } else {
            throw new IllegalArgumentException("Constraints must be of type Position");
        }
    }

    @Override
    public void layoutContainer(Container parent) {
        for (Component comp : parent.getComponents()) {
        	Position pos = componentPositions.get(comp);
        	
        	if (comp instanceof GButton) {
        		GButton button = (GButton) comp;
    	    			button.setBounds(pos.x,pos.y);
    	    		throw new RuntimeException("잘못된 add 매개변수 입력입니다. center/leftTop/rightTop 중 하나");
    	    	}
        	
        	else {
        		comp.setBounds(pos.x,pos.y,comp.getWidth(),comp.getHeight());
        	}
            if (pos != null) {
                // 원하는 기본 크기 또는 컴포넌트에 설정된 선호 크기 사용
                Dimension preferredSize = comp.getPreferredSize();
                comp.setBounds(pos.x, pos.y, preferredSize.width, preferredSize.height);
            }
        }
    }

    @Override public void addLayoutComponent(String name, Component comp) {}
    @Override public void removeLayoutComponent(Component comp) {
        componentPositions.remove(comp);
    }
    @Override public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(400, 400);
    }
    @Override public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(200, 200);
    }
    @Override public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override public float getLayoutAlignmentX(Container target) { return 0.0f; }
    @Override public float getLayoutAlignmentY(Container target) { return 0.0f; }
    @Override public void invalidateLayout(Container target) {}
    
    
    
    
}
