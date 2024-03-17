package net.icecheese.leagueofminecraft.player;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class ClientManaSystem {

    public static float mana;
    public static float maxMana;
    public static float regenRate;
    public static LazyOptional<PlayerManaSystem> playerManaCap;

    public static void set(float mana, float maxMana, float regenRate) {
        ClientManaSystem.mana = mana;
        ClientManaSystem.maxMana = maxMana;
        ClientManaSystem.regenRate = regenRate;
    }

}
