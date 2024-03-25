package net.icecheese.leagueofminecraft.characteritem.gangplank;

import net.icecheese.leagueofminecraft.levelitem.LevelItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// 1. Not stackable.
// 2. Has duration? (x)
// 3. Can be crafted.
public class GanplankGunItem extends LevelItem {

    public GanplankGunItem(Properties p_41383_) {
        super(p_41383_);
    }

    public void killBonus(ItemStack itemStack, Player player) {

        if (player.level().isClientSide) return;
        player.getInventory().placeItemBackInInventory(new ItemStack(Items.GOLD_INGOT, 1));

    }

    @Override
    public int calculateDamage() {
        int level = levelItemData.level;
        return (int)(level * 0.5f);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}
