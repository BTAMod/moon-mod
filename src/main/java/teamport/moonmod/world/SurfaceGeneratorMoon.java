package teamport.moonmod.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.SurfaceGenerator;
import net.minecraft.core.world.noise.BasePerlinNoise;
import net.minecraft.core.world.noise.PerlinNoise;

import java.util.Random;

public class SurfaceGeneratorMoon implements SurfaceGenerator {
	private final World world;
	private final BasePerlinNoise<?> soilNoise;
	private final BasePerlinNoise<?> mainNoise;
	private final boolean generateStoneVariants;

	public SurfaceGeneratorMoon(World world, BasePerlinNoise<?> soilNoise, BasePerlinNoise<?> mainNoise, boolean generateStoneVariants) {
		this.world = world;
		this.soilNoise = soilNoise;
		this.mainNoise = mainNoise;
		this.generateStoneVariants = generateStoneVariants;
	}

	public SurfaceGeneratorMoon(World world) {
		this(world,
			new PerlinNoise(world.getRandomSeed(), 4, 44),
			new PerlinNoise(world.getRandomSeed(), 4, 32),
			true
		);
	}

	@Override
	public void generateSurface(Chunk chunk, ChunkGeneratorResult chunkGeneratorResult) {
		int oceanY = this.world.getWorldType().getOceanY();
		int minY = this.world.getWorldType().getMinY();
		int maxY = this.world.getWorldType().getMaxY();
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int oceanBlock = this.world.getWorldType().getOceanBlock();
		int worldFillBlock = this.world.getWorldType().getFillerBlock();
		Random rand = new Random((long)chunkX * 341873128712L + (long)chunkZ * 132897987541L);
		double beachScale = 0.03125;
		double[] soilThicknessNoise = this.soilNoise
			.get(null, chunkX * 16, chunkZ * 16, 0.0, 16, 16, 1, beachScale * 2.0, beachScale * 2.0, beachScale * 2.0);
		double[] stoneLayerNoise = null;
		if (this.generateStoneVariants) {
			stoneLayerNoise = this.soilNoise
				.get(null, chunkX * 16, chunkZ * 16, 0.0, 16, 16, 1, beachScale * 4.0, beachScale * 4.0, beachScale * 4.0);
		}

		for(int z = 0; z < 16; ++z) {
			for(int x = 0; x < 16; ++x) {
				int soilThickness = (int)(soilThicknessNoise[z + x * 16] / 3.0 + 3.0 + rand.nextDouble() * 0.25);
				boolean generateBasaltLayer = false;
				int basaltThicknessLevel = 0;
				if (this.generateStoneVariants) {
					generateBasaltLayer = stoneLayerNoise[z + x * 16] + rand.nextDouble() * 0.2 > 0.0;
					basaltThicknessLevel = (int)(stoneLayerNoise[z + x] + rand.nextDouble() * 0.5);
				}

				int currentLayerDepth = -1;
				short topBlock = -1;
				short fillerBlock = -1;
				Biome lastBiome = null;

				for(int y = maxY; y >= minY; --y) {
					Biome biome = chunk.getBlockBiome(x, y, z);
					if (biome == null) {
						biome = this.world.getBiomeProvider().getBiome(chunkX * 16 + x, y >> 3, chunkZ * 16 + z);
					}

					int block = chunkGeneratorResult.getBlock(x, y, z);
					if ((biome != lastBiome || topBlock == -1 || fillerBlock == -1) && block == 0) {
						topBlock = biome.topBlock;
						fillerBlock = biome.fillerBlock;
					}

					lastBiome = biome;
					if (block == 0) {
						currentLayerDepth = -1;
					} else if (block == worldFillBlock) {
						if (currentLayerDepth == -1) {
							if (soilThickness <= 0) {
								topBlock = 0;
								fillerBlock = (short)worldFillBlock;
							} else if (y >= minY + oceanY - 4 && y <= minY + oceanY + 1) {
								topBlock = biome.topBlock;
								fillerBlock = biome.fillerBlock;
							}

							if (y < minY + oceanY && topBlock == 0) {
								topBlock = (short)oceanBlock;
							}

							currentLayerDepth = soilThickness;
							if (y >= minY + oceanY - 1) {
								chunkGeneratorResult.setBlock(x, y, z, topBlock);
							} else {
								chunkGeneratorResult.setBlock(x, y, z, fillerBlock);
							}
						} else if (this.generateStoneVariants && currentLayerDepth <= 0) {
							if (y >= minY + basaltThicknessLevel - rand.nextInt(3)
								&& y <= minY + 30 + basaltThicknessLevel - rand.nextInt(3)
								&& generateBasaltLayer) {
								chunkGeneratorResult.setBlock(x, y, z, Block.basalt.id);
							}
						} else {
							if (currentLayerDepth > 0) {
								--currentLayerDepth;
								chunkGeneratorResult.setBlock(x, y, z, fillerBlock);
							}
						}
					}
				}
			}
		}
	}
}
