package Logic;

import Data.LogData;
import Data.DataManager;

import java.util.List;
import java.util.ArrayList;

public class Suggest
{
    //OBJECTS
    private final LogData userLog;

    //VARIABLES
    private String[] suggestItems;
    private String[] suggestCharacters;

    public Suggest()
    {
        userLog = DataManager.getInstance().getLogMgr();
    }

    public String[] getSuggestedItems(final String id)
    {
        final String[] results = userLog.getResultLogs(id);
        final int RESULT_LIST_SIZE = results.length;

        List<Integer> itemRank = new ArrayList<>();


        for(int i = 0; i < RESULT_LIST_SIZE; ++i)
        {
            if(results[i].equals("win"))
            {
                //TODO : FIND MOST EFFICIENT WAY
                itemRank.add(i);
            }
        }

        return itemRank.toArray(new String[0]);
    }


    public String[] getSuggestedCharacter(final String id)
    {
        // TODO : BUILD THE RECOMMENDATION LOGIC

        return null;
    }
}
