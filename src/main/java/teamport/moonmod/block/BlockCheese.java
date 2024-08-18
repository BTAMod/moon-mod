package teamport.moonmod.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockPortal;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.util.helper.Side;
import teamport.moonmod.item.ItemScrewdriver;
import teamport.moonmod.item.MMItems;

public class BlockCheese extends Block {
	public BlockCheese(String key, int id, Material material) {
		super(key, id, material);
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		switch (dropCause) {
			case SILK_TOUCH:
			case PICK_BLOCK:
				return new ItemStack[]{new ItemStack(this)};
			default:
				return new ItemStack[]{new ItemStack(MMItems.cheese, 4)};
		}
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xp, double yp) {
		if (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemScrewdriver))
			return false;
		else if (player.getHeldItem().getItem() instanceof ItemScrewdriver) {
			player.getHeldItem().damageItem(1, player);

			BlockPortal portal = (BlockPortal) MMBlocks.portalMoon;
			return portal.tryToCreatePortal(world, x, y, z);
		} else return false;
	}
}
