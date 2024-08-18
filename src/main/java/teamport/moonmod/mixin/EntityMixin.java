package teamport.moonmod.mixin;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.moonmod.block.MMBlocks;
import teamport.moonmod.entity.EntityUFO;
import teamport.moonmod.item.MMItems;
import teamport.moonmod.world.ISpace;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixin {
	@Shadow double x;
	@Shadow double y;
	@Shadow double z;
	@Shadow World world;
	@Shadow int airSupply;

	@Unique
	private boolean moonMod_hasSuit() {
        if (!((Object) this instanceof EntityLiving)) return false;
	    EntityLiving thisAs = (EntityLiving) (Object) this;
		for (int i = 0; i < 4; i++) {
			if (!(thisAs instanceof EntityPlayer) || ((EntityPlayer) thisAs).inventory.armorInventory[i] == null)
				return false;
		}

		return ((EntityPlayer) thisAs).inventory.armorInventory[3].itemID == MMItems.spaceHelmet.id &&
			((EntityPlayer) thisAs).inventory.armorInventory[2].itemID == MMItems.spaceSuit.id &&
			((EntityPlayer) thisAs).inventory.armorInventory[1].itemID == MMItems.spacePants.id &&
			((EntityPlayer) thisAs).inventory.armorInventory[0].itemID == MMItems.spaceBoots.id;
	}

    @Inject(method = "isUnderLiquid", at = @At(value = "RETURN"), cancellable = true)
    public void moonMod_suffocate(Material material, CallbackInfoReturnable<Boolean> cir) {
        if (material != Material.water) return;
        if (!((Object) this instanceof EntityLiving)) return;
        EntityLiving living = (EntityLiving) (Object) this;
		boolean shouldSuffocate = false;
		boolean isInside = false;

		for (int _x = (int) (x - 3); _x < x + 3; _x++) {
			for (int _y = (int) (y - 6); _y < y; _y++) {
				for (int _z = (int) (z - 3); _z < z + 3; _z++) {
					if (world.getBlockId(_x, _y, _z) == MMBlocks.woolInvincible.id) isInside = true;
				}
			}
		}

		if (living.world.getWorldType() instanceof ISpace){
			shouldSuffocate = ((ISpace) living.world.getWorldType()).suffocate() && !isInside;
		}

		if (!moonMod_hasSuit() && (shouldSuffocate || cir.getReturnValue() && !living.canBreatheUnderwater()) && !(living instanceof EntityUFO)) {
			if (airSupply-- <= -20) airSupply = 0;

			cir.setReturnValue(true);
			return;
		}
        cir.setReturnValue(false);
        return;
    }
}
