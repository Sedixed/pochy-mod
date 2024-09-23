package net.sedixed.pochy.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sedixed.pochy.PochyMod;
import net.sedixed.pochy.config.ServerConfig;
import net.sedixed.pochy.entity.custom.PochyEntity;

import static net.sedixed.pochy.entity.ModEntities.POCHY;


public class ModEvents {
    @Mod.EventBusSubscriber(modid = PochyMod.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            boolean isNewPlayer = ServerConfig.addPlayerName(event.getEntity().getName().getString());
            if (isNewPlayer) {
                Player player = event.getEntity();
                Level level = player.level();

                double x = player.getX() + 0.1;
                double y = player.getY() + 1;
                double z = player.getZ();

                PochyEntity pochy = new PochyEntity(POCHY.get(), level);

                pochy.moveTo(x, y, z);
                pochy.setOwnerUUID(player.getUUID());
                pochy.setCustomName(Component.literal("Pochy"));
                pochy.setCustomNameVisible(true);
                level.addFreshEntity(pochy);
            }
        }
    }
}
