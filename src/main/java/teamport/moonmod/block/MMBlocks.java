package teamport.moonmod.block;

import net.minecraft.client.render.block.color.BlockColorWater;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.block.ItemBlockPainted;
import net.minecraft.core.sound.BlockSounds;
import teamport.moonmod.MMConfig;
import turniplabs.halplibe.helper.BlockBuilder;

import static net.minecraft.core.block.Block.fire;
import static teamport.moonmod.MoonMod.MOD_ID;

public class MMBlocks {
	private static int blockID = MMConfig.cfg.getInt("IDs.startingBlockID");

	public static final Block regolith = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.GRAVEL)
		.setHardness(0.5f)
		.setResistance(0.5f)
		.setTextures("moonmod:block/regolith")
		.setTags(BlockTags.MINEABLE_BY_SHOVEL, BlockTags.CAVES_CUT_THROUGH)
		.build(new Block("regolith", blockID++, Material.dirt));

	public static final Block woolReinforced = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.CLOTH)
		.setHardness(1.1f)
		.setResistance(6.0f)
		.setItemBlock(block -> new ItemBlockPainted(block, false))
		.setBlockModel(block -> new BlockModelReinforcedWool(block))
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CAVES_CUT_THROUGH)
		.build(new BlockReinforcedWool("wool.reinforced", blockID++));

	public static final Block woolInvincible = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.CLOTH)
		.setUnbreakable()
		.setTextures("moonmod:block/clothBlock")
		.setTags(BlockTags.NOT_IN_CREATIVE_MENU)
		.build(new BlockReinforcedWool("wool.reinforced", blockID++));

	public static final Block tent = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.CLOTH)
		.setHardness(1.1f)
		.setResistance(6.0f)
		.setTextures("moonmod:block/tent")
		.setItemBlock(block -> new ItemBlockPainted(block, false))
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CAVES_CUT_THROUGH)
		.build(new BlockTent("tent", blockID++));

	public static final Block tentInvincible = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.CLOTH)
		.setUnbreakable()
		.setTextures("moonmod:block/tent")
		.setItemBlock(block -> new ItemBlockPainted(block, false))
		.setTags(BlockTags.NOT_IN_CREATIVE_MENU)
		.build(new BlockTent("tent", blockID++));

	public static final Block cheese = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.WOOD)
		.setHardness(0.6f)
		.setResistance(0.6f)
		.setTextures("moonmod:block/cheeseBlock")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.MINEABLE_BY_AXE)
		.build(new BlockCheese("cheese", blockID++, Material.cloth));

	public static final Block portalMoon = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.GLASS)
		.setHardness(-1.0f)
		.setResistance(-1.0f)
		.setLuminance(15)
		.setTextures("minecraft:block/water_still")
		.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU)
		.setBlockColor(block -> new BlockColorWater(Colorizers.water))
		.build(new BlockPortalMoon("portal.moon", blockID++, 3, MMBlocks.cheese.id, fire.id));

	public static final Block lamp = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.GLASS)
		.setHardness(0.6f)
		.setResistance(6.0f)
		.setLuminance(15)
		.setTextures("moonmod:block/lamp")
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new BlockMoonLamp("lamp", blockID++, true));

	public static final Block lampUnlit = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.GLASS)
		.setHardness(0.6f)
		.setResistance(6.0f)
		.setLuminance(0)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.setTextures("moonmod:block/lampUnlit")
		.build(new BlockMoonLamp("lamp.unlit", blockID++, false));

	public void initializeBlocks(){
	}
}
