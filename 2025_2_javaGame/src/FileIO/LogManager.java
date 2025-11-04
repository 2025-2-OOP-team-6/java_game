package FileIO;

import java.util.ArrayList;

public class LogManager
{
    private Phase currentPhase;
    private ArrayList<LogFile> logFiles = new ArrayList<>();

    public void write(final long lineIndex, final long clueIndex)
    {
        LogFile log = new LogFile();
        log.setData(currentPhase, lineIndex, clueIndex);
        logFiles.add(log);
    }

    public String getBackup(final Phase phase)
    {
        for(LogFile log : logFiles)
        {
            if(log.getPhase() == phase)
            {
                return log.getData();
            }
        }

        return null;
    }

    public ArrayList<String> getAllLog(final Phase phase)
    {
        ArrayList<String> logContents = new ArrayList<>();

        for(LogFile log : logFiles)
        {
            String content = log.getData();
            logContents.add(content);
        }

        return logContents;
    }

    public void setCurrentPhase(final Phase phase)
    {
        currentPhase = phase;
    }

    class LogFile
    {
        Phase phase;
        long lineIndex;
        long clueIndex;

        public Phase getPhase(){
            return phase;
        }

        public String getData()
        {
            String s = phase.toString() + " " + String.valueOf(lineIndex) + " " + String.valueOf(clueIndex);
            return s;
        }

        public void setData(final Phase phase, final long lineIndex, final long clueIndex)
        {
            this.phase = phase;
            this.lineIndex = lineIndex;
            this.clueIndex = clueIndex;
        }
    }
}