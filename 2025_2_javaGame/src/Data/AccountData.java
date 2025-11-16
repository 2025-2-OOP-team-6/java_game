//package Data;
//
//import java.io.IOException;
//import java.io.FileWriter;
//import java.io.FileReader;
//import java.io.BufferedWriter;
//import java.io.BufferedReader;
//
//import java.util.Set;
//import java.util.Map;
//import java.util.HashMap;
//
//public class AccountData {
//
//    // CONST
//    private final int ID_IDX = 0;
//    private final int PW_IDX = 1;
//    private final String ACCOUNT_FILE = "assets//files//account_file.csv";
//
//    // VARIABLE
//    private HashMap<String, String> accountHashMap = new HashMap<>();
//
//
//    // FUNCTIONS
//    public boolean matches(final String id, final String pw)
//    {
//        String storedPW = accountHashMap.get(id);
//
//        if(storedPW != null && storedPW.equals(pw))
//        {
//            return true;
//        }
//
//        return false;
//    }
//
//    public void insertData(final String id, final String pw)
//    {
//        accountHashMap.put(id, pw);
//    }
//
//    public String[] getIDList()
//    {
//        Set<String> keyList = accountHashMap.keySet();
//        String[] idList = keyList.toArray(new String[keyList.size()]);
//
//        return idList;
//    }
//
//    public void readAccountData()
//    {
//        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//
//                if(parts.length >= 2)
//                {
//                    String id = parts[ID_IDX];
//                    String pw = parts[PW_IDX];
//                    accountHashMap.put(id, pw);
//                }
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Error: can not open this file (" + ACCOUNT_FILE + ")" );
//        }
//        catch(NullPointerException e)
//        {
//            System.err.println("Error: Invailed file data format");
//        }
//    }
//
//    public void storeAccountData()
//    {
//        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE)))
//        {
//            for(Map.Entry<String, String> node : accountHashMap.entrySet())
//            {
//                String data = String.format("%s,%s,%d,%d", node.getKey(), node.getValue(),-1,-1);
//                writer.write(data);
//                writer.newLine();
//            }
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//            System.err.println("Error: Can not write data (" + ACCOUNT_FILE + ")");
//        }
//    }
//}
