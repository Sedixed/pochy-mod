package net.sedixed.pochy.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sedixed.pochy.PochyMod;
import net.sedixed.pochy.entity.custom.PochyEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PochyMod.MOD_ID);

    public static final RegistryObject<EntityType<PochyEntity>> POCHY = ENTITY_TYPES.register(
            "pochy",
            () -> EntityType.Builder
                    .of(PochyEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 1.0f)
                    .build("pochy")
    );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
