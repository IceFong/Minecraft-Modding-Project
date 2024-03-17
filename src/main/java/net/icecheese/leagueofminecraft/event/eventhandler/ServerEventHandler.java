package net.icecheese.leagueofminecraft.event.eventhandler;

import net.icecheese.leagueofminecraft.LeagueOfMinecraft;
import net.icecheese.leagueofminecraft.event.ManaEvent;
import net.icecheese.leagueofminecraft.player.PlayerCapabilities;
import net.icecheese.leagueofminecraft.player.PlayerManaSystem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = LeagueOfMinecraft.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ServerEventHandler {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {

        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerCapabilities.PLAYER_MANA_SYS).isPresent()) {
                event.addCapability(new ResourceLocation(LeagueOfMinecraft.MODID, "properties"), new PlayerCapabilities());
            }
        }

    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {

        // If player died, move original capabilities new player clone.
        if (event.isWasDeath()) {
            Player oldClone = event.getOriginal();
            oldClone.getCapability(PlayerCapabilities.PLAYER_MANA_SYS).ifPresent(oldSys -> {
                oldClone.getCapability(PlayerCapabilities.PLAYER_MANA_SYS).ifPresent( newSys -> {
                    newSys.CopyFrom(oldSys);
                });
            });
        }

    }


}
