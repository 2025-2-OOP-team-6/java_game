package Data;

import Logic.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


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
    private LogData       logMgr;
    private UserData      userMgr;
    private ItemData      itemMgr;
    private AccountData   accountMgr;
    private CharactorData characMgr;
    private SuggestData   sugMgr;

    private User currentUser;
    private String[] rankList;

    public DataManager()
    {
        userMgr = new UserData();
        itemMgr = new ItemData();
        accountMgr = new AccountData();
        characMgr = new CharactorData();

        accountMgr.readAccountData();
        characMgr.readCharactorData();

        userMgr.readUserData(accountMgr.getIDList());
        itemMgr.readItemData();

        logMgr = new LogData(userMgr, characMgr, itemMgr);
        sugMgr = new SuggestData(logMgr, itemMgr, characMgr);
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
    public AccountData getAccountMgr()
    {
        return accountMgr;
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

    public LogData getLogMgr() {return logMgr;}

    public SuggestData getSugMgr() {return sugMgr;}

    public String[] getTotalRanks()
    {
        List<String> idList = new ArrayList<>(Arrays.asList(accountMgr.getIDList().clone()));

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

            int time1 = Integer.parseInt(log1.getTime());
            int time2 = Integer.parseInt(log2.getTime());

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
        accountMgr.storeAccountData();
        userMgr.storeUserData(currentUser.getId());
    }
}