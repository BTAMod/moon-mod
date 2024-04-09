package teamport.moonmod.world.biome;

import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import teamport.moonmod.MoonMod;

public class MoonBiomes {
	public static Biome BIOME_MOON =
		Biomes.register(MoonMod.MOD_ID + ":moon.lunar.plains",
			new BiomeMoon("moonmod.lunar.plains"));

}
