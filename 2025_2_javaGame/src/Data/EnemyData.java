package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


//적(Enemy) 데이터 관리 클래스.

public class EnemyData
{
    private static final String ENEMY_FILE = "..//assets//files//enemy_file.csv";

    public static class EnemyInfo
    {
        public final String id;
        public final int maxHp;
        public final int diceMin;
        public final int diceMax;
        public final int stage;

        public EnemyInfo(final String id,
                         final int maxHp,
                         final int diceMin,
                         final int diceMax,
                         final int stage)
        {
            this.id = id;
            this.maxHp = maxHp;
            this.diceMin = diceMin;
            this.diceMax = diceMax;
            this.stage = stage;
        }
    }

    private final HashMap<String, EnemyInfo> enemyMap;

    public EnemyData()
    {
        enemyMap = new HashMap<>();
        readEnemyData();
    }

    private void readEnemyData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(ENEMY_FILE)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                final String[] parts = line.split(",");

                if (parts.length < 5)
                {
                    continue;
                }

                final String id = parts[0].trim();
                final int maxHp = Integer.parseInt(parts[1].trim());
                final int diceMin = Integer.parseInt(parts[2].trim());
                final int diceMax = Integer.parseInt(parts[3].trim());
                final int stage = Integer.parseInt(parts[4].trim());

                final EnemyInfo info = new EnemyInfo(id, maxHp, diceMin, diceMax, stage);
                enemyMap.put(id, info);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Error: can not open this file (" + ENEMY_FILE + ")");
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            System.err.println("Error: invalid number format in " + ENEMY_FILE);
        }
    }

    public EnemyInfo getEnemy(final String id)
    {
        return enemyMap.get(id);
    }

    public EnemyInfo getEnemyForStage(final int stage)
    {
        for (EnemyInfo info : enemyMap.values())
        {
            if (info.stage == stage)
            {
                return info;
            }
        }
        return null;
    }
}
