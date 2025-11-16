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
    private CharactorData characMgr;

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

    public String[] getTotalRanks()
    {
        String[] idList =  accountMgr.getIDList();

        QuickSortUsers.quickSort(idList, 0, idList.length - 1, userMgr);

        return idList.clone();
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

    static class QuickSortUsers
    {
        public static int compareUser(String u1, String u2, UserData userMgr) {
            int rank1 = userMgr.getRank(u1);
            int rank2 = userMgr.getRank(u2);

            if (rank1 != rank2) {
                return rank1 - rank2;
            } else {
                int time1 = userMgr.getTime(u1);
                int time2 = userMgr.getTime(u2);
                return time1 - time2;
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