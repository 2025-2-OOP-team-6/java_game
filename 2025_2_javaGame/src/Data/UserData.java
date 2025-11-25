package Data;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

/*
 * User user
 * user.hashMap<enemy, Integer> //적, 조우횟수
 * user.hashMap<enemy, Integer> //stage, 횟수
 * 
 */
public class UserData {
    // INNER CLASS
<<<<<<< HEAD
    private class Info
    {
    	String id = "";
    	String pw = "";
        int clearStage = -1;
        int clearRank = -1;
=======
    private class Info {
        int clearTime = 0;
        int clearRank = 0;
        int coin = 0;
        String inventory = "";
        String profileImage = "default_profile.png";
>>>>>>> refs/remotes/origin/MyPage
    }

<<<<<<< HEAD
    //CONST
    private final int ID_IDX = 0;
    private final int PW_IDX = 1;
    private final int STAGE_IDX = 2;
    private final int RANK_IDX = 3;
    private final String ACCOUNT_FILE = "assets//files//account_file.csv";
=======
    // CONST
    private final int TIME_IDX = 0;
    private final int RANK_IDX = 1;
    private final int COIN_IDX = 2;
    private final int INVEN_IDX = 3;
    private final int PROFILE_IDX = 4;
    private final int MAX_CHOOSE_SIZE = 3;
    private final String PREFIX = "assets//files//";
    private final String SUFFIX = "_file.csv";
>>>>>>> refs/remotes/origin/MyPage

