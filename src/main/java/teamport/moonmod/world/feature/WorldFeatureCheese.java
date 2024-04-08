package teamport.moonmod.world.feature;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.moonmod.block.MMBlocks;

import java.util.Random;

public class WorldFeatureCheese extends WorldFeature {
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		if (y < world.getHeightBlocks()) {
			for (int chunk = 0; chunk < 16; ++chunk) {
				int _x = x + random.nextInt(8) - random.nextInt(8);
				int _z = z + random.nextInt(8) - random.nextInt(8);

				if (!world.isAirBlock(_x, y - 1, _z)) world.setBlockRaw(_x, y, _z, MMBlocks.cheese.id);
			}
			return true;
		}
		return false;
	}
}
