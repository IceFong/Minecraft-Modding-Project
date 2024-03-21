package net.icecheese.leagueofminecraft.characterskill.leesin.item;

import net.icecheese.leagueofminecraft.MyRegisterObjects;
import net.icecheese.leagueofminecraft.characterskill.leesin.entity.SkillEntity;
import net.icecheese.leagueofminecraft.characterskill.leesin.handler.SoundHandler;
import net.icecheese.leagueofminecraft.event.ManaEvent;
import net.icecheese.leagueofminecraft.player.PlayerCapabilities;
import net.icecheese.leagueofminecraft.player.PlayerManaSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.PlaySoundCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

import org.jetbrains.annotations.NotNull;

public class Skill1_Item extends Item {
    private boolean charged = false;
    private int charged_tick = 0;

    public Skill1_Item(Properties p_41383_) {
        super(p_41383_);
    }
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand p_43144_) {
        ItemStack itemStack = player.getItemInHand(p_43144_);
        //  level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (level.isClientSide) return InteractionResultHolder.fail(itemStack);

        // Q1
        if (!charged) {
            // Consume Mana Event
            if (!PlayerManaSystem.CheckManaAmount(player, 15.0f)) {
                return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
            }
            SkillEntity skillEntity = new SkillEntity(level, player);
            skillEntity.setItem(itemStack);
            skillEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(skillEntity);
            player.getCooldowns().addCooldown(this, 160);
            ResourceLocation soundID = MyRegisterObjects.Q_FIRES.getId();
            SoundHandler.playSoundToNear((ServerPlayer) player, soundID, SoundSource.PLAYERS, 1.0F, 1.0F);

        } 
        // Q2
        else {
            // Consume Mana Event
            if (!PlayerManaSystem.CheckManaAmount(player, 15.0f)) {
                return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
            }
            player.getCooldowns().addCooldown(this, 10);
            CompoundTag tag = player.getPersistentData();
            player.teleportTo(tag.getDouble("skill1_x"), tag.getDouble("skill1_y") + 1, tag.getDouble("skill1_z"));
            player.getPersistentData().putBoolean("skill1_charge", false);
            charged = false;
            ServerLevel serverLevel = (ServerLevel) level;
            Entity targetEntity = serverLevel.getEntity(tag.getUUID("skill1_charge_uuid"));
            if (targetEntity instanceof LivingEntity livingEntity) {
                livingEntity.getPersistentData().putBoolean("skill1_trigger", false);
                float maxHealth = livingEntity.getMaxHealth();
                float currentHealth = livingEntity.getHealth();
                float damage = (maxHealth - currentHealth) / 2;
                livingEntity.hurt(serverLevel.damageSources().playerAttack(player), 6 + damage);
            }
            ResourceLocation soundID = MyRegisterObjects.Q_2_CAST.getId();
            SoundHandler.playSoundToNear((ServerPlayer) player, soundID, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        
        player.awardStat(Stats.ITEM_USED.get(this));
        
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    public static boolean checkIsCharged(ItemStack itemStack){
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("charged");
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        if (!entity.level().isClientSide && entity instanceof Player player) {
            itemStack.getOrCreateTag().putBoolean("charged",charged);
            if (player.getPersistentData().getBoolean("skill1_charge")) {
                player.getCooldowns().removeCooldown(this);
                this.charged = true;
                charged_tick++;
                if (charged_tick >= 100) {
                    player.getPersistentData().putBoolean("skill1_charge", false);
                    player.getCooldowns().addCooldown(this, 10);
                    this.charged = false;
                    charged_tick = 0;
                    ServerLevel serverLevel = (ServerLevel) level;
                    CompoundTag tag = player.getPersistentData();
                    Entity targetEntity = serverLevel.getEntity(tag.getUUID("skill1_charge_uuid"));
                    if (targetEntity instanceof LivingEntity livingEntity) {
                        livingEntity.getPersistentData().putBoolean("skill1_trigger", false);
                    }
                }
            }
        }
    }
}
