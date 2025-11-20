package Data;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


public class LogData
{
    public final class LogCon {
        private final String date;
        private final String time;
        private final String rank;
        private final String item;
        private final String character;
        private final String mob;
        private final String result;

        public LogCon(String date, String time, String rank, String item, String character, String mob, String result) {
            this.date = date;
            this.time = time;
            this.rank = rank;
            this.item = item;
            this.character = character;
            this.mob = mob;
            this.result = result;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getRank() {
            return rank;
        }

        public String getItem() {
            return item;
        }

        public String getCharacter() {
            return character;
        }

        public String getMob() {
            return mob;
        }

        public String getResult() {
            return result;
        }
    }

    private class Logs
    {
        ArrayList<LogCon> logList = new ArrayList<>();
    }

    //CONST
    private final String SUFFIX = "_logData.csv";
    private final String PREFIX = "..//assets//files//";

    private final String DUMMY_LOG_ENTRY = "2000-01-01,00:00:00,0,DefaultItem,DefaultChar,DefaultMob,Win";

    private final int MINIUM_DATA_SIZE  = 7;
    private final int DATE_IDX          = 0;
    private final int TIME_IDX          = 1;
    private final int RANK_IDX          = 2;
    private final int ITEM_IDX          = 3;
    private final int CHARACTER_IDX     = 4;
    private final int MOB_IDX           = 5;
    private final int RESULT_IDX        = 6;

    //VARIABLES
    private HashMap<String, Logs> userLogsList;

    //DATA MANAGERS
    private UserData userMgr;
    private ItemData itemMgr;
    private CharactorData charMgr;


    public LogData(UserData userMgr, CharactorData charMgr, ItemData itemMgr)
    {
        userLogsList = new HashMap<>();

        this.userMgr = userMgr;
        this.charMgr = charMgr;
        this.itemMgr = itemMgr;

        readLogData();
    }


    // ------------- Getters ---------------

    public LogCon getLatestLog(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.logList.isEmpty())
        {
            return node.logList.get(node.logList.size() - 1);
        }

        return null;
    }


    public String[] getDataLogs(final String id)
    {
         Logs node = userLogsList.get(id);

         if(node != null && !node.logList.isEmpty())
         {
             final int LOG_LIST_SIZE = node.logList.size();
             String[] dateLogList = new String[LOG_LIST_SIZE];

             for(int i = 0; i < LOG_LIST_SIZE; ++i)
             {
                dateLogList[i] = node.logList.get(i).getDate();
             }

             return dateLogList;
         }

         return null;
    }

    public String[] getTimeLogs(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.logList.isEmpty())
        {
            final int LOG_LIST_SIZE = node.logList.size();
            String[] timeLogList = new String[LOG_LIST_SIZE];

            for(int i = 0; i < LOG_LIST_SIZE; ++i)
            {
                timeLogList[i] = node.logList.get(i).getTime();
            }

            return timeLogList;
        }

