package Login;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.util.Map;
import java.util.HashMap;

public class LoginLogic {
    private final String ACCOUNT_FILE = "..//account_file.csv";
    private HashMap<String, String> accountHashMap = new HashMap<>();

    public boolean matches(final String id, final String pw)
    {
        String storedPW = accountHashMap.get(id);

        if(storedPW != null && storedPW.equals(pw))
        {
            return true;
        }

        return false;
    }

    public void insertData(final String id, final String pw)
    {
        accountHashMap.put(id, pw);
    }

    public void readAccountData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts.length >= 2)
                {
                    String id = parts[0];
                    String pw = parts[1];
                    accountHashMap.put(id, pw);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: can not open this file (" + ACCOUNT_FILE + ")" );
        }
        catch(NullPointerException e)
        {
            System.err.println("Error: Invailed file data format");
        }
    }

    public void storeAccountData()
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE)))
        {
            for(Map.Entry<String, String> node : accountHashMap.entrySet())
            {
                String data = String.format("%s,%s", node.getKey(), node.getValue());
                writer.write(data);
                writer.newLine();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.err.println("Error: Can not write data (" + ACCOUNT_FILE + ")");
        }
    }
}
