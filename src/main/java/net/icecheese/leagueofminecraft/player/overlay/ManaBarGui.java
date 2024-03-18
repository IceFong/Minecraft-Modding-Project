package net.icecheese.leagueofminecraft.player.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;

import net.icecheese.leagueofminecraft.LeagueOfMinecraft;
import net.icecheese.leagueofminecraft.player.ClientManaSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

// @OnlyIn(Dist.CLIENT)
public class ManaBarGui extends Gui {

    private Minecraft mc;
    private static ResourceLocation manaBarBase = new ResourceLocation(LeagueOfMinecraft.MODID, "textures/gui/mana_bar_base.png");
    private static ResourceLocation manaBarContent = new ResourceLocation(LeagueOfMinecraft.MODID, "textures/gui/mana_bar_content.png");

    public ManaBarGui(Minecraft p_232355_, ItemRenderer p_232356_) {
        super(p_232355_, p_232356_);
        this.mc = p_232355_;
    }

    /*
        blit(
            int xPos,		// x position relative to the screen image below it (not the entire screen).
            int yPos,		// y position relative to the screen image below it (not the entire screen).
            int blitOffset,		// z position (blitOffSet)
            float textureX,		// x position on the texture image to draw from
            float textureY,		// y position on the texture image to draw from
            int imgSizeX,		// x image size to display (like crop in PS)
            int imgSizeY,		// y image size to display (like crop in PS)
            int scaleY,		// y image size (will scale image to fit)
            int scalex)		// x image size (will scale image to fit)
    */

    public static final IGuiOverlay manaBar = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        float mana = ClientManaSystem.mana;
        float maxMana = ClientManaSystem.maxMana;
        int manaBarWidth = (int) (mana / maxMana);

        int width = screenWidth / 2;
        int height = screenHeight;

        guiGraphics.blit(manaBarBase, width, height, 0, 0.0f, 0, 32, 32, 32, 32);

        // guiGraphics.drawCenteredString(Minecraft.getInstance ().font, "TestString", screenWidth/2, screenHeight/2, 40);
        
        // guiGraphics.drawCenteredString(n, "TestString", (int) screenWidth/2, (int) screenHeight/2, 1);

    });

    

    // @SubscribeEvent
    // public void onRenderManaBar(RenderGuiOverlayEvent.Pre event) {

    //     // if (event.getPhase() == EventPriority.HIGH) {}
    //     GuiGraphics guiGraphics = event.getGuiGraphics();
        
    //     float mana = ClientManaSystem.mana;
    //     float maxMana = ClientManaSystem.maxMana;
    //     int manaBarWidth = (int) (mana / maxMana);

    //     int width = event.getWindow().getWidth() / 2;
    //     int height = event.getWindow().getHeight();

    //     guiGraphics.blit(manaBarBase, width, height, 0, 0, 16, 16);
        

    // }
    


}
