package teamport.moonmod.entity;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import teamport.moonmod.block.MMBlocks;

public class EntityUFO extends EntityAnimal {

	public EntityUFO(World world) {
		super(world);
		skinName = "ufo";
	}

	@Override
	public String getLivingSound() {
		return null;
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
	protected int getDropItemId() {
		return Item.dustRedstone.id;
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
}
