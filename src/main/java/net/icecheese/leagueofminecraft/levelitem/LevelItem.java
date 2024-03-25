package net.icecheese.leagueofminecraft.levelitem;

import net.icecheese.leagueofminecraft.player.levelitem.PlayerLevelItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public class LevelItem extends Item {

    public PlayerLevelItems.LevelItemData levelItemData;
    public LevelItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }

    public void setLevelItemData(PlayerLevelItems.LevelItemData data) {
        levelItemData = data;
    }

    public int calculateDamage() {
        return 0;
    }
}

