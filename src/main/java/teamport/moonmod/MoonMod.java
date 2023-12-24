package teamport.moonmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.moonmod.world.BiomeProviderMoon;
import teamport.moonmod.world.WorldTypeMoon;
import teamport.moonmod.world.biome.MoonBiomes;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class MoonMod implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, PreLaunchEntrypoint {
    public static final String MOD_ID = "moonmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static WorldType MOON_WORLD;

    @Override
    public void onInitialize() {
        LOGGER.info("MoonMod has been initialized. Have fun, brave astronaut!");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void onPreLaunch() {
		new MoonBiomes().initializeBiomes();
		BiomeProviderMoon.init();
		MOON_WORLD = WorldTypes.register("moonmod.worldtype.moon", new WorldTypeMoon("moonmod.worldtype.moon"));
	}
}
