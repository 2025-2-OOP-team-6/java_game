package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import GameLogic.Entity;
import GameLogic.IGameRunnable;
import GameLogic.Item;

public class ItemData {
	private static final String ITEM_FILE = "assets//files//item_file.csv";

    private final HashMap<String, Item> itemMap;
    private final HashMap<String, IGameRunnable<Entity, String>> effectMap;
    private final DiceData diceData;

    public ItemData(DiceData diceData)
    {
    	this.diceData = diceData;
    	itemMap = new HashMap<>();
    	effectMap = new HashMap<>();
    	setEffectMap();
        readItemData();
    }

    private void setEffectMap() {
    	effectMap.put("hpUp", (e, id) -> e.heal());
    	effectMap.put("diceChange", (e, id) -> e.changeDice(diceData.get(id)));
    	effectMap.put("rangeUp", (e, id) -> {
    		if (e.getDice().range.getRange()[0] < 5)
    			e.getDice().range.startChange(1);
    	});
    	effectMap.put("rangeDown", (e, id) -> {
    		if (e.getDice().range.getRange()[0] > 2)
    			e.getDice().range.endChange(-1);	
    	});
    }
    private void readItemData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILE)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                final String[] parts = line.split(",");

                if (parts.length < 2)
                {
                    continue;
                }

                final String id = parts[0].trim();
                final String description = parts[1].trim();
                final String imagePath = parts[2].trim();
                

                final Item info = new Item(id, description, imagePath);
                info.setEffect(effectMap.get(id));
                itemMap.put(id, info);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Error: can not open this file (" + ITEM_FILE + ")");
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            System.err.println("Error: invalid number format in " + ITEM_FILE);
        }
    }

    public Item get(final String id)
    {
    	if (id == "null") return null; 
        return itemMap.get(id);
    }
    
}
