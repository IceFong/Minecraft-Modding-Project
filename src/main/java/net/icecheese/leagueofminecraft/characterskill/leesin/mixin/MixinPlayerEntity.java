package net.icecheese.leagueofminecraft.characterskill.leesin.mixin;

import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    private int triggerTick = 0;
    protected MixinPlayerEntity(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void onTick(CallbackInfo info) {
        if(!this.level().isClientSide){
            if(this.getPersistentData().getBoolean("skill2_trigger")){
                triggerTick++;
                if(triggerTick>=160){
                    triggerTick = 0;
                    this.getPersistentData().putBoolean("skill2_trigger",false);
                }
            }
        }
    }
}