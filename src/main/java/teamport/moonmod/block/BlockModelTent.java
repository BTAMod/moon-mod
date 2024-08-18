package teamport.moonmod.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import teamport.moonmod.MoonMod;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;

import static teamport.moonmod.MoonMod.MOD_ID;

public class BlockModelTent<T extends Block> extends BlockModelStandard<T> {
	private final IconCoordinate[] textures = new IconCoordinate[] {
		TextureRegistry.getTexture("moonmod:block/tent"),
		TextureRegistry.getTexture("moonmod:block/tentOrange"),
		TextureRegistry.getTexture("moonmod:block/tentMagenta"),
		TextureRegistry.getTexture("moonmod:block/tentLightBlue"),
		TextureRegistry.getTexture("moonmod:block/tentYellow"),
		TextureRegistry.getTexture("moonmod:block/tentLime"),
		TextureRegistry.getTexture("moonmod:block/tentPink"),
		TextureRegistry.getTexture("moonmod:block/tentGray"),
		TextureRegistry.getTexture("moonmod:block/tentLightGray"),
		TextureRegistry.getTexture("moonmod:block/tentCyan"),
		TextureRegistry.getTexture("moonmod:block/tentPurple"),
		TextureRegistry.getTexture("moonmod:block/tentBlue"),
		TextureRegistry.getTexture("moonmod:block/tentBrown"),
		TextureRegistry.getTexture("moonmod:block/tentGreen"),
		TextureRegistry.getTexture("moonmod:block/tentRed"),
		TextureRegistry.getTexture("moonmod:block/tentBlack"),
	};

	public BlockModelTent(Block b) {
		super(b);
	}

	@Override
	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
		return textures[data];
	}
}
