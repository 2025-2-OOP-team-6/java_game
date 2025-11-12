package Data;

import Logic.User;

import java.util.Currency;

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
    private ItemData    itemMgr;
    private AccountData accountMgr;

    private User currentUser;


    public DataManager()
    {
        userMgr = new UserData();
        itemMgr = new ItemData();
        accountMgr = new AccountData();

        itemMgr.readItemData();
        accountMgr.readAccountData();
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

    public User getCurrentUser()
    {
        return currentUser;
    }


    public void loadUser(final String userId)
    {
        currentUser = new User(userId);
        userMgr.readUserData(this.currentUser.getId());
    }

    public void storeAllDatas()
    {
        accountMgr.storeAccountData();
        userMgr.storeUserData(currentUser.getId());
    }
}