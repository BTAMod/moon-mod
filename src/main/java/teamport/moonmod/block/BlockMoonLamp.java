package teamport.moonmod.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemFirestriker;
import net.minecraft.core.world.World;
import teamport.moonmod.item.ItemScrewdriver;

public class BlockMoonLamp extends Block {
	boolean isActive;

	public BlockMoonLamp(String key, int id, boolean isActivated) {
		super(key, id, Material.glass);
		this.isActive = isActivated;
	}


	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		if (this.isActive) {
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver) {
				world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.lampUnlit.id, world.getBlockMetadata(x, y, z));
				player.getHeldItem().damageItem(1, player);
			}
		}
		else if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver) {
			world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.lamp.id, world.getBlockMetadata(x, y, z));
			player.getHeldItem().damageItem(1, player);
		}
	}

	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if (this.isActive) {
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver) {
				world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.lampUnlit.id, world.getBlockMetadata(x, y, z));
				player.getHeldItem().damageItem(1, player);
				player.swingItem();
			}
			return true;
		}
		else if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver) {
			world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.lamp.id, world.getBlockMetadata(x, y, z));
			player.getHeldItem().damageItem(1, player);
			player.swingItem();
		}
		return false;
	}

}
