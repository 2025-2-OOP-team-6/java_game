package FileIO;

//import java.util.Scanner;
//import java.util.ArrayList;

public interface IFile {
    enum FileType {
      City, Distance 
      //기타 회의 이후 자료 추가예정...()
    }

   

   public void read(String line);
   public void print();
   public boolean matches(String kwd);
   public String toCSVString();
   

   
    

   
}