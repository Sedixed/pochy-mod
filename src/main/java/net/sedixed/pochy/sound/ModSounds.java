package net.sedixed.pochy.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sedixed.pochy.PochyMod;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PochyMod.MOD_ID);

    public static final RegistryObject<SoundEvent> POCHY_IDLE = registerSoundEvent("pochy_idle");

    public static final RegistryObject<SoundEvent> POCHY_HURT = registerSoundEvent("pochy_hurt");

    public static final RegistryObject<SoundEvent> POCHY_DEATH = registerSoundEvent("pochy_death");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(
                name,
                () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(PochyMod.MOD_ID, name))
        );
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
