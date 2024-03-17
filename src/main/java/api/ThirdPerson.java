package api;

import io.socol.betterthirdperson.BetterThirdPerson;
import io.socol.betterthirdperson.CustomCameraConfigImpl;
import io.socol.betterthirdperson.CustomCameraListener;
import io.socol.betterthirdperson.api.CustomCameraManager;
import io.socol.betterthirdperson.impl.ClientAdapter;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class ThirdPerson {

    private static CustomCameraManager manager;
    
    public ThirdPerson() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            manager = new CustomCameraManager(ClientAdapter.INSTANCE);
            manager.setConfig(CustomCameraConfigImpl.create());
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        }
   }

    // @Mod.EventBusSubscriber( modid = LeagueOfMinecraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT )
    // private static class ThirdPersonClassEvent {
    //     @SubscribeEvent
    private void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new CustomCameraListener(manager));
    }
    // }
    
   public static CustomCameraManager getCameraManager() {
      return manager;
   }

}
