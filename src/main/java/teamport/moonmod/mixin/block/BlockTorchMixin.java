package teamport.moonmod.mixin.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTorch;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.moonmod.world.ISpace;

@Mixin(value = BlockTorch.class, remap = false)
public abstract class BlockTorchMixin extends Block {
	@Unique
	BlockTorch torch = (BlockTorch) (Object) this;

	public BlockTorchMixin(String key, int id, Material material) {
		super(key, id, material);
	}

	@Inject(method = "canPlaceBlockAt", at = @At("RETURN"), cancellable = true)
	private void moonmod_canPlaceBlockAt(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
	    if (world.worldType instanceof ISpace && ((ISpace) world.worldType).suffocate() && this.id == Block.torchCoal.id) {
			cir.setReturnValue(false);
		}
	}
}
