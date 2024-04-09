package teamport.moonmod.mixin;

import net.minecraft.client.render.LoadingScreenRenderer;
import net.minecraft.core.world.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.moonmod.MoonMod;
import teamport.moonmod.world.ModDimensions;

@Mixin(value = LoadingScreenRenderer.class, remap = false)
public class LoadingScreenRendererMixin {
	@Shadow
	private String backgroundPath;

	@Inject(method = "updateLoadingBackground(Lnet/minecraft/core/world/Dimension;)V", at = @At("HEAD"), cancellable = true)
	private void customBackground(Dimension dimension, CallbackInfo ci){
		if (dimension == ModDimensions.dimensionMoon){
			this.backgroundPath = "/assets/moonmod/block/regolith.png";
			ci.cancel();
		}
	}
}
