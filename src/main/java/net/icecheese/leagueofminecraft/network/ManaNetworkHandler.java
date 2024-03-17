package net.icecheese.leagueofminecraft.network;

import java.util.function.Supplier;

import org.antlr.v4.parse.ATNBuilder.subrule_return;

import net.icecheese.leagueofminecraft.network.messages.ManaPacket;
import net.icecheese.leagueofminecraft.network.messages.MotionPacket;
import net.icecheese.leagueofminecraft.player.ClientManaSystem;
import net.icecheese.leagueofminecraft.player.PlayerCapabilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/*
 * This class handles the server to client (S2C) syncronizes the client mana system
 * Call whenever send server data to client
 */

public class ManaNetworkHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation("leagueofminecraft", "mananetworkhandler"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
        );

    public static void handle(ManaPacket msg, Supplier<NetworkEvent.Context> ctx) {
        
		ctx.get().enqueueWork(() -> {
            ClientManaSystem.set(msg.mana, msg.maxMana, msg.regenRate);
            ctx.get().getSender().getCapability(PlayerCapabilities.PLAYER_MANA_SYS).ifPresent(
                sys -> {
                    sys.mana = msg.mana;
                    sys.maxMana = msg.maxMana;
                    sys.regenRate = msg.regenRate;

                }
            );
        });
        
		ctx.get().setPacketHandled(true);
    }

}
