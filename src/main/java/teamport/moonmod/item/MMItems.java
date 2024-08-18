package teamport.moonmod.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.moonmod.MMConfig;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.ItemBuilder;

import static teamport.moonmod.MoonMod.MOD_ID;

public class MMItems {
	private static int itemID = MMConfig.cfg.getInt("IDs.startingItemID");

	public static final Item cheese = new ItemBuilder(MOD_ID)
	    .setIcon("moonmod:item/cheeseSlice")
	    .build(
		   new ItemFood("cheese", itemID++, 4, 10, false, 8)
		);

	public static final Item sonicScrewdriver = new ItemBuilder(MOD_ID)
	    .setIcon("moonmod:item/screwer")
	    .build(
		   new ItemScrewdriver("screwdriver", itemID++)
		);

	public static final ArmorMaterial spacesuit = ArmorHelper.createArmorMaterial(MOD_ID, "moonSuit", 240, 20.0f, 20.0f, 20.0f, 20.0f);

	public static final Item spaceHelmet = new ItemBuilder(MOD_ID)
	    .setIcon("moonmod:item/spaceHelmet")
	    .build(
		   new ItemArmor("armor.helmet.space", itemID++, spacesuit, 0)
		);

	public static final Item spaceSuit = new ItemBuilder(MOD_ID)
	    .setIcon("moonmod:item/spaceSuit")
	    .build(
		   new ItemArmor("armor.chestplate.space", itemID++, spacesuit, 1)
		);

	public static final Item spacePants = new ItemBuilder(MOD_ID)
	    .setIcon("moonmod:item/spacePants")
	    .build(
		   new ItemArmor("armor.leggings.space", itemID++, spacesuit, 2)
		);

	public static final Item spaceBoots = new ItemBuilder(MOD_ID)
	    .setIcon("moonmod:item/spaceBoots")
	    .build(
		   new ItemArmor("armor.boots.space", itemID++, spacesuit, 3)
		);

	public void initializeItems() {
	}
}
