//package Data;
//
//import java.io.IOException;
//import java.io.FileReader;
//import java.io.BufferedReader;
//
//import java.util.List;
//import java.util.HashMap;
//import java.util.ArrayList;
//
//public class CharactorData
//{
//    class Traits
//    {
//        String trait1 = "";
//        String trait2 = "";
//        String trait3 = "";
//    }
//
//    private final String CHARACTOR_FILE = "assets//files//charactor_class.csv";
//    private final int CLASS_IDX = 0;
//    private final int TRAIT1_IDX = 1;
//    private final int TRAIT2_IDX = 2;
//    private final int TRAIT3_IDX = 3;
//
//    private List<String> jobList;
//    private HashMap<String, Traits> charactorInfo;
//
//
//    public CharactorData()
//    {
//        jobList = new ArrayList<>();
//        charactorInfo = new HashMap<>();
//    }
//
//    public String[] getjobList()
//    {
//        return jobList.toArray(new String[0]);
//    }
//
//    public String getTrait(final String job, final int index)
//    {
//        Traits node = charactorInfo.get(job);
//
//        if(node != null && index <= 3)
//        {
//            if(index == 1) { return node.trait1;}
//            if(index == 2) { return node.trait2;}
//            if(index == 3) { return node.trait3;}
//        }
//
//        return null;
//    }
//
//    public void readCharactorData()
//    {
//        try (BufferedReader br = new BufferedReader(new FileReader(CHARACTOR_FILE))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//
//                if(parts.length >= 4)
//                {
//                    Traits node = new Traits();
//                    String job = parts[CLASS_IDX];
//
//                    node.trait1 = parts[TRAIT1_IDX];
//                    node.trait2 = parts[TRAIT2_IDX];
//                    node.trait3 = parts[TRAIT3_IDX];
//
//                    jobList.add(job);
//                    charactorInfo.put(job, node);
//                }
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Error: can not open this file (" + CHARACTOR_FILE + ")" );
//        }
//        catch(NullPointerException e)
//        {
//            System.err.println("Error: Invailed file data format");
//        }
//    }
//}
