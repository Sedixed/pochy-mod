package net.sedixed.pochy.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.sedixed.pochy.entity.animations.ModAnimationDefinitions;
import net.sedixed.pochy.entity.custom.PochyEntity;

public class PochyModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart body;
	private final ModelPart body2;
	private final ModelPart frontleftleg;
	private final ModelPart frontrightleg;
	private final ModelPart backleftleg;
	private final ModelPart backrightleg;
	private final ModelPart head;
	private final ModelPart tail;

	public PochyModel(ModelPart root) {
		this.body = root.getChild("body");
		this.body2 = root.getChild("body").getChild("body2");
		this.frontleftleg = root.getChild("body").getChild("frontleftleg");
		this.frontrightleg = root.getChild("body").getChild("frontrightleg");
		this.backleftleg = root.getChild("body").getChild("backleftleg");
		this.backrightleg = root.getChild("body").getChild("backrightleg");
		this.head = root.getChild("body").getChild("head");
		this.tail = root.getChild("body").getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-0.5F, 19.0F, 2.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 0.0F, -5.0F, 7.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.3042F, -4.2777F, 0.0F));

		PartDefinition frontleftleg = body.addOrReplaceChild("frontleftleg", CubeListBuilder.create().texOffs(12, 19).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.8042F, 0.7223F, -3.0F));

		PartDefinition frontrightleg = body.addOrReplaceChild("frontrightleg", CubeListBuilder.create().texOffs(25, 19).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.1958F, 0.7223F, -3.0F));

		PartDefinition backleftleg = body.addOrReplaceChild("backleftleg", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.8042F, 0.7223F, 3.0F));

		PartDefinition backrightleg = body.addOrReplaceChild("backrightleg", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.1958F, 0.7223F, 3.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.3042F, -2.2777F, -5.0F));

		PartDefinition head2 = head.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(16, 33).addBox(-4.5F, -8.0F, -6.0F, 9.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition leftear = head.addOrReplaceChild("leftear", CubeListBuilder.create(), PartPose.offset(-2.5F, -7.25F, -1.0F));

		PartDefinition leftear_r1 = leftear.addOrReplaceChild("leftear_r1", CubeListBuilder.create().texOffs(13, 34).addBox(-2.0F, -3.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, -0.3491F));

		PartDefinition rightear = head.addOrReplaceChild("rightear", CubeListBuilder.create(), PartPose.offset(2.5F, -7.25F, -1.0F));

		PartDefinition rightear_r1 = rightear.addOrReplaceChild("rightear_r1", CubeListBuilder.create().texOffs(6, 39).mirror().addBox(-2.0F, -3.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.25F, 0.0F, -0.1309F, 0.0F, 0.3491F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(6, 44).addBox(-2.0F, -3.5F, -6.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.8042F, -1.2777F, 5.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(24, 1).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.0F, -0.25F, -0.5236F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 48, 48);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

		this.animateWalk(ModAnimationDefinitions.POCHY_WALK, limbSwing, limbSwingAmount, 1f, 1f);
		this.animate(((PochyEntity) entity).idleAnimationState, ModAnimationDefinitions.POCHY_IDLE, ageInTicks, 1f);
		this.animate(((PochyEntity) entity).swimAnimationState, ModAnimationDefinitions.POCHY_SWIM, ageInTicks, 1f);
		this.animate(((PochyEntity) entity).runAnimationState, ModAnimationDefinitions.POCHY_RUN, ageInTicks, 1f);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0f, 30.f);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0f, 45.0f);
		this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180f);
		this.head.xRot = pHeadPitch * ((float) Math.PI / 180f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return body;
	}
}