    // VARIABLES
    private HashMap<String, Info> userHashMap;
    

<<<<<<< HEAD
    public String[] getIDList()
    {
        Set<String> keyList = userHashMap.keySet();
        String[] idList = keyList.toArray(new String[2]);
=======
    // FUNCTIONS

    // ------------------ Getters -----------------
    public String[] getInventory(final String id) {
        String[] inventory;
        Info userInfo = userHashMap.get(id);
>>>>>>> refs/remotes/origin/MyPage

<<<<<<< HEAD
        return idList;
    }


    public boolean matches(final String id, final String pw)
    {
    	if (pw == null) return false;
    	readUserData();
        Info storedUser = userHashMap.get(id);
        if(storedUser != null && storedUser.pw.equals(pw))
        {
            return true;
=======
        if (userInfo != null && userInfo.inventory != null) {
            String invenString = userInfo.inventory;
            String[] parts = invenString.split(" ");

            inventory = parts;

            return inventory;
>>>>>>> refs/remotes/origin/MyPage
        }

        return false;
    }
<<<<<<< HEAD
    
    public int getStage(final String id)
    {
=======

    public String[] getChoosedCharactor() {
        return choosedCharactor;
    }

    public int getCoin(final String id) {
>>>>>>> refs/remotes/origin/MyPage
        Info userInfo = userHashMap.get(id);

<<<<<<< HEAD
        if(userInfo != null && userInfo.clearStage != -1)
        {
            return userInfo.clearStage;
=======
        if (userInfo != null && userInfo.coin != 0) {
            return userInfo.coin;
>>>>>>> refs/remotes/origin/MyPage
        }

<<<<<<< HEAD
        return -1;
=======
        return 0;
    }

    public int getTime(final String id) {
        Info userInfo = userHashMap.get(id);

        if (userInfo != null && userInfo.clearTime != 0) {
            return userInfo.clearTime;
        }

        return 0;
>>>>>>> refs/remotes/origin/MyPage
    }

    public int getRank(final String id) {
        Info userInfo = userHashMap.get(id);

<<<<<<< HEAD
        if(userInfo != null && userInfo.clearRank != -1)
        {
=======
        if (userInfo != null && userInfo.clearRank != 0) {
>>>>>>> refs/remotes/origin/MyPage
            return userInfo.clearRank;
        }

        return -1;
    }

<<<<<<< HEAD
=======
    public String getProfileImage(String id) {
        Info userInfo = userHashMap.get(id);

        if (userInfo != null && userInfo.profileImage != null) {
            return "assets/images/userProfiles/" + userInfo.profileImage;
        }
        return "assets/images/userProfiles/default.png";
    }

    // ----------------------- Setters -------------------------
>>>>>>> refs/remotes/origin/MyPage

<<<<<<< HEAD
    public void updateStage(final String id, final int stage)
    {
=======
    public void updateChoosedCharactor(final String[] list) {
        if (list != null)
            choosedCharactor = list;
    }

    public void addNewUser(final String id) {
        Info newUser = new Info();
        userHashMap.put(id, newUser);
        storeUserData(id);
    }

    public void addNewItem(final String id, final String[] items) {
>>>>>>> refs/remotes/origin/MyPage
        Info userInfo = userHashMap.get(id);
<<<<<<< HEAD
        userInfo.clearStage = stage;
=======
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < items.length; ++i) {
            sb.append(" ").append(items[i]);
        }

        userInfo.inventory += sb.toString();
        storeUserData(id);
    }

    public void dropItem(final String id, String[] drops) {
        Info userInfo = userHashMap.get(id);
        String invenList = userInfo.inventory;

        for (int i = 0; i < drops.length; ++i) {
            invenList = invenList.replace((drops[i] + " "), "");
        }

        userInfo.inventory = invenList;
    }

    public void updateTime(final String id, final int time) {
        Info userInfo = userHashMap.get(id);
        userInfo.clearTime = time;
>>>>>>> refs/remotes/origin/MyPage
    }

    public void updateRank(final String id, final int rank) {
        Info userInfo = userHashMap.get(id);
        userInfo.clearRank = rank;
    }
//
//    public void updateCoin(final String id, final int coin)
//    {
//        Info userInfo = userHashMap.get(id);
//        userInfo.coin = coin;
//    }

<<<<<<< HEAD
    public void addUserData(final String id, final String pw) {
    	{
    		
    		
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE,true)))
            {
            	Info userInfo = new Info();

                String data = String.format("%s,%s,%d,%d", id, pw, userInfo.clearStage, userInfo.clearRank);
                writer.write(data);
                writer.newLine();
                
=======
    public void updateCoin(final String id, final int coin) {
        Info userInfo = userHashMap.get(id);
        userInfo.coin = coin;
    }

    public void updateProfileImage(String id, String fileName) {
        Info userInfo = userHashMap.get(id);
        userInfo.profileImage = fileName;
    }

    // - File I/O -

    public void readUserData(final String[] userIDList) {
        final int MINIUM_DATA_SIZE = 3; // add userProfile

        userHashMap = new HashMap<>();

        for (String id : userIDList) {
            final String USER_FILE = PREFIX + id + SUFFIX;
            File userFile = new File(USER_FILE);

            if (!userFile.exists()) {
                System.out.println("File not found" + USER_FILE + "creating new file..");
                addNewUser(id);
                continue;
>>>>>>> refs/remotes/origin/MyPage
            }
<<<<<<< HEAD
            catch(IOException e)
            {
=======

            try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
                String line = br.readLine();

                if(line != null)
                {
                    String[] parts = line.split(",");

                    if (parts.length >= MINIUM_DATA_SIZE) {
                        Info infoNode = new Info();

                        infoNode.clearTime = Integer.parseInt(parts[TIME_IDX]);
                        infoNode.clearRank = Integer.parseInt(parts[RANK_IDX]);
                        infoNode.coin = Integer.parseInt(parts[COIN_IDX]);
                        infoNode.inventory = parts[INVEN_IDX];

                        // add userprofile
                        if (parts.length > PROFILE_IDX) {
                            infoNode.profileImage = parts[PROFILE_IDX];
                        } else {
                            infoNode.profileImage = "default_profile.png";
                        }

                        userHashMap.put(id, infoNode);
                    }
                }
            } catch (IOException e) {
>>>>>>> refs/remotes/origin/MyPage
                e.printStackTrace();
<<<<<<< HEAD
                System.err.println("Error: Can not write data (" + ACCOUNT_FILE + ")");
=======
                System.err.println("Error: can not open this file (" + USER_FILE + ")");
            } catch (NullPointerException e) {
                System.err.println("Error: Invailed file data format");
>>>>>>> refs/remotes/origin/MyPage
            }
        }
    }
    // - File I/O -

<<<<<<< HEAD
    public void readUserData()
    {
        
=======
    public void storeUserData(final String id) {
        final String USER_FILE = PREFIX + id + SUFFIX;
>>>>>>> refs/remotes/origin/MyPage

        userHashMap = new HashMap<>();

<<<<<<< HEAD
        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                
                if(parts.length <= 4)
                {
                	String id = parts[ID_IDX];
                    String pw = parts[PW_IDX];
                    
                    int stage = Integer.parseInt(parts[STAGE_IDX]);
                    int rank = Integer.parseInt(parts[RANK_IDX]);
                    

                    Info infoNode = new Info();

                    infoNode.id = id;
                    infoNode.pw = pw;
                    infoNode.clearRank = rank;
                    infoNode.clearStage = stage;

                    userHashMap.put(id, infoNode);
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



    public void storeUserData()
    {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE)))
        {
            for(Map.Entry<String, Info> node : userHashMap.entrySet())
            {
                Info userInfo = node.getValue();

                String data = String.format("%s,%s,%d,%d", userInfo.id, userInfo.pw, userInfo.clearStage, userInfo.clearRank);
                writer.write(data);
                writer.newLine();
            }
        }
        catch(IOException e)
        {
=======
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {

            if (userInfo != null) {
                String data = String.format("%d,%d,%d,%s,%s", userInfo.clearTime, userInfo.clearRank, userInfo.coin,
                        userInfo.inventory, userInfo.profileImage);
                writer.write(data);
            }

        } catch (IOException e) {
>>>>>>> refs/remotes/origin/MyPage
            e.printStackTrace();
<<<<<<< HEAD
            System.err.println("Error: Can not write data (" + ACCOUNT_FILE + ")");
=======
            System.err.println("Error: Can not write data (" + USER_FILE + ")");
        }
    }

    public void storeAllUserData() {
        for (String id : userHashMap.keySet()) {
            storeUserData(id);
>>>>>>> refs/remotes/origin/MyPage
        }
    }
}