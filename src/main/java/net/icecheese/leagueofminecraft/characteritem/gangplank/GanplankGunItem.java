package net.icecheese.leagueofminecraft.characteritem.gangplank;

import net.icecheese.leagueofminecraft.levelitem.LevelItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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

}
