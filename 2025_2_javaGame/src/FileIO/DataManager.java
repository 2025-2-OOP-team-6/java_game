package FileIO;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class DataManager
{
    enum DataType
    {
        USER,
        ITEM
    }

    private final String USER_FILE = "user_file.csv";
    private final String ITEM_FILE = "item_file.csv";

    HashMap<String, UserData> userDataHashMap = new HashMap<>();
    ArrayList<String> itemList = new ArrayList<>();

    public void readData(final DataType type, final String name)
    {
        switch(type)
        {
            case USER:
                final String FILE_NAME = name + "_" + USER_FILE;
                Scanner fileIn = null;

                try
                {
                    fileIn = new Scanner(new File(FILE_NAME));
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                    assert(true): "Invailed file name";
                }

                while(fileIn.hasNext())
                {

                }


                break;
            case ITEM:


                break;
            default:
                assert (true): "Error: Invailed file type";
        }
    }

    public void writeData(final DataType type, String data)
    {
        String[] parts = data.split(" ");
        String storeData = String.format("%s,%s", parts[0], parts[1]);

    }


    public void putUserData(final String name, final String id, final String pw)
    {

    }




}