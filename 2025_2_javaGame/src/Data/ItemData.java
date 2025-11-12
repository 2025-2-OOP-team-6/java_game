package Data;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class ItemData
{
    //CONST
    private final String ITEM_FILE = "..//assets//files//item_file.csv";

    //VARIABLE
    private String[] itemList;


    //FUNCTIONS
    public String[] getItemList()
    {
        return itemList;
    }

    public void readItemData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                itemList = parts;
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
