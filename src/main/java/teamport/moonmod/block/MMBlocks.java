package teamport.moonmod.block;

import net.minecraft.client.render.block.color.BlockColorWater;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.block.ItemBlock;
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
		.setTextures("regolith.png")
		.setTags(BlockTags.MINEABLE_BY_SHOVEL, BlockTags.CAVES_CUT_THROUGH)
		.build(new Block("regolith", blockID++, Material.dirt));

	public static final Block woolReinforced = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.CLOTH)
		.setHardness(1.1f)
		.setResistance(6.0f)
		.setTextures("clothBlock.png")
		.setItemBlock(block -> new ItemBlockPainted(block, false))
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CAVES_CUT_THROUGH)
		.build(new BlockReinforcedWool("wool.reinforced", blockID++));

	public static final Block woolInvincible = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.CLOTH)
		.setUnbreakable()
		.setTextures("clothBlock.png")
		.setTags(BlockTags.NOT_IN_CREATIVE_MENU)
		.build(new BlockReinforcedWool("wool.reinforced", blockID++));

	public static final Block tent = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.CLOTH)
		.setHardness(1.1f)
		.setResistance(6.0f)
		.setTextures("tent.png")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CAVES_CUT_THROUGH)
		.build(new BlockTent("wool.reinforced.tent", blockID++));

	public static final Block tentInvincible = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.CLOTH)
		.setUnbreakable()
		.setTextures("tent.png")
		.setTags(BlockTags.NOT_IN_CREATIVE_MENU)
		.build(new BlockTent("wool.reinforced.tent", blockID++));

	public static final Block cheese = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.WOOD)
		.setHardness(0.6f)
		.setResistance(0.6f)
		.setTextures("cheeseBlock.png")
		.setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.MINEABLE_BY_AXE)
		.build(new BlockCheese("cheese", blockID++, Material.cloth));

	public static final Block portalMoon = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.GLASS)
		.setHardness(-1.0f)
		.setResistance(-1.0f)
		.setLuminance(15)
		.setTextures(13, 12)
		.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU)
		.setBlockColor(new BlockColorWater())
		.build(new BlockPortalMoon("portal.moon", blockID++, 3, MMBlocks.cheese.id, fire.id));

	public static final Block litLamp = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.GLASS)
		.setHardness(0.5f)
		.build(new BlockLitLamp("lamp.lit", blockID++))
		.withLightEmission(0.9375f)
		.withDisabledStats()
		.withDisabledNeighborNotifyOnMetadataChange()
		.withTags(BlockTags.NOT_IN_CREATIVE_MENU);

	public void initializeBlocks(){
	}
}
