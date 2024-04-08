package teamport.moonmod.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.TextureHelper;

import static teamport.moonmod.MoonMod.MOD_ID;

public class BlockReinforcedWool extends Block {
	private static final int[] textures = new int[] {
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlock.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockOrange.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockMagenta.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockLightBlue.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockYellow.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockLime.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockPink.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockGray.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockLightGray.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockCyan.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockPurple.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockBlue.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockBrown.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockGreen.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockRed.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "clothBlockBlack.png"),
	};

	public BlockReinforcedWool(String key, int id) {
		super(key, id, Material.cloth);
	}

	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(this, 1, meta)};
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(Side side, int data) {
		return textures[data];
	}
}
