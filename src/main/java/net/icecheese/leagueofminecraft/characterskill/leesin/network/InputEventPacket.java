package net.icecheese.leagueofminecraft.characterskill.leesin.network;

import java.util.List;
import java.util.function.Supplier;

import net.icecheese.leagueofminecraft.characterskill.leesin.network.messages.PlayerActionPacket;
import net.icecheese.leagueofminecraft.characterskill.leesin.network.messages.PlayerAction;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
						LeeSin_Skill_4(player, InteractionHand.MAIN_HAND);
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

		List<LivingEntity> listOfLivingEntity = player.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, AABB.ofSize(player.getPosition(1.0F),10,10,10));
		float dis_low = 10000.0f;
		LivingEntity entityToKick = null;
		for (LivingEntity livingEntity : listOfLivingEntity) {
			// Do player eye view and intersects with entity AABB
			Vec3 playerEye = player.getEyePosition();
			Vec3 playerView = player.getViewVector(1.0f).normalize().scale(5.0f);
			AABB entityAABB = livingEntity.getBoundingBox();

			ClipResult clipResult = Vec3ClipLine(3, entityAABB, playerEye, playerEye.add(playerView));
			if (clipResult.result) {
				// Find nearest entity
				float dis = player.distanceTo(livingEntity);
				if (dis_low > dis) {
					dis_low = dis;
					entityToKick = livingEntity;
				}
			}
		}

		if (entityToKick != null) {
			mainHoldItem.interactLivingEntity(player, entityToKick, hand);
		}

	}


	/*
	 * Find intersection of a line with AABB 
	 */

	private static ClipResult Vec3ClipLine(int dim, final AABB aabb, Vec3 v0, Vec3 v1) {
        // System.out.println("dim = " + dim);
        if (dim < 1) {
            ClipResult clipResult = new ClipResult();
                clipResult.result = true;
                clipResult.f_low = 0.0d;
                clipResult.f_high = 1.0d;
            return clipResult;
        }

        ClipResult clipResult = Vec3ClipLine(dim-1, aabb, v0, v1);
            double f_low = clipResult.f_low;
            double f_high = clipResult.f_high;

        if (!clipResult.result) {
            return clipResult;
        }

		double aabb_min, aabb_max;
		double dim_v0, dim_v1;
		aabb_max = aabb_min = dim_v0 = dim_v1 = 0.0d;
		// 1. get projected vector at dimension 0 = x, 1 = y, 2 = z
		switch (dim) {
			case 1:
				aabb_min = aabb.minX;
				aabb_max = aabb.maxX;
				dim_v0 = v0.x;
				dim_v1 = v1.x;
				break;
			case 2:
				aabb_min = aabb.minY;
				aabb_max = aabb.maxY;
				dim_v0 = v0.y;
				dim_v1 = v1.y;
				break;
			case 3:
				aabb_min = aabb.minZ;
				aabb_max = aabb.maxZ;
				dim_v0 = v0.z;
				dim_v1 = v1.z;
				break;
			default:
				System.err.println("dim can't be " + dim);
				break;
		}

		double length = dim_v1 - dim_v0;
		double f_dim_low = (aabb_min - dim_v0) / length;
		double f_dim_high = (aabb_max - dim_v0) / length;

		if (f_dim_low > f_dim_high) {
			double temp = f_dim_high;
			f_dim_high = f_dim_low;
			f_dim_low = temp;
        }

        boolean result = true;
        if (f_dim_high < f_low) {
            result = false;
        }
        if (f_dim_low > f_high) {
            result = false;
        }

        f_low = Math.max(f_low, f_dim_low);
        f_high = Math.min(f_high, f_dim_high);

        clipResult.f_low = f_low;
        clipResult.f_high = f_high;
        clipResult.result = result;

		if (f_low > f_high) {
            result = false;
        }

        // System.out.println("f_low = " + f_low + ", f_high = " + f_high);
	
        return clipResult;
    }

    static class ClipResult {
        public boolean result;
        public double f_low, f_high;
    };

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
