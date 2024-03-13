package net.icecheese.leagueofminecraft.eventhandler;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class InteractEntityEvent extends PlayerEvent {

    public InteractEntityEvent(Player player) {
        super(player);
    }

}
