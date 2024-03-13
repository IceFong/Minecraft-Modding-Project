package net.icecheese.leagueofminecraft.characterskill.leesin.network;

import java.util.function.Supplier;

import net.icecheese.leagueofminecraft.characterskill.leesin.network.messages.PlayerActionPacket;
import net.icecheese.leagueofminecraft.characterskill.leesin.network.messages.PlayerAction;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfig.Server;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class InputEventPacket {

	public static int PACKET_ID = 0;
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation("leagueofminecraft", "inputeventpacket"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
        );

    public static void handle(PlayerActionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        
		ctx.get().enqueueWork(() -> {
			// DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> InputEventPacket.handlePacket(msg, ctx));]
			ServerPlayer player = ctx.get().getSender();
			if (player != null) {

				PlayerAction action = msg.getAction();
				switch (action) {
					case GeneralSkillUse:
						useHandItem(player, InteractionHand.MAIN_HAND);
						break;
					case LeeSin_Skill_4:
						useHandItem(player, InteractionHand.MAIN_HAND);
						break;
				
					default:
						break;
				}
				
			}
        });
        
		ctx.get().setPacketHandled(true);
    }

	public static void useHandItem(ServerPlayer player, InteractionHand hand) {
		ItemStack mainHoldItem = player.getItemInHand(hand);
		mainHoldItem.use(player.level(), player, hand);
	}

	public static void LeeSin_Skill_4(ServerPlayer player, InteractionHand hand) {
		ItemStack mainHoldItem = player.getItemInHand(hand);
		// mainHoldItem.interactLivingEntity(player, player., hand);
	}

	// public static void handlePacket(PlayerActionPacket msg, Supplier<NetworkEvent.Context> ctx) {
	// 	// Do stuff
	// 	ServerPlayer player = ctx.get().getSender();
	// 	if (player != null) {
	// 		String playerName = player.getName().toString();
	// 		System.out.println(playerName);
	// 		player.sendSystemMessage(Component.literal(msg.getAction() + "||" + playerName));
	// 	}
	// }

}
