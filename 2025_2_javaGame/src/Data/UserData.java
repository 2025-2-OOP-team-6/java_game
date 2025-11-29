package Data;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.util.Map;
import java.util.Set;

import GameLogic.Item;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class UserData {
	// INNER CLASS
	private class Info {
		String id = "";
		String pw = "";
		int clearStage = 0;
		int clearRank = 0;
		int coin = 0;
		String profileImage = "default_profile.png";
		ArrayList<Item> inven = new ArrayList<>();
		

	}

	// CONST
	private final ItemData itemMgr;
	private final int ID_IDX = 0;
	private final int PW_IDX = 1;
	private final int STAGE_IDX = 2;
	private final int RANK_IDX = 3;
	private final int COIN_IDX = 4;
	private final int PROFILE_IDX = 5;
	private final String ACCOUNT_FILE = "assets//files//account_file.csv";

	// VARIABLES
	private HashMap<String, Info> userHashMap;

	public UserData(ItemData itemMgr) {
		this.itemMgr = itemMgr;
	}
	
	public String[] getIDList() {
		Set<String> keyList = userHashMap.keySet();
		String[] idList = keyList.toArray(new String[2]);

		return idList;
	}

	public boolean matches(final String id, final String pw) {
		if (pw == null)
			return false;
		readUserData();
		Info storedUser = userHashMap.get(id);
		if (storedUser != null && storedUser.pw.equals(pw)) {
			return true;
		}

		return false;
	}

	public int getStage(final String id) {
		Info userInfo = userHashMap.get(id);

		if (userInfo != null && userInfo.clearStage != 0) {
			return userInfo.clearStage;
		}

		return 0;
	}
	

	public int getRank(final String id) {
		Info userInfo = userHashMap.get(id);

		if (userInfo != null && userInfo.clearRank != 0) {
			return userInfo.clearRank;
		}

		return 0;
	}

	public int getCoin(final String id) {
		Info userInfo = userHashMap.get(id);

		if (userInfo != null && userInfo.coin != 0) {
			return userInfo.coin;
		}

		return 0;
	}

	public String getProfile(final String id) {
		Info userInfo = userHashMap.get(id);

		if (userInfo != null && userInfo.profileImage != null) {
			return userInfo.profileImage;
		}

		return null;
	}

	public void updateStage(final String id, final int stage) {
		Info userInfo = userHashMap.get(id);
		userInfo.clearStage = stage;
	}

	public void updateRank(final String id, final int rank) {
		Info userInfo = userHashMap.get(id);
		userInfo.clearRank = rank;
	}

    public void updateCoin(final String id, final int coin)
    {
        Info userInfo = userHashMap.get(id);
        userInfo.coin = coin;
    }

    public boolean checkUser(final String id) {
    	return userHashMap.containsKey(id);
    }
	public void addUserData(final String id, final String pw) {
		{

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE, true))) {
				Info userInfo = new Info();

				String data = String.format("%s,%s,%d,%d,%d,%s", id, pw, userInfo.clearStage, userInfo.clearRank,
						userInfo.coin, userInfo.profileImage);
				writer.write(data);
				writer.newLine();

			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Error: Can not write data (" + ACCOUNT_FILE + ")");
			}
		}
	}
	// - File I/O -

	public void readUserData() {

		userHashMap = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");

				if (parts.length <= 6) {
					String id = parts[ID_IDX];
					String pw = parts[PW_IDX];

					int stage = Integer.parseInt(parts[STAGE_IDX]);
					int rank = Integer.parseInt(parts[RANK_IDX]);
					int coin = Integer.parseInt(parts[COIN_IDX]);
					
					String profile = parts[PROFILE_IDX];

					Info infoNode = new Info();

					infoNode.id = id;
					infoNode.pw = pw;
					infoNode.clearRank = rank;
					infoNode.clearStage = stage;
					infoNode.coin = coin;
					infoNode.profileImage = profile;

					userHashMap.put(id, infoNode);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error: can not open this file (" + ACCOUNT_FILE + ")");
		} catch (NullPointerException e) {
			System.err.println("Error: Invailed file data format");
		}

	}

	public void storeUserData() {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE))) {
			for (Map.Entry<String, Info> node : userHashMap.entrySet()) {
				Info userInfo = node.getValue();

				String data = String.format("%s,%s,%d,%d,%d,%s", userInfo.id, userInfo.pw, userInfo.clearStage,
						userInfo.clearRank, userInfo.coin, userInfo.profileImage);
				writer.write(data);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error: Can not write data (" + ACCOUNT_FILE + ")");
		}
	}

	public void addNewItem(String userid,String[] itemList) {
		Info userInfo = userHashMap.get(userid);
		for(String itemid:itemList)
			userInfo.inven.add(itemMgr.get(itemid));
		
	}

	public String getProfileImage(String id) {
		return userHashMap.get(id).profileImage;
	}

	public String[] getInventory(String id) {
		String[] invenString = new String[userHashMap.get(id).inven.size()];
		int i = 0;
		for(Item item:userHashMap.get(id).inven) {
			invenString[i] = userHashMap.get(id).inven.get(i).name;
		}
			
			
		return invenString;	
	}
}