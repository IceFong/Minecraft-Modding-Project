package net.icecheese.leagueofminecraft.characterskill.leesin.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SoundHandler {
    public static void playSoundToNear(ServerPlayer player, ResourceLocation soundID, SoundSource category, float volume, float pitch) {
        BlockPos pos = player.blockPosition();
        Vec3 vec3 = new Vec3(pos.getX(), pos.getY(), pos.getZ());
        Holder<SoundEvent> holder = Holder.direct(SoundEvent.createVariableRangeEvent(soundID));
        ClientboundSoundPacket packet = new ClientboundSoundPacket(holder, category, vec3.x(), vec3.y(), vec3.z(), volume, pitch, 0);

        List<ServerPlayer> nearbyPlayers = Objects.requireNonNull(player.getServer()).getPlayerList().getPlayers().stream()
                .filter(p -> p.distanceToSqr(vec3) <= 32 * 32)
                .toList();

        for (ServerPlayer nearbyPlayer : nearbyPlayers) {
            nearbyPlayer.connection.send(packet);
        }
    }
    public static void playSoundToNear(Entity entity, ResourceLocation soundID, SoundSource category, float volume, float pitch) {
        if(!entity.level().isClientSide){
            BlockPos pos = entity.blockPosition();
            Vec3 vec3 = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            Holder<SoundEvent> holder = Holder.direct(SoundEvent.createVariableRangeEvent(soundID));
            ClientboundSoundPacket packet = new ClientboundSoundPacket(holder, category, vec3.x(), vec3.y(), vec3.z(), volume, pitch, 0);

            ServerLevel serverLevel =(ServerLevel)entity.level();
            List<ServerPlayer> nearbyPlayers = serverLevel.players().stream()
                    .filter(p -> p.distanceToSqr(vec3) <= 32 * 32)
                    .toList();

            for (ServerPlayer nearbyPlayer : nearbyPlayers) {
                nearbyPlayer.connection.send(packet);
            }
        }
    }
}

