package net.icecheese.leagueofminecraft.network.messages;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Vector3f;

import java.util.UUID;

public class MotionPacket {
    Vector3f vec3f;
    UUID uuid;

    public MotionPacket(Vector3f vec3f, UUID uuid){
        this.vec3f = vec3f;
        this.uuid = uuid;
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeVector3f(this.vec3f);
        friendlyByteBuf.writeUUID(this.uuid);
    }

    public static MotionPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new MotionPacket(friendlyByteBuf.readVector3f(),friendlyByteBuf.readUUID());
    }

    public Vector3f getVec3f() {
        return vec3f;
    }

    public UUID getUuid() {
        return this.uuid;
    }

}
