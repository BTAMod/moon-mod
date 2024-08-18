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

@Mixin(value = EntityLiving.class, remap = false)
public abstract class EntityLivingMixin extends Entity {
    @Unique
    private final EntityLiving thisAs = (EntityLiving) (Object) this;

    @Unique
    private double gravityScale;

    public EntityLivingMixin(World world) {
        super(world);
    }

    @Inject(method = "moveEntityWithHeading(FF)V", at = @At("HEAD"))
    private void moonMod_getGravity(float moveStrafing, float moveForward, CallbackInfo cbi){
        gravityScale = 1f;
        if (world.getWorldType() instanceof ISpace && !(thisAs instanceof EntityUFO)){
            gravityScale = ((ISpace)world.worldType).getGravityScalar();
        }
    }

    @Redirect(method = "moveEntityWithHeading(FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/EntityLiving;yd:D", opcode = Opcodes.PUTFIELD))
    private void moonMod_setEntityGravity(EntityLiving entity, double yd) { // A probably terrible way of modifying gravity by a scalar
        double offset = -(yd - this.yd);
        if ((0.021 > offset && offset > 0.019) || (0.081 > offset && offset > 0.079))  // If falling in water or in air
            entity.yd -= offset * gravityScale;
        else // Else regular behavior
            if ((-0.251 < yd && yd < -0.249)) entity.yd = yd * gravityScale; // Terminal velocity.
            else entity.yd = yd; // Regular behavior.
    }


    @ModifyVariable(method = "causeFallDamage(F)V", at = @At(value = "STORE"), ordinal = 0)
    private int moonMod_fallDamage(int i){
        return (int)((i * gravityScale) - (3/gravityScale) + 3);
    }
}
