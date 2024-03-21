package net.icecheese.leagueofminecraft.player;

import net.icecheese.leagueofminecraft.player.mana.PlayerManaSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerCapabilities implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerManaSystem> PLAYER_MANA_SYS = CapabilityManager.get(new CapabilityToken<PlayerManaSystem>() {
        
    });

    private PlayerManaSystem playerManaSystem;
    private final LazyOptional<PlayerManaSystem> optional = LazyOptional.of(this::createPlayerManaSystem);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_MANA_SYS) {
            return optional.cast();
        }
        
        return LazyOptional.empty();
    }

    // Generate 100 mana, 100 maxMana and 0.01 rate (Could adjust in the future)
    private <T> @NotNull PlayerManaSystem createPlayerManaSystem() {
        if (this.playerManaSystem == null) {
            this.playerManaSystem = new PlayerManaSystem(100.0f, 100.0f, 10.0f);
        }

        return this.playerManaSystem;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerManaSystem().save(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerManaSystem().load(nbt);
    }

}
