package net.icecheese.leagueofminecraft.characterskill.leesin.mixin;

import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
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

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    int skill4TriggerTick = 0;
    float red = 112f / 255.0f;
    float green = 239.0f / 255.0f;
    float blue = 1.0f;
    Vector3f colorStart = new Vector3f(red, green, blue);
    Vector3f colorEnd = new Vector3f(1, 1, 1);
    ParticleOptions particleData = new DustColorTransitionOptions(colorStart, colorEnd, 2);
    ParticleOptions particleData1 = new DustColorTransitionOptions(colorStart, colorEnd, 5);
    public MixinLivingEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void onTick(CallbackInfo info) {
        if(!this.level().isClientSide){
            if(this.getPersistentData().getBoolean("skill1_trigger")){
                ServerLevel serverLevel = (ServerLevel) this.level();
                serverLevel.sendParticles(particleData,this.getX(),this.getY()+2,this.getZ(),1,0.1,0.2,0.1,1);
                serverLevel.sendParticles(particleData1,this.getX(),this.getY()-0.05,this.getZ(),4,0.2,0,0.2,1);
            }
            if(this.getPersistentData().getBoolean("skill4_trigger")){
                skill4TriggerTick++;
                ServerLevel serverLevel = (ServerLevel) this.level();
                serverLevel.sendParticles(particleData1,this.getX(),this.getY()+1,this.getZ(),1,0,0.1,0,1);
                serverLevel.sendParticles(particleData,this.getX(),this.getY()+1,this.getZ(),4,0.2,0.2,0,1);
                if(skill4TriggerTick >= 30){
                    skill4TriggerTick = 0;
                    this.getPersistentData().putBoolean("skill4_trigger",false);
                }
            }
        }
    }
    @Inject(method = "pushEntities()V", at = @At("HEAD"))
    public void onPushEntities(CallbackInfo ci) {
        if(!this.level().isClientSide){
            ServerLevel serverLevel = (ServerLevel) this.level();
            if (this.getPersistentData().getBoolean("skill4_trigger")) {
                LivingEntity livingEntity = (LivingEntity) serverLevel.getEntity(this.getPersistentData().getUUID("skill4_trigger_uuid"));
                List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), EntitySelector.pushableBy(this));
                for (Entity entity : list) {
                    if (entity instanceof LivingEntity && livingEntity instanceof Player player) {
                        entity.hurt(serverLevel.damageSources().playerAttack(player), 6.0F);
                    }
                }
            }
        }
    }



    @Inject(method = "hurt", at = @At("HEAD"))
    public void onHurt(DamageSource p_36154_, float p_36155_, CallbackInfoReturnable<Boolean> cir) {
        if(!this.level().isClientSide){
            if(p_36154_.getEntity() != null && p_36154_.getEntity() instanceof Player player && player.getPersistentData().getBoolean("skill2_trigger")){
                player.heal(p_36155_ / 8);
            }
        }
    }
}