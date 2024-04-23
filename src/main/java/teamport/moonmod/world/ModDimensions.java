package teamport.moonmod.world;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import teamport.moonmod.MMConfig;
import teamport.moonmod.MoonMod;
import teamport.moonmod.block.MMBlocks;

public class ModDimensions {
	public static Dimension dimensionMoon = new Dimension("moon", Dimension.overworld, 3f, MMBlocks.portalMoon.id).setDefaultWorldType(MoonMod.MOON_WORLD);
	static
	{
		Dimension.registerDimension(MMConfig.cfg.getInt("IDs.dimension"), dimensionMoon);
	}
	public static void register() {}

	public static void dimensionShift(int targetDimension){
		Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;

		Dimension lastDim = Dimension.getDimensionList().get(player.dimension);
		Dimension newDim = Dimension.getDimensionList().get(targetDimension);
		System.out.println("Switching to dimension \"" + newDim.getTranslatedName() + "\"!!");
		player.dimension = targetDimension;
		world.setEntityDead(player);
		player.removed = false;
		player.moveTo(player.x * Dimension.getCoordScale(lastDim, newDim), player.y, player.z * Dimension.getCoordScale(lastDim, newDim), player.yRot, player.xRot);

		if (player.isAlive()) {
			world.updateEntityWithOptionalForce(player, false);
		}

		world = new World(world, newDim);
		if (newDim == lastDim.homeDim) {
			mc.changeWorld(world, "Leaving " + lastDim.getTranslatedName(), player);
		} else {
			mc.changeWorld(world, "Entering " + newDim.getTranslatedName(), player);
		}
		player.world = world;
		if (player.isAlive()) {
			player.moveTo(player.x, world.worldType.getMaxY()+1, player.z, player.yRot, player.xRot);
			world.updateEntityWithOptionalForce(player, false);
		}

	}
}
