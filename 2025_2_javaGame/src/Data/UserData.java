package Data;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class UserData
{
    // INNER CLASS
    private class Info
    {
        int clearTime = -1;
        int clearRank = -1;
        int coin = -1;
        String inventory = "";
    }

    //CONST
    private final int TIME_IDX = 0;
    private final int RANK_IDX = 1;
    private final int COIN_IDX = 2;
    private final int INVEN_IDX = 3;
    private final int MAX_CHOOSE_SIZE = 3;
    private final String PREFIX = "..//assets//files//";
    private final String SUFFIX = "_file.csv";


    // VARIABLES
    private String[] userIDList;
    private String[] choosedCharactor;
    private HashMap<String, Info> userHashMap;


    public UserData()
    {
        choosedCharactor = new String[MAX_CHOOSE_SIZE];
    }


    // FUNCTIONS

    // - Getters -
    public String[] getInventory(final String id)
    {
        String[] inventory;
        Info userInfo = userHashMap.get(id);

        if(userInfo != null && userInfo.inventory != null)
        {
            String invenString = userInfo.inventory;
            String[] parts = invenString.split(" ");

            inventory = parts;

            return inventory;
        }

        return null;
    }

    public String[] getChoosedCharactor()
    {
        return choosedCharactor;
    }

    public int getCoin(final String id)
    {
        Info userInfo = userHashMap.get(id);

        if(userInfo != null && userInfo.coin != -1)
        {
            return userInfo.coin;
        }

        return -1;
    }

    public int getTime(final String id)
    {
        Info userInfo = userHashMap.get(id);

        if(userInfo != null && userInfo.clearTime != -1)
        {
            return userInfo.clearTime;
        }

        return -1;
    }

    public int getRank(final String id)
    {
        Info userInfo = userHashMap.get(id);

        if(userInfo != null && userInfo.clearRank != -1)
        {
            return userInfo.clearRank;
        }

        return -1;
    }

    // - Setters

    public void updateChoosedCharactor(final String[] list)
    {
        if(list != null)
            choosedCharactor = list;
    }

    public void addNewUser(final String id)
    {
        Info newUser = new Info();
        userHashMap.put(id, newUser);
    }

    public void addNewItem(final String id, final String[] items)
    {
        Info userInfo = userHashMap.get(id);
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < items.length; ++i)
        {
            sb.append(" ").append(items[i]);
        }

        userInfo.inventory += sb.toString();
    }

    public void dropItem(final String id, String[] drops)
    {
        Info userInfo = userHashMap.get(id);
        String invenList = userInfo.inventory;

        for(int i = 0; i < drops.length; ++i)
        {
            invenList = invenList.replace((drops[i] + " "), "");
        }

        userInfo.inventory = invenList;
    }

    public void updateTime(final String id, final int time)
    {
        Info userInfo = userHashMap.get(id);
        userInfo.clearTime = time;
    }

    public void updateRank(final String id, final int rank)
    {
        Info userInfo = userHashMap.get(id);
        userInfo.clearRank = rank;
    }

    public void updateCoin(final String id, final int coin)
    {
        Info userInfo = userHashMap.get(id);
        userInfo.coin = coin;
    }


    // - File I/O -

    public void readUserData(final String[] userIDList)
    {
        final int MINIUM_DATA_SIZE = 3;

        userHashMap = new HashMap<>();

        for(String id : userIDList)
        {
            final String USER_FILE = PREFIX + id + SUFFIX;

            try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");

                    if(parts.length >= MINIUM_DATA_SIZE)
                    {
                        int time = Integer.parseInt(parts[TIME_IDX]);
                        int rank = Integer.parseInt(parts[RANK_IDX]);
                        int coin = Integer.parseInt(parts[COIN_IDX]);
                        String invenString = parts[INVEN_IDX];

                        Info infoNode = new Info();
                        infoNode.clearRank = rank;
                        infoNode.clearTime = time;
                        infoNode.coin = coin;
                        infoNode.inventory = invenString;

                        userHashMap.put(id, infoNode);
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error: can not open this file (" + USER_FILE + ")" );
            }
            catch(NullPointerException e)
            {
                System.err.println("Error: Invailed file data format");
            }
        }
    }



    public void storeUserData(final String id)
    {
        final String USER_FILE = PREFIX + id + SUFFIX;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE)))
        {
            for(Map.Entry<String, Info> node : userHashMap.entrySet())
            {
                Info userInfo = node.getValue();

                String data = String.format("%d,%d,%d,%s", userInfo.clearTime, userInfo.clearRank, userInfo.coin, userInfo.inventory);
                writer.write(data);
                writer.newLine();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.err.println("Error: Can not write data (" + USER_FILE + ")");
        }
    }
}