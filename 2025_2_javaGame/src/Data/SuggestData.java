package Data;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.ArrayList;

public class SuggestData
{
    private class DataCon
    {
        int index;
        int usedCount;
    }

    //OBJECTS
    private final LogData logMgr;
    private final ItemData itemMgr;
    private final CharactorData charMgr;


    public SuggestData(LogData logMgr, ItemData itemMgr, CharactorData charMgr)
    {
        this.logMgr = logMgr;
        this.itemMgr = itemMgr;
        this.charMgr = charMgr;
    }



    public String[] getSuggestItems(final String id)
    {
        final String[] itemList = itemMgr.getItemNames();
        final int[] itemUsage   = logMgr.getItemGraphData(id);

        final int ITEM_LIST_LENGTH = itemList.length;

        String[] sugItemList = new String[ITEM_LIST_LENGTH];
        List<DataCon> reorderedList = new ArrayList<>();


        for(int i = 0; i < ITEM_LIST_LENGTH; ++i)
        {
            DataCon con = new DataCon();
            con.index = i;
            con.usedCount = itemUsage[i];

            reorderedList.add(con);
        }

        reorderedList.sort((con1, con2) ->
                Integer.compare(con2.usedCount, con1.usedCount)
        );

        for(int i = 0; i < ITEM_LIST_LENGTH; ++i)
        {
            DataCon con = reorderedList.get(i);
            sugItemList[i] = itemList[con.index];
        }

        return sugItemList;
    }


    public String[] getSuggestedCharacter(final String id)
    {
        final String[] charList = charMgr.getjobList();
        final int[] charUsgae = logMgr.getCharGraphData(id);

        final int CHAR_LIST_LENGTH = charList.length;

        String[] sugCharList = new String[CHAR_LIST_LENGTH];
        List<DataCon> reorder = new ArrayList<>();

        for(int i = 0; i < CHAR_LIST_LENGTH; ++i)
        {
            DataCon con = new DataCon();
            con.index = i;
            con.usedCount = charUsgae[i];
        }

        reorder.sort((con1, con2) ->
            Integer.compare(con2.usedCount, con1.usedCount)
        );

        for(int i = 0; i < CHAR_LIST_LENGTH; ++i)
        {
            DataCon con = reorder.get(i);
            sugCharList[i] = charList[con.index];
        }

        return sugCharList;
    }
}
