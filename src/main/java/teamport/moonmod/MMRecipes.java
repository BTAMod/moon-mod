package teamport.moonmod;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import teamport.moonmod.block.MMBlocks;
import teamport.moonmod.item.MMItems;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static teamport.moonmod.MoonMod.MOD_ID;

public class MMRecipes implements RecipeEntrypoint {
	@Override
	public void onRecipesReady() {
		/* cloth and tent colors in order
		00 - White
		01 - Orange
		02 - Magenta
		03 - Light Blue
		04 - Yellow
		05 - Lime
		06 - Pink
		07 - Gray
		08 - Silver
		09 - Cyan
		10 - Purple
		11 - Blue
		12 - Brown
		13 - Green
		14 - Red
		15 - Black
		*/

		// Blocks
		RecipeBuilderShaped clothShape = new RecipeBuilderShaped(MOD_ID, "111", "121", "111")
			.addInput('2', Item.ingotGold);

		RecipeBuilderShaped tentShape = new RecipeBuilderShaped(MOD_ID, " 1 ", "121")
			.addInput('2', Item.doorOak);

		RecipeBuilder.Shaped(MOD_ID)
			.setShape( " G ", "GRI", " I ")
			.addInput('G', Block.glass)
			.addInput('R', Item.dustGlowstone)
			.addInput('I', Item.ingotIron)
			.create("lamp", MMBlocks.lamp.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape("11", "11")
			.addInput('1', Item.bucketMilk)
			.setConsumeContainer(false)
			.create("cheese_block_from_milk", MMBlocks.cheese.getDefaultStack());
		RecipeBuilder.Shaped(MOD_ID)
			.setShape("11", "11")
			.addInput('1', MMItems.cheese)
			.create("cheese_block_from_cheese", MMBlocks.cheese.getDefaultStack());

		clothShape
			.addInput('1', Block.wool, 0)
			.create("cloth_from_white_wool", new ItemStack(MMBlocks.woolReinforced, 8, 0));
		clothShape
			.addInput('1', Block.wool, 1)
			.create("cloth_from_yellow_wool", new ItemStack(MMBlocks.woolReinforced, 8, 1));
		clothShape
			.addInput('1', Block.wool, 2)
			.create("cloth_from_magenta_wool", new ItemStack(MMBlocks.woolReinforced, 8, 2));
		clothShape
			.addInput('1', Block.wool, 3)
			.create("cloth_from_lightblue_wool", new ItemStack(MMBlocks.woolReinforced, 8, 3));
		clothShape
			.addInput('1', Block.wool, 4)
			.create("cloth_from_yellow_wool", new ItemStack(MMBlocks.woolReinforced, 8, 4));
		clothShape
			.addInput('1', Block.wool, 5)
			.create("cloth_from_lime_wool", new ItemStack(MMBlocks.woolReinforced, 8, 5));
		clothShape
			.addInput('1', Block.wool, 6)
			.create("cloth_from_pink_wool", new ItemStack(MMBlocks.woolReinforced, 8, 6));
		clothShape
			.addInput('1', Block.wool, 7)
			.create("cloth_from_gray_wool", new ItemStack(MMBlocks.woolReinforced, 8, 7));
		clothShape
			.addInput('1', Block.wool, 8)
			.create("cloth_from_silver_wool", new ItemStack(MMBlocks.woolReinforced, 8, 8));
		clothShape
			.addInput('1', Block.wool, 9)
			.create("cloth_from_cyan_wool", new ItemStack(MMBlocks.woolReinforced, 8, 9));
		clothShape
			.addInput('1', Block.wool, 10)
			.create("cloth_from_purple_wool", new ItemStack(MMBlocks.woolReinforced, 8, 10));
		clothShape
			.addInput('1', Block.wool, 11)
			.create("cloth_from_blue_wool", new ItemStack(MMBlocks.woolReinforced, 8, 11));
		clothShape
			.addInput('1', Block.wool, 12)
			.create("cloth_from_brown_wool", new ItemStack(MMBlocks.woolReinforced, 8, 12));
		clothShape
			.addInput('1', Block.wool, 13)
			.create("cloth_from_green_wool", new ItemStack(MMBlocks.woolReinforced, 8, 13));
		clothShape
			.addInput('1', Block.wool, 14)
			.create("cloth_from_red_wool", new ItemStack(MMBlocks.woolReinforced, 8, 14));
		clothShape
			.addInput('1', Block.wool, 15)
			.create("cloth_from_black_wool", new ItemStack(MMBlocks.woolReinforced, 8, 15));


		tentShape
			.addInput('1', MMBlocks.woolReinforced, 0)
			.create("tent_from_white_cloth", new ItemStack(MMBlocks.tent, 1, 0));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 1)
			.create("tent_from_orange_cloth", new ItemStack(MMBlocks.tent, 1, 1));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 2)
			.create("tent_from_magenta_cloth", new ItemStack(MMBlocks.tent, 1, 2));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 3)
			.create("tent_from_lightblue_cloth", new ItemStack(MMBlocks.tent, 1, 3));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 4)
			.create("tent_from_yellow_cloth", new ItemStack(MMBlocks.tent, 1, 4));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 5)
			.create("tent_from_lime_cloth", new ItemStack(MMBlocks.tent, 1, 5));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 6)
			.create("tent_from_pink_cloth", new ItemStack(MMBlocks.tent, 1, 6));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 7)
			.create("tent_from_gray_cloth", new ItemStack(MMBlocks.tent, 1, 7));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 8)
			.create("tent_from_silver_cloth", new ItemStack(MMBlocks.tent, 1, 8));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 9)
			.create("tent_from_cyan_cloth", new ItemStack(MMBlocks.tent, 1, 9));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 10)
			.create("tent_from_purple_cloth", new ItemStack(MMBlocks.tent, 1, 10));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 11)
			.create("tent_from_blue_cloth", new ItemStack(MMBlocks.tent, 1, 11));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 12)
			.create("tent_from_brown_cloth", new ItemStack(MMBlocks.tent, 1, 12));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 13)
			.create("tent_from_green_cloth", new ItemStack(MMBlocks.tent, 1, 13));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 14)
			.create("tent_from_red_cloth", new ItemStack(MMBlocks.tent, 1, 14));
		tentShape
			.addInput('1', MMBlocks.woolReinforced, 15)
			.create("tent_from_black_cloth", new ItemStack(MMBlocks.tent, 1, 15));

		RecipeBuilder.Shaped(MOD_ID)
			.setShape("111", "121")
			.addInput('1', MMBlocks.woolReinforced)
			.addInput('2', Block.glass)
			.create("space_helmet", MMItems.spaceHelmet.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape("1 1", "212", "222")
			.addInput('1', Item.ingotIron)
			.addInput('2', MMBlocks.woolReinforced)
			.create("space_suit", MMItems.spaceSuit.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape("121", "1 1", "2 2")
			.addInput('1', MMBlocks.woolReinforced)
			.addInput('2', Item.ingotIron)
			.create("space_leggings", MMItems.spacePants.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape("1 1", "2 2")
			.addInput('1', Item.ingotIron)
			.addInput('2', MMBlocks.woolReinforced)
			.create("space_boots", MMItems.spaceBoots.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape("  1", " 2 ", "2  ")
			.addInput('1', Item.dustRedstone)
			.addInput('2', Item.ingotIron)
			.create("sonic_screwdriver", MMItems.sonicScrewdriver.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape("121", "212", "121")
			.addInput('1', MMBlocks.regolith)
			.addInput('2', Item.sulphur)
			.create("tnt_regolith", new ItemStack(Block.tnt, 2));
	}

	@Override
	public void initNamespaces() {
	}
}
