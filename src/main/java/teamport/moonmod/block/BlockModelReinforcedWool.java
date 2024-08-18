package teamport.moonmod.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;

import static teamport.moonmod.MoonMod.MOD_ID;

public class BlockModelReinforcedWool<T extends Block> extends BlockModelStandard<T> {
	private static final IconCoordinate[] textures = new IconCoordinate[] {
		TextureRegistry.getTexture("moonmod:block/clothBlock"),
		TextureRegistry.getTexture("moonmod:block/clothBlockOrange"),
		TextureRegistry.getTexture("moonmod:block/clothBlockMagenta"),
		TextureRegistry.getTexture("moonmod:block/clothBlockLightBlue"),
		TextureRegistry.getTexture("moonmod:block/clothBlockYellow"),
		TextureRegistry.getTexture("moonmod:block/clothBlockLime"),
		TextureRegistry.getTexture("moonmod:block/clothBlockPink"),
		TextureRegistry.getTexture("moonmod:block/clothBlockGray"),
		TextureRegistry.getTexture("moonmod:block/clothBlockLightGray"),
		TextureRegistry.getTexture("moonmod:block/clothBlockCyan"),
		TextureRegistry.getTexture("moonmod:block/clothBlockPurple"),
		TextureRegistry.getTexture("moonmod:block/clothBlockBlue"),
		TextureRegistry.getTexture("moonmod:block/clothBlockBrown"),
		TextureRegistry.getTexture("moonmod:block/clothBlockGreen"),
		TextureRegistry.getTexture("moonmod:block/clothBlockRed"),
		TextureRegistry.getTexture("moonmod:block/clothBlockBlack"),
	};

	public BlockModelReinforcedWool(Block b) {
		super(b);
	}

	@Override
	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
		return textures[data];
	}
}
