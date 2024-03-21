package net.icecheese.leagueofminecraft.characterskill.leesin.item;

import net.icecheese.leagueofminecraft.MyRegisterObjects;
import net.icecheese.leagueofminecraft.characterskill.leesin.handler.SoundHandler;
import net.icecheese.leagueofminecraft.player.PlayerManaSystem;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class Skill4_Item extends Item {
    float red = 112f / 255.0f;
    float green = 239.0f / 255.0f;
    float blue = 1.0f;
    Vector3f colorStart = new Vector3f(red, green, blue);
    Vector3f colorEnd = new Vector3f(1, 1, 1);
    ParticleOptions particleData = new DustColorTransitionOptions(colorStart, colorEnd, 1);

    public Skill4_Item(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResult interactLivingEntity(ItemStack p_41398_, Player player, LivingEntity livingEntity, InteractionHand p_41401_) {
        ItemStack itemStack = player.getItemInHand(p_41401_);
        if (!player.level().isClientSide && !player.getCooldowns().isOnCooldown(p_41398_.getItem())) {
            // Consume Mana Event
            if (!PlayerManaSystem.CheckManaAmount(player, 0.0f)) {
                return InteractionResultHolder.pass(itemStack).getResult();
            }
            ServerLevel serverLevel = (ServerLevel) player.level();
            livingEntity.setDeltaMovement(livingEntity.position().subtract(player.position()).normalize().scale(2.0D));
            livingEntity.hurt(serverLevel.damageSources().playerAttack(player), 10);
            livingEntity.getPersistentData().putBoolean("skill4_trigger", true);
            livingEntity.getPersistentData().putUUID("skill4_trigger_uuid", player.getUUID());
            player.getCooldowns().addCooldown(this, 20);
            player.awardStat(Stats.ITEM_USED.get(this));
            ResourceLocation soundID = MyRegisterObjects.R_CAST.getId();
            SoundHandler.playSoundToNear((ServerPlayer) player, soundID, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.sidedSuccess(itemStack, player.level().isClientSide()).getResult();
        }
        return InteractionResultHolder.pass(itemStack).getResult();
    }

}
