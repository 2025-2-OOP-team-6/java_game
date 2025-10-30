package FileIO;

import java.util.ArrayList;
import Util.Phase;

public class FileManager
{
    ArrayList<IFile> clueList = new ArrayList<>();
    ArrayList<IFile> lineList = new ArrayList<>();
    LogManager logManager = new LogManager();

    public void restoreFiles(final Phase phase)
    {
        String backup = logManager.getBackup(phase);
        String[] parts = backup.split(" ");
        long lineIndex = Integer.parseInt(parts[1]);
        long clueIndex = Integer.parseInt(parts[2]);

        // -- set clue and line index

        logManager.setCurrentPhase(phase);
    }

    public ArrayList<String> getAllLog(final Phase phase)
    {
        return logManager.getAllLog(phase);
    }

    public String getNextLine(final Phase phase)
    {
        String nextLine = null;

        return nextLine;
    }

    public boolean isOpenableClue(final Phase phase)
    {

        return true;
    }

    public String getClue(final Phase phase)
    {
        String clueData = null;
        return clueData;
    }
}
