package net.icecheese.leagueofminecraft.network.messages;

import net.icecheese.leagueofminecraft.player.PlayerManaSystem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public class ManaPacket {

    public float mana;
    public float maxMana;
    public float regenRate;

    public ManaPacket( PlayerManaSystem sys ) {
        this.mana = sys.mana;
        this.maxMana = sys.maxMana;
        this.regenRate = sys.regenRate;
    }

    public ManaPacket(float mana, float maxMana, float regenRate) {
        this.mana = mana;
        this.maxMana = maxMana;
        this.regenRate = regenRate;
    }

    public void Encoder(FriendlyByteBuf buf) {
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat("leagueofminecraft.manapacket.mana", mana);
        nbt.putFloat("leagueofminecraft.manapacket.maxMana", maxMana);
        nbt.putFloat("leagueofminecraft.manapacket.regenRate", regenRate);
        buf.writeNbt(nbt);
    }

    public static ManaPacket Decoder(FriendlyByteBuf buf) {
        CompoundTag nbt = buf.readNbt();
        float mana = nbt.getFloat("leagueofminecraft.manapacket.mana");
        float maxMana = nbt.getFloat("leagueofminecraft.manapacket.maxMana");
        float regenRate = nbt.getFloat("leagueofminecraft.manapacket.regenRate");
        
        return new ManaPacket(mana, maxMana, regenRate);
    }

}