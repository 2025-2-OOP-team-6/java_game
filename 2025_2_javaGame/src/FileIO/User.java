package FileIO;

import java.util.HashMap;

public class User implements IFile {
    // 필드 대신 HashMap으로 데이터 관리
    private HashMap<String, String> userInfo = new HashMap<>();

    @Override
    public void read(String line) {
        String[] parts = line.split(",");
        if (parts.length >= 2) {
            // HashMap에 데이터 저장
            this.userInfo.put("ID", parts[0].trim());
            this.userInfo.put("PW", parts[1].trim());
        }
    }

    @Override
    public void print() {
        // HashMap에서 데이터 가져와 출력
        System.out.printf("%s님 환영합니다\n", this.userInfo.get("ID"));
        // 보안을 위해 PW는 출력하지 않거나 마스킹 처리 권장
         System.out.printf("비번 확인 코드:%s\n", this.userInfo.get("PW")); 
    }

    @Override
    public boolean matches(String kwd) {
        // ID 또는 PW 중 하나라도 키워드와 일치하면 true
        String id = this.userInfo.get("ID");
        String pw = this.userInfo.get("PW");
        
        if (id != null && id.equals(kwd)) {
            return true;
        }
        if (pw != null && pw.equals(kwd)) {
            return true;
        }
        return false;
    }

    @Override
    public String toCSVString() {
        // HashMap 데이터를 CSV 형식으로 변환
        return String.format("%s,%s", this.userInfo.get("ID"), this.userInfo.get("PW"));
    }
}