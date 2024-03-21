package net.icecheese.leagueofminecraft.player.overlay;

import com.mojang.blaze3d.systems.RenderSystem;

import net.icecheese.leagueofminecraft.LeagueOfMinecraft;
import net.icecheese.leagueofminecraft.player.mana.ClientManaSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

// @OnlyIn(Dist.CLIENT)
public class ManaBarGui extends Gui {

    private Minecraft mc;
    private static ResourceLocation manaBarBase = new ResourceLocation(LeagueOfMinecraft.MODID, "textures/gui/mana_bar_base.png");
    private static ResourceLocation manaBarContent = new ResourceLocation(LeagueOfMinecraft.MODID, "textures/gui/mana_bar_content.png");
    private static ResourceLocation testPNG = new ResourceLocation(LeagueOfMinecraft.MODID, "textures/gui/test.png");
    private static int barWidthPixels = 32;
    private static int barHeightPixels = 8;


    public ManaBarGui(Minecraft p_232355_, ItemRenderer p_232356_) {
        super(p_232355_, p_232356_);
        this.mc = p_232355_;
    }

    /*
        GuiGraphics.blit(
            ResourceLocation,
            int px, int py, (x, y position to render on the screen)
            float xTexture, float yTexture, (x, y position in the image to start at)
            int xImage, int yImage, (size of the image should be)
            int xScalar, int yScalar (scale image to x*y <not given is 256 * 256>)
     */
    private static float f_1 = 0.0f;
    private static int i_1 = 0;
    public static int xCon = 36;
    public static int yCon = 0;
    public static int hCon = 0;
    public static int jCon = 0;
    public static int kCon = 0;
    public static final IGuiOverlay manaBar = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        float mana = ClientManaSystem.mana;
        float maxMana = ClientManaSystem.maxMana;
        float progress = (float) (mana / maxMana);
    
        // int px = screenWidth / 8 + xCon;
        // int py = screenHeight * 9 / 10 + yCon;
        int px = 0;
        int py = 0;
        float xScalar = 3.5f;
        float yScalar = 1.5f;
        int xInnerCenter = (int) (barWidthPixels * xScalar) / 2;
        int yInnerCenter = (int) (barHeightPixels * yScalar) / 2;

        RenderSystem.enableBlend();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, screenHeight - barHeightPixels * yScalar, 0);
        innerScaleAndProgressBlit(guiGraphics, manaBarContent, px, py, barWidthPixels, barHeightPixels, xScalar, yScalar, progress);
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, screenHeight - barHeightPixels * yScalar, 0);
        innerScaleBlit(guiGraphics, manaBarBase, px, py, barWidthPixels, barHeightPixels, xScalar, yScalar);
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((float) barWidthPixels * xScalar / 2.0f, screenHeight - (barHeightPixels * yScalar), 0);
        String manaString = ((int)mana) + "/" + ((int)maxMana);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, manaString, 0, 0, 0);
        guiGraphics.pose().popPose();
        RenderSystem.disableBlend();


        // Up going method
//        guiGraphics.blit(testPNG, 0, (int) (72 - f_1), 36.0f, 72.0f - f_1, 36, (int)f_1, 72, 72);

//        UpGoingBlit(guiGraphics, testPNG, 0, 72, 36.0f, 72.0f, 72, 72, f_1);
//        f_1 += 0.01f;
//        f_1 = f_1 % 1.00f;


    });

    private  static void UpGoingBlit(GuiGraphics guiGraphics, ResourceLocation resourceLocation, int px, int py, float xTexture, float yTexture, int xScalar, int yScalar, float percentage) {
        float f_1 = percentage * yScalar;
        int ppy = (int) (py - f_1);
        int yyTexture = (int) (yTexture - f_1);

        guiGraphics.blit(resourceLocation, px, ppy, xTexture, yyTexture, (int) xTexture, (int)f_1, xScalar, yScalar);
    }

    private static void innerBlit(GuiGraphics guiGraphics, ResourceLocation pAtlasLocation, int pX, int pY, int pWidth, int pHeight) {
        guiGraphics.blit(pAtlasLocation, pX, pY, 0, 0, pWidth, pHeight, pWidth, pHeight);
    }

    private static void innerScaleBlit(GuiGraphics guiGraphics, ResourceLocation pAtlasLocation, int pX, int pY, int pWidth, int pHeight, float xScalar, float yScalar) {
        innerBlit(guiGraphics, pAtlasLocation, pX, pY, (int) (pWidth * xScalar), (int) (pHeight * yScalar));
    }

    private static void innerScaleAndProgressBlit(GuiGraphics guiGraphics, ResourceLocation pAtlasLocation, int pX, int pY, int pWidth, int pHeight, float xScalar, float yScalar, float progress) {
        guiGraphics.blit(pAtlasLocation, pX, pY, 0, 0, (int) ((pWidth * xScalar) * progress), (int) (pHeight * yScalar), (int) (pWidth * xScalar), (int) (pHeight * yScalar));
    }

    

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
