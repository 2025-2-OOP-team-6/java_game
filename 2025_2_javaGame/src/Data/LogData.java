package Data;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


public class LogData
{
    class Logs
    {
        List<String> dateLogs       = new ArrayList<>();
        List<String> timeLogs       = new ArrayList<>();
        List<String> rankLogs       = new ArrayList<>();
        List<String> itemLogs       = new ArrayList<>();
        List<String> charactorLogs  = new ArrayList<>();
        List<String> mobLogs        = new ArrayList<>();
        List<String> resultLogs     = new ArrayList<>();
    }

    //CONST
    private final String SUFFIX = "_logData.csv";
    private final String PREFIX = "..//assets//files//";

    private final int MINIUM_DATA_SIZE  = 7;
    private final int DATE_IDX          = 0;
    private final int TIME_IDX          = 1;
    private final int RANK_IDX          = 2;
    private final int ITEM_IDX          = 3;
    private final int CHARACTOR_IDX     = 4;
    private final int MOB_IDX           = 5;
    private final int RESULT_IDX        = 6;

    //VARIABLES
    private HashMap<String, Logs> userLogsList;

    //DATA MANAGERS
    private UserData userMgr;
    private CharactorData charMgr;


    public LogData()
    {
        userLogsList = new HashMap<>();

        userMgr = DataManager.getInstance().getUserMgr();
        charMgr = DataManager.getInstance().getCharactorMgr();

        readLogData();
    }

    // ------------- Getters ---------------

    public String[] getDataLogs(final String id)
    {
         Logs node = userLogsList.get(id);

         if(node != null && !node.dateLogs.isEmpty())
         {
             return node.dateLogs.toArray(new String[0]).clone();
         }

         return null;
    }

    public String[] getTimeLogs(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.timeLogs.isEmpty())
        {
            return node.itemLogs.toArray(new String[0]).clone();
        }

        return null;
    }

    public String[] getTimeGraphData(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.itemLogs.isEmpty())
        {
            return node.itemLogs.toArray(new String[0]).clone();
        }

        return null;
    }

    public String[] getRankGraphData(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.rankLogs.isEmpty())
        {
            return node.rankLogs.toArray(new String[0]).clone();
        }

        return null;
    }

    public int[] getItemGraphData(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.itemLogs.isEmpty())
        {
            final ItemData itemMgr = DataManager.getInstance().getItemMgr();
            final int ITEM_LIST_LENGTH = itemMgr.getItemNames().length;
            final int ITEM_LOG_LENGTH = node.itemLogs.size();

            final String[] itemList = itemMgr.getItemNames();
            int[] itemUseCount = new int[ITEM_LIST_LENGTH];


            for(int i = 0; i < ITEM_LIST_LENGTH; ++i)
            {
                itemUseCount[i] = 0;

                for(int j = 0; j < ITEM_LOG_LENGTH; ++j)
                {
                    if(itemList[i].equals(node.itemLogs.get(j)))
                    {
                        itemUseCount[i] += 1;
                    }
                }
            }

            return itemUseCount;
        }

        return null;
    }

    public int[] getCharGraphData(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.charactorLogs.isEmpty())
        {
            final String[] charList = charMgr.getjobList();
            final int CHARACTOR_LIST_LENGTH = charList.length;
            final int CHARACTOR_LOG_LENGTH = node.charactorLogs.size();

            int[] charUseCount = new int[CHARACTOR_LIST_LENGTH];

            for(int i = 0; i < CHARACTOR_LIST_LENGTH; ++i)
            {
                charUseCount[i] = 0;

                for(int j = 0; j < CHARACTOR_LOG_LENGTH; ++j)
                {
                    if(charList[i].equals(node.charactorLogs.get(j)))
                    {
                        charUseCount[i] += 1;
                    }
                }
            }

            return charUseCount;
        }

        return null;
    }

    public int[] getMobGraphData(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.mobLogs.isEmpty())
        {
            // TODO : PUT MOB MANAGER
            final int MOB_LIST_LENGTH = 1;
            final int MOB_LOG_LENGTH = node.mobLogs.size();
            final String[] mobList = null;

            int[] mobUseCount = new int[MOB_LIST_LENGTH];

            for(int i = 0; i < MOB_LIST_LENGTH; ++i)
            {
                mobUseCount[i] = 0;

                for(int j = 0; j < MOB_LOG_LENGTH; ++j)
                {
                    if(mobList[i].equals(null))
                    {
                        mobUseCount[i] += 1;
                    }
                }
            }

            return mobUseCount;
        }

        return null;
    }

    public String[] getResultLogs(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.resultLogs.isEmpty())
        {
            return node.resultLogs.toArray(new String[0]).clone();
        }

        return null;
    }



    // ----------------- Functions ---------------------

    private void addNewUser(final String id)
    {
        Logs newLog = new Logs();
        userLogsList.put(id, newLog);
    }

    public void insertLog(final String id, final String logString)
    {
        Logs target = userLogsList.get(id);
        final String parts[] = logString.split(" ");

        assert(target != null) : "Error: Invalid user id" + id + "log insertion failure";
        assert(parts.length >= MINIUM_DATA_SIZE) : "Error: not enough parameters";

        target.dateLogs.add(parts[DATE_IDX]);
        target.timeLogs.add(parts[TIME_IDX]);
        target.rankLogs.add(parts[RANK_IDX]);
        target.itemLogs.add(parts[ITEM_IDX]);
        target.charactorLogs.add(parts[CHARACTOR_IDX]);
        target.mobLogs.add(parts[MOB_IDX]);
        target.resultLogs.add(parts[RESULT_IDX]);
    }

    private void readLogData()
    {
        final String[] userIdList = DataManager.getInstance().getAccountMgr().getIDList();

        for(String userId : userIdList)
        {
            final String USER_FILE = PREFIX + userId + SUFFIX;

            File userFile = new File(USER_FILE);
            if(!userFile.exists())
            {
                addNewUser(userId);
                writeLogData(userId);
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
                String line = br.readLine();
                if(line != null)
                {
                    String[] parts = line.split(",");

                    assert(parts.length >= MINIUM_DATA_SIZE) : "Error: Invaild argument";

                        Logs target = new Logs();

                        target.dateLogs.add(parts[DATE_IDX]);
                        target.timeLogs.add(parts[TIME_IDX]);
                        target.rankLogs.add(parts[RANK_IDX]);
                        target.itemLogs.add(parts[ITEM_IDX]);
                        target.charactorLogs.add(parts[CHARACTOR_IDX]);
                        target.mobLogs.add(parts[MOB_IDX]);
                        target.resultLogs.add(parts[RESULT_IDX]);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error: can not open this file (" + USER_FILE + ")" );
            }
            catch(NullPointerException e)
            {
                System.err.println("Error: Invaild file data format");
            }
        }
    }

    public void writeLogData(final String id)
    {
        final String USER_FILE = PREFIX + id + SUFFIX;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE)))
        {
            final Logs userLogs = userLogsList.get(id);
            final int LOGS_LENGTH = userLogs.dateLogs.size();

            for(int i = 0; i < LOGS_LENGTH; ++i)
            {
                String date = userLogs.dateLogs.get(i);
                String time = userLogs.timeLogs.get(i);
                String rank = userLogs.rankLogs.get(i);
                String item = userLogs.itemLogs.get(i);
                String charactor = userLogs.charactorLogs.get(i);
                String mob = userLogs.mobLogs.get(i);
                String result = userLogs.resultLogs.get(i);


                String line = String.format("%s,%s,%s,%s,%s,%s,%s", date, time, rank, item, charactor, mob, result);
                writer.write(line);
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
