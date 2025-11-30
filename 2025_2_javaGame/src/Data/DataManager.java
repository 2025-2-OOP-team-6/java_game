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


    public DataManager()
    {
        
        diceMgr = new DiceData();
        itemMgr = new ItemData(diceMgr);
        userMgr = new UserData(itemMgr);
        enemyMgr = new EnemyData(diceMgr, itemMgr);
        
        
        
        final Player player = new Player("player", 5, diceMgr.get("first"));
        eventListener = new EventListener(player, logMgr, currentUser);
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
    	eventListener = new EventListener(player, logMgr, currentUser);
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
            ansMgr = new AnalysisData(logMgr, itemMgr, currentUser.getId());
        }

        return ansMgr;
    }

    public String[] getTotalRanks()
    {

        List<String> idList = new ArrayList<>(Arrays.asList(userMgr.getIDList().clone()));
        idList.sort((id1, id2) ->{
            int stage1 =userMgr.getStage(id1);
            int stage2 = userMgr.getStage(id2);

            if(stage1 == 0 && stage2 == 0) return 0;
            if(stage1 == 0) return 1;
            if(stage2 == 0) return -1;

      

            if(stage1 != stage2)
            {
                return Integer.compare(stage2, stage1);
            }

            return Integer.compare(stage2, stage1);
        });

        userMgr.updateRank(currentUser.getId(), idList.indexOf(currentUser.getId())+1);
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
}