package Data;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class ItemData
{
    class ItemInfo
    {
        int attack  = -1;
        int price   = -1;
    }

    //CONST
    private final String ITEM_FILE = "..//assets//files//item_file.csv";
    private final int ITEM_IDX   = 0;
    private final int ATTACK_IDX = 1;
    private final int PRICE_IDX  = 2;

    //VARIABLE
    private List<String> itemNames = new ArrayList<>();
    private HashMap<String, ItemInfo> itemInfoList = new HashMap<>();

    //FUNCTIONS
    public String[] getItemNames()
    {
        return itemNames.toArray(new String[0]);
    }

    public int getAttack(final String itemName)
    {
        ItemInfo info = itemInfoList.get(itemName);

        if(info != null && info.attack != -1)
        {
            return info.attack;
        }

        return -1;
    }

    public int getPrice(final String itemName)
    {
        ItemInfo info = itemInfoList.get(itemName);

        if(info != null && info.price != -1)
        {
            return info.price;
        }

        return -1;
    }

    public void readItemData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts.length >= 3)
                {
                    ItemInfo itemNode = new ItemInfo();
                    String item = parts[ITEM_IDX];
                    itemNode.attack = Integer.parseInt(parts[ATTACK_IDX]);
                    itemNode.price  = Integer.parseInt(parts[PRICE_IDX]);

                    itemNames.add(item);
                    itemInfoList.put(item, itemNode);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: can not open this file (" + ITEM_FILE + ")" );
        }
        catch(NullPointerException e)
        {
            System.err.println("Error: Invailed file data format");
        }
    }
}
