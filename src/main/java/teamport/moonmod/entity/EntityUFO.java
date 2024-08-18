package teamport.moonmod.entity;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.WeightedRandomLootObject;
import teamport.moonmod.block.MMBlocks;
import teamport.moonmod.item.MMItems;

public class EntityUFO extends EntityAnimal {
	public EntityUFO(World world) {
		super(world);
		textureIdentifier = new NamespaceID("moonmod", "ufo");
		this.mobDrops.add(new WeightedRandomLootObject(Item.dustRedstone.getDefaultStack(), 0, 2));
	}

   @Override
	public String getEntityTexture() {
		return "/assets/moonmod/textures/mob/ufo/" + this.getSkinVariant() + ".png";
	}

	@Override
	public int getSkinVariant() {
		int skinVariantCount = 5;
		return this.entityData.getByte(1) % skinVariantCount;
	}

	@Override
	public String getLivingSound() {
		return random.nextInt(1000) == 0 ? "moonmod.yippee" : null;
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	public boolean getCanSpawnHere() {
		int x = MathHelper.floor_double(this.x);
		int y = MathHelper.floor_double(this.bb.minY);
		int z = MathHelper.floor_double(this.z);
		int id = this.world.getBlockId(x, y - 1, z);
		if (Block.blocksList[id] == null) {
			return false;
		} else {
			return Block.blocksList[id] == MMBlocks.regolith;
		}
	}

	@Override
	public boolean interact(EntityPlayer player) {
		super.interact(player);
		ItemStack stack = player.getHeldItem();

		if (stack != null && stack.getItem() == MMItems.cheese) {
			playLivingSound();
            stack.consumeItem(player);

			return true;
		} else return false;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		EntityPlayer player = world.getClosestPlayerToEntity(this, 8.0d);
		if (player != null && player.getHeldItem() != null) {
			if (player.getHeldItem().getItem() == MMItems.cheese) {
				faceEntity(player, 100.0f, 10.0f);
			}
		}
	}
}
