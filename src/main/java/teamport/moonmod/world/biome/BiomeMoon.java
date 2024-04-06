package teamport.moonmod.world.biome;

import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.weather.Weather;
import teamport.moonmod.block.MMBlocks;
import teamport.moonmod.entity.EntityUFO;

public class BiomeMoon extends Biome {

	public BiomeMoon(String name) {
		super(name);
		setBlockedWeathers(Weather.overworldRain, Weather.overworldStorm, Weather.overworldWinterSnow);
		setColor(0);
		setTopBlock(MMBlocks.regolith.id);
		setFillerBlock(MMBlocks.regolith.id);

		spawnableAmbientCreatureList.clear();
		spawnableCreatureList.clear();
		spawnableMonsterList.clear();

		spawnableCreatureList.add(new SpawnListEntry(EntityUFO.class, 4));
	}

	@Override
	public int getSkyColor(float temperature) {
		return 0;
	}
}
