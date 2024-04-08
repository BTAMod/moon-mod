package teamport.moonmod.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.moonmod.MoonMod;
import teamport.moonmod.item.ItemScrewdriver;
import turniplabs.halplibe.helper.TextureHelper;

import static teamport.moonmod.MoonMod.MOD_ID;

public class BlockTent extends Block {
	private final int[] textures = new int[]{
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tent.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentOrange.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentMagenta.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentLightBlue.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentYellow.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentLime.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentPink.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentGray.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentLightGray.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentCyan.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentPurple.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentBlue.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentBrown.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentGreen.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentRed.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "tentBlack.png"),
	};

	public BlockTent(String key, int id) {
		super(key, id, Material.cloth);
	}

	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		ItemStack item = player.getHeldItem();
		if (item != null && item.getItem() instanceof ItemScrewdriver) {
			if (world.getBlockId(x, y, z) == MMBlocks.tentInvincible.id) {
				destroyTent(x, y, z, world, player);
			} else {
				if (!checkBasis(x, y, z, player, world))
					return false;

				buildTent(x, y, z, world);
			}
		}
		return true;
	}

	private boolean checkBasis(int x, int y, int z, EntityPlayer player, World world) {
		for (int _x = x - 2; _x < x + 2; _x++) {
			for (int _z = z - 3; _z < z + 4; _z++) {
				if (!world.getBlockMaterial(_x, y, _z).isSolid()) {
					player.addChatMessage("moonmod.tent.build.fail");
					return false;
				}
				for (int _y = y + 1; _y < y + 5; _y++) {
					if (!world.isAirBlock(_x, _y, _z)) {
						player.addChatMessage("moonmod.tent.build.fail");
						return false;
					}
				}
			}
		}

		return world.getWorldType() == MoonMod.MOON_WORLD;
	}

	private void buildTent(int x, int y, int z, World world) {
		int block;
		int meta = world.getBlockMetadata(x, y, z);
		for (int tY = 0; tY < 5; tY++) {
			for (int tX = 0; tX < 7; tX++) {
				for (int tZ = 0; tZ < 8; tZ++) {
					if (tY == 0 && tZ == 4 && tX == 3)
						continue;

					block = tentScheme[tY][tZ][tX];

					if (block <= 0)
						continue;
					world.setBlock(tX + x - 3, tY + y, tZ + z - 4, block);
					world.setBlockMetadata(tX + x - 3, tY + y, tZ + z - 4, meta);

					if (block == t || block == b) {
						int doorMeta = 3;
						if (tY > 1) doorMeta = 11;

						world.setBlockMetadata(tX + x - 3, tY + y, tZ + z - 4, doorMeta);
					}
				}
			}
		}

		world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.tentInvincible.id, meta);
	}

	private void destroyTent(int x, int y, int z, World world, EntityPlayer player) {
		int meta = world.getBlockMetadata(x, y, z);
		Block block;
		boolean failed = false;
		for (int tY = 0; tY < 5; tY++) {
			for (int tX = 0; tX < 7; tX++) {
				for (int tZ = 0; tZ < 8; tZ++) {
					if (tentScheme[tY][tZ][tX] == -1)
						continue;

					block = world.getBlock(tX + x - 3, tY + y, tZ + z - 4);
					if (block == null) continue;
					else if (block instanceof BlockTent) continue;
					else if (block.id != tentScheme[tY][tZ][tX]) {
						int blockMeta = world.getBlockMetadata(tX + x, tY + y, tZ + z);
						block.dropBlockWithCause(world, EnumDropCause.WORLD, x, y + 1, z, blockMeta, null);
					}

					int blockID = 0;
					if (tY == 0) {
						blockID = world.getBlockId(x, y - 1, z);

						if (blockID != MMBlocks.regolith.id) failed = true;
					}

					if (!failed) world.setBlockWithNotify(tX + x - 3, tY + y, tZ + z - 4, blockID);
				}
			}
		}

		if (!failed) world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.tent.id, meta);
		else player.addChatMessage("moonmod.tent.destroy.fail");
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(MMBlocks.tent, 1, meta)};
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(Side side, int data) {
		return textures[data];
	}

	private final int a = -1;
	private final int w = MMBlocks.woolInvincible.id;
	private final int t = Block.doorPlanksOakBottom.id;
	private final int b = Block.doorPlanksOakTop.id;
	private final int[][][] tentScheme = new int[][][] {
		new int[][] {
			new int[] {a, w, w, w, w, w, a},
			new int[] {w, w, w, w, w, w, w},
			new int[] {w, w, w, w, w, w, w},
			new int[] {w, w, w, w, w, w, w},
			new int[] {w, w, w, w, w, w, w},
			new int[] {w, w, w, w, w, w, w},
			new int[] {a, w, w, w, w, w, a},
			new int[] {a, a, w, w, w, a, a}
		},

		new int[][] {
			new int[] {a, w, w, w, w, w, a},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {a, w, 0, 0, 0, w, a},
			new int[] {a, a, w, t, w, a, a}
		},

		new int[][] {
			new int[] {a, w, w, w, w, w, a},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {a, w, 0, 0, 0, w, 0},
			new int[] {a, a, w, b, w, a, a}
		},

		new int[][]{
			new int[] {a, a, w, w, w, a, a},
				new int[] {a, w, 0, 0, 0, w, a},
				new int[] {a, w, 0, 0, 0, w, a},
				new int[] {a, w, 0, 0, 0, w, a},
				new int[] {a, w, 0, 0, 0, w, a},
				new int[] {a, w, 0, 0, 0, w, a},
				new int[] {a, a, w, w, w, a, a},
				new int[] {a, a, a, w, a, a, a}
			},

		new int[][]{
			new int[]{a, a, a, a, a, a, a},
			new int[]{a, a, w, w, w, a, a},
			new int[]{a, a, w, w, w, a, a},
			new int[]{a, a, w, w, w, a, a},
			new int[]{a, a, w, w, w, a, a},
			new int[]{a, a, w, w, w, a, a},
			new int[]{a, a, a, a, a, a, a},
			new int[]{a, a, a, a, a, a, a}
		}
	};
}
