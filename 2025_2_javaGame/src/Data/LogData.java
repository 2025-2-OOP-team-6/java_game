package Data;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.time.Duration;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


public class LogData
{
    public final class LogCon {
        private final LocalDate date; // yyyy - mm - dd
        private final LocalTime time; // hh : mm : ss
        private final String rank;
        private final String item;
        private final String character;
        private final String mob;
        private final String result;

        public LogCon(LocalDate date, LocalTime time, String rank, String item, String character, String mob, String result) {
            this.date = date;
            this.time = time;
            this.rank = rank;
            this.item = item;
            this.character = character;
            this.mob = mob;
            this.result = result;
        }

        public String getDate() {

            String dateString = date.format(DATE_FORMAT);

            return dateString;
        }

        public LocalTime getTime() {
            return time;
        }

        public String getRank() {return rank;}

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
        ArrayList<String> dateList = new ArrayList<>();
    }

    //CONST
    private final static String SUFFIX = "_logData.csv";
    private final static String PREFIX = "assets//files//";

    private final static String DUMMY_LOG_ENTRY = "2000-01-01,00:00:00,0,DefaultItem,DefaultChar,DefaultMob,Win";

    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final static int MINIUM_DATA_SIZE  = 7;
    private final static int DATE_IDX          = 0;
    private final static int TIME_IDX          = 1;
    private final static int RANK_IDX          = 2;
    private final static int ITEM_IDX          = 3;
    private final static int CHARACTER_IDX     = 4;
    private final static int MOB_IDX           = 5;
    private final static int RESULT_IDX        = 6;

    //VARIABLES
    private HashMap<String, Logs> userLogsList;

    //DATA MANAGERS
    private final UserData userMgr;
    private final ItemData itemMgr;
 


    public LogData(UserData userMgr, ItemData itemMgr, final String[] userIdList)
    {
        userLogsList = new HashMap<>();

        this.userMgr = userMgr;
        
        this.itemMgr = itemMgr;

        readLogData(userIdList);
    }


    // ------------- Getters ---------------

    public String[] getDateArray(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.dateList.isEmpty())
        {
            return node.dateList.toArray(new String[0]);
        }

        return null;
    }

    public String getBestRank(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node == null || node.logList.isEmpty())
        {
            return "N/A";
        }

        int bestRank = Integer.MIN_VALUE;

        for(LogCon log : node.logList)
        {
            int currentRank = Integer.parseInt(log.getRank());
            if(currentRank > bestRank)
            {
                bestRank = currentRank;
            }
        }

        return (bestRank == Integer.MIN_VALUE) ? "N/A" : String.valueOf(bestRank);
    }

    public int getPlayTime(final String id)
    {
        int playTime = 0;



        return playTime;
    }

    public LogCon getLatestLog(final String id)
    {
        Logs node = userLogsList.get(id);

        if(node != null && !node.logList.isEmpty())
        {
            return node.logList.get(node.logList.size() - 1);
        }

        return null;
    }

    public List<LogCon> getLogByDate(final String id, final String date)
    {
        Logs node = userLogsList.get(id);
        List<LogCon> todayLogs = new ArrayList<>();

        if(node != null && !node.logList.isEmpty())
        {
            for(LogCon log : node.logList)
            {
                if(log.getDate().equals(date))
                {
                    todayLogs.add(log);
                }
            }
        }

        return todayLogs;
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
                String timeString = node.logList.get(i).getTime().format(TIME_FORMAT);
                timeLogList[i] = timeString;
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
//
//    public int[] getItemGraphData(final String id)
//    {
//        Logs node = userLogsList.get(id);
//
//        if(node != null && !node.logList.isEmpty())
//        {
//            //final int ITEM_LIST_LENGTH = itemMgr.getItemNames().length;
//            final int ITEM_LOG_LENGTH = node.logList.size();
//
//            final String[] itemList = itemMgr.getItemNames();
//            int[] itemUseCount = new int[ITEM_LIST_LENGTH];
//
//            for(int i = 0; i < ITEM_LIST_LENGTH; ++i)
//            {
//                itemUseCount[i] = 0;
//
//                for(int j = 0; j < ITEM_LOG_LENGTH; ++j)
//                {
//                    if(itemList[i].equals(node.logList.get(j).getItem()))
//                    {
//                        itemUseCount[i] += 1;
//                    }
//                }
//            }
//
//            return itemUseCount;
//        }
//
//        return null;
//    }

//    public int[] getCharGraphData(final String id)
//    {
//        Logs node = userLogsList.get(id);
//
//        if(node != null && !node.logList.isEmpty())
//        {
//            final String[] charList = charMgr.getjobList();
//            final int CHARACTER_LIST_LENGTH = charList.length;
//            final int CHARACTER_LOG_LENGTH = node.logList.size();
//
//            int[] charUseCount = new int[CHARACTER_LIST_LENGTH];
//
//            for(int i = 0; i < CHARACTER_LIST_LENGTH; ++i)
//            {
//                charUseCount[i] = 0;
//
//                for(int j = 0; j < CHARACTER_LOG_LENGTH; ++j)
//                {
//                    if(charList[i].equals(node.logList.get(j).getCharacter()))
//                    {
//                        charUseCount[i] += 1;
//                    }
//                }
//            }
//
//            return charUseCount;
//        }
//
//        return null;
//    }

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

    public int getBattleCnt(final String id) {
        return getResultLogs(id).length;
    }

    public int getWinCnt(final String id) {
        String[] results = getResultLogs(id);
        int winCnt = 0;

        for (String r : results) {
            if (r.equals("WIN")) {
                winCnt++;
            }
        }
        return winCnt;
    }

    public int getLoseCnt(final String id) {
        String[] results = getResultLogs(id);
        int loseCnt = 0;

        for (String r : results) {
            if (r.equals("LOSE")) {
                loseCnt++;
            }
        }
        return loseCnt;
    }

    public double getWinRate(String userId) {
        int wins = getWinCnt(userId);
        int battles = getBattleCnt(userId);

        if (battles == 0) return 0;
        return (int)((wins / (double)battles) * 100);
    }


    // ----------------- Functions ---------------------

    private void addNewUser(final String id)
    {
        Logs newLog = new Logs();

        String[] parts = DUMMY_LOG_ENTRY.split(",");

        LogCon newCon = new LogCon(
                LocalDate.now(),
                LocalTime.now(),
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
                LocalDate.now(),
                LocalTime.now(),
                parts[RANK_IDX],
                parts[ITEM_IDX],
                parts[CHARACTER_IDX],
                parts[MOB_IDX],
                parts[RESULT_IDX]
        );

        target.logList.add(newLog);
    }

    private void readLogData(final String[] userIdList)
    {
        String newDate = "";

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

                    LocalDate date;
                    LocalTime time;

                    time = LocalTime.parse(parts[TIME_IDX], TIME_FORMAT);
                    date = LocalDate.parse(parts[DATE_IDX], DATE_FORMAT);

                    if(!parts[DATE_IDX].equals(newDate))
                    {
                        newDate = parts[DATE_IDX];
                        newLogs.dateList.add(parts[DATE_IDX]);
                    }

                    LogCon newCon = new LogCon(
                            date,
                            time,
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
