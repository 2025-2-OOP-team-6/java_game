package Data;

import Logic.User;

import java.util.List;
import java.util.ArrayList;

public class AnalysisData
{
    private class UsageData
    {
        int index;
        int usedCount;

        UsageData(final int index, final int usedCount)
        {
            this.index = index;
            this.usedCount = usedCount;
        }
    }

    //OBJECTS
    private final LogData logMgr;
    private final ItemData itemMgr;
    

    private String[] dateArray;
    private List<Integer> winGraph;
    private List<Integer> loseGraph;
    private List<Integer> playTimeGraph;


    public AnalysisData(LogData logMgr, ItemData itemMgr,  final String id)
    {
        this.logMgr = logMgr;
        this.itemMgr = itemMgr;

        dateArray = logMgr.getDateArray(id);
    }


    private String[] sortByUsage(final String[] names, final int[] usage)
    {
        final int NAME_LENGTH = names.length;

        if(usage == null)
        {
            return names.clone();
        }

        List<UsageData> list = new ArrayList<>(NAME_LENGTH);

        for(int i = 0; i < NAME_LENGTH; ++i)
        {
            list.add(new UsageData(i, usage[i]));
        }

        list.sort((node1, node2) -> Integer.compare(node2.usedCount, node1.usedCount));

        String[] result = new String[NAME_LENGTH];

        for(int i = 0; i < NAME_LENGTH; ++i)
        {
            result[i] = names[list.get(i).index];
        }

        return result;
    }


    public String[] getSuggestItems(final String id)
    {
        final String[] ITEM_LIST = itemMgr.getItemNames().clone();
        //final int[] ITEM_USAGE = logMgr.getItemGraphData(id).clone();

        String[] itemList = new String[3];
        itemList[0] = ITEM_LIST[0];
        itemList[1] = ITEM_LIST[1];
        itemList[2] = ITEM_LIST[2];
        return itemList;
    }

//    public String[] getSuggestedCharacter(final String id)
//    {
//        final String[] CHAR_LIST = charMgr.getjobList().clone();
//        final int[] CHAR_USAGE = logMgr.getCharGraphData(id).clone();
//
//        return sortByUsage(CHAR_LIST, CHAR_USAGE);
//    }

    public void anlysisPlayData(final String id)
    {
        winGraph = new ArrayList<>();
        loseGraph = new ArrayList<>();
        playTimeGraph = new ArrayList<>();
        
        for (int i = 0; i < dateArray.length; i++) {
            winGraph.add(0);
            loseGraph.add(0);
            playTimeGraph.add(0);
        }

        for (int i = 0; i < dateArray.length; i++) {
            List<LogData.LogCon> logs = logMgr.getLogByDate(id, dateArray[i]);

            for (LogData.LogCon node : logs) {

                playTimeGraph.set(i, playTimeGraph.get(i) + 1);

                if ("Win".equals(node.getResult())) {
                    winGraph.set(i, winGraph.get(i) + 1);
                } else if ("Lose".equals(node.getResult())) {
                    loseGraph.set(i, loseGraph.get(i) + 1);
                }
            }
        }
        
    }

    public List<Integer> getWinGraph()
    {
        return winGraph;
    }
    public List<Integer> getLoseGraph()
    {
        return loseGraph;
    }
    public List<Integer> getPlayTimeGraph()
    {
        return playTimeGraph;
    }
}
