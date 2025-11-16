package Logic;

import java.util.Arrays;

public class ItemManager
{
    public static final int MAX_SLOTS = 6;

    public enum EffectType
    {
        NONE,
        HEAL,          
        EXTRA_DICE,    
        SHIELD,        
        EXTRA_DAMAGE   
    }

    public static class BattleItem
    {
        public final String id;
        public final String name;
        public final String description;
        public final EffectType effectType;
        public final int magnitude;

        public BattleItem(final String id,
                          final String name,
                          final String description,
                          final EffectType effectType,
                          final int magnitude)
        {
            this.id = id;
            this.name = name;
            this.description = description;
            this.effectType = effectType;
            this.magnitude = magnitude;
        }
    }

    private final BattleItem[] slots;

    public ItemManager()
    {
        slots = new BattleItem[MAX_SLOTS];
    }

    public void setItem(final int index, final BattleItem item)
    {
        checkIndex(index);
        slots[index] = item;
    }

    public BattleItem getItem(final int index)
    {
        checkIndex(index);
        return slots[index];
    }

    public void clearSlot(final int index)
    {
        checkIndex(index);
        slots[index] = null;
    }

    public void clearAll()
    {
        Arrays.fill(slots, null);
    }

    public BattleItem[] getAllItems()
    {
        return slots.clone();
    }

    private void checkIndex(final int index)
    {
        if (index < 0 || index >= MAX_SLOTS)
        {
            throw new IndexOutOfBoundsException(
                "Item slot index out of range: " + index);
        }
    }
}
