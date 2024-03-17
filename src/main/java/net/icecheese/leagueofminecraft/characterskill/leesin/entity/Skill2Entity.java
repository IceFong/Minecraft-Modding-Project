package net.icecheese.leagueofminecraft.characterskill.leesin.entity;

import net.icecheese.leagueofminecraft.LeagueOfMinecraft;
import net.icecheese.leagueofminecraft.MyRegisterObjects;
import net.icecheese.leagueofminecraft.characterskill.leesin.handler.SoundHandler;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.joml.Vector3f;

public class Skill2Entity extends ThrowableItemProjectile {
    private int life_time = 0;

    float red = 112f / 255.0f;
    float green = 239.0f / 255.0f;
    float blue = 1.0f;
    Vector3f colorStart = new Vector3f(red, green, blue);
    Vector3f colorEnd = new Vector3f(1, 1, 1);
    ParticleOptions particleData = new DustColorTransitionOptions(colorStart, colorEnd, 2);
    ParticleOptions particleData1 = new DustColorTransitionOptions(colorStart, colorEnd, 1);

    public Skill2Entity(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
    }

    public Skill2Entity(Level p_37499_, LivingEntity p_37500_) {
        super(MyRegisterObjects.Skill2Entity.get(), p_37500_, p_37499_);
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
            ServerLevel serverLevel = (ServerLevel) this.level();
            if (this.getOwner() instanceof Player player) {
                MobEffectInstance effect = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 1);
                if (p_37406_ instanceof BlockHitResult result) {
                    Block hitBlock = this.level().getBlockState(result.getBlockPos()).getBlock();
                    Block hitUpBlock = this.level().getBlockState(result.getBlockPos().above()).getBlock();
                    if (hitBlock == Blocks.SOUL_LANTERN || hitBlock == Blocks.LANTERN || hitUpBlock == Blocks.TORCH || hitUpBlock == Blocks.REDSTONE_TORCH || hitUpBlock == Blocks.SOUL_TORCH || hitBlock == Blocks.CAMPFIRE) {
                        serverLevel.sendParticles(particleData,result.getLocation().x,result.getLocation().y+0.25,result.getLocation().z,50,0.1,0.2,0.1,0.25);
                        /*
                        MotionPacket packet = new MotionPacket(result.getLocation().scale(1.25F).toVector3f(), player.getUUID());
                        LOLSkill.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), packet);*/
                        player.teleportTo(result.getLocation().x,result.getLocation().y+1,result.getLocation().z);
                    }else{
                        serverLevel.sendParticles(particleData,player.getX(),player.getY(),player.getZ(),50,0.5,1,0.5,1);
                    }
                    player.getPersistentData().putBoolean("skill2_charge",true);
                    player.addEffect(effect);
                    ResourceLocation soundID = MyRegisterObjects.W_CAST.getId();
                    SoundHandler.playSoundToNear(this, soundID, SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.getCooldowns().removeCooldown(MyRegisterObjects.SKILL2_ITEM.get());
                } else if (p_37406_ instanceof EntityHitResult result) {
                    if(result.getEntity() instanceof Player livingEntity){
                        serverLevel.sendParticles(particleData,livingEntity.getX(),livingEntity.getY()+0.5,livingEntity.getZ(),50,0.5,1,0.5,1);
                        serverLevel.sendParticles(particleData,player.getX(),player.getY()+0.5,player.getZ(),50,0.5,1,0.5,1);
                        player.getPersistentData().putBoolean("skill2_charge",true);
                        /*
                        MotionPacket packet = new MotionPacket(result.getLocation().scale(1.25F).toVector3f(), player.getUUID());
                        LOLSkill.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), packet);*/
                        player.teleportTo(result.getLocation().x,result.getLocation().y,result.getLocation().z);
                        player.addEffect(effect);
                        livingEntity.addEffect(effect);
                        ResourceLocation soundID = MyRegisterObjects.W_CAST.getId();
                        SoundHandler.playSoundToNear(this, soundID, SoundSource.PLAYERS, 1.0F, 1.0F);
                        player.getCooldowns().removeCooldown(MyRegisterObjects.SKILL2_ITEM.get());
                    }
                }else{
                    player.getCooldowns().removeCooldown(MyRegisterObjects.SKILL2_ITEM.get());
                }
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
