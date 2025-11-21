package Data;

import Logic.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Duration;
import java.time.format.DateTimeFormatter;


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
    private static AnalysisData  ansMgr;
    private static LogData       logMgr;
    private static UserData      userMgr;
    private static ItemData      itemMgr;
    private static AccountData   accountMgr;
    private static CharactorData characMgr;

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

        logMgr = new LogData(userMgr, characMgr, itemMgr, accountMgr.getIDList());
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
        accountMgr.storeAccountData();
        userMgr.storeUserData(currentUser.getId());
    }
}