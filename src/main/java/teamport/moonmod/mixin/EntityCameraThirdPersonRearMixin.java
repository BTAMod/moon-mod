package teamport.moonmod.mixin;

import teamport.moonmod.entity.EntityRocket;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.client.render.camera.EntityCamera;
import net.minecraft.client.render.camera.EntityCameraThirdPersonRear;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = EntityCameraThirdPersonRear.class, remap = false)
public abstract class EntityCameraThirdPersonRearMixin extends EntityCamera {
    public EntityCameraThirdPersonRearMixin(Minecraft mc, EntityLiving entity) {
        super(mc, entity);
    }

    @ModifyConstant(method = "getCameraDistance", constant = @Constant(intValue = 5))
    private int getNumSteps(int og) {
        return mc.thePlayer.vehicle instanceof EntityRocket ? 15 : 5;
	}
}
