package net.icecheese.leagueofminecraft.characterskill.leesin.entity;

import net.icecheese.leagueofminecraft.characterskill.MyRegisterObjects;
import net.icecheese.leagueofminecraft.characterskill.leesin.handler.SoundHandler;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class SkillEntity extends ThrowableItemProjectile {
    private int life_time = 0;

    float red = 112f / 255.0f;
    float green = 239.0f / 255.0f;
    float blue = 1.0f;
    Vector3f colorStart = new Vector3f(red, green, blue);
    Vector3f colorEnd = new Vector3f(1, 1, 1);
    ParticleOptions particleData = new DustColorTransitionOptions(colorStart, colorEnd, 1);

    public SkillEntity(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
    }

    public SkillEntity(Level p_37499_, LivingEntity p_37500_) {
        super(MyRegisterObjects.SkillEntity.get(), p_37500_, p_37499_);
    }

    @Override
    protected Item getDefaultItem() {
        return MyRegisterObjects.SKILL1_ITEM.get();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) this.level();
            life_time++;
            serverLevel.sendParticles(particleData,this.getX(),this.getY(),this.getZ(),1,0,0,0,0.25);
            if (life_time > 120) {
                this.level().broadcastEntityEvent(this, (byte) 3);
                this.discard();
            }
        }
    }

    protected void onHit(HitResult p_37406_) {
        super.onHit(p_37406_);
        if (!this.level().isClientSide) {
            if (this.getOwner() instanceof Player player) {
                if (p_37406_ instanceof EntityHitResult result) {
                    if(result.getEntity() instanceof LivingEntity livingEntity){
                        Vec3 vec3 = result.getLocation();
                        ServerLevel serverLevel = (ServerLevel) this.level();
                        livingEntity.getPersistentData().putBoolean("skill1_trigger",true);
                        player.getPersistentData().putUUID("skill1_charge_uuid",livingEntity.getUUID());
                        player.getPersistentData().putDouble("skill1_x",vec3.x);
                        player.getPersistentData().putDouble("skill1_y",vec3.y);
                        player.getPersistentData().putDouble("skill1_z",vec3.z);
                        player.getPersistentData().putBoolean("skill1_charge",true);
                        livingEntity.hurt(serverLevel.damageSources().playerAttack(player), 2);
                        ResourceLocation soundID = MyRegisterObjects.Q_HIT.getId();
                        SoundHandler.playSoundToNear(this, soundID, SoundSource.PLAYERS, 1.0F, 1.0F);
                        /*
                        MotionPacket packet = new MotionPacket(result.getLocation().scale(1.25F).toVector3f(), player.getUUID());
                        LOLSkill.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), packet);*/
                    }
                }
                player.getCooldowns().removeCooldown(MyRegisterObjects.SKILL1_ITEM.get());
                player.getCooldowns().addCooldown(MyRegisterObjects.SKILL1_ITEM.get(),10);
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }
}
