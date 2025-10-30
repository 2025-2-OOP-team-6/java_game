package FileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public interface IFile
{
    enum FileType
    {
        CLUE, LOG, LINE, NON
    }

    ArrayList<String> content = null;
    long identifier = 0;
    // -- put identifier --

    public void readFile(Scanner scan);
    public void write(final String content);
    public String find(final String keyword);
    public FileType getType();
    public long getIdentifier();
    public void setIdentifier(long index);
}
