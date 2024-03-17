package net.icecheese.leagueofminecraft.characterskill.leesin.item;

import net.icecheese.leagueofminecraft.MyRegisterObjects;
import net.icecheese.leagueofminecraft.characterskill.leesin.handler.SoundHandler;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Skill3_Item extends Item {
    private boolean charged = false;
    private int charged_tick = 0;
    float red = 112f / 255.0f;
    float green = 239.0f / 255.0f;
    float blue = 1.0f;
    Vector3f colorStart = new Vector3f(red, green, blue);
    Vector3f colorEnd = new Vector3f(1, 1, 1);
    ParticleOptions particleData = new DustColorTransitionOptions(colorStart, colorEnd, 1);
    List<LivingEntity> hitEntities = null;
    int numParticles = 100;
    double angleStep = 2.0 * Math.PI / numParticles;

    public Skill3_Item(Properties p_41383_) {
        super(p_41383_);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level p_43142_, Player player, @NotNull InteractionHand p_43144_) {
        ItemStack itemStack = player.getItemInHand(p_43144_);
        if (!p_43142_.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) p_43142_;
            if (!charged) {
                // Deal area damage around the player and mark the hit entities
                hitEntities = p_43142_.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(4.0D));
                for (LivingEntity entity : hitEntities) {
                    if(!entity.getUUID().equals(player.getUUID())){
                        entity.hurt(serverLevel.damageSources().playerAttack(player), 5.0F);
                        serverLevel.sendParticles(particleData,entity.getX(),entity.getY()+1,entity.getZ(),30,0,0.25,0,0.25);
                    }
                }
                for (int i = 0; i < numParticles; i++) {
                    double angle = angleStep * i;
                    double x = player.getX() + 4.0 * Math.cos(angle);
                    double y = player.getY();
                    double z = player.getZ() + 4.0 * Math.sin(angle);
                    serverLevel.sendParticles(particleData,x,y,z,1,0,0,0,0.25);
                }
                ResourceLocation soundID = MyRegisterObjects.E_CAST.getId();
                SoundHandler.playSoundToNear((ServerPlayer) player, soundID, SoundSource.PLAYERS, 1.0F, 1.0F);
                serverLevel.sendParticles(particleData,player.getX(),player.getY(),player.getZ(),150,2,0,2,0.25);
                charged = true;
            } else {
                for (LivingEntity entity : hitEntities) {
                    if(!entity.getUUID().equals(player.getUUID())) {
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 5, 0));  // Apply Slowness I for 5 seconds
                        serverLevel.sendParticles(particleData,entity.getX(),entity.getY()+1,entity.getZ(),30,0,0.25,0,0.25);
                    }
                }
                charged = false;
                ResourceLocation soundID = MyRegisterObjects.E_2_CAST.getId();
                SoundHandler.playSoundToNear((ServerPlayer) player, soundID, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.getCooldowns().addCooldown(this,10);
            }
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, p_43142_.isClientSide());
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        if (!entity.level().isClientSide && entity instanceof Player player) {
            itemStack.getOrCreateTag().putBoolean("charged",charged);
            if (charged) {
                player.getCooldowns().removeCooldown(this);
                charged_tick++;
                if (charged_tick >= 100) {
                    player.getCooldowns().addCooldown(this,10);
                    this.charged = false;
                    charged_tick = 0;
                }
            }
        }
    }
}
