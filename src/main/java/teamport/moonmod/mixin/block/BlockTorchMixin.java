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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.moonmod.world.ISpace;

import java.util.Random;

@Mixin(value = BlockTorch.class, remap = false)
public abstract class BlockTorchMixin extends Block {

	@Unique
	BlockTorch torch = (BlockTorch) (Object) this;

	public BlockTorchMixin(String key, int id, Material material) {
		super(key, id, material);
	}

	@Inject(method = "updateTick", at = @At("TAIL"), cancellable = true)
	private void moonMod_updateTick(World world, int x, int y, int z, Random rand, CallbackInfo ci) {
		if (world.worldType instanceof ISpace && ((ISpace) world.worldType).suffocate()) {
			torch.dropBlockWithCause(world, EnumDropCause.WORLD, x, y, z, 0, null);
			world.setBlockWithNotify(x, y, z, 0);

			ci.cancel();
		}
	}
}
