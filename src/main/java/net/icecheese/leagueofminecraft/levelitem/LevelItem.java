package net.icecheese.leagueofminecraft.levelitem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public class LevelItem extends Item {

    public LevelItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }

}
