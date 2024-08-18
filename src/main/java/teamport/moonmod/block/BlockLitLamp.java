package teamport.moonmod.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLamp;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import net.minecraft.core.util.helper.Side;
import teamport.moonmod.item.ItemScrewdriver;

import java.util.Random;

public class BlockLitLamp extends BlockLamp {
	public BlockLitLamp(String key, int id) {
		super(key, id, true);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xp, double yp) {
		int meta = world.getBlockMetadata(x, y, z);

		if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemScrewdriver) {
			world.setBlockAndMetadataWithNotify(x, y, z, Block.lampIdle.id, meta);
			player.getHeldItem().damageItem(1, player);
		}
		return true;
	}
}
