package net.sedixed.pochy.config;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.sedixed.pochy.PochyMod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = PochyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> REGISTERED_PLAYERS = BUILDER
            .comment("Registered players.")
            .defineListAllowEmpty("players", List.of(), Config::validatePlayerName);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
    }

    public static List<String> getPlayerNames() {
        return (List<String>) REGISTERED_PLAYERS.get();
    }

    public static void setPlayerNames(List<String> playerNames) {
        REGISTERED_PLAYERS.set(playerNames);
    }

    /**
     * Adds a player name to the registered player names list if it isn't already in it.
     * @param playerName The player name to add.
     * @return True if the list has changed, false otherwise.
     */
    public static boolean addPlayerName(String playerName) {
        ArrayList<String> playerNames = new ArrayList<>(REGISTERED_PLAYERS.get());
        if (playerNames.contains(playerName)) {
            return false;
        }
        playerNames.add(playerName);
        REGISTERED_PLAYERS.set(playerNames);
        return true;
    }

    public static void removePlayerName(String playerName) {
        List<String> playerNames = (List<String>) REGISTERED_PLAYERS.get();
        playerNames.remove(playerName);
        REGISTERED_PLAYERS.set(playerNames);
    }

    /**
     * Validation of a player name (must be a non-empty string)
     * @param obj String to insert.
     * @return true if the predicate is valid.
     */
    private static boolean validatePlayerName(final Object obj) {
        return obj instanceof final String itemName && itemName.length() >= 3 && itemName.length() <= 16;
    }

    /**
     * private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
     *
     *         private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
     *                 .comment("Whether to log the dirt block on common setup")
     *                 .define("logDirtBlock", true);
     *
     *         private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
     *                 .comment("A magic number")
     *                 .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);
     *
     *         public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
     *                 .comment("What you want the introduction message to be for the magic number")
     *                 .define("magicNumberIntroduction", "The magic number is... ");
     *
     *         // a list of strings that are treated as resource locations for items
     *         private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
     *                 .comment("A list of items to log on common setup.")
     *                 .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);
     *
     *         static final ForgeConfigSpec SPEC = BUILDER.build();
     *
     *         public static boolean logDirtBlock;
     *         public static int magicNumber;
     *         public static String magicNumberIntroduction;
     *         public static Set<Item> items;
     *
     *         private static boolean validateItemName(final Object obj)
     *         {
     *             return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
     *         }
     *
     *         @SubscribeEvent
     *         static void onLoad(final ModConfigEvent event)
     *         {
     *             logDirtBlock = LOG_DIRT_BLOCK.get();
     *             magicNumber = MAGIC_NUMBER.get();
     *             magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
     *
     *             // convert the list of strings into a set of items
     *             items = ITEM_STRINGS.get().stream()
     *                     .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
     *                     .collect(Collectors.toSet());
     *         }
     *
     *         public static int getStage() {
     *         return STAGE.get();
     *     }
     *
     *     public static void setStage(int newValue) {
     *         STAGE.set(newValue);
     *     }
     */
}
