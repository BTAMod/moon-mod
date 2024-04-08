package teamport.moonmod.mixin;

import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.client.gui.hud.OxygenBarComponent;
import net.minecraft.core.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = OxygenBarComponent.class, remap = false)
public abstract class OxygenBarComponentMixin extends MovableHudComponent {

	public OxygenBarComponentMixin(String key, int xSize, int ySize, Layout layout) {
		super(key, xSize, ySize, layout);
	}

	@Redirect(method = "isVisible", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/EntityPlayerSP;isUnderLiquid(Lnet/minecraft/core/block/material/Material;)Z"))
	private boolean moonMod_isVisible(EntityPlayerSP instance, Material material) {
		return instance.airSupply < 300;
	}
}
