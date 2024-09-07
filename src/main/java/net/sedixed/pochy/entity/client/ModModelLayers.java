package net.sedixed.pochy.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.sedixed.pochy.PochyMod;

public class ModModelLayers {
    public static final ModelLayerLocation POCHY_LAYER = new ModelLayerLocation(
            new ResourceLocation(PochyMod.MOD_ID, "pochy_layer"), "main"
    );
}
