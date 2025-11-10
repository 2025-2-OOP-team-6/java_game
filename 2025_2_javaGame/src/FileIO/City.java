package FileIO;
//import java.util.Scanner;

public class City implements IFile {
	
	String cName;
	int distance;


	@Override //Manager readALL에서 넘긴 도시를 읽습니다.
	public void read(String line) {

		String[] parts = line.split(",");
		

		if (parts.length >= 2) {

			this.cName = parts[0].trim();
			this.distance = Integer.parseInt(parts[1].trim());
		}
	}
 //read를 출력합니다.
	public void print() {

		System.out.printf("도시: %s, 거리: %d\n", cName, distance);
	}

	public boolean matches(String kwd) {
		if (cName.equals(kwd))
			return true;
     

		if (Integer.toString(distance).equals(kwd))
			return true;
		
		return false;
	}
	
	//각 객체에서 새로 생성하여 사용
	public String toCSVString() {
		
		return String.format("%s,%d", cName, distance);
		
	}
}


