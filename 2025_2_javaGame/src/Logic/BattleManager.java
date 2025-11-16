package Logic;

import java.util.Random;

/*
  전투로직을 담당하는 매니저.
  - 주사위 범위
  - 스테이지 번호
  - 승패 판정 
 */
public class BattleManager
{
    public enum DuelResult
    {
        PLAYER_WIN,
        ENEMY_WIN,
        DRAW
    }

    private static final int DEFAULT_STAGE = 1;

    private static final int DEFAULT_PLAYER_MAX_HP = 5;
    private static final int DEFAULT_ENEMY_MAX_HP  = 5;

    private static final int DEFAULT_PLAYER_DICE_MIN = 1;
    private static final int DEFAULT_PLAYER_DICE_MAX = 6;
    private static final int DEFAULT_ENEMY_DICE_MIN  = 1;
    private static final int DEFAULT_ENEMY_DICE_MAX  = 6;

    // 랜덤 주사위
    private final Random random = new Random();

    private int stage;

    private int playerMaxHp;
    private int enemyMaxHp;

    private int playerDiceMin;
    private int playerDiceMax;
    private int enemyDiceMin;
    private int enemyDiceMax;

    public BattleManager()
    {
        stage = DEFAULT_STAGE;

        playerMaxHp = DEFAULT_PLAYER_MAX_HP;
        enemyMaxHp  = DEFAULT_ENEMY_MAX_HP;

        playerDiceMin = DEFAULT_PLAYER_DICE_MIN;
        playerDiceMax = DEFAULT_PLAYER_DICE_MAX;
        enemyDiceMin  = DEFAULT_ENEMY_DICE_MIN;
        enemyDiceMax  = DEFAULT_ENEMY_DICE_MAX;
    }

    public int getStage()
    {
        return stage;
    }

    public int getPlayerMaxHp()
    {
        return playerMaxHp;
    }

    public int getEnemyMaxHp()
    {
        return enemyMaxHp;
    }

    public int getPlayerDiceMin()
    {
        return playerDiceMin;
    }

    public int getPlayerDiceMax()
    {
        return playerDiceMax;
    }

    public int getEnemyDiceMin()
    {
        return enemyDiceMin;
    }

    public int getEnemyDiceMax()
    {
        return enemyDiceMax;
    }

    // 핵심 로직: 1회 결투
    //플레이어와 적이 각각 주사위를 한 번 굴려서누가 이겼는지 결과만 리턴ㄴ

    public DuelResult duel()
    {
        int playerRoll = roll(playerDiceMin, playerDiceMax);
        int enemyRoll  = roll(enemyDiceMin, enemyDiceMax);

        System.out.printf("Duel: player=%d, enemy=%d%n", playerRoll, enemyRoll);

        if (playerRoll > enemyRoll)
        {
            return DuelResult.PLAYER_WIN;
        }
        if (enemyRoll > playerRoll)
        {
            return DuelResult.ENEMY_WIN;
        }
        return DuelResult.DRAW;
    }

    // 주사위 굴리기 최소, 최대 포함
    private int roll(final int min, final int max)
    {
        return random.nextInt(max - min + 1) + min;
    }

    public void setStage(final int stage)
    {
        if (stage <= 0)
        {
            throw new IllegalArgumentException("stage must be positive");
        }
        this.stage = stage;
    }
}
