package net.icecheese.leagueofminecraft.event.eventhandler;

import com.mojang.blaze3d.platform.InputConstants;

import net.icecheese.leagueofminecraft.LeagueOfMinecraft;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill1_Item;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill2_Item;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill3_Item;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill4_Item;
import net.icecheese.leagueofminecraft.network.InputNetworkHandler;
import net.icecheese.leagueofminecraft.network.messages.PlayerAction;
import net.icecheese.leagueofminecraft.network.messages.PlayerActionPacket;
import net.icecheese.leagueofminecraft.player.overlay.ManaBarGui;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class EventHandler {

    @Mod.EventBusSubscriber(modid = LeagueOfMinecraft.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
    public static class ClientForgeEvent {
        // private static final int GLFW_KEY_X = 88;

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
                    InputNetworkHandler.INSTANCE.sendToServer(new PlayerActionPacket(PlayerAction.GeneralSkillUse));
                }
            }
            if (event.getKey() == InputConstants.KEY_X && event.getAction() == InputConstants.PRESS) {
                ItemStack mainHoldItem = Minecraft.getInstance().player.getMainHandItem();
                if (mainHoldItem.getItem() instanceof Skill2_Item) {
                    InputNetworkHandler.INSTANCE.sendToServer(new PlayerActionPacket(PlayerAction.GeneralSkillUse));
                }
            }
            if (event.getKey() == InputConstants.KEY_C && event.getAction() == InputConstants.PRESS) {
                ItemStack mainHoldItem = Minecraft.getInstance().player.getMainHandItem();
                if (mainHoldItem.getItem() instanceof Skill3_Item) {
                    InputNetworkHandler.INSTANCE.sendToServer(new PlayerActionPacket(PlayerAction.GeneralSkillUse));
                }
            }
            if (event.getKey() == InputConstants.KEY_V && event.getAction() == InputConstants.PRESS) {
                ItemStack mainHoldItem = Minecraft.getInstance().player.getMainHandItem();
                if (mainHoldItem.getItem() instanceof Skill4_Item) {
                    InputNetworkHandler.INSTANCE.sendToServer(new PlayerActionPacket(PlayerAction.LeeSin_Skill_4));
                }
            }

        }

        @SubscribeEvent
        public static void AdjustManaBarPositionOnScreen(InputEvent.Key event) {

            if (event.getKey() == InputConstants.KEY_DOWN) {
                ManaBarGui.yCon++;
            }
            if (event.getKey() == InputConstants.KEY_UP) {
                ManaBarGui.yCon--;
            }
            if (event.getKey() == InputConstants.KEY_LEFT) {
                ManaBarGui.xCon--;
            }
            if (event.getKey() == InputConstants.KEY_RIGHT) {
                ManaBarGui.xCon++;
            }
            if (event.getKey() == InputConstants.KEY_H) {
                ManaBarGui.hCon++;
            }
            if (event.getKey() == InputConstants.KEY_J) {
                ManaBarGui.jCon++;
            }
            if (event.getKey() == InputConstants.KEY_K) {
                ManaBarGui.kCon++;
            }

        }
    }

    
    
    @Mod.EventBusSubscriber(modid = LeagueOfMinecraft.MODID, bus = Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvent {

        @SubscribeEvent
        public static void registerOverlay(RegisterGuiOverlaysEvent event) {
            
            event.registerAboveAll("manabar", ManaBarGui.manaBar);

        }

    }
    

}
