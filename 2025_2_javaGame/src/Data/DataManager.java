package Data;

import Logic.User;

import java.util.Currency;

import GameLogic.EventListener;
import GameLogic.GameManager;
import GameLogic.Player;

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
    private CharactorData characMgr;
    private EnemyData 	enemyMgr;
    private EventListener eventListener;

    

    private User currentUser;
    private String[] rankList;

	

    public DataManager()
    {
        userMgr = new UserData();
        diceMgr = new DiceData();
        itemMgr = new ItemData(diceMgr);
        enemyMgr = new EnemyData(diceMgr, itemMgr);
        characMgr = new CharactorData();
         
        
        final Player player = new Player("player", 5, diceMgr.get("first"));
        eventListener = new EventListener(player);
        characMgr.readCharactorData();

        userMgr.readUserData();
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
    
    public CharactorData getCharactorMgr()
    {
        return characMgr;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    
    public void initEventListener(Player player) {
    	eventListener = new EventListener(player);
    }
    
    public String[] getTotalRanks()
    {
        String[] idList =  userMgr.getIDList();

        QuickSortUsers.quickSort(idList, 0, idList.length - 1, userMgr);

        return idList.clone();
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