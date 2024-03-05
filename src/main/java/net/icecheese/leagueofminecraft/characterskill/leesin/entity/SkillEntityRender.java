package net.icecheese.leagueofminecraft.characterskill.leesin.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;

public class SkillEntityRender<T extends Entity & ItemSupplier> extends ThrownItemRenderer<T> {
    public SkillEntityRender(EntityRendererProvider.Context p_174416_, float p_174417_, boolean p_174418_) {
        super(p_174416_, p_174417_, p_174418_);
    }

    public SkillEntityRender(EntityRendererProvider.Context p_174414_) {
        super(p_174414_);
    }

    @Override
    public void render(T p_116085_, float p_116086_, float p_116087_, PoseStack p_116088_, MultiBufferSource p_116089_, int p_116090_) {
    }

}
