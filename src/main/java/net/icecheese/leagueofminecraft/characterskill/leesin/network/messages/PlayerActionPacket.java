package net.icecheese.leagueofminecraft.characterskill.leesin.network.messages;

import net.minecraft.network.FriendlyByteBuf;

public class PlayerActionPacket {

    PlayerAction action; 

    public PlayerActionPacket(PlayerAction action) {
        this.action = action;
    }

    public void Encoder(FriendlyByteBuf buf) {
        if (action != null) {
            buf.writeEnum(action);
        }
    }

    public static PlayerActionPacket Decoder(FriendlyByteBuf buf) {
        return new PlayerActionPacket(buf.readEnum(PlayerAction.class));
    }

    public PlayerAction getAction() {
        return this.action;
    }

}