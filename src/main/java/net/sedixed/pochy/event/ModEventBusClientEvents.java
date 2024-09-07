package net.sedixed.pochy.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sedixed.pochy.PochyMod;
import net.sedixed.pochy.entity.client.ModModelLayers;
import net.sedixed.pochy.entity.client.PochyModel;

@Mod.EventBusSubscriber(modid = PochyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.POCHY_LAYER, PochyModel::createBodyLayer);
    }
}
