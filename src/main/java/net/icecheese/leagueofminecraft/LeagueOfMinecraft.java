package net.icecheese.leagueofminecraft;

import com.mojang.logging.LogUtils;

import org.joml.Vector3f;
import org.slf4j.Logger;

import net.icecheese.leagueofminecraft.characterskill.MyRegisterObjects;
import net.icecheese.leagueofminecraft.characterskill.leesin.entity.Skill2Entity;
import net.icecheese.leagueofminecraft.characterskill.leesin.entity.SkillEntity;
import net.icecheese.leagueofminecraft.characterskill.leesin.entity.SkillEntityRender;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill1_Item;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
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

    public static final String MODID = "leagueofminecraft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LeagueOfMinecraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        /*
         * Network packet
         */
        // INSTANCE.registerMessage(messageIndex++, MotionPacket.class, MotionPacket::encode, MotionPacket::decode, (packet, context) -> {
        //     context.get().enqueueWork(() -> {
        //         AbstractClientPlayer player = null;
        //         if (Minecraft.getInstance().level != null) {
        //             player = (AbstractClientPlayer) Minecraft.getInstance().level.getPlayerByUUID(packet.getUuid());
        //         }
        //         if (player != null) {
        //             Vector3f vector3f = packet.getVec3f();
        //             Vec3 vec3d = new Vec3(vector3f.x,vector3f.y,vector3f.z);
        //             Vec3 vec3d_ = (new Vec3(vec3d.x - player.getX(), vec3d.y - player.getY(), vec3d.z - player.getZ()));
        //             player.setDeltaMovement(player.getDeltaMovement().add(vec3d_));
        //         }
        //     });
        //     context.get().setPacketHandled(true);
        // });
        MyRegisterObjects.ITEMS.register(modEventBus);
        MyRegisterObjects.THROWN.register(modEventBus);
        MyRegisterObjects.CREATIVE_MODE_TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
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

