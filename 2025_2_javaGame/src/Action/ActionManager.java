package Action;

import javax.swing.JButton;
import java.util.HashMap;

public class ActionManager {
	private HashMap<JButton, Runnable> buttonHashMap = new HashMap<>();
	
	public void put(JButton jButton, Runnable runnable) {
		
		buttonHashMap.put(jButton, runnable);
		addListener(jButton);
	}
	
	private void addListener(JButton jButton) {
		jButton.addActionListener(e -> {
            Runnable runnable = buttonHashMap.get(e.getSource());
            if (runnable != null) {
                runnable.run(); 
            }
        });
	}
}
