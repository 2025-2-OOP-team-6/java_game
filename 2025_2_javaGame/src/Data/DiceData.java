package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import GameLogic.Dice;
import GameLogic.DiceRange;

public class DiceData {
	private static final String DICE_FILE = "assets//files//dice_file.csv";
        
    private final HashMap<String, Dice> diceMap;
    

    public DiceData()
    {
    	diceMap = new HashMap<>();
        readItemData();
    }

    private void readItemData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(DICE_FILE)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
            	
                final String[] parts = line.split(",");

                if (parts.length < 3)
                {
                    continue;
                }

                final String id = parts[0].trim();
                final DiceRange range = new DiceRange(Integer.parseInt(parts[1].trim()),Integer.parseInt(parts[2].trim()));
                

                final Dice info = new Dice(id, range);
                diceMap.put(id, info);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Error: can not open this file (" + DICE_FILE + ")");
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            System.err.println("Error: invalid number format in " + DICE_FILE);
        }
    }

    public Dice get(final String id)
    {
        return diceMap.get(id);
    }
    
    public int getSize()
    {
        return diceMap.size();
    }
}
