package net.icecheese.leagueofminecraft.player;

import net.icecheese.leagueofminecraft.player.levelitem.PlayerLevelItems;
import net.icecheese.leagueofminecraft.player.levelitem.PlayerLevelItems;
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
    public static Capability<PlayerLevelItems> PLAYER_LEVEL_ITEM = CapabilityManager.get(new CapabilityToken<PlayerLevelItems>() {

    });

    private PlayerManaSystem playerManaSystem;
    private PlayerLevelItems playerLevelItems;
    private final LazyOptional<PlayerManaSystem> optional = LazyOptional.of(this::createPlayerManaSystem);
    private final LazyOptional<PlayerLevelItems> optionalLevelItem = LazyOptional.of(this::createPlayerLevelItems);

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
    private <T> @NotNull PlayerLevelItems createPlayerLevelItems() {
        if (this.playerLevelItems == null) {
            this.playerLevelItems = new PlayerLevelItems();
        }

        return this.playerLevelItems;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = createPlayerManaSystem().save();
        CompoundTag nbt2 = createPlayerLevelItems().save();

        CompoundTag ret = new CompoundTag();
        ret.put("leagueofminecraft.capabilities.manasystem", nbt);
        ret.put("leagueofminecraft.capabilities.levelitems", nbt2);
        return ret;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        CompoundTag nbt = (CompoundTag) tag.get("leagueofminecraft.capabilities.manasystem");
        CompoundTag nbt2 = (CompoundTag) tag.get("leagueofminecraft.capabilities.levelitems");
        createPlayerManaSystem().load(nbt);
        createPlayerLevelItems().load(nbt2);
    }

}
