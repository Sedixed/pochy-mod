package net.sedixed.pochy.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sedixed.pochy.PochyMod;
import net.sedixed.pochy.entity.custom.PochyEntity;

public class PochyRenderer extends MobRenderer<PochyEntity, PochyModel<PochyEntity>> {
    public PochyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PochyModel<>(pContext.bakeLayer(ModModelLayers.POCHY_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(PochyEntity pochyEntity) {
        return new ResourceLocation(PochyMod.MOD_ID, "textures/entity/pochy.png");
    }

    @Override
    public void render(PochyEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        // test
        if (false) {
            pPoseStack.scale(30f, 30f, 30f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
