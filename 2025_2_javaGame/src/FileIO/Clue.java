package FileIO;

import java.util.Scanner;

public class Clue implements IFile
{
    @Override
    public void readFile(Scanner scan) {

    }

    @Override
    public void write(String content) {

    }

    @Override
    public String find(String keyword) {


        return "";
    }

    @Override
    public FileType getType() {
        return FileType.CLUE;
    }

    @Override
    public long getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(long index) {

    }
}
