package FileIO;

public class UserData
{
    private String name;
    private String id;
    private String pw;
    private int time;
    private int stage;
    private int rank;


    // Setter

    public UserData(){};

    public UserData(final String name, final String id, final String pw)
    {
        this.name = name;
        this.id = id;
        this.pw = pw;
    }

    public void setTimeAndStage(final int time, final int stage)
    {
        this.time = time;
    }


    // Getter

    public String getUserDataToOneString()
    {
        return String.format("%s %s %s %d %d %d", name, id, pw, time, stage, rank);
    }

    public int getTime()
    {
        return time;
    }

    public int getStage()
    {
        return stage;
    }


    // Functions

    public boolean isBetterThen(final UserData other)
    {
        if(this.stage > other.getStage()) { return true; }
        else if(this.stage == other.getStage() && this.time < other.getTime()) { return true; }
        return false;
    }
}