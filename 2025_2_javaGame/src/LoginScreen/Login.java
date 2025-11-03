package LoginScreen;

import java.util.HashMap;

public class Login {
	private HashMap<String, String> loginData = new HashMap<>();
	
	public void signUp(String id, String password) {
		loginData.put(id, password);
	}
	
	public void signIn(String id, String password) {
		if (loginData.containsKey(id)) {
			if (loginData.get(id).equals(password)) {
				System.out.print("로그인 완료");
				return;
			}
		}
	}
}
