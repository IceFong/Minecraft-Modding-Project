package net.icecheese.leagueofminecraft.characterskill.leesin.item;

import net.icecheese.leagueofminecraft.MyRegisterObjects;
import net.icecheese.leagueofminecraft.characterskill.leesin.entity.Skill2Entity;
import net.icecheese.leagueofminecraft.characterskill.leesin.handler.SoundHandler;
import net.icecheese.leagueofminecraft.player.mana.PlayerManaSystem;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class Skill2_Item extends Item {
    private boolean charged = false;
    private int charged_tick = 0;
    Vector3f colorEnd = new Vector3f(1, 1, 1);
    ParticleOptions particleData1 = new DustColorTransitionOptions(colorEnd, colorEnd, 2);

    public Skill2_Item(Properties p_41383_) {
        super(p_41383_);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand p_43144_) {
        ItemStack itemStack = player.getItemInHand(p_43144_);
        //  level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            if (!charged) {
                if (!PlayerManaSystem.CheckManaAmount(player, 15.0f)) {
                    return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
                }
                Skill2Entity $$4 = new Skill2Entity(level, player);
                $$4.setItem(itemStack);
                $$4.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity($$4);
                player.getCooldowns().addCooldown(this, 200);
            } else{
                if (!PlayerManaSystem.CheckManaAmount(player, 15.0f)) {
                    return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
                }
                player.getPersistentData().putBoolean("skill2_trigger",true);
                charged = false;
                player.getPersistentData().putBoolean("skill2_charge", false);
                player.getCooldowns().addCooldown(this, 10);
                ResourceLocation soundID = MyRegisterObjects.W_2_CAST.getId();
                SoundHandler.playSoundToNear((ServerPlayer) player, soundID, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        if (!entity.level().isClientSide && entity instanceof Player player) {
            itemStack.getOrCreateTag().putBoolean("charged",charged);
            if (player.getPersistentData().getBoolean("skill2_charge")) {
                player.getCooldowns().removeCooldown(this);
                this.charged = true;
                charged_tick++;
                if (charged_tick >= 100) {
                    player.getPersistentData().putBoolean("skill2_charge", false);
                    player.getCooldowns().addCooldown(this, 10);
                    this.charged = false;
                    charged_tick = 0;
                }
            }
        }
    }
}
