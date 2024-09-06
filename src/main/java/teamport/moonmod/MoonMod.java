package teamport.moonmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import com.mojang.nbt.CompoundTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.moonmod.block.MMBlocks;
import teamport.moonmod.entity.EntityUFO;
import teamport.moonmod.entity.EntityRocket;
import teamport.moonmod.entity.render.*;
import teamport.moonmod.item.MMItems;
import teamport.moonmod.world.BiomeProviderMoon;
import teamport.moonmod.world.WorldTypeMoon;
import teamport.moonmod.world.biome.MoonBiomes;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import org.useless.dragonfly.helper.ModelHelper;

public class MoonMod implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint {
	public static final String MOD_ID = "moonmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static WorldType MOON_WORLD;
	public static Dimension dimensionMoon;

    public static void dimensionShift(int dim) {
		Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
		Dimension lastDim = Dimension.getDimensionList().get(mc.thePlayer.dimension);
		Dimension newDim = Dimension.getDimensionList().get(dim);
		System.out.println("Switching to dimension \"" + newDim.getTranslatedName() + "\"!! (" + dim + ")");
		mc.thePlayer.dimension = dim;
		double d = mc.thePlayer.x;
		double d1 = mc.thePlayer.z;
		double newY = ((EntityRocket) mc.thePlayer.vehicle).y;
		d *= Dimension.getCoordScale(lastDim, newDim);
		d1 *= Dimension.getCoordScale(lastDim, newDim);
		mc.thePlayer.moveTo(d, newY, d1, mc.thePlayer.yRot, mc.thePlayer.xRot);
		CompoundTag rocket = new CompoundTag();
		((EntityRocket) mc.thePlayer.vehicle).saveWithoutId(rocket);
		//mc.theWorld.setEntityDead(((EntityRocket) mc.thePlayer.vehicle));
		mc.theWorld.setEntityDead(mc.thePlayer);
		mc.thePlayer.removed = false;
		if (mc.thePlayer.isAlive()) {
			mc.theWorld.updateEntityWithOptionalForce(mc.thePlayer, false);
		}

		World world;
		world = new World(mc.theWorld, newDim);
		if (newDim == lastDim.homeDim) {
			mc.changeWorld(world, "Leaving " + lastDim.getTranslatedName(), mc.thePlayer);
		} else {
			mc.changeWorld(world, "Entering " + newDim.getTranslatedName(), mc.thePlayer);
		}

		mc.thePlayer.world = mc.theWorld;
		if (mc.thePlayer.isAlive()) {
			mc.thePlayer.moveTo(d, newY, d1, mc.thePlayer.yRot, mc.thePlayer.xRot);
			EntityRocket rocketEntity = new EntityRocket(world);
			rocketEntity.load(rocket);
			rocketEntity.moveTo(d, newY, d1, rocketEntity.yRot, rocketEntity.xRot);
			world.updateEntityWithOptionalForce(mc.thePlayer, false);
			world.entityJoinedWorld(rocketEntity);
			mc.thePlayer.startRiding(rocketEntity);
			rocketEntity.state = 3;
		}
	}


	@Override
	public void onInitialize() {
	  LOGGER.info("MoonMod has been initialized. Have fun, brave astronaut!");
	}

	@Override
	public void beforeClientStart() {
		SoundHelper.addSound(MOD_ID, "yippee.wav");
	}

	@Override
	public void afterClientStart() {

	}

	@Override
	public void beforeGameStart() {
		MMConfig.cfg.loadConfig();

		new MoonBiomes().initializeBiomes();
		BiomeProviderMoon.init();
		MOON_WORLD = WorldTypes.register(MoonMod.MOD_ID + ":moon", new WorldTypeMoon("moonmod.worldtype.moon"));

		new MMBlocks().initializeBlocks();
		new MMItems().initializeItems();

		EntityHelper.createEntity(EntityUFO.class, MMConfig.cfg.getInt("IDs.ufo"), "ufo",
		    () -> new UFORenderer(ModelHelper.getOrCreateEntityModel(
		        MOD_ID,
				"entity/ufo.json",
				UFOModel.class
			))
		);
		EntityHelper.createEntity(EntityRocket.class, MMConfig.cfg.getInt("IDs.ufo") + 1, "rocketman",
		    () -> new RocketRenderer()
		);


	}

	@Override
	public void afterGameStart() {
		MobInfoRegistry.register(EntityUFO.class, "moon.ufo.name", "moon.ufo.desc", 10, 10, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(Item.dustRedstone), 1.0f, 0 ,2)});
	}
}
