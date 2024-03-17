package net.icecheese.leagueofminecraft.player;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.joml.Math;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class PlayerManaSystem  {

    // 1. Add mana system to player (done)
    // 2. Mana can be consume and automatically regenerate
    // 3. load & save when player enter or leave

    public float mana = 0.0f;
    public float maxMana = 0.0f;
    public float regenRate = 0.0f;

    public PlayerManaSystem() {

    }

    public PlayerManaSystem(float mana, float maxMana, float regenRate) {
        this.mana = mana;
        this.maxMana = maxMana;
        this.regenRate = regenRate;
    }

    public void RegenMana() {
        this.mana = Math.min(maxMana, mana + regenRate);
    }

    public void AddMana(float add) {
        this.mana = Math.min(maxMana, mana + add);
    }

    public void SubMana(float sub) {
        this.mana = Math.max(0.0f, mana - sub);
    }

    public void AddMaxMana(float add) {
        this.maxMana += add;
    }

    public void SubMaxMana(float sub) {
        this.maxMana = Math.max(0.0f, maxMana - sub);
    }

    public void AddRegenRate(float add) {
        this.regenRate += add;
    }

    public void SubRegenRate(float sub) {
        this.regenRate = Math.max(0.0f, regenRate - sub);
    }

    public void SetMana(float mana) {
        this.mana = mana;
    }

    public void SetMaxMana(float max) {
        this.maxMana = max;
    }

    public void SetRegenRate(float rate) {
        this.regenRate = rate;
    }

    public boolean hasEnoughMana( float amount ) {
        return (amount >= this.mana) ? true : false;
    }

    public void load(CompoundTag tag) {
        
        this.mana = tag.getFloat("leagueofminecraft.mana");
        this.maxMana = tag.getFloat("leagueofminecraft.maxMana");
        this.regenRate = tag.getFloat("leagueofminecraft.regenRate");

    }

    public void save(CompoundTag tag) {

        tag.putFloat("leagueofminecraft.mana", this.mana);
        tag.putFloat("leagueofminecraft.maxMana", this.maxMana);
        tag.putFloat("leagueofminecraft.regenRate", this.regenRate);

    }

    public void DisplayInGame(Player player) {
        // LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            String s = "Mana: " + String.valueOf(Math.floor(mana));
            player.sendSystemMessage(Component.literal(s));
        }
    }

    public void CopyFrom(PlayerManaSystem pms) {
        this.mana = pms.mana;
        this.regenRate = pms.regenRate;
    }

}