        return null;
    }

    public String[] getRankGraphData(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.logList.isEmpty())
        {
            final int LOG_LIST_SIZE = node.logList.size();
            String[] RankLogList = new String[LOG_LIST_SIZE];

            for(int i = 0; i < LOG_LIST_SIZE; ++i)
            {
                RankLogList[i] = node.logList.get(i).getRank();
            }

            return RankLogList;
        }

        return null;
    }

    public int[] getItemGraphData(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.logList.isEmpty())
        {
            final int ITEM_LIST_LENGTH = itemMgr.getItemNames().length;
            final int ITEM_LOG_LENGTH = node.logList.size();

            final String[] itemList = itemMgr.getItemNames();
            int[] itemUseCount = new int[ITEM_LIST_LENGTH];

            for(int i = 0; i < ITEM_LIST_LENGTH; ++i)
            {
                itemUseCount[i] = 0;

                for(int j = 0; j < ITEM_LOG_LENGTH; ++j)
                {
                    if(itemList[i].equals(node.logList.get(j).getItem()))
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

        if(node != null && !node.logList.isEmpty())
        {
            final String[] charList = charMgr.getjobList();
            final int CHARACTER_LIST_LENGTH = charList.length;
            final int CHARACTER_LOG_LENGTH = node.logList.size();

            int[] charUseCount = new int[CHARACTER_LIST_LENGTH];

            for(int i = 0; i < CHARACTER_LIST_LENGTH; ++i)
            {
                charUseCount[i] = 0;

                for(int j = 0; j < CHARACTER_LOG_LENGTH; ++j)
                {
                    if(charList[i].equals(node.logList.get(j).getCharacter()))
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

        if(node != null && !node.logList.isEmpty())
        {
            // TODO : PUT MOB MANAGER
            final int MOB_LIST_LENGTH = 1;
            final int MOB_LOG_LENGTH = node.logList.size();
            final String[] mobList = null;

            int[] mobUseCount = new int[MOB_LIST_LENGTH];

            for(int i = 0; i < MOB_LIST_LENGTH; ++i)
            {
                mobUseCount[i] = 0;

                for(int j = 0; j < MOB_LOG_LENGTH; ++j)
                {
                    if(mobList[i].equals(node.logList.get(i).getMob()))
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

        if(node != null && !node.logList.isEmpty())
        {
            final int LOG_LIST_SIZE = node.logList.size();
            String[] resultLogList = new String[LOG_LIST_SIZE];

            for(int i = 0; i < LOG_LIST_SIZE; ++i)
            {
                resultLogList[i] = node.logList.get(i).getResult();
            }

            return resultLogList;
        }

        return null;
    }



    // ----------------- Functions ---------------------

    private void addNewUser(final String id)
    {
        Logs newLog = new Logs();

        String[] parts = DUMMY_LOG_ENTRY.split(",");

        LogCon newCon = new LogCon(
                parts[DATE_IDX],
                parts[TIME_IDX],
                parts[RANK_IDX],
                parts[ITEM_IDX],
                parts[CHARACTER_IDX],
                parts[MOB_IDX],
                parts[RESULT_IDX]
        );

        newLog.logList.add(newCon);

        userLogsList.put(id, newLog);
    }

    public void insertLog(final String id, final String logString)
    {
        Logs target = userLogsList.get(id);
        final String parts[] = logString.split(" ");

        assert(target != null) : "Error: Invalid user id" + id + "log insertion failure";
        assert(parts.length >= MINIUM_DATA_SIZE) : "Error: not enough parameters";

        LogCon newLog = new LogCon(
                parts[DATE_IDX],
                parts[TIME_IDX],
                parts[RANK_IDX],
                parts[ITEM_IDX],
                parts[CHARACTER_IDX],
                parts[MOB_IDX],
                parts[RESULT_IDX]
        );

        target.logList.add(newLog);
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
                continue;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
                String line;

                Logs newLogs = new Logs();

                while((line = br.readLine()) != null)
                {
                    String[] parts = line.split(",");

                    assert(parts.length >= MINIUM_DATA_SIZE) : "Error: Invaild argument";

                    LogCon newCon = new LogCon(
                            parts[DATE_IDX],
                            parts[TIME_IDX],
                            parts[RANK_IDX],
                            parts[ITEM_IDX],
                            parts[CHARACTER_IDX],
                            parts[MOB_IDX],
                            parts[RESULT_IDX]
                    );

                    newLogs.logList.add(newCon);
                }

                userLogsList.put(userId, newLogs);
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
            final int LOGS_LENGTH = userLogs.logList.size();

            for(LogCon con : userLogs.logList)
            {
                String line = String.format(
                        "%s,%s,%s,%s,%s,%s,%s",
                        con.getDate(),
                        con.getTime(),
                        con.getRank(),
                        con.getItem(),
                        con.getCharacter(),
                        con.getMob(),
                        con.getResult()
                );
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
