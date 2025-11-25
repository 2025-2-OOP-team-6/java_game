package Data;

import Logic.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Duration;
import java.time.format.DateTimeFormatter;


import GameLogic.EventListener;
import GameLogic.GameManager;
import GameLogic.Player;
/*
 * /*
 * hashMap<enemy, Integer> //적, 조우횟수
 * 
 */

public class DataManager
{
    //ENUM
    enum Type
    {
        ACCOUNT,
        ITEMS_LIST,
        USER_DATA
    }

    //STATIC
    private static DataManager instance;

    //VARIABLES
    private UserData    userMgr;
    private DiceData 	diceMgr;
    private ItemData    itemMgr;
    private EnemyData 	enemyMgr;
    private EventListener eventListener;
    private LogData 	logMgr;
    private AnalysisData 	ansMgr;
    private User currentUser;
    private String[] rankList;


    public DataManager()
    {
        userMgr = new UserData();
        diceMgr = new DiceData();
        itemMgr = new ItemData(diceMgr);
        enemyMgr = new EnemyData(diceMgr, itemMgr);
        
        
        
        final Player player = new Player("player", 5, diceMgr.get("first"));
        eventListener = new EventListener(player);
        //characMgr.readCharactorData();

        userMgr.readUserData();

        logMgr = new LogData(userMgr, itemMgr, userMgr.getIDList());
    }

    public static DataManager getInstance()
    {
        if(instance == null)
        {
            instance = new DataManager();
        }
         
        return instance;
    }


    //FUNCTIONS

    // - Getters
    public EventListener getEventListener() {
		return eventListener;
	}
    public EnemyData getEnemyMgr()
    {
        return enemyMgr;
    }

    public DiceData getDiceMgr()
    {
        return diceMgr;
    }
    public ItemData getItemMgr()
    {
        return itemMgr;
    }

    public UserData getUserMgr()
    {
        return userMgr;
    }
    

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void initEventListener(Player player) {
    	eventListener = new EventListener(player);
    }
    


    // NOTICE : USE THESE FUNCTIONS AFTER GENERATE OTHER MGR

    public LogData getLogMgr()
    {
        return logMgr;
    }

    public AnalysisData getAnsMgr()
    {
        if(ansMgr == null)
        {
            ansMgr = new AnalysisData(logMgr, itemMgr, characMgr, currentUser.getId());
        }

        return ansMgr;
    }

    public String[] getTotalRanks()
    {

        List<String> idList = new ArrayList<>(Arrays.asList(userMgr.getIDList().clone()));
        idList.sort((id1, id2) ->{
            LogData.LogCon log1 = logMgr.getLatestLog(id1);
            LogData.LogCon log2 = logMgr.getLatestLog(id2);

            if(log1 == null && log2 == null) return 0;
            if(log1 == null) return 1;
            if(log2 == null) return -1;

            int rank1 = Integer.parseInt(log1.getRank());
            int rank2 = Integer.parseInt(log2.getRank());

            if(rank1 != rank2)
            {
                return Integer.compare(rank1, rank2);
            }


            int time1 = log1.getTime().toSecondOfDay();
            int time2 = log2.getTime().toSecondOfDay();

            return Integer.compare(time1, time2);
        });

        return idList.toArray(new String[0]);
    }

    public void loadUser(final String userId)
    {
        currentUser = new User(userId);
    }

    public void storeAllDatas()
    {
        userMgr.storeUserData();
    }
    static class QuickSortUsers
    {
        public static int compareUser(String u1, String u2, UserData userMgr) {
            int rank1 = userMgr.getRank(u1);
            int rank2 = userMgr.getRank(u2);
            if (rank1 == -1) {
            	return 1;
            }
            if (rank2 == -1) {
            	return -1;
            }
            if (rank1 != rank2) {
                return rank1 - rank2;
            } else {
                int stage1 = userMgr.getStage(u1);
                int stage2 = userMgr.getStage(u2);
                return stage2 - stage1;
            }
        }

        public static void quickSort(String[] users, int left, int right, UserData userMgr) {
            if (left < right) {
                int pivotIndex = partition(users, left, right, userMgr);
                quickSort(users, left, pivotIndex - 1, userMgr);
                quickSort(users, pivotIndex + 1, right, userMgr);
            }
        }

        private static int partition(String[] users, int left, int right, UserData userMgr) {
            String pivot = users[right];
            int i = left - 1;

            for (int j = left; j < right; j++) {
                if (compareUser(users[j], pivot, userMgr) <= 0) {
                    i++;
                    swap(users, i, j);
                }
            }
            swap(users, i + 1, right);
            return i + 1;
        }

        private static void swap(String[] users, int i, int j) {
            String temp = users[i];
            users[i] = users[j];
            users[j] = temp;
        }
    }
}