package net.msrandom.wings.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.msrandom.wings.WingsAndClaws;
import net.msrandom.wings.client.renderer.entity.model.IcyPlowheadModel;
import net.msrandom.wings.entity.monster.IcyPlowheadEntity;

public class IcyPlowheadRenderer extends MobRenderer<IcyPlowheadEntity, IcyPlowheadModel> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[8];
    private final IcyPlowheadModel adult;
    private final IcyPlowheadModel child;

    public IcyPlowheadRenderer(EntityRendererManager p_i50961_1_) {
        super(p_i50961_1_, new IcyPlowheadModel.Adult(), 0.75f);
        adult = entityModel;
        child = new IcyPlowheadModel.Child();
    }

    @Override
    public void render(IcyPlowheadEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        entityModel = entityIn.isChild() ? child : adult;
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(IcyPlowheadEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entitylivingbaseIn.pitch));
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
    }

    @Override
    public ResourceLocation getEntityTexture(IcyPlowheadEntity entity) {
        byte texture = 0;
        if (entity.isChild()) texture |= 1;
        if (entity.getGender()) texture |= 2;
        if (entity.isSleeping()) texture |= 4;
        if (TEXTURES[texture] == null) {
            String entityTexture = String.format("%s_%s%s", ((texture & 1) != 0) ? "child" : "adult", ((texture & 2) != 0) ? "male" : "female", ((texture & 4) != 0) ? "_sleep" : "");
            ResourceLocation location = new ResourceLocation(WingsAndClaws.MOD_ID, "textures/entity/icy_plowhead/" + entityTexture + ".png");
            TEXTURES[texture] = location;
            return location;
        }
        return TEXTURES[texture];
    }
}
