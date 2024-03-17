package net.icecheese.leagueofminecraft.event;

import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class ManaEvent extends Event {

    public Player player;

    public ManaEvent(Player player) {
        this.player = player;
    }

    @Cancelable
    public static class ConsumeManaEvent extends ManaEvent {
        
        public float amount;
        
        public ConsumeManaEvent(Player player, float amount) {
            super(player);
            this.amount = amount;
        }

    }

}
