package teamport.moonmod.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemFirestriker;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.moonmod.item.ItemScrewdriver;

public class BlockMoonLamp extends Block {
	boolean isActive;

	public BlockMoonLamp(String key, int id, boolean isActivated) {
		super(key, id, Material.glass);
		this.isActive = isActivated;
	}


	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xp, double yp) {
		if (this.isActive) {
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver) {
				world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.lampUnlit.id, world.getBlockMetadata(x, y, z));
				player.getHeldItem().damageItem(1, player);
				return true;
			}
		} else if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver) {
			world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.lamp.id, world.getBlockMetadata(x, y, z));
			player.getHeldItem().damageItem(1, player);
			return true;
		}
		return false;
	}

    @Override
    public void onBlockLeftClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xHit, double yHit) {
		if (this.isActive) {
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver) {
				world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.lampUnlit.id, world.getBlockMetadata(x, y, z));
				player.getHeldItem().damageItem(1, player);
				player.swingItem();
			}
		} else if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver) {
			world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.lamp.id, world.getBlockMetadata(x, y, z));
			player.getHeldItem().damageItem(1, player);
			player.swingItem();
		}
	}

}
