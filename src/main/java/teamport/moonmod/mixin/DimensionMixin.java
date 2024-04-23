package teamport.moonmod.mixin;

import net.minecraft.core.world.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.moonmod.MMConfig;
import teamport.moonmod.MoonMod;
import teamport.moonmod.block.MMBlocks;

import static teamport.moonmod.world.ModDimensions.dimensionMoon;

@Mixin(value = Dimension.class, remap = false)
public abstract class DimensionMixin {


	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void addDimension(CallbackInfo ci){
		dimensionMoon = new Dimension("moon", Dimension.overworld, 3f, MMBlocks.portalMoon.id).setDefaultWorldType(MoonMod.MOON_WORLD);
		Dimension.registerDimension(MMConfig.cfg.getInt("IDs.dimension"), dimensionMoon);
	}
}
