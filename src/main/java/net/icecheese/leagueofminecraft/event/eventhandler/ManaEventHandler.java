package net.icecheese.leagueofminecraft.event.eventhandler;

import org.stringtemplate.v4.compiler.CodeGenerator.region_return;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService.Phase;
import io.socol.betterthirdperson.api.TickPhase;
import net.icecheese.leagueofminecraft.LeagueOfMinecraft;
import net.icecheese.leagueofminecraft.event.ManaEvent;
import net.icecheese.leagueofminecraft.network.ManaNetworkHandler;
import net.icecheese.leagueofminecraft.network.messages.ManaPacket;
import net.icecheese.leagueofminecraft.player.ClientManaSystem;
import net.icecheese.leagueofminecraft.player.PlayerCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.PacketTarget;

@Mod.EventBusSubscriber(modid = LeagueOfMinecraft.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ManaEventHandler {

    // On player join sync client mana system with server
    // Send message from server to client
    @SubscribeEvent
    public static void onPlayerJoinLevel(EntityJoinLevelEvent event) {

        if (!(event.getEntity() instanceof Player)) return;
        
        Player player = (Player) event.getEntity();
        if (player.level().isClientSide) return;
        
        player.getCapability(PlayerCapabilities.PLAYER_MANA_SYS).ifPresent(
            sys -> {
                ManaNetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ManaPacket(sys));
                ClientManaSystem.playerManaCap = player.getCapability(PlayerCapabilities.PLAYER_MANA_SYS);
            }
        );

    }

    /*
     * Regenerates mana on server side and sync to all client
     */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        
        Player player = event.player;
        if (player == null) return;
        if (player.level().isClientSide) return;
        if (event.phase == TickEvent.Phase.END) return;

        player.getCapability(PlayerCapabilities.PLAYER_MANA_SYS).ifPresent(sys -> {
            // Do regen mana every second
            if (player.level().getGameTime() % 20 == 0) {
                sys.RegenMana();
                sys.DisplayInGame(player);
                ManaNetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ManaPacket(sys));
            }
        });

    }


    /*
     * when player use mana, sycn to server current mana
     */
    @SubscribeEvent
    public static void PlayerCosumeManaHandler( ManaEvent.ConsumeManaEvent event ) {

        if (event.player.level().isClientSide) return;
        
        Player player = event.player;
        player.getCapability(PlayerCapabilities.PLAYER_MANA_SYS).ifPresent(
            sys -> {
                sys.SubMana(event.amount);
                ManaNetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ManaPacket(sys));
            }
        );

    }

    // @SubscribeEvent
    // public static void DebugTick(TickEvent.PlayerTickEvent event) {
    //     if (event.side == LogicalSide.SERVER) {
    //         event.player.getCapability(PlayerCapabilities.PLAYER_MANA_SYS).ifPresent(
    //             sys -> {
    //                 System.out.println(sys.mana);
    //             }
    //         );
    //     }
    //     else {
    //         System.out.println(ClientManaSystem.mana);
    //     }
    // }


}
