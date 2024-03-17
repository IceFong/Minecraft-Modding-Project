package net.icecheese.leagueofminecraft;

import com.mojang.logging.LogUtils;

import api.ThirdPerson;

import org.slf4j.Logger;

import net.icecheese.leagueofminecraft.characterskill.leesin.entity.SkillEntityRender;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill1_Item;
import net.icecheese.leagueofminecraft.network.InputNetworkHandler;
import net.icecheese.leagueofminecraft.network.ManaNetworkHandler;
import net.icecheese.leagueofminecraft.network.messages.ManaPacket;
import net.icecheese.leagueofminecraft.network.messages.PlayerActionPacket;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(LeagueOfMinecraft.MODID)
public class LeagueOfMinecraft {

    public static int PACKET_ID = 0;
    public static final String MODID = "leagueofminecraft";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static ThirdPerson thirdPerson;

    public LeagueOfMinecraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Objects
        MyRegisterObjects.ITEMS.register(modEventBus);
        MyRegisterObjects.THROWN.register(modEventBus);
        MyRegisterObjects.CREATIVE_MODE_TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.post(null);

        // Network
        InputNetworkHandler.INSTANCE.registerMessage(
                PACKET_ID++, 
                PlayerActionPacket.class, 
                PlayerActionPacket::Encoder, 
                PlayerActionPacket::Decoder, 
                InputNetworkHandler::handle);
        ManaNetworkHandler.INSTANCE.registerMessage(
                PACKET_ID++, 
                ManaPacket.class, 
                ManaPacket::Encoder, 
                ManaPacket::Decoder, 
                ManaNetworkHandler::handle);

        // Third person camera
        thirdPerson = new ThirdPerson();
    }



    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetupEvent(FMLClientSetupEvent event) {
            ItemProperties.register(MyRegisterObjects.SKILL1_ITEM.get(), new ResourceLocation("charged"), (stack, world, entity, seed) -> Skill1_Item.checkIsCharged(stack) ? 1:0);
            ItemProperties.register(MyRegisterObjects.SKILL2_ITEM.get(), new ResourceLocation("charged"), (stack, world, entity, seed) -> Skill1_Item.checkIsCharged(stack) ? 1:0);
            ItemProperties.register(MyRegisterObjects.SKILL3_ITEM.get(), new ResourceLocation("charged"), (stack, world, entity, seed) -> Skill1_Item.checkIsCharged(stack) ? 1:0);
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(MyRegisterObjects.SkillEntity.get(), SkillEntityRender::new);
            event.registerEntityRenderer(MyRegisterObjects.Skill2Entity.get(), SkillEntityRender::new);
        }
    }
}

