package Action;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GButton extends JButton implements IGameUI {
	ActionManager actionManager = new ActionManager();
	
	public GButton(ImageIcon image, Runnable runnable) {
		super(image);
		
		actionManager.put(this, runnable);
		
		if (getImageIcon() == null) {
	        throw new RuntimeException("no such a image file: " + image.toString());
	    }
		
		setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        
        setBounds(0,0);
	}
	
	public GButton(String imagePath, Runnable runnable) {
		super(new ImageIcon(imagePath));
		
		actionManager.put(this, runnable);
		
		if (getImageIcon() == null) {
	        throw new RuntimeException("no such a image file: " + imagePath);
	    }
		
		setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        
        setBounds(0,0);
	}
	
	
	@Override
	public void setBounds(int x, int y) {
		super.setBounds(x, y, getIcon().getIconWidth(), getIcon().getIconHeight());
	}
	
	@Override
	public void setScale(float scale) {
		Image originalImage = getImageIcon().getImage();
		Image scaledImage = originalImage.getScaledInstance((int)(originalImage.getWidth(null) * scale), (int)(originalImage.getHeight(null) * scale), Image.SCALE_SMOOTH);
		setIcon(new ImageIcon(scaledImage));
	}
	
	@Override
	public ImageIcon getImageIcon() {
	    Icon icon = getIcon();

	    if (icon instanceof ImageIcon imageIcon) {  
	        return imageIcon;  
	    } else {
	        return null;  
	    }
	}

	public void setListener(Runnable r) {
		actionManager.put(this, r);
		
	}
	
	
}
