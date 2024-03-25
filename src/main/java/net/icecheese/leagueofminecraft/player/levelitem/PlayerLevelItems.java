package net.icecheese.leagueofminecraft.player.levelitem;

import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class PlayerLevelItems {

    List<LevelItemData> itemData;

    public PlayerLevelItems() {
        itemData = new ArrayList<>();
    }

    public void addItemType(LevelItemData data) {
        itemData.add(data);
    }

    public static class LevelItemData {
        public int exp;
        public int level;
        public int eachLevelExp = 10;
        public int baseLevelExpRequired = 10;
        public LevelItemType itemType;

        public LevelItemData(int exp, int level, LevelItemType itemType) {
            this.exp = exp;
            this.level = level;
            this.itemType = itemType;
        }

        public void addExp(int amount) {
            exp += amount;
            if (checkLevelUp()) {
                int temp_amount = exp;
                while (temp_amount >= (level - 1)*eachLevelExp + baseLevelExpRequired)
                {
                    temp_amount -= (level - 1)*eachLevelExp + baseLevelExpRequired;
                    level += 1;
                }
                exp = temp_amount;
            }
        }

        public boolean checkLevelUp() {
            return exp >= eachLevelExp * level + baseLevelExpRequired;
        };

        public CompoundTag save(CompoundTag nbt) {
//            nbt.putString("leagueofminecraft.levelitem.itemtype", itemType.toString());
            nbt.putInt("leagueofminecraft.levelitem." + itemType.toString() + ".exp", exp);
            nbt.putInt("leagueofminecraft.levelitem." + itemType.toString() + ".level", level);
            return nbt;
        }

        public void load(CompoundTag nbt) {
//            String type = nbt.getString("leagueofminecraft.levelitem.itemtype");
//                itemType = LevelItemType.valueOf(type);
            exp = nbt.getInt("leagueofminecraft.levelitem." + itemType.toString() + ".exp");
            level = nbt.getInt("leagueofminecraft.levelitem." + itemType.toString() + ".level");
        }

    }

    public void load(CompoundTag nbt) {
        for (LevelItemType type: LevelItemType.values()) {
            int exp = nbt.getInt("leagueofminecraft.levelitem." + type + ".exp" );
            int level = nbt.getInt("leagueofminecraft.levelitem." + type + ".level" );
            LevelItemData data = new LevelItemData(exp, level, type);
        }
    }

    public CompoundTag save() {
        CompoundTag nbt = new CompoundTag();
        for (LevelItemData data: itemData) {
            nbt.putInt("leagueofminecraft.levelitem." + data.itemType + ".exp", data.exp);
            nbt.putInt("leagueofminecraft.levelitem." + data.itemType + ".level", data.level);
        }

        return nbt;
    }

}
