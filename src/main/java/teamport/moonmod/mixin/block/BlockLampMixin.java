package teamport.moonmod.mixin.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLamp;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import teamport.moonmod.block.MMBlocks;
import teamport.moonmod.item.ItemScrewdriver;

@Mixin(value = BlockLamp.class, remap = false)
public abstract class BlockLampMixin extends Block {
	public BlockLampMixin(String key, int id, Material material) {
		super(key, id, material);
	}

	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		int meta = world.getBlockMetadata(x, y, z);

		if (!world.isClientSide) {
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemScrewdriver) {
				world.setBlockAndMetadataWithNotify(x, y, z, MMBlocks.litLamp.id, meta);
				player.getHeldItem().damageItem(1, player);
			}
		}
		return true;
	}
}
