package net.icecheese.leagueofminecraft.eventhandler;

import java.util.List;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.InputConstants;

import ca.weblite.objc.Message;
import cpw.mods.modlauncher.api.ITransformer.Target;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill1_Item;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill2_Item;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill3_Item;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill4_Item;
import net.icecheese.leagueofminecraft.characterskill.leesin.network.InputEventPacket;
import net.icecheese.leagueofminecraft.characterskill.leesin.network.messages.PlayerActionPacket;
import net.icecheese.leagueofminecraft.characterskill.leesin.network.messages.PlayerAction;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.event.level.NoteBlockEvent.Play;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = "leagueofminecraft", bus = Bus.FORGE, value = Dist.CLIENT)
public class EventHandler {

    // private static final int GLFW_KEY_X = 88;
    private static int id = 0;

    @SubscribeEvent
    public static void PickupItem( EntityItemPickupEvent event ) {
        Entity entity = event.getEntity();
        
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;
        
        if (player.level().isClientSide) return;

        ItemEntity itemEntity = event.getItem();
        if (itemEntity == null) return;

        Component itemName = itemEntity.getName();
        player.sendSystemMessage(itemName);

    }

    @SubscribeEvent
    public static void KeyboardPressed( InputEvent.Key event ) {

        Player player = (Player) Minecraft.getInstance().player;
        if (player == null) return;

        if (event.getKey() == InputConstants.KEY_Z && event.getAction() == InputConstants.PRESS) {
            ItemStack mainHoldItem = Minecraft.getInstance().player.getMainHandItem();
            if (mainHoldItem.getItem() instanceof Skill1_Item) {
                InputEventPacket.INSTANCE.sendToServer(new PlayerActionPacket(PlayerAction.GeneralSkillUse));
            }
        }
        if (event.getKey() == InputConstants.KEY_X && event.getAction() == InputConstants.PRESS) {
            ItemStack mainHoldItem = Minecraft.getInstance().player.getMainHandItem();
            if (mainHoldItem.getItem() instanceof Skill2_Item) {
                InputEventPacket.INSTANCE.sendToServer(new PlayerActionPacket(PlayerAction.GeneralSkillUse));
            }
        }
        if (event.getKey() == InputConstants.KEY_C && event.getAction() == InputConstants.PRESS) {
            ItemStack mainHoldItem = Minecraft.getInstance().player.getMainHandItem();
            if (mainHoldItem.getItem() instanceof Skill3_Item) {
                InputEventPacket.INSTANCE.sendToServer(new PlayerActionPacket(PlayerAction.GeneralSkillUse));
            }
        }
        if (event.getKey() == InputConstants.KEY_V && event.getAction() == InputConstants.PRESS) {
            
            ItemStack mainHoldItem = Minecraft.getInstance().player.getMainHandItem();
            if (mainHoldItem.getItem() instanceof Skill4_Item) {
                InputEventPacket.INSTANCE.sendToServer(new PlayerActionPacket(PlayerAction.LeeSin_Skill_4));
            }
        }

    }

}
