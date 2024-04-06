package teamport.moonmod.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.moonmod.item.ItemScrewdriver;
import teamport.moonmod.item.MMItems;

public class BlockTent extends Block {
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

	private boolean checkBasis(int x, int y, int z, EntityPlayer ep, World world) {
		for (int i = x - 2; i < x + 2; i++) {
			for (int j = z - 3; j < z + 4; j++) {
				if (!world.getBlockMaterial(i, y, j).isSolid()) {
					ep.addChatMessage("moonmod.tent.fail");
					return false;
				}
				for (int k = y + 1; k < y + 5; k++) {
					if (!world.isAirBlock(i, k, j)) {
						ep.addChatMessage("moonmod.tent.fail");
						return false;
					}
				}
			}
		}
		return true;
	}

	private void buildTent(int x, int y, int z, World world) {
		int block;
		for (int tY = 0; tY < 5; tY++) {
			for (int tX = 0; tX < 7; tX++) {
				for (int tZ = 0; tZ < 8; tZ++) {
					if (tY == 0 && tZ == 4 && tX == 3)
						continue;

					block = tentScheme[tY][tZ][tX];

					if (block <= 0)
						continue;
					world.setBlock(tX + x - 3, tY + y, tZ + z - 4, block);
					world.setBlockMetadata(tX + x - 3, tY + y, tZ + z - 4, 0);

					if (block == t || block == b) {
						int meta = 3;
						if (tY > 1)
							meta = 11;
						world.setBlockMetadata(tX + x - 3, tY + y, tZ + z - 4, meta);
					}
				}
			}
		}

		int meta = world.getBlockMetadata(x, y, z);
		world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.tentInvincible.id, meta);
	}

	private void destroyTent(int x, int y, int z, World world, EntityPlayer player) {
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.tent.id, meta);
		Block block;
		for (int tY = 0; tY < 5; tY++) {
			for (int tX = 0; tX < 7; tX++) {
				for (int tZ = 0; tZ < 8; tZ++) {
					if (tentScheme[tY][tZ][tX] == -1)
						continue;

					block = world.getBlock(tX + x - 3, tY + y, tZ + z - 4);
					if (block == null) {
						continue;
					} else if (block instanceof BlockTent)
						continue;
					else if (block.id != tentScheme[tY][tZ][tX]) {
						// TODO - Fix this dropping default meta (0)
						int blockMeta = world.getBlockMetadata(x, y, z);
						block.dropBlockWithCause(world, EnumDropCause.WORLD, x, y + 1, z, blockMeta, null);
					}

					int blockID = 0;
					if (tY == 0) {
						blockID = world.getBlockId(x, y - 1, z);
					}

					world.setBlockWithNotify(tX + x - 3, tY + y, tZ + z - 4, blockID);
				}
			}
		}
	}

	private final int w = MMBlocks.woolInvincible.id;
	private final int t = Block.doorPlanksOakBottom.id;
	private final int b = Block.doorPlanksOakTop.id;
	private final int[][][] tentScheme = new int[][][] {
		new int[][] {
			new int[] { -1, w, w, w, w, w, -1 },
			new int[] {w, w, w, w, w, w, w},
			new int[] {w, w, w, w, w, w, w},
			new int[] {w, w, w, w, w, w, w},
			new int[] {w, w, w, w, w, w, w},
			new int[] {w, w, w, w, w, w, w},
			new int[] { -1, w, w, w, w, w, -1 },
			new int[] { -1, -1, w, w, w, -1, -1 }
		},

		new int[][] {
			new int[] { -1, w, w, w, w, w, -1 },
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0,/**/0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] { -1, w, 0, 0, 0, w, -1 },
			new int[] { -1, -1, w, t, w, -1, -1 }
		},

		new int[][] {
			new int[] { -1, w, w, w, w, w, -1 },
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] {w, 0, 0, 0, 0, 0, w},
			new int[] { -1, w, 0, 0, 0, w, 0 },
			new int[] { -1, -1, w, b, w, -1, -1 }
		},

		new int[][]
			{ new int[] { -1, -1, w, w, w, -1, -1 },
			new int[] { -1, w, 0, 0, 0, w, -1 },
			new int[] { -1, w, 0, 0, 0, w, -1 },
			new int[] { -1, w, 0, 0, 0, w, -1 },
			new int[] { -1, w, 0, 0, 0, w, -1 },
			new int[] { -1, w, 0, 0, 0, w, -1 },
			new int[] { -1, -1, w, w, w, -1, -1 },
			new int[] { -1, -1, -1, w, -1, -1, -1 }
			},

		new int[][]
			{ new int[] { -1, -1, -1, -1, -1, -1, -1 },
			new int[] { -1, -1, w, w, w, -1, -1 },
			new int[] { -1, -1, w, w, w, -1, -1 },
			new int[] { -1, -1, w, w, w, -1, -1 },
			new int[] { -1, -1, w, w, w, -1, -1 },
			new int[] { -1, -1, w, w, w, -1, -1 },
			new int[] { -1, -1, -1, -1, -1, -1, -1 },
			new int[] { -1, -1, -1, -1, -1, -1, -1 }
			}
	};
}
