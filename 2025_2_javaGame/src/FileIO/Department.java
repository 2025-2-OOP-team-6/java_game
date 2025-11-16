package FileIO;
import FileIO.Factory;
import FileIO.IFile;
import FileIO.DataManager;
public class Department {
	DataManager <City>cMgr = new DataManager<>(); // 도시 자료형 매니저 
	DataManager<User>uMgr = new DataManager<>(); // 사용자 자료형 매니저 
	void mymain() {
		//도시리스트를 읽어오고 읽어온 데이터를 CSV파일에 저장합니다.
		cMgr.readAll("cityList.csv", new Factory<City>() {
			public City create() {
				return new City();
			}
			
		});
	
	cMgr.printAll();
	cMgr.writeCSV("city_list.csv");
	
	//userList파일을 읽어 ArrayLis에 저장
	uMgr.readAll("userList.csv", new Factory<User>() {
		public User create() {
			return new User();
		}
	});
	//ArrayList 를 출력 
	uMgr.printAll();
	//user_list.csv파일을 생성, ArrayList내용을 저장.
	uMgr.writeCSV("user_list.csv");
	
	
	
	
	}
	
}
	
