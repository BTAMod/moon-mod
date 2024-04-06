package teamport.moonmod.mixin;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.moonmod.entity.EntityUFO;
import teamport.moonmod.item.MMItems;
import teamport.moonmod.world.ISpace;

@Mixin(value = EntityLiving.class, remap = false)
public abstract class EntityLivingMixin extends Entity {
	@Unique
	private double gravityScale;
	@Unique
	private final EntityLiving thisAs = (EntityLiving) (Object) this;

	public EntityLivingMixin(World world) {
		super(world);
	}

	@Unique
	private boolean moonMod_hasSuit() {
		for (int i = 0; i < 4; i++) {
			if (!(thisAs instanceof EntityPlayer) || ((EntityPlayer) thisAs).inventory.armorInventory[i] == null)
				return false;
		}

		return ((EntityPlayer) thisAs).inventory.armorInventory[3].itemID == MMItems.spaceHelmet.id &&
			((EntityPlayer) thisAs).inventory.armorInventory[2].itemID == MMItems.spaceSuit.id &&
			((EntityPlayer) thisAs).inventory.armorInventory[1].itemID == MMItems.spacePants.id &&
			((EntityPlayer) thisAs).inventory.armorInventory[0].itemID == MMItems.spaceBoots.id;
	}

	@Redirect(method = "trySuffocate", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/EntityLiving;isUnderLiquid(Lnet/minecraft/core/block/material/Material;)Z"))
	private boolean moonMod_suffocate(EntityLiving living, Material material){
		boolean shouldSuffocate = false;

		if (living.world.getWorldType() instanceof ISpace){
			shouldSuffocate = ((ISpace) living.world.getWorldType()).suffocate();
		}

		if (!moonMod_hasSuit() && (shouldSuffocate || isUnderLiquid(material)) && !(living instanceof EntityUFO)) {
			--this.airSupply;
			if (this.airSupply == -20)
				this.airSupply = 0;

			return true;
		}
        return false;
    }


	@Inject(method = "moveEntityWithHeading(FF)V", at = @At("HEAD"))
	private void moonMod_getGravity(float moveStrafing, float moveForward, CallbackInfo cbi){
		gravityScale = 1f;
		if (world.getWorldType() instanceof ISpace && !(thisAs instanceof EntityUFO)){
			gravityScale = ((ISpace)world.worldType).getGravityScalar();
		}
	}

	@Redirect(method = "moveEntityWithHeading(FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/EntityLiving;yd:D", opcode = Opcodes.PUTFIELD))
	private void moonMod_setEntityGravity(EntityLiving entity, double yd) { //Probably terrible way of modifying gravity by a scalar
		double offset = -(yd - this.yd);
		if ((0.021 > offset && offset > 0.019) || (0.081 > offset && offset > 0.079)){ // If falling in water or in air
			entity.yd -= offset * gravityScale;
		} else if ((-0.251 < yd && yd < -0.249)) { // Terminal velocity
			entity.yd = yd * gravityScale;
		} else { // Else regular behavior
			entity.yd = yd;
		}
	}


	@ModifyVariable(method = "causeFallDamage(F)V", at = @At(value = "STORE"), ordinal = 0)
	private int moonMod_fallDamage(int i){
		return (int)((i * gravityScale) - (3/gravityScale) + 3);
	}
}
