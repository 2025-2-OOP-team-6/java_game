package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import GameLogic.Dice;
import GameLogic.Enemy;
import GameLogic.Item;


//적(Enemy) 데이터 관리 클래스.

public class EnemyData
{
    private static final String ENEMY_FILE = "assets//files//enemy_file.csv";

    private final HashMap<String, Enemy> enemyMap;
    private final DiceData diceData;
    private final ItemData itemData;
    private final HashMap<Item, Integer> dropTable;

    public EnemyData(DiceData diceData, ItemData itemData)
    {
    	dropTable = new HashMap<>();
    	this.diceData = diceData;
    	this.itemData = itemData;
    	enemyMap = new HashMap<>();
        readEnemyData();
    }

    private void readEnemyData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(ENEMY_FILE)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                final String[] parts = line.split(",");

                final String id = parts[0].trim();
                final int maxHp = Integer.parseInt(parts[1].trim());
                final Dice dice = diceData.get(parts[2].trim());
                final int length = Integer.parseInt(parts[3].trim());
                for(int i=0; i<length; i++)
                	dropTable.put(itemData.get(parts[4+i].trim()), Integer.parseInt(parts[5+i].trim()));
                
                final Enemy info = new Enemy(id, maxHp, dice, dropTable);
                enemyMap.put(id, info);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Error: can not open this file (" + ENEMY_FILE + ")");
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            System.err.println("Error: invalid number format in " + ENEMY_FILE);
        }
    }

    public Enemy get(final String id)
    {
        return enemyMap.get(id);
    }
}
