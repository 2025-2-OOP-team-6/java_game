package Data;

import java.time.LocalTime;
import java.util.ArrayList;

public class AchievementManager {
    private final LogData logMgr;

    public AchievementManager(LogData logMgr) {
        this.logMgr = logMgr;
    }

    public String getPlayCountAcheivement(String userId) {
        int battleCnt = logMgr.getBattleCnt(userId);

        if (battleCnt >= 15) return "고수";
        if (battleCnt >= 5) return "중급자";
        return "초심자";
    }

    public String getTimeAcheivement(String userId) {

        String[] timeLogs = logMgr.getTimeLogs(userId);
        if (timeLogs == null || timeLogs.length == 0) {
            return null;
        }

        int morningCnt=0;
        int eveningCnt=0;
        int owlCnt=0;

        for (String t : timeLogs) {
            LocalTime time = LocalTime.parse(t);

            // - 아침형 인간 -
            if (!time.isBefore(LocalTime.of(9, 0)) &&
                  time.isBefore(LocalTime.of(11, 0))) {
                morningCnt++;
            }
            // - 저녁형 인간 -
            else if (!time.isBefore(LocalTime.of(19, 0)) || 
                       time.isBefore(LocalTime.of(22, 0))) {
                eveningCnt++;
            }
            // - 올빼미 -
            else if (!time.isBefore(LocalTime.of(2, 0)) &&
              time.isBefore(LocalTime.of(5, 0))) {
                owlCnt++;
            }
        }
         
        int max = Math.max(morningCnt, Math.max(eveningCnt, owlCnt));
        if (max == 0) return null;
        if (max == morningCnt) return "아침형 인간";
        if (max == eveningCnt) return "저녁형 인간";
        return "올빼미";
    }

    public String getWinningTitle(String userId) {
        
        String[] results = logMgr.getResultLogs(userId);
        int curWin = 0; 
        int maxWin = 0;

        for (String r : results) {
            if (r.equals("Win")) {
            curWin++;
                maxWin = Math.max(maxWin, curWin);
            } else {
                curWin = 0;
            }
        }

        if (maxWin >= 7) return "무적의 전사";
        if (maxWin >= 5) return "연승의 주인공";
        if (maxWin >= 3) return "불꽃의 주자";

        return null;
    }

    public String getAchievements(String userId) {

        ArrayList<String> achievementList = new ArrayList<>();

        achievementList.add(getPlayCountAcheivement(userId));
        achievementList.add(getTimeAcheivement(userId));
        achievementList.add(getWinningTitle(userId));

        String result = "";

        for (String achieve : achievementList) {
            if (achieve == null) continue;  

            if (!result.equals("")) {
                result += " / "; 
            }

            result += achieve;
        }

        if (result.equals("")) return null;

        return "(" + result + ")";
    }
}

