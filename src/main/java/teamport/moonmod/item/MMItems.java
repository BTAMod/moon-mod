package teamport.moonmod.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.moonmod.MMConfig;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.ItemHelper;

import static teamport.moonmod.MoonMod.MOD_ID;

public class MMItems {
	private static int itemID = MMConfig.cfg.getInt("IDs.startingItemID");

	public static final Item cheese = ItemHelper.createItem(MOD_ID,
		new ItemFood("cheese", itemID++, 4, false),
		"cheeseSlice.png");

	public static final Item sonicScrewdriver = ItemHelper.createItem(MOD_ID,
		new ItemScrewdriver("screwdriver", itemID++),
		"screwer.png");

	public static final ArmorMaterial spacesuit = ArmorHelper.createArmorMaterial(MOD_ID, "moonSuit", 240, 20.0f, 20.0f, 20.0f, 20.0f);

	public static final Item spaceHelmet = ItemHelper.createItem(MOD_ID,
		new ItemArmor("armor.helmet.space", itemID++, spacesuit, 0),
		"spaceHelmet.png");

	public static final Item spaceSuit = ItemHelper.createItem(MOD_ID,
		new ItemArmor("armor.chestplate.space", itemID++, spacesuit, 1),
		"spaceSuit.png");

	public static final Item spacePants = ItemHelper.createItem(MOD_ID,
		new ItemArmor("armor.leggings.space", itemID++, spacesuit, 2),
		"spacePants.png");

	public static final Item spaceBoots = ItemHelper.createItem(MOD_ID,
		new ItemArmor("armor.boots.space", itemID++, spacesuit, 3),
		"spaceBoots.png");

	public void initializeItems(){

	}
}
