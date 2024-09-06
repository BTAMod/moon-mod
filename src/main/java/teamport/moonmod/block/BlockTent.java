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
import teamport.moonmod.entity.EntityRocket;

import static teamport.moonmod.MoonMod.MOD_ID;

import java.util.stream.IntStream;

public class BlockTent extends Block {
	public BlockTent(String key, int id) {
		super(key, id, Material.cloth);
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xp, double yp) {
		ItemStack item = player.getHeldItem();
		if (item != null && item.getItem() instanceof ItemScrewdriver) {
			if (world.getBlockId(x, y, z) == MMBlocks.tentInvincible.id) {
				destroyTent(x, y, z, world, player);
			} else {
				/*if (!checkBasis(x, y, z, player, world))
					return false;

				buildTent(x, y, z, world);*/
				EntityRocket rocket = EntityRocket.buildRocket(world, x, y, z);
				if (rocket == null) return true;
				rocket.moveTo(x, y, z, 0, 0);
				world.entityJoinedWorld(rocket);
                rocket.interact(player);
			}
			return true;
		}
		return false;
	}

	private boolean checkBasis(int x, int y, int z, EntityPlayer player, World world) {
        int allowedIds[] = new int[]{0, MMBlocks.regolith.id, MMBlocks.tent.id, MMBlocks.tentInvincible.id};
		for (int tY = 0; tY < 5; tY++) {
			for (int tX = 0; tX < 7; tX++) {
				for (int tZ = 0; tZ < 8; tZ++) {
				    int id = world.getBlockId(tX + x - 3, tY + y, tZ + z);
    				if (!IntStream.of(allowedIds).anyMatch(i -> i == id)) {
	   				    player.sendTranslatedChatMessage("moonmod.tent.build.fail");
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
		for (int tY = 0; tY < 5; tY++) {
			for (int tX = 0; tX < 7; tX++) {
				for (int tZ = 0; tZ < 8; tZ++) {
					if (tentScheme[tY][tZ][tX] == -1)
						continue;

					boolean failed = false;
                    block = world.getBlock(tX + x - 3, tY + y, tZ + z - 4);
					if (block == null) continue;
					else if (block instanceof BlockTent) continue;
					else if (block.id != tentScheme[tY][tZ][tX]) {
    					// This was commented out to prevent exploits, such as using it to instantly break unbreakable blocks
    					// Instead it just ignores them :P
						/*
						int blockMeta = world.getBlockMetadata(tX + x, tY + y, tZ + z);
						block.dropBlockWithCause(world, EnumDropCause.WORLD, x, y + 1, z, blockMeta, null);
						*/
						failed = true;
					}

					int blockID = 0;
					if (tY == 0) {
						blockID = world.getBlockId(x, y - 1, z);

						if (blockID != MMBlocks.regolith.id && blockID != 0) blockID = MMBlocks.regolith.id;
					}

					if (!failed) world.setBlockWithNotify(tX + x - 3, tY + y, tZ + z - 4, blockID);
				}
			}
		}

		world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.tent.id, meta);
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(MMBlocks.tent, 1, meta)};
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
