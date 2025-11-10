package FileIO;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class DataManager< T extends IFile >  
{
	public ArrayList<T> cList = new ArrayList<>();
/*
	public T find(String name) {
		for (T p : mList) {
			if (p.matches(name))
				return p;
		}
		return null;
	}
추가 기능 구현 예정....
	*/


	public void readAll(String filename, Factory<T> fac) {
	    Scanner filein = null;
	    try {
	        filein = new Scanner(new File(filename));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        System.exit(0);
	    }
	
	    while (filein.hasNextLine()) {
	        String line = filein.nextLine(); 
	        
	        if (line.trim().isEmpty()) {
	            continue;
	        }
	        T m = fac.create();
	        m.read(line);
	        cList.add(m);
	    }
	    filein.close();
	}

	public Scanner openFile(String filename) {
		Scanner filein = null;
		try {
			filein = new Scanner(new File(filename));
		} 
		catch (IOException e) {
			System.out.println("파일 입력 오류");
			System.exit(0);
		}
		return filein;
	}

	public void printAll() {
		for (T p : cList)
			p.print();
	}
	
	public void writeCSV(String filename) {
		try (PrintWriter writer = new PrintWriter(new File(filename))) {
			for(T p : cList) {
				String csvLine = p.toCSVString();
				writer.println(csvLine);
			
			}
			}catch (FileNotFoundException e) {
		        // 5. 파일 생성/쓰기 중 오류가 발생하면 메시지를 출력합니다.
		        System.out.println(filename + " 파일 쓰기 중 오류가 발생했습니다.");
		        e.printStackTrace();
		    }
			
	}

	public void search(Scanner scan) {
		String kwd = null;
		while (true) {
			System.out.print("검색키워드:");
			kwd = scan.next();
			if (kwd.contentEquals("end"))
				break;
			for (T m : cList) {
				if (m.matches(kwd))
					m.print();
					
					
			}
			
		}
	}
}