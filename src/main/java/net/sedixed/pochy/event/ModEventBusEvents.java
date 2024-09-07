package net.sedixed.pochy.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sedixed.pochy.PochyMod;
import net.sedixed.pochy.entity.ModEntities;
import net.sedixed.pochy.entity.custom.PochyEntity;

@Mod.EventBusSubscriber(modid = PochyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.POCHY.get(), PochyEntity.createAttributes().build());
    }
}
