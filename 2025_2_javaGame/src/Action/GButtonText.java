package Action;

import javax.swing.JButton;

public class GButtonText extends JButton{
	ActionManager actionManager = new ActionManager();
	
	public GButtonText(String text, Runnable runnable) {
		super(text);
		actionManager.put(this, runnable);
	}
	
}
