package net.icecheese.leagueofminecraft.player.levelitem;

import java.util.ArrayList;
import java.util.List;

public class PlayerLevelItem {

    public static final List<String> LEVEL_ITEMS = new ArrayList<String>();

    public PlayerLevelItem() {
        LEVEL_ITEMS.add("gangplank.gun");
    }


    public static class LevelItemData {
        public int exp;
        public int level;
        public  int levelMaxExp;
        public int eachLevelExp;
        public int baseLevelExpRequired;

        public LevelItemData(int exp, int level, int eachLevelExp) {
            this.exp = exp;
            this.level = level;
            this.eachLevelExp = eachLevelExp;
            levelMaxExp = baseLevelExpRequired + level * eachLevelExp;
        }

        public void addExp(int amount) {
            exp += amount;
            if (checkLevelUp()) {
                int n;
                exp = n * (baseLevelExpRequired + eachLevelExp * (level+n)/2);
            }
        }

        public boolean checkLevelUp() {
            return (this.exp >= this.eachLevelExp) ? true : false;
        };

    }

}
